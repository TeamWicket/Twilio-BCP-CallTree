package org.wicket.calltree.services;

import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;

import javax.annotation.Nullable;
import java.util.List;

public interface ContactService {
    List<ContactDto> saveList(List<ContactDto> dtoList);

    ContactDto saveOrUpdate(ContactDto contactDto);

    void deleteContact(Long id);

    Integer getNumContacts();

    List<ContactDto> getAllContacts(@Nullable String orderDirection, @Nullable String orderByValue,
                                    @Nullable Integer page, @Nullable Integer size);

    ContactDto getContact(Long id);

    List<ContactDto> getAllSelectedRole(Role role);

    List<ContactDto> getCalltreeUntilRole(Role role);

    ContactDto fetchContactByPhoneNumber(String string);

    List<ContactDto> fetchManyContactsById(long[] id);
}
