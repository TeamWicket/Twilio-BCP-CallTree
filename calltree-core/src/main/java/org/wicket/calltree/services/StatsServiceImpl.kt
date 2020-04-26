package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.enums.SmsStatus
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStats
import org.wicket.calltree.services.utils.zonedDateTimeDifference
import java.time.temporal.ChronoUnit

@Service
class StatsServiceImpl(private val bcpMessageService: BcpMessageService,
                       private val bcpEventService: BcpEventService) : StatsService {

  override fun calculateStats(eventId: Long, responseWithinMinutes: Long): BcpStats {
    val event = bcpEventService.getEventById(eventId)
    val eventTimestamp = event.timestamp
    val eventMessages = bcpMessageService.findMessagesByBcpEvent(event.id!!)
    val average = calculateOverallAverage(eventTimestamp!!, eventMessages)
    val receivedSms = countReceivedSms(eventMessages)
    val percentageResponse = eventMessages.size * 100 / receivedSms.toDouble()

    return BcpStats(average, eventMessages.size, receivedSms, percentageResponse)
  }

  override fun contactStats(eventId: Long): List<BcpContactStats> {
    val eventMessages = bcpMessageService.findMessagesByBcpEvent(eventId)
    return eventMessages.map {
      val stats = BcpContactStats(
          it.bcpEvent.twilioNumber.twilioNumber,
          it.outboundMessage,
          it.dateCreated,
          it.recipientNumber,
          it.recipientTimestamp,
          it.recipientMessage,
          it.bcpEvent.eventName
      )
      stats
    }.toList()
  }

  private fun calculateOverallAverage(eventTime: String, messagesList: List<BcpMessageDto>): Double {
    return messagesList.map {
      val replyTimestamp = it.recipientTimestamp
      zonedDateTimeDifference(eventTime, replyTimestamp, ChronoUnit.MINUTES)
    }.average()
  }

  private fun countReceivedSms(messagesList: List<BcpMessageDto>): Int {
    return messagesList.filter { it.smsStatus == SmsStatus.RECEIVED }
        .count()
  }
}