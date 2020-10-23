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

    @Test
    void getContact_ReturnsSuccessfullyForCorrectID() {
        var contacts = contactService.getAllContacts(null, null, null, null);
        var erich = contacts.get(0);
        var erichById = contactService.getContact(erich.getId());
        assertEquals(erich.getId(), erichById.getId());
        assertEquals(erich.getFirstName(), erichById.getFirstName());
        assertEquals(erich.getLastName(), erichById.getLastName());
        assertEquals(erich.getPhoneNumber(), erichById.getPhoneNumber());
        assertEquals(erich.getRole(), erichById.getRole());
    }

    @Test
    void getContact_IdNotFound_WillThrowException() {
        assertThrows(
                ContactException.class,
                () -> contactService.getContact(Long.MIN_VALUE)
        );
    }

    @Test
    void deleteContact_SuccessfulDeleteReducesCountByOne() {
        var original = contactService.getAllContacts(null, null, null, null);
        var id = original
                .get(0)
                .getId();
        contactService.deleteContact(id);
        var deleted = contactService.getAllContacts(null, null, null, null);
        assertEquals(original.size() - 1, deleted.size());
    }

    @Test
    void deleteContact_IdNotFound_NothingHappens() {
        var original = contactService.getAllContacts(null, null, null, null);
        contactService.deleteContact(Long.MIN_VALUE);
        var deleted = contactService.getAllContacts(null, null, null, null);
        assertEquals(original.size(), deleted.size());
    }

    @Test
    void saveOrUpdate_ContactFound_UpdateSuccessfully() {
        var aContact = contactService.getContact(0L);
        var newFirstName = aContact.getFirstName() + '$';
        aContact.setFirstName(newFirstName);
        contactService.saveOrUpdate(aContact);
        aContact = contactService.getContact(0L);
        assertEquals(newFirstName, aContact.getFirstName());
    }

    @Test
    void saveOrUpdate_NewContact_SaveSuccessfully() {
        var phoneNumber = "+555";
        ContactDto contact = new ContactDto();
        contact.setFirstName("Kelsey");
        contact.setLastName("HighTower");
        contact.setPhoneNumber(phoneNumber);
        contact.setRole(Role.REPORTER);
        contact.setPointOfContactId(3L);
        assertThrows(
                ContactException.class,
                () -> contactService.fetchContactByPhoneNumber(phoneNumber));
        var kelsey = contactService.saveOrUpdate(contact);
        assertEquals(
                kelsey.getId(),
                contactService.getContact(
                    kelsey.getId()
                ).getId()
        );
    }

    @Test
    void saveOrUpdate_Null_NothingHappensReturnsNull() {
        var originalSize = contactService
                .getAllContacts(null, null, null, null)
                .size();
        var result = contactService.saveOrUpdate(null);

        assertEquals(null, result);

        var newSize = contactService
                .getAllContacts(null, null, null, null)
                .size();

        assertEquals(originalSize, newSize);
    }

    @Test
    void saveList_OnlyIncludedAreSaved() {
        var before = contactService.getAllContacts(null, null, null, null);
        var kelsey = new ContactDto();
        var phoneNumber = "+555";
        kelsey.setFirstName("Kelsey");
        kelsey.setLastName("HighTower");
        kelsey.setPhoneNumber(phoneNumber);
        kelsey.setRole(Role.REPORTER);
        kelsey.setPointOfContactId(3L);
        var first = before.get(0);
        var second = before.get(1);
        first.setFirstName("First");
        second.setFirstName("Second");
        var modified = List.of(first, second, kelsey);
        assertThrows(
                ContactException.class,
                () -> contactService.fetchContactByPhoneNumber(phoneNumber)
        );

        contactService.saveList(modified);

        var after = contactService.getAllContacts(null, null, null, null);
        assertEquals(before.size() + 1, after);
        assertEquals("First", after.get(0).getFirstName());
        assertEquals("Second", after.get(0).getFirstName());
        assertEquals("Kelsey", contactService.fetchContactByPhoneNumber(phoneNumber));
    }

    @Test
    public void saveList_NullList_NothingHappens() {
        var before = contactService.getAllContacts(null, null, null, null);
        contactService.saveList(null);
        var after = contactService.getAllContacts(null, null, null, null);
        assertEquals(before.size(), after.size());
    }
}
