package org.wicket.calltree.services

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.StatsWrapperDto
import org.wicket.calltree.enums.SmsStatus
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStats
import org.wicket.calltree.services.utils.zonedDateTimeDifference
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.validation.constraints.NotBlank

@Service
class StatsServiceImpl(private val bcpMessageService: BcpMessageService,
                       private val bcpEventService: BcpEventService) : StatsService {

  override fun calculateStats(eventId: Long, responseWithinMinutes: Long): BcpStats {
    val event = bcpEventService.getEventById(eventId)
    val eventTimestamp = event.timestamp
    val eventMessages = bcpMessageService.findMessagesByBcpEvent(event.id!!)
    val average = calculateOverallAverage(eventTimestamp!!, eventMessages)
    val receivedSms = countReceivedSms(eventTimestamp, eventMessages, responseWithinMinutes)
    val percentageResponse = eventMessages.size * 100 / receivedSms.toDouble()

    return BcpStats(average, eventMessages.size, receivedSms, percentageResponse)
  }

  override fun contactStats(eventId: Long, orderValue: String, direction: Sort.Direction, page: Int, size: Int): StatsWrapperDto {
    val eventMessages = bcpMessageService.getAllPageMessage(eventId, orderValue, direction, page, size)

    val contactStatsList = eventMessages.content.map {
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

    return StatsWrapperDto(eventMessages.totalElements.toInt(), contactStatsList)
  }

  private fun calculateOverallAverage(eventTime: String, messagesList: List<BcpMessageDto>): Double {
    return messagesList.map {
      val replyTimestamp = it.recipientTimestamp
      zonedDateTimeDifference(eventTime, replyTimestamp, ChronoUnit.MINUTES)
    }.average()
  }

  private fun countReceivedSms(eventTime: @NotBlank String, messagesList: List<BcpMessageDto>,
                               minutes: Long): Int {
    return messagesList.filter {
      it.recipientTimestamp != null &&
      ZonedDateTime.parse(it.recipientTimestamp) <= ZonedDateTime.parse(eventTime).plusMinutes(minutes) &&
        it.smsStatus == SmsStatus.RECEIVED
         }
        .count()
  }
}