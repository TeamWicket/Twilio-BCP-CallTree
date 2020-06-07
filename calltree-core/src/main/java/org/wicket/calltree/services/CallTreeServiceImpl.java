package org.wicket.calltree.services;

import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.*;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.exceptions.BcpEventException;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.model.Recipient;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
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
    private final TwilioNumberService numberService;
    private final JmsTemplate jmsTemplate;

    @NotNull
    @Override
    public long initiateCalls(@NotNull BcpStartRequest bcpStartRequest) {
        TwilioNumberDto numberDto = numberService.getNumberById(bcpStartRequest.getTwilioNumberId());
        if (!numberDto.getIsAvailable()) {
            throw new BcpEventException("This number is already being used for an event");
        }
        BcpEventDto event = saveNewEvent(bcpStartRequest);

        var recipientList = contactService.getCalltreeUntilRole(bcpStartRequest.getToRoles()).stream()
                .map(c -> new Recipient(new PhoneNumber(c.getPhoneNumber()), bcpStartRequest.getText()))
                .collect(Collectors.toList());

        var messages = twilioService.sendSms(recipientList);

        var responses = messages.stream()
                .map(v -> {
                    Response response = mapper.messageToResponse(v);
                    response.setBcpEvent(event);
                    response.setDateSent(ZonedDateTime.now().toString());
                    if (Objects.equals(response.getStatus(), "failed")) {
                        response.setSmsStatus(SmsStatus.ERROR);
                    } else {
                        response.setSmsStatus(SmsStatus.SENT);
                    }
                    return response;
                })
                .collect(Collectors.toList());

        bcpMessageService.saveSmsFromResponse(responses);

        return event.getId();
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
        TwilioNumberDto twilioNumber = bcpEventDto.getTwilioNumber();
        twilioNumber.setIsAvailable(true);

        numberService.saveNumber(twilioNumber);
        bcpEventService.saveEvent(bcpEventDto);
    }

    @NotNull
    @Override
    public Page<BcpEvent> pagedEvents(int page, int size) {
        return bcpEventService.getPagedEvents(page, size);
    }

    private BcpMessageDto smsParser(String body) {
        if (body == null) {
            throw new RuntimeException("body of incoming sms is null");
        }

        String fromPhone = StringUtils.substringBetween(body, "From=", "&").replace("%2B", "+");
        String toPhone = StringUtils.substringBetween(body, "To=", "&").replace("%2B", "+");
        if (StringUtils.isEmpty(fromPhone) || StringUtils.isEmpty(toPhone)) {
            throw new RuntimeException("Message to/from unidentified number");
        }

        BcpMessageDto message = bcpMessageService.findActiveMessagesByRecipientNumber(fromPhone, toPhone);
        if (message.getId() == null) {
            throw new RuntimeException(String.format("No active event for number: %s", fromPhone));
        }

        message.setSmsStatus(SmsStatus.RECEIVED);
        message.setRecipientMessage(URLDecoder.decode(StringUtils.substringBetween(body, "Body=", "&"), StandardCharsets.UTF_8));
        message.setRecipientCountry(StringUtils.substringBetween(body, "FromCountry=", "&"));
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
        TwilioNumberDto numberDto = numberService.getNumberById(request.getTwilioNumberId());
        numberDto.setIsAvailable(false);
        numberService.saveNumber(numberDto);
        var event = new BcpEventDto(null, request.getEventName(),
                ZonedDateTime.now().toString(), numberDto, true, null);
        return bcpEventService.saveEvent(event);
    }
}
