package org.wicket.calltree.services;

import com.twilio.rest.api.v2010.account.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.*;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.model.BcpStartRequest;
import org.wicket.calltree.service.TwilioService;
import org.wicket.calltree.services.utils.MessageMapper;

import java.time.ZonedDateTime;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CallTreeServiceImplTest {
    private static final Long NUMBER_ID = 1L;
    private static final String REPLY = "Test Reply";
    private static final String FROM_NUMBER_RAW = "441234";
    private static final String TO_NUMBER_RAW = "4412344";

    @Mock
    private TwilioNumberDto twilioNumberDto;
    @Mock
    private BcpEventDto eventDto;
    @Mock
    private BcpMessageDto messageDto;
    @Mock
    private ContactDto contactDto;
    @Mock
    private ContactDto managerDto;
    @Mock
    private Message message;
    @Mock
    private Response response;
    @Mock
    private MessageMapper mapper;
    @Mock
    private TwilioService twilioService;
    @Mock
    private ContactService contactService;
    @Mock
    private BcpMessageService bcpMessageService;
    @Mock
    private BcpEventService bcpEventService;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private TwilioNumberService numberService;
    @InjectMocks
    private CallTreeServiceImpl callTreeServiceImpl;

    @BeforeEach
    void setUp() {
        Long managerId = 1L;

        when(twilioNumberDto.getIsAvailable()).thenReturn(true);

        when(contactDto.getPhoneNumber()).thenReturn("+44656757373");
        when(contactDto.getPointOfContactId()).thenReturn(managerId);

        when(eventDto.getTwilioNumber()).thenReturn(twilioNumberDto);

        when(messageDto.getId()).thenReturn(1L);

        when(mapper.messageToResponse(any())).thenReturn(response);

        when(bcpEventService.saveEvent(any())).thenReturn(eventDto);
        when(bcpEventService.getAllEvents()).thenReturn(singletonList(eventDto));

        when(bcpMessageService.findActiveMessagesByRecipientNumber("+" + FROM_NUMBER_RAW, "+" + TO_NUMBER_RAW))
                .thenReturn(messageDto);

        when(contactService.getCalltreeUntilRole(Role.REPORTER)).thenReturn(singletonList(contactDto));
        when(contactService.fetchContactByPhoneNumber(any())).thenReturn(contactDto);
        when(contactService.getContact(managerId)).thenReturn(managerDto);

        when(numberService.getNumberById(NUMBER_ID)).thenReturn(twilioNumberDto);

        when(twilioService.sendSms(anyList())).thenReturn(singletonList(message));
        when(twilioService.replyToReceivedSms(anyString())).thenReturn(REPLY);
    }

    @Test
    void initiateCalls_WithValidRequest_ReturnsExpectedResponse() {
        BcpStartRequest request = new BcpStartRequest("test", Role.REPORTER, "Test Event", NUMBER_ID, ZonedDateTime.now());

        Long output = callTreeServiceImpl.initiateCalls(request);

        assertThat(output).isEqualTo(eventDto.getId());
    }

    @Test
    void replyToSms_WithValidBody_ReturnsResponse() {
        String body = "ToCountry=US&ToState=DC&SmsMessageSid=test&NumMedia=0&ToCity=&FromZip=&SmsSid=test&FromState=&SmsStatus=received&FromCity=&Body=3&FromCountry=GB&To=%2B" + TO_NUMBER_RAW + "&MessagingServiceSid=test&ToZip=&NumSegments=1&MessageSid=test&AccountSid=test&From=%2B" + FROM_NUMBER_RAW + "&ApiVersion=2010-04-01";

        String reply = callTreeServiceImpl.replyToSms(body);

        assertThat(reply).isEqualTo(REPLY);
    }

    @Test
    void endEvent_WithValidEvent_CallsServices() {
        callTreeServiceImpl.endEvent(eventDto);

        verify(bcpEventService, atMostOnce()).saveEvent(eventDto);
        verify(numberService, atMostOnce()).saveNumber(twilioNumberDto);
    }
}