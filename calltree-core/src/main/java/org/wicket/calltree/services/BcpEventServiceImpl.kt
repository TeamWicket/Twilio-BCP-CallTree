package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.exceptions.BcpEventException
import org.wicket.calltree.mappers.BcpEventMapper
import org.wicket.calltree.repository.BcpEventRepository
import java.util.stream.Collectors

@Service
class BcpEventServiceImpl(private val bcpEventRepo: BcpEventRepository,
                          private val bcpEventMapper: BcpEventMapper) : BcpEventService {

  override fun getAllEvents(): List<BcpEventDto> {
    return bcpEventRepo.findAll().stream()
        .map { bcpEventMapper.entityToDto(it) }
        .collect(Collectors.toList())
  }

  override fun getEventById(id: Long): BcpEventDto {
    val event = bcpEventRepo.findById(id)
    return bcpEventMapper.entityToDto(event.orElseThrow{ BcpEventException("Event not found") })
  }

  override fun saveEvent(eventDto: BcpEventDto): BcpEventDto {
    val persistedEvent = bcpEventRepo.save(bcpEventMapper.dtoToEntity(eventDto))
    return bcpEventMapper.entityToDto(persistedEvent)
  }

  override fun deleteEventByTwilioNumber(number: String) {
    val event = bcpEventRepo.findByTwilioNumber(number)
    event.ifPresent { bcpEventRepo.delete(it) }
  }

  override fun getEventByNumber(number: String): BcpEventDto {
    val event = bcpEventRepo.findByTwilioNumber(number)
    return bcpEventMapper.entityToDto(event.orElseThrow { BcpEventException("Event not found") })
  }
}