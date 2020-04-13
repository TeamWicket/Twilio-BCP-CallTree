package org.wicket.calltree.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.InboundSmsDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.model.Recipient;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 11/04/2020 16:01
 */
@Service
@RequiredArgsConstructor
public class CallTreeServiceImpl implements CallTreeService {
    private final TwilioService twilioService;
    private final MessageMapper mapper;
    private final ContactService contactService;

    @NotNull
    @Override
    public List<Response> initiateCalls(@NotNull BcpStartRequest bcpStartRequest) {
        List<Recipient> recipientList = contactService.getCalltreeUntilRole(bcpStartRequest.getToRoles()).stream()
                .map(c -> new Recipient(new PhoneNumber(c.getPhoneNumber()), bcpStartRequest.getText()))
                .collect(Collectors.toList());

        List<Message> messages = twilioService.sendSms(recipientList);

        return messages.stream()
                .map(mapper::messageToResponse)
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public String replyToSms(@NotNull String body) {
        InboundSmsDto inboundSmsDto = smsParser(body);
        return twilioService.replyToReceivedSms(inboundSmsDto);
    }

    protected InboundSmsDto smsParser(String body) {
        if (body == null) {
            throw new RuntimeException("body of incoming sms is null");
        }

        String[] chunks = body.split("&");
        InboundSmsDto inboundSmsDto = new InboundSmsDto();

        // mapping from the APIs. These values will not change.
        inboundSmsDto.setToCountry(chunks[0].replace("ToCountry=", ""));
        inboundSmsDto.setSmsStatus(chunks[8].replace("SmsStatus=", ""));
        inboundSmsDto.setBody(chunks[10].replace("Body=", ""));
        inboundSmsDto.setFromCountry(chunks[11].replace("FromCountry=", ""));
        inboundSmsDto.setFrom(chunks[18].replace("From=%2B", "+"));

        return inboundSmsDto;
    }
}
