package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpMessageDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.mappers.BcpMessageMapper
import org.wicket.calltree.models.BcpMessage
import org.wicket.calltree.repository.BcpMessageRepository
import java.util.stream.Collectors

@Service
class BcpMessageServiceImpl(private val bcpMessageMapper: BcpMessageMapper,
                            private val bcpEventRepository: BcpMessageRepository,
                            private val bcpMessageRepository: BcpMessageRepository) : BcpMessageService {

  override fun saveSmsFromResponse(responseList: List<Response>) {
    responseList.forEach {
      val mappedEntity: BcpMessage = bcpMessageMapper.responseToEntity(it)
      bcpEventRepository.save(mappedEntity)
    }
  }

  override fun saveBcpEventSms(bcpMessageDto: BcpMessageDto) {
    val mappedEntity: BcpMessage = bcpMessageMapper.dtoToEntity(bcpMessageDto)
    bcpMessageRepository.save(mappedEntity)
  }

  override fun findMessagesByBcpEvent(bcpEventId: Long): List<BcpMessageDto> {
    val messageList = bcpEventRepository.findAllByBcpEvent_Id(bcpEventId)
    return messageList.stream()
        .map { bcpMessageMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }

  override fun findActiveMessagesByRecipientNumber(recipientNumber: String, twilioNumber: String): BcpMessageDto {
    val message = bcpMessageRepository.findFirstByRecipientNumberAndBcpEvent_TwilioNumber_TwilioNumberAndBcpEvent_IsActive(
            recipientNumber, twilioNumber, true)
    return bcpMessageMapper.entityToDto(message);
  }
}