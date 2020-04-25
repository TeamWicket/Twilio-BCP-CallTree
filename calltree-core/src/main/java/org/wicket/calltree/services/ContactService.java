package org.wicket.calltree.services;

import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Alessandro Arosio - 07/04/2020 20:16
 */
public interface ContactService {
    List<ContactDto> saveList(List<ContactDto> dtoList);

    ContactDto saveOrUpdate(ContactDto contactDto);

    void deleteContact(Long id);

    List<ContactDto> getAllContacts(@Nullable String order);

    ContactDto getContact(Long id);

    List<ContactDto> getAllSelectedRole(Role role);

    List<ContactDto> getCalltreeUntilRole(Role role);

    ContactDto fetchContactByPhoneNumber(String string);
}
