package org.wicket.calltree.services;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.model.Recipient;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 11/04/2020 16:01
 */
@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("body to string: {}", body.toString());
        log.info("body class: {}", body.getClass());
        log.info("Body properties {}", Arrays.toString(body.getClass().getDeclaredFields()));
        return twilioService.replyToReceivedSms();
    }
}
