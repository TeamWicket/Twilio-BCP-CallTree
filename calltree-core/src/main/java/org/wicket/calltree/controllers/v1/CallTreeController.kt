package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.model.BcpContactStats
import org.wicket.calltree.model.BcpStartRequest
import org.wicket.calltree.model.BcpStats
import org.wicket.calltree.services.CallTreeService
import javax.validation.Valid
import kotlin.math.min

/**
 * @author Alessandro Arosio - 11/04/2020 13:14
 */
@RestController
@RequestMapping("/api/v1/calltree")
class CallTreeController(private val service: CallTreeService) {

  @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Initiate BCP calls")
  fun startCalls(@RequestBody @Valid bcpStartRequest: BcpStartRequest) {
    service.initiateCalls(bcpStartRequest)
  }

  @GetMapping("/stats/{number}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Calculate stats")
  fun getStats(@PathVariable number: String, @RequestParam minutes: Long): BcpStats {
    return service.calculateStats(number, minutes)
  }

  @GetMapping("/stats/contacts/{number}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Calculate individual stats for each Contact")
  fun calculateContactsStats(@PathVariable number: String) : List<BcpContactStats> {
    return service.contactsStats(number)
  }

  @PostMapping("/twilio", produces = [MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Reply to incoming SMS (Twilio specific only)")
  fun replyToIncomingSms(@RequestBody body: String): String {
    return service.replyToSms(body)
  }

  @GetMapping("/twilio-numbers", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Get all registered Twilio numbers")
  fun getAllTwilioNumbers(): List<String> {
    return service.fetchTwilioNumbers()
  }

  @GetMapping("/twilio/events", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Get all active events")
  fun checkActiveEvents(): List<BcpEventDto> {
    return service.checkEvent()
  }

  @GetMapping("/terminate/{twilioNumber}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Terminate a BCP event")
  fun terminateCallTree(@PathVariable twilioNumber: String) {
    service.endEvent(twilioNumber)
  }
}