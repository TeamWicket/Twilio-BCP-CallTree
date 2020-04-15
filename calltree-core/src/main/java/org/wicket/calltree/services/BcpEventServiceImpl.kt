package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.mappers.BcpEventMapper
import org.wicket.calltree.repository.BcpEventRepository

@Service
class BcpEventServiceImpl(private val bcpEventRepo: BcpEventRepository,
                          private val bcpEventMapper: BcpEventMapper) : BcpEventService {

  override fun getAllEvents(): List<BcpEventDto> {
    TODO("Not yet implemented")
  }

  override fun getEventsByName(name: String): List<BcpEventDto> {
    TODO("Not yet implemented")
  }

  override fun getEventById(id: Long): BcpEventDto {
    TODO("Not yet implemented")
  }
}