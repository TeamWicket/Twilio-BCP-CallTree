package org.wicket.calltree.dto

import org.wicket.calltree.model.BcpContactStats

data class StatsWrapperDto(val totalElements: Int, val statsList: List<BcpContactStats>)