package org.wicket.calltree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.BcpMessageMapper;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.BcpMessage;
import org.wicket.calltree.models.Contact;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandro Arosio - 05/04/2020 15:08
 */
@ExtendWith(SpringExtension.class)
public class MapperTest {

    private static ContactMapper contactMapper;
    private static BcpMessageMapper outboundMapper;

    @BeforeAll
    static void beforeAll() {
        contactMapper = Mappers.getMapper(ContactMapper.class);
        outboundMapper = Mappers.getMapper(BcpMessageMapper.class);
    }

    @Test
    public void convertToEntity() {
        ContactDto contactDto = new ContactDto();
        contactDto.setFirstName("Warwick");
        contactDto.setLastName("Wicket");
        contactDto.setRole(Role.CHAMPION);
        contactDto.setPhoneNumber("+123");

        var contact = contactMapper.dtoToContact(contactDto);

        assertNotNull(contact);
        assertEquals(contact.getRole(), contactDto.getRole());
    }

    @Test
    public void convertToDto() {
        Contact contact = new Contact();
        contact.setFirstName("Warwick");
        contact.setLastName("Wicket");
        contact.setRole(Role.CHAMPION);
        contact.setPhoneNumber("+123");

        var dto = contactMapper.contactToDto(contact);

        assertNotNull(dto);
        assertEquals(dto.getRole(), contact.getRole());
    }

    @Test
    void testConvertResponseToOutboundSmsEntity() {
        Response response = new Response();
        response.setBody("this is a body");
        response.setDateCreated("2020-04-11T16:07:50.000Z");
        response.setDateUpdated("2020-04-11T16:07:50.000Z");
        response.setDateSent("2020-04-11T16:07:50.000Z");
        response.setTo("+9999");
        response.setStatus("queued");
        response.setSid("sid222");
        response.setApiVersion("v1");
        response.setErrorCode("0");

        BcpMessage bcpEventSms = outboundMapper.responseToEntity(response);

        assertEquals(response.getBody(), bcpEventSms.getOutboundMessage());
        assertEquals(response.getDateCreated(), bcpEventSms.getDateCreated());
        assertEquals(response.getDateUpdated(), bcpEventSms.getDateUpdated());
        assertEquals(response.getDateSent(), bcpEventSms.getDateSent());
        assertEquals(response.getTo(), bcpEventSms.getRecipientNumber());
        assertNull(bcpEventSms.getSmsStatus());
    }
}
