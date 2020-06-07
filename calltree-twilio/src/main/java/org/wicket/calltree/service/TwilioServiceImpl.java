package org.wicket.calltree.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Balance;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wicket.calltree.model.Recipient;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String replyToReceivedSms(String reply) {
        Body body = new Body
                .Builder(reply)
                .build();
        com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message.Builder()
                .body(body)
                .build();
        MessagingResponse twiml = new MessagingResponse.Builder()
                .message(sms)
                .build();
        return twiml.toXml();
    }

    @Override
    public Double getBalance() {
        Twilio.init(accountSid, authToken);
        Balance balance = Balance.fetcher(accountSid).fetch();
        return Double.valueOf(balance.getBalance());
    }
}
