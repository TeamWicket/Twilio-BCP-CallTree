package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.mappers.BcpEventSmsMapper
import org.wicket.calltree.models.BcpMessage
import org.wicket.calltree.repository.BcpEventSmsRepository
import java.util.stream.Collectors

@Service
class SmsServiceImpl(private val bcpEventSmsMapper: BcpEventSmsMapper,
                     private val bcpEventRepository: BcpEventSmsRepository,
                     private val bcpEventSmsRepository: BcpEventSmsRepository) : SmsService {

  override fun saveSmsFromResponse(responseList: List<Response>) {
    responseList.forEach {
      val mappedEntity: BcpMessage = bcpEventSmsMapper.responseToEntity(it)
      bcpEventRepository.save(mappedEntity)
    }
  }

  override fun saveBcpEventSms(bcpMessageDto: BcpMessageDto) {
    val mappedEntity: BcpMessage = bcpEventSmsMapper.dtoToEntity(bcpMessageDto)
    bcpEventSmsRepository.save(mappedEntity)
  }

  override fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpMessageDto> {
    val messageList = bcpEventRepository.findAllByBcpEvent_Id(bcpEventId)
    return messageList.stream()
        .map { bcpEventSmsMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }

  override fun findActiveMessagesByRecipientNumber(recipientNumber: String): BcpMessageDto {
    val message = bcpEventSmsRepository.findFirstByRecipientNumberAndBcpEvent_IsActive(recipientNumber, true);
    return bcpEventSmsMapper.entityToDto(message);
  }
}