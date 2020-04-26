package org.wicket.calltree.services

import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.Response

/**
 * @author Alessandro Arosio - 14/04/2020 18:18
 */
interface SmsService {
  fun saveSmsFromResponse(responseList: List<Response>)
  fun saveBcpEventSms(bcpMessageDto: BcpMessageDto)
  fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpMessageDto>
  fun findActiveMessagesByRecipientNumber(recipientNumber: String): BcpMessageDto
}