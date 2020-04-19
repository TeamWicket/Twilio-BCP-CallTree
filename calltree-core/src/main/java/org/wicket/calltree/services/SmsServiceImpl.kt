package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.InboundSmsDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.mappers.InboundSmsMapper
import org.wicket.calltree.mappers.OutboundSmsMapper
import org.wicket.calltree.repository.InboundSmsRepository
import org.wicket.calltree.repository.OutBoundSmsRepository

@Service
class SmsServiceImpl(private val outboundMapper: OutboundSmsMapper,
                     private val inboundMapper: InboundSmsMapper,
                     private val outBoundRepository: OutBoundSmsRepository,
                     private val inboundRepository: InboundSmsRepository) : SmsService {

  override fun saveOutboundSms(responseList: List<Response>) {
    responseList.forEach {
      val mappedEntity = outboundMapper.responseToEntity(it)
      outBoundRepository.save(mappedEntity)
    }
  }

  override fun saveInboundSms(inboundSmsDto: InboundSmsDto) {
    val mappedEntity = inboundMapper.dtoToEntity(inboundSmsDto)
    inboundRepository.save(mappedEntity)
  }

  override fun terminateEvent(twilioNumber: String) {
    val smsForEvent = inboundRepository.findAllByToTwilioNumber(twilioNumber)
    inboundRepository.deleteAll(smsForEvent)
  }
}