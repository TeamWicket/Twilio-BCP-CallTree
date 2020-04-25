package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpEventSmsDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.mappers.BcpEventSmsMapper
import org.wicket.calltree.models.BcpEventSms
import org.wicket.calltree.repository.BcpEventSmsRepository
import java.util.stream.Collectors

@Service
class SmsServiceImpl(private val bcpEventSmsMapper: BcpEventSmsMapper,
                     private val bcpEventRepository: BcpEventSmsRepository) : SmsService {

  override fun saveOutboundSms(responseList: List<Response>) {
    responseList.forEach {
      val mappedEntity: BcpEventSms = bcpEventSmsMapper.responseToEntity(it)
      bcpEventRepository.save(mappedEntity)
    }
  }

  override fun saveInboundSms(inboundSmsDto: BcpEventSmsDto) {
    //@todo
  }

  override fun terminateEvent(twilioNumber: String) {
    //@todo
  }

  override fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpEventSmsDto> {
    val outboundList = bcpEventRepository.findAllByBcpEvent_Id(bcpEventId)
    return outboundList.stream()
        .map { bcpEventSmsMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }
}