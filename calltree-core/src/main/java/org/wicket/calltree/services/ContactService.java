package org.wicket.calltree.services;

import org.wicket.calltree.dto.ContactDto;

import java.util.List;

/**
 * @author Alessandro Arosio - 07/04/2020 20:16
 */
public interface ContactService {
    List<ContactDto> saveList(List<ContactDto> dtoList);

    ContactDto saveOrUpdate(ContactDto contactDto);

    void deleteContact(Long id);

    List<ContactDto> getAllContacts();

    ContactDto getContact(ContactDto contactDto);
}
