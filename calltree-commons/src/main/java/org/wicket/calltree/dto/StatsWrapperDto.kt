package org.wicket.calltree.dto

import org.wicket.calltree.model.BcpContactStats

/**
 * @author Alessandro Arosio - 27/04/2020 18:15
 */
data class StatsWrapperDto(val totalElements: Int, val statsList: List<BcpContactStats>)