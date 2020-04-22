package org.wicket.calltree.service;

import com.twilio.rest.api.v2010.account.Message;
import org.wicket.calltree.model.Recipient;

import java.util.List;

/**
 * @author Alessandro Arosio - 11/04/2020 14:06
 */
public interface TwilioService {
    List<Message> sendSms(List<Recipient> recipients);

    String replyToReceivedSms(String reply);

    List<String> getTwilioNumbers();
}
