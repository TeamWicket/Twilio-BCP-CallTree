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
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.Contact;
import org.wicket.calltree.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    ContactDto contact;

    @BeforeEach
    void setUp() {
        contact = new ContactDto();
        contact.setId(1L);
        contact.setFirstName("Dummy");
        contact.setLastName("Contact");
        contact.setPhoneNumber("+222");
        contact.setRole(Role.REPORTER);
    }

    @Test
    void saveList() {
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
    void saveOrUpdate() {
    }

    @Test
    void testDeleteContact() {
        Contact fromDb = mock(Contact.class);

        when(repository.findById(1L)).thenReturn(Optional.of(fromDb));

        contact.setCallingOption(List.of(CallingOption.SMS));
        contactService.deleteContact(1L);

        verify(repository, atLeastOnce()).delete(fromDb);
    }

    @Test
    void getAllContacts() {
    }

    @Test
    void getContact() {
    }
}