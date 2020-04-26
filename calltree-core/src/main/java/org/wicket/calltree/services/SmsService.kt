package org.wicket.calltree.services

import org.wicket.calltree.dto.InboundSmsDto
import org.wicket.calltree.dto.OutboundSmsDto
import org.wicket.calltree.dto.Response

/**
 * @author Alessandro Arosio - 14/04/2020 18:18
 */
interface SmsService {
  fun saveOutboundSms(responseList: List<Response>)
  fun saveInboundSms(inboundSmsDto: InboundSmsDto)
  fun terminateEvent(twilioNumber: String)
  fun findOutboundMessagesByTwilioNumber(twilioNumber: String): List<OutboundSmsDto>
  fun findInboundMessagesByTwilioNumber(twilioNumber: String): List<InboundSmsDto>
}