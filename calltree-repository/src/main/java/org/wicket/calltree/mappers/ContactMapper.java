package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.models.Contact;

@Mapper
public interface ContactMapper {
    ContactDto contactToDto (Contact contact);
    Contact dtoToContact(ContactDto dto);
}
