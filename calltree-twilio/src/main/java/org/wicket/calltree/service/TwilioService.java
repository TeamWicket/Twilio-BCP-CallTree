package org.wicket.calltree.service;

import com.twilio.rest.api.v2010.account.Message;
import org.wicket.calltree.model.Recipient;

import java.util.List;

public interface TwilioService {
    List<Message> sendSms(List<Recipient> recipients);

    String replyToReceivedSms(String reply);

    Double getBalance();
}
