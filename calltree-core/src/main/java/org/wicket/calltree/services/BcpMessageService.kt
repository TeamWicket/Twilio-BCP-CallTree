package org.wicket.calltree.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.models.BcpMessage

/**
 * @author Alessandro Arosio - 14/04/2020 18:18
 */
interface BcpMessageService {
  fun saveSmsFromResponse(responseList: List<Response>)
  fun saveBcpEventSms(bcpMessageDto: BcpMessageDto)
  fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpMessageDto>
  fun findActiveMessagesByRecipientNumber(recipientNumber: String, twilioNumber: String): BcpMessageDto
  fun getAllPageMessage(eventId: Long, orderValue: String,
                        direction: Sort.Direction, page: Int, size: Int): Page<BcpMessage>
}