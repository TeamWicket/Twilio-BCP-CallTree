package org.wicket.calltree.services;

import org.wicket.calltree.dto.ContactDto;

import java.util.List;

/**
 * @author Alessandro Arosio - 07/04/2020 20:16
 */
public interface ContactService {
    ContactDto saveOrUpdate(ContactDto contactDto);

    void deleteContact(ContactDto contactDto);

    List<ContactDto> getAllContacts();

    ContactDto getContact(ContactDto contactDto);
}
