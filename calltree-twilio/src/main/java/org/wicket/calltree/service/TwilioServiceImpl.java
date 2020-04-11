package org.wicket.calltree.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wicket.calltree.model.Recipient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 11/04/2020 14:10
 */
@Service
public class TwilioServiceImpl implements TwilioService {

    @Value("${account.sid}")
    private String accountSid;

    @Value("${auth.token}")
    private String authToken;

    @Override
    public List<Message> sendSms(List<Recipient> recipients) {
        Twilio.init(accountSid, authToken);
        return recipients.stream()
                .map(recipient ->
                        Message.creator(
                                recipient.getReceiver(),
                                recipient.getSender(),
                                recipient.getMessage()
                        ).create()
                )
                .collect(Collectors.toList());
    }
}
