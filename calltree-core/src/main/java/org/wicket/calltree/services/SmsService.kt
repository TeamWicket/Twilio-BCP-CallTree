package org.wicket.calltree.services

import org.wicket.calltree.dto.BcpEventSmsDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.models.BcpEventSms

/**
 * @author Alessandro Arosio - 14/04/2020 18:18
 */
interface SmsService {
  fun saveSmsFromResponse(responseList: List<Response>)
  fun saveBcpEventSms(bcpEventSmsDto: BcpEventSmsDto)
  fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpEventSmsDto>
  fun findActiveMessagesByRecipientNumber(recipientNumber: String): BcpEventSmsDto
}