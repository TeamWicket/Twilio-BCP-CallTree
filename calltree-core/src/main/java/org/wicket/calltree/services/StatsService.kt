package org.wicket.calltree.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStats

/**
 * @author Alessandro Arosio - 26/04/2020 16:50
 */
interface StatsService {
  fun calculateStats(eventId: Long, responseWithinMinutes: Long): BcpStats
  fun contactStats(eventId: Long): List<BcpContactStats>
}