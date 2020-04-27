package org.wicket.calltree.services;

import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.BcpEventDto;
import org.wicket.calltree.dto.BcpMessageDto;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.exceptions.BcpEventException;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.model.Recipient;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 11/04/2020 16:01
 */
@Service
@RequiredArgsConstructor
public class CallTreeServiceImpl implements CallTreeService {
    private final MessageMapper mapper;
    private final TwilioService twilioService;
    private final ContactService contactService;
    private final BcpMessageService bcpMessageService;
    private final BcpEventService bcpEventService;
    private final JmsTemplate jmsTemplate;

    @NotNull
    @Override
    public List<Response> initiateCalls(@NotNull BcpStartRequest bcpStartRequest) {
        BcpEventDto event = saveNewEvent(bcpStartRequest);

        var recipientList = contactService.getCalltreeUntilRole(bcpStartRequest.getToRoles()).stream()
                .map(c -> new Recipient(new PhoneNumber(c.getPhoneNumber()), bcpStartRequest.getText()))
                .collect(Collectors.toList());

        var messages = twilioService.sendSms(recipientList);

        var responses = messages.stream()
                .map(v -> {
                    Response response = mapper.messageToResponse(v);
                    response.setBcpEvent(event);
                    if (Objects.equals(response.getStatus(), "failed")) {
                        response.setSmsStatus(SmsStatus.ERROR);
                    } else {
                        response.setSmsStatus(SmsStatus.SENT);
                    }
                    return response;
                })
                .collect(Collectors.toList());

        bcpMessageService.saveSmsFromResponse(responses);

        return responses;
    }

    @NotNull
    @Override
    public String replyToSms(@NotNull String body) {
        BcpMessageDto bcpMessageDto = smsParser(body);
        ContactDto contactDto = contactService.fetchContactByPhoneNumber(bcpMessageDto.getRecipientNumber());
        ContactDto manager = contactService.getContact(contactDto.getPointOfContactId());
        String reply = buildReply(contactDto, manager);

        bcpMessageService.saveBcpEventSms(bcpMessageDto);

        jmsTemplate.convertAndSend("EndBcpEventQueue", bcpMessageDto);

        return twilioService.replyToReceivedSms(reply);
    }

    @Override
    public void endEvent(BcpEventDto bcpEventDto) {
        bcpEventDto.setIsActive(false);
        bcpEventService.saveEvent(bcpEventDto);
    }

    @NotNull
    @Override
    public List<BcpEventDto> checkEvent() {
        return bcpEventService.getAllEvents();
    }

    @NotNull
    @Override
    public Page<BcpEvent> pagedEvents(int page, int size) {
        return bcpEventService.getPagedEvents(page, size);
    }

    protected BcpMessageDto smsParser(String body) {
        if (body == null) {
            throw new RuntimeException("body of incoming sms is null");
        }

        String[] chunks = body.split("&");

        String fromPhone = chunks[18].replace("From=", "").replace("%2B", "+");
        String toPhone = chunks[12].replace("To=", "").replace("%2B", "+");
        BcpMessageDto message = bcpMessageService.findActiveMessagesByRecipientNumber(fromPhone, toPhone);
        if (message.getId() == null) {
            throw new RuntimeException(String.format("No active event for number: %s", fromPhone));
        }

        message.setSmsStatus(SmsStatus.RECEIVED);
        message.setRecipientMessage(chunks[10].replace("Body=", ""));
        message.setRecipientCountry(chunks[0].replace("FromCountry=", ""));
        message.setRecipientTimestamp(ZonedDateTime.now().toString());

        return message;
    }

    private String buildReply(ContactDto contact, ContactDto manager) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi ");
        sb.append(contact.getFirstName());
        sb.append(" ");
        sb.append(contact.getLastName());
        sb.append(", ");
        sb.append("if you have any further query, please contact your manager ");
        sb.append(manager.getFirstName());
        sb.append(" ");
        sb.append(manager.getLastName());
        sb.append(" at ");
        sb.append(manager.getPhoneNumber());

        return sb.toString();
    }

    private BcpEventDto saveNewEvent(BcpStartRequest request) {
        BcpEventDto eventDto = bcpEventService.getEventByNumber(request.getTwilioNumber().getId());
        if (eventDto.getId() != null) {
            throw new BcpEventException("This number is being used for the event: " + eventDto.getEventName() +
                    ", please release the number before initiate a new event");
        }
        var event = new BcpEventDto(null, request.getEventName(),
                null, request.getTwilioNumber(), true, null);
        return bcpEventService.saveEvent(event);
    }
}
