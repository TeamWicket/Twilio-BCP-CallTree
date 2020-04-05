package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.models.Contact;

/**
 * @author Alessandro Arosio - 05/04/2020 15:05
 */
@Mapper
public interface ContactMapper {
    ContactDto contactToDto (Contact contact);
    Contact dtoToContact(ContactDto dto);
}
