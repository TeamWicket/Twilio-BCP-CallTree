package org.wicket.calltree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.Contact;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ContactMapperTest {
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "Guy";
    private static final String PHONE_NUMBER = "+44686763673";
    private static final Long POINT_OF_CONTACT_ID = 2L;
    private static final Long VERSION = 3L;
    private static ContactMapper contactMapper;
    private static ContactDto contactDto;
    private static Contact contact;

    @BeforeAll
    static void setUp() {
        contactMapper = Mappers.getMapper(ContactMapper.class);

        contact = Contact.builder().id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE_NUMBER)
                .role(Role.LEADER)
                .pointOfContactId(POINT_OF_CONTACT_ID)
                .version(VERSION)
                .build();

        contactDto = ContactDto.builder().id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE_NUMBER)
                .role(Role.LEADER)
                .pointOfContactId(POINT_OF_CONTACT_ID)
                .version(VERSION)
                .build();
    }

    @Test
    public void contactToDto_WithId_ReturnsDtoWithSameId() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getId()).isEqualTo(ID);
    }

    @Test
    public void contactToDto_WithFirstName_ReturnsDtoWithSameFirstName() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getFirstName()).isEqualTo(FIRST_NAME);
    }
    @Test
    public void contactToDto_WithLastName_ReturnsDtoWithSameLastName() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void contactToDto_WithPhoneNumber_ReturnsDtoWithSamePhoneNumber() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    public void contactToDto_WithRole_ReturnsDtoWithSameRole() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getRole()).isEqualTo(Role.LEADER);
    }

    @Test
    public void contactToDto_WithPointOfContactId_ReturnsDtoWithSamePointOfContactId() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getPointOfContactId()).isEqualTo(POINT_OF_CONTACT_ID);
    }

    @Test
    public void contactToDto_WithVersion_ReturnsDtoWithSameVersion() {
        ContactDto contactDto = contactMapper.contactToDto(contact);

        assertThat(contactDto.getVersion()).isEqualTo(VERSION);
    }

    @Test
    public void dtoToContact_WithId_ReturnsContactWithSameId() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getId()).isEqualTo(ID);
    }

    @Test
    public void dtoToContact_WithFirstName_ReturnsContactWithSameFirstName() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getFirstName()).isEqualTo(FIRST_NAME);
    }
    @Test
    public void dtoToContact_WithLastName_ReturnsContactWithSameLastName() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void dtoToContact_WithPhoneNumber_ReturnsContactWithSamePhoneNumber() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    public void dtoToContact_WithRole_ReturnsContactWithSameRole() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getRole()).isEqualTo(Role.LEADER);
    }

    @Test
    public void dtoToContact_WithPointOfContactId_ReturnsContactWithSamePointOfContactId() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getPointOfContactId()).isEqualTo(POINT_OF_CONTACT_ID);
    }

    @Test
    public void dtoToContact_WithVersion_ReturnsContactWithSameVersion() {
        Contact contact = contactMapper.dtoToContact(contactDto);

        assertThat(contact.getVersion()).isEqualTo(VERSION);
    }
}
