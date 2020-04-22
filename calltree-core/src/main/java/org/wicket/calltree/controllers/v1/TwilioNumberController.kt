package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.services.TwilioNumberService

@RestController
@RequestMapping("/api/v1/numbers")
class TwilioNumberController(private val numberService: TwilioNumberService) {

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Fetch all numbers")
    fun fetchAllNumbers(): List<TwilioNumberDto> {
        return numberService.getAllNumbers()
    }
}