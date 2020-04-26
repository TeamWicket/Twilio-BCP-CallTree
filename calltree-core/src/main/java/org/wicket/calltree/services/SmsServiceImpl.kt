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
                     private val bcpEventRepository: BcpEventSmsRepository,
                     private val bcpEventSmsRepository: BcpEventSmsRepository) : SmsService {

  override fun saveSmsFromResponse(responseList: List<Response>) {
    responseList.forEach {
      val mappedEntity: BcpEventSms = bcpEventSmsMapper.responseToEntity(it)
      bcpEventRepository.save(mappedEntity)
    }
  }

  override fun saveBcpEventSms(bcpEventSmsDto: BcpEventSmsDto) {
    val mappedEntity: BcpEventSms = bcpEventSmsMapper.dtoToEntity(bcpEventSmsDto)
    bcpEventSmsRepository.save(mappedEntity)
  }

  override fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpEventSmsDto> {
    val messageList = bcpEventRepository.findAllByBcpEvent_Id(bcpEventId)
    return messageList.stream()
        .map { bcpEventSmsMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }

  override fun findActiveMessagesByRecipientNumber(recipientNumber: String): BcpEventSmsDto {
    val message = bcpEventSmsRepository.findFirstByRecipientNumberAndBcpEvent_IsActive(recipientNumber, true);
    return bcpEventSmsMapper.entityToDto(message);
  }
}