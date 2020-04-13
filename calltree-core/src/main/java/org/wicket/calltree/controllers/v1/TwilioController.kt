package org.wicket.calltree.controllers.v1

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wicket.calltree.dto.InboundSmsDto
import org.wicket.calltree.services.CallTreeService

/**
 * @author Alessandro Arosio - 13/04/2020 08:56
 */
@RestController
@RequestMapping("/api/v1/twilio")
class TwilioController(private val callTreeService: CallTreeService) {

  @PostMapping(produces = [MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE])
  fun replyToIncomingSms(@RequestBody body: String): String {
    return callTreeService.replyToSms(body)
  }
}