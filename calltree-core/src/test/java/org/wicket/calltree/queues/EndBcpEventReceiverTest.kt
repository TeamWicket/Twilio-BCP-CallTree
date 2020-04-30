package org.wicket.calltree.queues

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.enums.SmsStatus
import org.wicket.calltree.services.BcpEventService
import org.wicket.calltree.services.BcpMessageService
import org.wicket.calltree.services.TwilioNumberService

@ExtendWith(SpringExtension::class)
internal class EndBcpEventReceiverTest {
    companion object {
        private const val BCP_EVENT_ID = 1L
    }

    @Mock
    lateinit var twilioNumber: TwilioNumberDto
    @Mock
    lateinit var bcpEvent: BcpEventDto
    @Mock
    lateinit var bcpMessage1: BcpMessageDto
    @Mock
    lateinit var bcpMessage2: BcpMessageDto
    @Mock
    lateinit var twilioNumberService: TwilioNumberService
    @Mock
    lateinit var bcpEventService: BcpEventService
    @Mock
    lateinit var bcpMessageService: BcpMessageService
    @InjectMocks
    lateinit var endBcpEventReceiver: EndBcpEventReceiver

    @BeforeEach
    fun setUp() {
        Mockito.`when`(bcpEvent.twilioNumber).thenReturn(twilioNumber)
        Mockito.`when`(bcpEvent.id).thenReturn(BCP_EVENT_ID)

        Mockito.`when`(bcpMessage1.bcpEvent).thenReturn(bcpEvent)
        Mockito.`when`(bcpMessage1.smsStatus).thenReturn(SmsStatus.SENT)
        Mockito.`when`(bcpMessage1.bcpEvent).thenReturn(bcpEvent)

        Mockito.`when`(bcpMessage2.bcpEvent).thenReturn(bcpEvent)
        Mockito.`when`(bcpMessage2.smsStatus).thenReturn(SmsStatus.SENT)
        Mockito.`when`(bcpMessage2.bcpEvent).thenReturn(bcpEvent)

        Mockito.`when`(bcpMessageService.findMessagesByBcpEvent(BCP_EVENT_ID)).thenReturn(listOf(bcpMessage1, bcpMessage2))
    }

    @Test
    fun receiveEndEventMessage_WithEventContainingSentMessages_DoesNotUpdateEventAndNumber() {
        endBcpEventReceiver.receiveEndEventMessage(bcpMessage1)

        Mockito.verify(bcpEventService, Mockito.never()).saveEvent(bcpEvent)
        Mockito.verify(twilioNumberService, Mockito.never()).saveNumber(twilioNumber)
    }

    @Test
    fun receiveEndEventMessage_WithEventContainingNoSentMessages_UpdatesEventAndNumber() {
        Mockito.`when`(bcpMessage2.smsStatus).thenReturn(SmsStatus.RECEIVED)
        Mockito.`when`(bcpMessage1.smsStatus).thenReturn(SmsStatus.RECEIVED)

        endBcpEventReceiver.receiveEndEventMessage(bcpMessage1)

        Mockito.verify(bcpEventService, Mockito.atMostOnce()).saveEvent(bcpEvent)
        Mockito.verify(twilioNumberService, Mockito.atMostOnce()).saveNumber(twilioNumber)
    }
}