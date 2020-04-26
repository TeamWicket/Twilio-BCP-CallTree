package org.wicket.calltree.services;

import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.*;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.exceptions.BcpEventException;
import org.wicket.calltree.model.BcpContactStats;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.model.BcpStats;
import org.wicket.calltree.model.Recipient;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.wicket.calltree.services.utils.TimeUtilsKt.zonedDateTimeDifference;

/**
 * @author Alessandro Arosio - 11/04/2020 16:01
 */
@Service
@RequiredArgsConstructor
public class CallTreeServiceImpl implements CallTreeService {
    private final MessageMapper mapper;
    private final TwilioService twilioService;
    private final ContactService contactService;
    private final SmsService smsService;
    private final BcpEventService bcpEventService;

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

        smsService.saveSmsFromResponse(responses);

        return responses;
    }

    @NotNull
    @Override
    public String replyToSms(@NotNull String body) {
        BcpMessageDto bcpMessageDto = smsParser(body);
        ContactDto contactDto = contactService.fetchContactByPhoneNumber(bcpMessageDto.getRecipientNumber());
        ContactDto manager = contactService.getContact(contactDto.getPointOfContactId());
        String reply = buildReply(contactDto, manager);

        smsService.saveBcpEventSms(bcpMessageDto);

        return twilioService.replyToReceivedSms(reply);
    }

    @NotNull
    @Override
    public List<String> fetchTwilioNumbers() {
        return twilioService.getTwilioNumbers();
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
    public BcpStats calculateStats(@NotNull TwilioNumberDto twilioNumber, long minutes) {
       /* BcpEventDto event = bcpEventService.getEventByNumber(twilioNumber.getId());
        List<BcpEventSmsDto> messages = smsService.findMessagesByBcpEvent(event.getId());
        String eventTime = bcpEventService.getEventByNumber(twilioNumber.getId()).getTimestamp();

        Double averageTime = calculateOverallAverage(eventTime, messages);
        Double responseBelowXMinutes = calculateResponseWithinXMinutes(twilioNumber.getTwilioNumber(), minutes, eventTime);

        BcpStats bcpStats = new BcpStats();
        bcpStats.setMessagesSent(messages.size());
        bcpStats.setMessagesReceived(messages.stream()
                .filter(m -> m.getSmsStatus().equals(SmsStatus.RECEIVED)).map(e -> 1)
                .reduce(0, Integer::sum));
        bcpStats.setAverage(averageTime);
        bcpStats.setReplyPercentageWithinXMinutes(responseBelowXMinutes);

        return bcpStats;*/
        //@todo needs reworked as part of stats story
        return new BcpStats();
    }

    @NotNull
    @Override
    public List<BcpContactStats> contactsStats(long bcpEventId) {
        List<BcpMessageDto> messages = smsService.findMessagesByBcpEvent(bcpEventId);
        val resultList = new ArrayList<BcpContactStats>();

        messages.forEach(msg -> {
            val stats = new BcpContactStats(msg.getBcpEvent().getTwilioNumber().getTwilioNumber(),
                    msg.getOutboundMessage(),
                    msg.getDateCreated(),
                    msg.getRecipientNumber(),
                    msg.getRecipientTimestamp(),
                    msg.getRecipientMessage(),
                    msg.getBcpEvent().getEventName());
            resultList.add(stats);
        });

        return resultList;
    }

    private Double calculateResponseWithinXMinutes(BcpEventDto bcpEvent, Long minutes, String eventTime) {
        List<BcpMessageDto> messages = smsService.findMessagesByBcpEvent(bcpEvent.getId()).stream()
                .filter(x -> x.getSmsStatus().equals(SmsStatus.RECEIVED))
                .collect(Collectors.toList());

        long numberOfReplies = messages.stream().filter(sms -> {
            long difference = zonedDateTimeDifference(eventTime, sms.getRecipientTimestamp(), ChronoUnit.MINUTES);
            return difference < minutes;
        }).count();

        return (messages.size() * 100) / (double) numberOfReplies;
    }


    private Double calculateOverallAverage(String eventTime, List<BcpMessageDto> response) {
        List<Long> timeRespList = response.stream()
                .map(e -> {
                    String replyTimestamp = e.getRecipientTimestamp();
                    return zonedDateTimeDifference(eventTime, replyTimestamp, ChronoUnit.MINUTES);
                }).collect(Collectors.toList());
        return timeRespList.stream()
                .mapToLong(e -> e)
                .average()
                .orElseThrow(RuntimeException::new);
    }

    protected BcpMessageDto smsParser(String body) {
        if (body == null) {
            throw new RuntimeException("body of incoming sms is null");
        }

        String[] chunks = body.split("&");

        String fromPhone = chunks[20].replace("From=", "").replace("%2B", "+");
        BcpMessageDto message = smsService.findActiveMessagesByRecipientNumber(fromPhone);
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
