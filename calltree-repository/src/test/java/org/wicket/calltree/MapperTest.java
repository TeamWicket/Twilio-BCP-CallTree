package org.wicket.calltree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.mappers.OutboundSmsMapper;
import org.wicket.calltree.models.Contact;
import org.wicket.calltree.models.OutboundSms;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandro Arosio - 05/04/2020 15:08
 */
@ExtendWith(SpringExtension.class)
public class MapperTest {

    private static ContactMapper contactMapper;
    private static OutboundSmsMapper outboundMapper;

    @BeforeAll
    static void beforeAll() {
        contactMapper = Mappers.getMapper(ContactMapper.class);
        outboundMapper = Mappers.getMapper(OutboundSmsMapper.class);
    }

    @Test
    public void convertToEntity() {
        ContactDto contactDto = new ContactDto();
        contactDto.setFirstName("Warwick");
        contactDto.setLastName("Wicket");
        contactDto.setRole(Role.CHAMPION);
        contactDto.setPhoneNumber("+123");
        contactDto.setCallingOption(List.of(CallingOption.WHATSAPP, CallingOption.SMS));

        var contact = contactMapper.dtoToContact(contactDto);

        assertNotNull(contact);
        assertThat(contact.getCallingOption()).hasSize(2);
        assertEquals(contact.getRole(), contactDto.getRole());
    }

    @Test
    public void convertToDto() {
        Contact contact = new Contact();
        contact.setFirstName("Warwick");
        contact.setLastName("Wicket");
        contact.setRole(Role.CHAMPION);
        contact.setPhoneNumber("+123");
        contact.setCallingOption(List.of(CallingOption.WHATSAPP));

        var dto = contactMapper.contactToDto(contact);

        assertNotNull(dto);
        assertThat(dto.getCallingOption()).hasSize(1);
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

        OutboundSms outboundSms = outboundMapper.responseToEntity(response);

        assertEquals(response.getBody(), outboundSms.getBody());
        assertEquals(response.getDateCreated(), outboundSms.getDateCreated());
        assertEquals(response.getTo(), outboundSms.getToNumber());
        assertNull(outboundSms.getFromNumber());
        assertEquals(response.getStatus(), outboundSms.getStatus());
    }
}
