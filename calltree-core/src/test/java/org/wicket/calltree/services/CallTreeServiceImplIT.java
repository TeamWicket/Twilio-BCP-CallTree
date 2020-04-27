package org.wicket.calltree.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.models.BcpMessage;
import org.wicket.calltree.models.Contact;
import org.wicket.calltree.models.TwilioNumber;
import org.wicket.calltree.repository.BcpEventRepository;
import org.wicket.calltree.repository.BcpMessageRepository;
import org.wicket.calltree.repository.ContactRepository;
import org.wicket.calltree.repository.TwilioNumberRepository;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CallTreeServiceImplIT {
    private static final String TWILIO_NUMBER = "TESTNUMBER";
    private static final String RECIPIENT_NUMBER_ONE = "TESTRECEIVER";
    private static final String RECIPIENT_NUMBER_TWO = "ANOTHERRECEIVER";
    private static BcpMessage testBcpMessageOne;
    @Autowired
    CallTreeService callTreeService;


    @BeforeAll
    static void setUp(@Autowired TwilioNumberRepository twilioNumberRepository, @Autowired ContactRepository contactRepository,
                      @Autowired BcpEventRepository bcpEventRepository, @Autowired BcpMessageRepository bcpMessageRepository) {
        TwilioNumber testTwilioNumber = new TwilioNumber();
        testTwilioNumber.setTwilioNumber("+" + TWILIO_NUMBER);
        testTwilioNumber.setIsAvailable(false);
        testTwilioNumber = twilioNumberRepository.save(testTwilioNumber);

        Contact testManager = new Contact();
        testManager.setFirstName("Manager");
        testManager.setLastName("O'Manager");
        testManager.setPhoneNumber("+" + RECIPIENT_NUMBER_TWO);
        testManager.setRole(Role.CHAMPION);
        testManager.setVersion(0L);
        testManager = contactRepository.save(testManager);

        Contact testContact = new Contact();
        testContact.setFirstName("Test");
        testContact.setLastName("McTest");
        testContact.setPhoneNumber("+" + RECIPIENT_NUMBER_ONE);
        testContact.setRole(Role.LEADER);
        testContact.setVersion(0L);
        testContact.setPointOfContactId(testManager.getId());
        testContact = contactRepository.save(testContact);

        BcpEvent testBcpEvent = new BcpEvent();
        testBcpEvent.setEventName("Test Event");
        testBcpEvent.setTimestamp(ZonedDateTime.now());
        testBcpEvent.setTwilioNumber(testTwilioNumber);
        testBcpEvent.setVersion(0L);
        testBcpEvent.setIsActive(true);
        testBcpEvent = bcpEventRepository.save(testBcpEvent);

        testBcpMessageOne = new BcpMessage();
        testBcpMessageOne.setBcpEvent(testBcpEvent);
        testBcpMessageOne.setDateCreated("04 Jul 2020");
        testBcpMessageOne.setDateUpdated("04 Jul 2020");
        testBcpMessageOne.setOutboundMessage("TestMessage");
        testBcpMessageOne.setSmsStatus(SmsStatus.SENT);
        testBcpMessageOne.setRecipientNumber("+" + RECIPIENT_NUMBER_ONE);
        testBcpMessageOne = bcpMessageRepository.save(testBcpMessageOne);
    }

    @Test
    void replyToSms_WithValidInputMessage_UpdatesTheExistingMessageWithReceivedMessageAndSetsStatusToReceived(@Autowired BcpMessageRepository bcpMessageRepository) {
        String resposne = "Hello!";
        String testContactResponse = "ToCountry=JE&ToState=&SmsMessageSid=SM923d2a091f30fb5f1bda413a60e24916&NumMedia=0&ToCity=&FromZip=&SmsSid=SM923d2a091f30fb5f1bda413a60e24916&FromState=&SmsStatus=received&FromCity=&Body=" + resposne + "&FromCountry=GB&To=%2B"+ TWILIO_NUMBER + "&MessagingServiceSid=MGc5f2f28b11431418f1a8d700dadc0a37&ToZip=&NumSegments=1&MessageSid=SM923d2a091f30fb5f1bda413a60e24916&AccountSid=AC98cef14d723c7d0c452d990168531a36&From=%2B" + RECIPIENT_NUMBER_ONE +"&ApiVersion=2010-04-01";

        callTreeService.replyToSms(testContactResponse);
        BcpMessage updatedMessage = bcpMessageRepository.findById(testBcpMessageOne.getId()).get();

        assertThat(updatedMessage.getRecipientMessage()).isEqualTo(resposne);
        assertThat(updatedMessage.getSmsStatus()).isEqualTo(SmsStatus.RECEIVED);
    }
}