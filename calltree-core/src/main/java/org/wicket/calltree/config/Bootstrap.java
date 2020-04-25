package org.wicket.calltree.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.models.BcpEventSms;
import org.wicket.calltree.models.TwilioNumber;
import org.wicket.calltree.repository.*;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 05/04/2020 17:50
 */
@Configuration
@RequiredArgsConstructor
@Profile({"!live"})
public class Bootstrap {
    private final ContactRepository contactRepository;
    private final ContactMapper mapper;
    private final BcpEventSmsRepository bcpEventSmsRepository;
    private final BcpEventRepository bcpEventRepository;
    private final TwilioNumberRepository twilioNumberRepository;

    private static final String TWILIO_NUMBER = "+0132456";

    @PostConstruct
    private void populateDatabase() {
        // GoF, authors of Design Patterns

        ContactDto contactOne = new ContactDto();
        contactOne.setFirstName("Erich");
        contactOne.setLastName("Gamma");
        contactOne.setCallingOption(List.of(CallingOption.WHATSAPP));
        contactOne.setPhoneNumber("+123");
        contactOne.setRole(Role.CHAMPION);

        ContactDto contactTwo = new ContactDto();
        contactTwo.setFirstName("Richard");
        contactTwo.setLastName("Helm");
        contactTwo.setCallingOption(List.of(CallingOption.SMS));
        contactTwo.setPhoneNumber("+456");
        contactTwo.setRole(Role.MANAGER);
        contactTwo.setPointOfContactId(1L);

        ContactDto contactThree = new ContactDto();
        contactThree.setFirstName("Ralph");
        contactThree.setLastName("Johnson");
        contactThree.setCallingOption(List.of(CallingOption.SMS, CallingOption.WHATSAPP));
        contactThree.setPhoneNumber("+789");
        contactThree.setRole(Role.LEADER);
        contactThree.setPointOfContactId(2L);

        ContactDto contactFour = new ContactDto();
        contactFour.setFirstName("John");
        contactFour.setLastName("Vlissides");
        contactFour.setCallingOption(List.of(CallingOption.SMS, CallingOption.WHATSAPP));
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
                ZonedDateTime.parse("2020-04-14T18:42:06.000Z"), persistedNumber, null);
        BcpEvent persistedEvent = bcpEventRepository.save(bcpEvent);

        BcpEventSms eventSms = new BcpEventSms();
        eventSms.setBcpEvent(persistedEvent);
        eventSms.setOutboundMessage("testing sms persistence");
        eventSms.setDateCreated("14 April 2020 at 18:43:25 UTC");
        eventSms.setSmsStatus(SmsStatus.RECEIVED);
        eventSms.setRecipientNumber("+444");
        eventSms.setRecipientCountry("GB");
        eventSms.setRecipientMessage("Hello!");
        eventSms.setRecipientTimestamp("2020-04-14T19:44:50.851113+01:00[Europe/London]");

        bcpEventSmsRepository.save(eventSms);

    }
}
