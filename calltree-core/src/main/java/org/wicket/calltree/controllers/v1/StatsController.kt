package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStats
import org.wicket.calltree.services.StatsService

/**
 * @author Alessandro Arosio - 26/04/2020 19:40
 */
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
  fun calculateContactsStats(@RequestParam eventId: Long) : List<BcpContactStats> {
    return statsService.contactStats(eventId)
  }
}