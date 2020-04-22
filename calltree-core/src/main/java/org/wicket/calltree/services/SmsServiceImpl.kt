package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.InboundSmsDto
import org.wicket.calltree.dto.OutboundSmsDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.mappers.InboundSmsMapper
import org.wicket.calltree.mappers.OutboundSmsMapper
import org.wicket.calltree.models.TwilioNumber
import org.wicket.calltree.repository.InboundSmsRepository
import org.wicket.calltree.repository.OutBoundSmsRepository
import org.wicket.calltree.repository.TwilioNumberRepository
import java.util.stream.Collectors

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

  override fun findOutboundMessagesByTwilioNumber(twilioNumber: String): List<OutboundSmsDto> {
    val outboundList = outBoundRepository.findAllByFromNumber(twilioNumber)
    return outboundList.stream()
        .map { outboundMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }

  override fun findInboundMessagesByTwilioNumber(twilioNumber: String): List<InboundSmsDto> {
    val inboundList = inboundRepository.findAllByToTwilioNumber(twilioNumber)
    return inboundList.stream()
        .map { inboundMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }
}