package org.wicket.calltree.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wicket.calltree.model.Recipient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 11/04/2020 14:10
 */
@Service
@Slf4j
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
    public String replyToReceivedSms() {
//        post("/sms", (req, res) -> {
//            res.type("application/xml");
            Body body = new Body
                    .Builder("The Robots are coming! Head for the hills!")
                    .build();
            com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message.Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse.Builder()
                    .message(sms)
                    .build();
            log.info("TwiML to xml: {}", twiml.toXml());
            return twiml.toXml();
//        });
    }
}
