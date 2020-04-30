package org.wicket.calltree.services

import org.springframework.data.domain.Page
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.models.BcpEvent

interface BcpEventService {
  fun getAllEvents() : List<BcpEventDto>

  fun getEventById(id: Long) : BcpEventDto

  fun saveEvent(eventDto: BcpEventDto) : BcpEventDto

  fun deleteEventByTwilioNumber(twilioNumberId: Long)

  fun getEventByNumber(twilioNumberId: Long): BcpEventDto

  fun getPagedEvents(page: Int, size: Int): Page<BcpEvent>

}