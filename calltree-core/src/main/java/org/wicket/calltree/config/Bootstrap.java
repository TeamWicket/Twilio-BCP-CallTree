package org.wicket.calltree.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.models.InboundSms;
import org.wicket.calltree.models.OutboundSms;
import org.wicket.calltree.repository.BcpEventRepository;
import org.wicket.calltree.repository.ContactRepository;
import org.wicket.calltree.repository.InboundSmsRepository;
import org.wicket.calltree.repository.OutBoundSmsRepository;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 05/04/2020 17:50
 */
@Configuration
@RequiredArgsConstructor
public class Bootstrap {
    private final ContactRepository contactRepository;
    private final ContactMapper mapper;
    private final InboundSmsRepository inboundRepo;
    private final OutBoundSmsRepository outboundRepo;
    private final BcpEventRepository bcpEventRepository;

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

        BcpEvent bcpEvent = new BcpEvent(null, "TEST-EVENT",
                ZonedDateTime.parse("2020-04-14T18:42:06.000Z"), TWILIO_NUMBER, null);
        BcpEvent persistedEvent = bcpEventRepository.save(bcpEvent);

        InboundSms inbound = new InboundSms();
        inbound.setBody("Hello!");
        inbound.setFromContactNumber("+444");
        inbound.setToTwilioNumber(TWILIO_NUMBER);
        inbound.setFromCountry("GB");
        inbound.setSmsStatus("received");
        inbound.setTimestamp("2020-04-14T19:44:50.851113+01:00[Europe/London]");

        inboundRepo.save(inbound);

        OutboundSms outbound = new OutboundSms();
        outbound.setBody("testing sms persistence");
        outbound.setDateCreated("14 April 2020 at 18:43:25 UTC");
        outbound.setToNumber("+444");
        outbound.setStatus("queued");
        outbound.setFromNumber(TWILIO_NUMBER);
        outbound.setBcpEvent(persistedEvent);

        outboundRepo.save(outbound);

    }
}
