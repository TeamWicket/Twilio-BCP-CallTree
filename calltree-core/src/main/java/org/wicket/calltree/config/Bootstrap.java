package org.wicket.calltree.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.models.BcpMessage;
import org.wicket.calltree.models.TwilioNumber;
import org.wicket.calltree.repository.BcpEventRepository;
import org.wicket.calltree.repository.BcpMessageRepository;
import org.wicket.calltree.repository.ContactRepository;
import org.wicket.calltree.repository.TwilioNumberRepository;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Profile({"!live"})
public class Bootstrap {
    private final ContactRepository contactRepository;
    private final ContactMapper mapper;
    private final BcpMessageRepository bcpMessageRepository;
    private final BcpEventRepository bcpEventRepository;
    private final TwilioNumberRepository twilioNumberRepository;

    private static final String TWILIO_NUMBER = "+0132456";

    @PostConstruct
    private void populateDatabase() {
        // GoF, authors of Design Patterns

        ContactDto contactOne = new ContactDto();
        contactOne.setFirstName("Erich");
        contactOne.setLastName("Gamma");
        contactOne.setPhoneNumber("+123");
        contactOne.setRole(Role.CHAMPION);

        ContactDto contactTwo = new ContactDto();
        contactTwo.setFirstName("Richard");
        contactTwo.setLastName("Helm");
        contactTwo.setPhoneNumber("+456");
        contactTwo.setRole(Role.MANAGER);
        contactTwo.setPointOfContactId(1L);

        ContactDto contactThree = new ContactDto();
        contactThree.setFirstName("Ralph");
        contactThree.setLastName("Johnson");
        contactThree.setPhoneNumber("+789");
        contactThree.setRole(Role.LEADER);
        contactThree.setPointOfContactId(2L);

        ContactDto contactFour = new ContactDto();
        contactFour.setFirstName("John");
        contactFour.setLastName("Vlissides");
        contactFour.setPhoneNumber("+444");
        contactFour.setRole(Role.REPORTER);
        contactFour.setPointOfContactId(3L);

        contactRepository.saveAll(List.of(contactOne, contactTwo, contactThree, contactFour)
                .stream()
                .map(mapper::dtoToContact)
                .collect(Collectors.toList()));

        TwilioNumber twilioNumber = new TwilioNumber(null, TWILIO_NUMBER, true);
        TwilioNumber persistedNumber = twilioNumberRepository.save(twilioNumber);

        BcpEvent bcpEvent = new BcpEvent(null, "TEST-EVENT",
                ZonedDateTime.parse("2020-04-14T18:42:06.000Z"), persistedNumber, false, null);
        BcpEvent persistedEvent = bcpEventRepository.save(bcpEvent);


        BcpEvent bcpEvent2 = new BcpEvent(null, "TEST-EVENT-QUEUE",
                ZonedDateTime.parse("2020-04-20T18:42:06.000Z"), persistedNumber, true, null);
        BcpEvent persistedEvent2 = bcpEventRepository.save(bcpEvent2);

        BcpMessage eventSms = new BcpMessage();
        eventSms.setBcpEvent(persistedEvent);
        eventSms.setOutboundMessage("testing sms persistence");
        eventSms.setDateCreated("14 April 2020 at 18:43:25 UTC");
        eventSms.setSmsStatus(SmsStatus.RECEIVED);
        eventSms.setRecipientNumber("+444");
        eventSms.setRecipientCountry("GB");
        eventSms.setRecipientMessage("Hello!");
        eventSms.setRecipientTimestamp("2020-04-14T19:44:50.851113+01:00[Europe/London]");

        bcpMessageRepository.save(eventSms);

        BcpMessage eventSms2 = new BcpMessage();
        eventSms2.setBcpEvent(persistedEvent2);
        eventSms2.setOutboundMessage("testing sms persistence");
        eventSms2.setDateCreated("14 April 2020 at 18:43:25 UTC");
        eventSms2.setSmsStatus(SmsStatus.SENT);
        eventSms2.setRecipientNumber("+444");

        bcpMessageRepository.save(eventSms2);

        BcpMessage eventSms3 = new BcpMessage();
        eventSms3.setBcpEvent(persistedEvent);
        eventSms3.setOutboundMessage("testing sms persistence");
        eventSms3.setDateCreated("14 April 2020 at 18:53:25 UTC");
        eventSms3.setSmsStatus(SmsStatus.SENT);
        eventSms3.setRecipientNumber("+444");
        eventSms3.setRecipientTimestamp("2020-04-14T19:48:50.851113+01:00[Europe/London]");
        eventSms3.setSmsStatus(SmsStatus.RECEIVED);

        bcpMessageRepository.save(eventSms3);

    }
}
