package org.wicket.calltree.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.exceptions.ContactException;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.Contact;
import org.wicket.calltree.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Alessandro Arosio - 07/04/2020 22:43
 */
@ExtendWith(SpringExtension.class)
class ContactServiceImplTest {
    @Mock
    ContactRepository repository;

    @Mock
    ContactMapper mapper;

    @Captor
    ArgumentCaptor<List<Contact>> captor;

    @InjectMocks
    ContactServiceImpl contactService;

    ContactDto contactDto;

    @BeforeEach
    void setUp() {
        contactDto = new ContactDto();
        contactDto.setId(1L);
        contactDto.setFirstName("Dummy");
        contactDto.setLastName("Contact");
        contactDto.setPhoneNumber("+222");
        contactDto.setRole(Role.REPORTER);
        contactDto.setPointOfContactId(55L);
    }

    @Test
    void testSaveList() {
        List<ContactDto> dtoList = mock(List.class);
        List<Contact> list = new ArrayList<>();
        Contact cont = new Contact();
        cont.setId(1L);
        list.add(cont);
        list.add(cont);
        cont.setId(53L);
        list.add(cont);
        final List<Contact> mockedList = mock(List.class);
        mockedList.addAll(list);
        contactService.saveList(dtoList);

        when(repository.saveAll(anyList())).thenReturn(mockedList);
        verify(mockedList).addAll(captor.capture());

        final List<Contact> captured = captor.getValue();

        assertEquals(53L, captured.get(2).getId());

    }

    @Test
    void testSaveContact_WillReturnValidContact() {
        Contact contact = new Contact();

        Contact savedContact = new Contact();
        savedContact.setId(77L);

        assertNull(contact.getId());

        when(mapper.dtoToContact(any(ContactDto.class))).thenReturn(contact);
        when(repository.save(contact)).thenReturn(savedContact);

        contactService.saveOrUpdate(this.contactDto);

        assertEquals(77L, savedContact.getId());
    }

    @Test
    void testUpdateContact_WillUpdateExistingContact() {
        Contact contact = createDummyContact();
        Optional<Contact> opt = Optional.of(contact);

        assertEquals("oldName", contact.getFirstName());

        contact.setFirstName("NEW");

        when(repository.findById(anyLong())).thenReturn(opt);
        when(mapper.dtoToContact(any(ContactDto.class))).thenReturn(contact);
        when(repository.save(any(Contact.class))).thenReturn(contact);

        contactService.saveOrUpdate(contactDto);
        assertEquals("NEW", contact.getFirstName());
        verify(repository, atMostOnce()).save(any(Contact.class));
    }

    @Test
    void testDeleteContact() {
        Contact fromDb = mock(Contact.class);

        when(repository.findById(1L)).thenReturn(Optional.of(fromDb));

        contactService.deleteContact(1L);

        verify(repository, atLeastOnce()).delete(fromDb);
    }

    @Test
    void testGetAllContacts_WillReturnListOfContacts() {
        List<ContactDto> mockList = mock(List.class);
        List<Contact> contactList = new ArrayList<>();
        Contact dummy = createDummyContact();
        contactList.add(dummy);
        contactList.add(dummy);
        contactList.add(dummy);

        when(mapper.contactToDto(any())).thenReturn(new ContactDto());
        when(repository.findAll()).thenReturn(contactList);
        when(mockList.size()).thenReturn(contactList.size());

        contactService.getAllContacts(null, null, null, null);

        assertThat(mockList).hasSize(3);
    }

    @Test
    void getContact_Found_WillReturn_ContactDto() {
        ContactDto dto = new ContactDto();
        dto.setId(134L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(new Contact()));
        when(mapper.contactToDto(any(Contact.class))).thenReturn(dto);


        ContactDto contact = contactService.getContact(7L);

        assertEquals(dto.getId(), contact.getId());
    }

    @Test
    void getContact_ContactNotFound_WillThrowException() {
        Long idNotPresent = 0L;

        assertThrows(ContactException.class, () -> contactService.getContact(idNotPresent));
    }

    private Contact createDummyContact() {
        Contact contact = new Contact();
        contact.setId(9L);
        contact.setRole(Role.MANAGER);
        contact.setPhoneNumber("+000");
        contact.setFirstName("oldName");
        contact.setLastName("Murphy");
        contact.setPointOfContactId(99L);
        return contact;
    }
}