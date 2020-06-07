package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Sort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStats
import org.wicket.calltree.model.DashboardInfo
import org.wicket.calltree.services.StatsService

@RestController
@RequestMapping("/api/v1/stats")
class StatsController(private val statsService: StatsService) {

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Calculate stats")
  fun getStats(@RequestParam eventId: Long, @RequestParam minutes: Long): BcpStats {
    return statsService.calculateStats(eventId, minutes)
  }

  @GetMapping("/total", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Calculate individual stats for each Contact")
  fun calculateContactsStats(@RequestParam eventId: Long,
                             @RequestParam orderByValue: String,
                             @RequestParam direction: Sort.Direction,
                             @RequestParam page: Int,
                             @RequestParam size: Int) : ResponseEntity<List<BcpContactStats>> {
    val stats = statsService.contactStats(eventId, orderByValue, direction, page, size)
    val map = HttpHeaders()
    map["X-Total-Count"] = stats.totalElements.toString()

    return ResponseEntity(stats.statsList, map, HttpStatus.OK)
  }

  @GetMapping("/dashboard", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Calculate stats")
  fun getDash(): DashboardInfo {
    return statsService.dashboardStats()
  }
}