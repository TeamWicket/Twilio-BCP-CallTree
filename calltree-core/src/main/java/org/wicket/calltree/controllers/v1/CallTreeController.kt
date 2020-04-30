package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.model.BcpStartRequest
import org.wicket.calltree.models.BcpEvent
import org.wicket.calltree.services.CallTreeService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/events")
class CallTreeController(private val service: CallTreeService) {

  @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Create and Initiate BCP event")
  fun startCalls(@RequestBody @Valid bcpStartRequest: BcpStartRequest) : Long {
    return service.initiateCalls(bcpStartRequest)
  }

  @PostMapping("/twilio", produces = [MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Reply to incoming SMS (Twilio specific only)")
  fun replyToIncomingSms(@RequestBody body: String): String {
    return service.replyToSms(body)
  }

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Get all events")
  fun checkActiveEvents(@RequestParam _start: Int,
                        @RequestParam _end: Int): ResponseEntity<List<BcpEvent>> {
    val totalEvents = service.pagedEvents(_start, _end)
    val map = HttpHeaders()
    map["X-Total-Count"] = totalEvents.totalElements.toString()

    return ResponseEntity<List<BcpEvent>>(totalEvents.content, map, HttpStatus.OK);
  }

  @GetMapping("/terminate/{twilioNumber}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Terminate a BCP event")
  fun terminateCallTree(@PathVariable bcpEventDto: BcpEventDto) {
    service.endEvent(bcpEventDto)
  }

  @GetMapping("/many", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Get many events by ID")
  fun getMany(@RequestParam vararg id: Long): ResponseEntity<List<BcpEvent>> {
    val totalEvents = service.pagedEvents(0, 20)
    val map = HttpHeaders()
    map["X-Total-Count"] = totalEvents.totalElements.toString()

    return ResponseEntity<List<BcpEvent>>(totalEvents.content, map, HttpStatus.OK);
  }
}