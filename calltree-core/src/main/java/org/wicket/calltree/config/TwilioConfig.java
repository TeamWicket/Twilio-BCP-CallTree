package org.wicket.calltree.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Alessandro Arosio - 03/04/2020 22:00
 */
@Configuration
@PropertySource("classpath:twilio.properties")
@Getter
@Slf4j
public class TwilioConfig {

    @Value("${account.sid}")
    private String accountSid;

    @Value("${auth.token}")
    private String authToken;
}
