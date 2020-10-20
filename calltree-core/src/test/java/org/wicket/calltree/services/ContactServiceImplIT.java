package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.exceptions.ContactException;

import java.util.List;
import org.wicket.calltree.models.Contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactServiceImplIT {

    @Autowired
    ContactService contactService;

    @Test
    void getAllSelectedRole_ReturnsOnlyContactsWithSelectedRole() {
        List<ContactDto> allSelectedRole = contactService.getAllSelectedRole(Role.LEADER);

        /**
         * Due to other tests adding entities to the contactService,
         * the test fails when compiled locally iff
         * ContactServiceImplIT is ran before this.
         */
        for (ContactDto contact : allSelectedRole) {
            assertEquals(Role.LEADER, contact.getRole());
        }
        assertEquals("Ralph", allSelectedRole.get(0).getFirstName());
        assertEquals("Johnson", allSelectedRole.get(0).getLastName());
    }

    @Test
    void getCalltreeUntilRole_ReturnsAllUntilLeader() {
        List<ContactDto> tree = contactService.getCalltreeUntilRole(Role.LEADER);
        List<ContactDto> reporters = contactService.getAllSelectedRole(Role.REPORTER);

        assertThat(reporters).hasSize(1);
        assertEquals(Role.REPORTER, reporters.get(0).getRole());

        assertThat(tree).hasSize(2);
        assertThat(tree).doesNotContain(reporters.get(0));
    }

    @Test
    void contactServiceGetNumContacts_ReturnsProperValue() {
        assertEquals(4, contactService.getNumContacts());
    }

    @Test
    void getCalltreeUntilRole_ReturnsEmptyForChampion() {
        List<ContactDto> tree = contactService.getCalltreeUntilRole(Role.CHAMPION);

        assertThat(tree).isEmpty();
    }

    @Test
    void getCalltreeUntilRole_ReporterReturnsAllExceptForChampion() {
        List<ContactDto> tree = contactService.getCalltreeUntilRole(Role.REPORTER);

        for (ContactDto c : tree) assertNotEquals(Role.CHAMPION, c.getRole());
    }

    @Test
    void getCalltreeUntilRole_Manager() {
        List<ContactDto> tree = contactService.getCalltreeUntilRole(Role.MANAGER);

        assertThat(tree).hasSize(1);
    }
    @Test
    void getCalltreeUntilRole_UnassignedThrowsException() {
        assertThrows(
                ContactException.class,
                () ->{ contactService.getCalltreeUntilRole(Role.DUMMY); }
        );
    }

    @Test
    void fetchContactByPhoneNumber_ReturnsContactDto() {
        ContactDto contactDto = contactService.fetchContactByPhoneNumber("+444");

        assertEquals("Vlissides", contactDto.getLastName());
    }

    @Test
    void fetchContactByPhoneNumber_ContactNotFound_WillThrowException() {
        assertThrows(ContactException.class, () -> contactService.fetchContactByPhoneNumber("+00000000000"));
    }

    @Test
    void getSortedPagedListTest() {
        List<ContactDto> contacts = contactService.getAllContacts("ASC", "id", 0, 3);

        assertEquals(3, contacts.size());
    }

    @Test
    void checkValidOrderTest() {
        List<ContactDto> contacts = contactService.getAllContacts(null, null, null, null);

        assertEquals(4, contacts.size());
        assertEquals("Erich", contacts.get(0).getFirstName());
        assertEquals("Richard", contacts.get(1).getFirstName());
        assertEquals("Ralph", contacts.get(2).getFirstName());
        assertEquals("John", contacts.get(3).getFirstName());

    }

    @Test
    void getSortedPagedList_InCorrectOrderTest() {
        List<ContactDto> contacts = contactService.getAllContacts("DESC", "id", 1, 2);
        System.out.println(contacts);
        assertEquals(2, contacts.size());
        assertEquals(2, contacts.get(0).getId());
        assertEquals(1, contacts.get(1).getId());
    }
}
