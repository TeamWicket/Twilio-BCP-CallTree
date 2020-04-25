package org.wicket.calltree.services;

import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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
                    if (Objects.equals(response.getStatus() ,"failed")) {
                        response.setSmsStatus(SmsStatus.ERROR);
                    } else {
                        response.setSmsStatus(SmsStatus.SENT);
                    }
                    return response;
                })
                .collect(Collectors.toList());

        smsService.saveOutboundSms(responses);

        return responses;
    }

    @NotNull
    @Override
    public String replyToSms(@NotNull String body) {

        //@todo
        /*InboundSmsDto inboundSmsDto = smsParser(body);
        ContactDto contactDto = contactService.fetchContactByPhoneNumber(inboundSmsDto.getFromContactNumber());
        ContactDto manager = contactService.getContact(contactDto.getPointOfContactId());
        String reply = buildReply(contactDto, manager);

        smsService.saveInboundSms(inboundSmsDto);*/

        return "string";//twilioService.replyToReceivedSms(reply);
    }

    @NotNull
    @Override
    public List<String> fetchTwilioNumbers() {
        return twilioService.getTwilioNumbers();
    }

    @Override
    public void endEvent(TwilioNumberDto twilioNumber) {
        smsService.terminateEvent(twilioNumber.getTwilioNumber());
        bcpEventService.deleteEventByTwilioNumber(twilioNumber.getId());
    }

    @NotNull
    @Override
    public List<BcpEventDto> checkEvent() {
        return bcpEventService.getAllEvents();
    }

    @NotNull
    @Override
    public BcpStats calculateStats(@NotNull TwilioNumberDto twilioNumber, long minutes) {
        BcpEventDto event = bcpEventService.getEventByNumber(twilioNumber.getId());
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

        return bcpStats;
    }

    @NotNull
    @Override
    public List<BcpContactStats> contactsStats(@NotNull String twilioNumber) {
        /*List<InboundSmsDto> inboundResponses = smsService.findInboundMessagesByTwilioNumber(twilioNumber);
        List<BcpEventSmsDto> outboundResponses = smsService.findOutboundMessagesByTwilioNumber(twilioNumber);
        val resultList = new ArrayList<BcpContactStats>();

        outboundResponses.forEach(out -> {
            Optional<InboundSmsDto> match = inboundResponses.stream()
                    .filter(in -> out.getRecipientNumber().equals(in.getFromContactNumber()))
                    .findFirst();
            match.ifPresent(inSms -> {
                val stats = new BcpContactStats(out.getBcpEvent().getTwilioNumber().getTwilioNumber(),
                        out.getOutboundMessage(),
                        out.getDateCreated(),
                        out.getRecipientNumber(), inSms.getTimestamp(), inSms.getBody(), out.getBcpEvent().getEventName());
                resultList.add(stats);
            });
        });*/

        return emptyList();//@todo return with new SMS resultList;
    }

    private Double calculateResponseWithinXMinutes(String twilioNumber, Long minutes, String eventTime) {
        /*List<InboundSmsDto> inboundList = smsService.findInboundMessagesByTwilioNumber(twilioNumber);

        long numberOfReplies = inboundList.stream().filter(sms -> {
            long difference = zonedDateTimeDifference(eventTime, sms.getTimestamp(), ChronoUnit.MINUTES);
            return difference < minutes;
        }).count(); */

        //@todo implement for new SMS
        return 1d;//(inboundList.size() * 100) / (double) numberOfReplies;
    }


    private Double calculateOverallAverage(String eventTime, List<BcpEventSmsDto> response) {
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

    protected BcpEventSmsDto smsParser(String body, BcpEventSmsDto message) {
        if (body == null) {
            throw new RuntimeException("body of incoming sms is null");
        }

        String[] chunks = body.split("&");

        // mapping from the APIs. These values will not change.
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
                null, request.getTwilioNumber(), null);
        return bcpEventService.saveEvent(event);
    }
}
