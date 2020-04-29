package org.wicket.calltree;

import org.junit.jupiter.api.BeforeAll;
import org.mapstruct.factory.Mappers;
import org.wicket.calltree.dto.BcpEventDto;
import org.wicket.calltree.dto.BcpMessageDto;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.mappers.BcpEventMapper;
import org.wicket.calltree.mappers.BcpMessageMapper;
import org.wicket.calltree.mappers.TwilioNumberMapper;
import org.wicket.calltree.models.BcpEvent;
import org.wicket.calltree.models.BcpMessage;
import org.wicket.calltree.models.TwilioNumber;

import java.time.ZonedDateTime;

abstract class AbstractEventMapperTest {
    protected static final Long NUMBER_ID = 21L;
    protected static final String NUMBER = "+44765868464";

    protected static final Long EVENT_ID = 11L;
    protected static final String EVENT_NAME = "Test Event";
    protected static final ZonedDateTime EVENT_TIME = ZonedDateTime.now();
    protected static final Long EVENT_VERSION = 12L;

    protected static final Long MESSAGE_ID = 1L;
    protected static final String DATE_CREATED = "21 May 2016";
    protected static final String DATE_UPDATED = "22 May 2016";
    protected static final String DATE_SENT = "23 May 2016";
    protected static final String OUTBOUND_MESSAGE = "Test Message";
    protected static final String RECIPIENT_NUMBER = "+1893749734";
    protected static final String RECIPIENT_COUNTRY = "US";
    protected static final String RECIPIENT_MESSAGE = "Test Response";
    protected static final String RECIPIENT_TIMESTAMP = "24 May 2016 15:00";
    protected static final String ERROR_MESSAGE = "Test Error";
    protected static final Long MESSAGE_VERSION = 2L;

    protected static TwilioNumberDto twilioNumberDto;
    protected static TwilioNumber twilioNumber;
    protected static BcpEventDto bcpEventDto;
    protected static BcpEvent bcpEvent;
    protected static BcpMessageDto bcpMessageDto;
    protected static BcpMessage bcpMessage;
    protected static TwilioNumberMapper twilioNumberMapper;
    protected static BcpEventMapper bcpEventMapper;
    protected static BcpMessageMapper bcpMessageMapper;

    @BeforeAll
    static void setUp() {
        twilioNumberMapper = Mappers.getMapper(TwilioNumberMapper.class);
        bcpEventMapper = Mappers.getMapper(BcpEventMapper.class);
        bcpMessageMapper = Mappers.getMapper(BcpMessageMapper.class);

        twilioNumber = TwilioNumber.builder().id(NUMBER_ID)
                .twilioNumber(NUMBER)
                .isAvailable(true)
                .build();

        bcpEvent = BcpEvent.builder().id(EVENT_ID)
                .eventName(EVENT_NAME)
                .timestamp(EVENT_TIME)
                .twilioNumber(twilioNumber)
                .isActive(true)
                .version(EVENT_VERSION)
                .build();


        bcpMessage = BcpMessage.builder().id(MESSAGE_ID)
                .bcpEvent(bcpEvent)
                .dateCreated(DATE_CREATED)
                .dateUpdated(DATE_UPDATED)
                .dateSent(DATE_SENT)
                .outboundMessage(OUTBOUND_MESSAGE)
                .smsStatus(SmsStatus.RECEIVED)
                .recipientNumber(RECIPIENT_NUMBER)
                .recipientCountry(RECIPIENT_COUNTRY)
                .recipientMessage(RECIPIENT_MESSAGE)
                .recipientTimestamp(RECIPIENT_TIMESTAMP)
                .errorMessage(ERROR_MESSAGE)
                .version(MESSAGE_VERSION)
                .build();

        twilioNumberDto = TwilioNumberDto.builder().id(NUMBER_ID)
                .twilioNumber(NUMBER)
                .isAvailable(true)
                .build();

        bcpEventDto = BcpEventDto.builder().id(EVENT_ID)
                .eventName(EVENT_NAME)
                .timestamp(EVENT_TIME.toString())
                .twilioNumber(twilioNumberDto)
                .isActive(true)
                .version(EVENT_VERSION)
                .build();

        bcpMessageDto = BcpMessageDto.builder().id(MESSAGE_ID)
                .bcpEvent(bcpEventDto)
                .dateCreated(DATE_CREATED)
                .dateUpdated(DATE_UPDATED)
                .dateSent(DATE_SENT)
                .outboundMessage(OUTBOUND_MESSAGE)
                .smsStatus(SmsStatus.RECEIVED)
                .recipientNumber(RECIPIENT_NUMBER)
                .recipientCountry(RECIPIENT_COUNTRY)
                .recipientMessage(RECIPIENT_MESSAGE)
                .recipientTimestamp(RECIPIENT_TIMESTAMP)
                .errorMessage(ERROR_MESSAGE)
                .version(MESSAGE_VERSION)
                .build();
    }
}
