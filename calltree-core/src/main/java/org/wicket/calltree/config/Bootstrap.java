package org.wicket.calltree.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.repository.ContactRepository;

import javax.annotation.PostConstruct;
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
        contactFour.setPhoneNumber("+789");
        contactFour.setRole(Role.REPORTER);
        contactFour.setPointOfContactId(3L);

        contactRepository.saveAll(List.of(contactOne, contactTwo, contactThree, contactFour)
                .stream()
                .map(mapper::dtoToContact)
                .collect(Collectors.toList()));
    }
}
