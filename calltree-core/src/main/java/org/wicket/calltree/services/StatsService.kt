package org.wicket.calltree.services

import org.springframework.data.domain.Sort
import org.wicket.calltree.dto.StatsWrapperDto
import org.wicket.calltree.model.BcpStats

interface StatsService {
  fun calculateStats(eventId: Long, responseWithinMinutes: Long): BcpStats
  fun contactStats(eventId: Long, orderValue: String,
                   direction: Sort.Direction, page: Int, size: Int): StatsWrapperDto
}