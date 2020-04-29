package org.wicket.calltree.queues

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.enums.SmsStatus
import org.wicket.calltree.services.BcpEventService
import org.wicket.calltree.services.BcpMessageService
import org.wicket.calltree.services.TwilioNumberService

@Component
class EndBcpEventReceiver(private val twilioNumberService: TwilioNumberService,
                          private val bcpEventService: BcpEventService,
                          private val bcpMessageService: BcpMessageService) {

    @JmsListener(destination = "EndBcpEventQueue", containerFactory = "bcpEventFactory")
    fun receiveEndEventMessage(bcpMessageDto: BcpMessageDto) {
        val event:BcpEventDto = bcpMessageDto.bcpEvent
        val messages: List<BcpMessageDto> = bcpMessageService.findMessagesByBcpEvent(event.id!!)

        if (messages.stream().noneMatch { msg: BcpMessageDto -> msg.smsStatus == SmsStatus.SENT }) {
            event.isActive = false
            bcpEventService.saveEvent(event)

            val twilioNumber = event.twilioNumber
            twilioNumber.isAvailable = true
            twilioNumberService.saveNumber(twilioNumber)
        }
    }
}