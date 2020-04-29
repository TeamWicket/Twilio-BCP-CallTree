package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.models.TwilioNumber
import org.wicket.calltree.repository.TwilioNumberRepository
import org.wicket.calltree.services.TwilioNumberService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/numbers")
class TwilioNumberController(private val numberService: TwilioNumberService, private val repo: TwilioNumberRepository) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Fetch all numbers")
    fun fetchAllNumbers(@RequestParam _start: Int,
                        @RequestParam _end: Int,
                        @RequestParam(required = false) _getAvail: Boolean = false): ResponseEntity<List<TwilioNumber>> {
        val result : Page<TwilioNumber>
        val map = HttpHeaders()

        if (_getAvail){
            map["X-Total-Count"] = repo.findAllByIsAvailable(true).count().toString()
            return ResponseEntity<List<TwilioNumber>>(repo.findAllByIsAvailable(true), map, HttpStatus.OK)
        }
        else{
            result = numberService.getAllNumbers(_start, _end)
        }
        map["X-Total-Count"] = result.totalElements.toString()
        return ResponseEntity<List<TwilioNumber>>(result.content, map, HttpStatus.OK)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Save new Twilio numbers for BCP calls")
    fun addNewNumber(@RequestBody number: @Valid TwilioNumberDto): TwilioNumberDto {
        return numberService.saveNewNumber(number)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNumber(@RequestBody numberDto: @Valid TwilioNumberDto) {
        numberService.deleteNumber(numberDto)
    }

    @GetMapping("/available", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get all available Twilio numbers")
    fun getAvailableNumbers(): List<TwilioNumberDto> {
        return numberService.getAvailableNumbers()
    }

    @GetMapping("/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Search Twilio number by id")
    fun searchById(@PathVariable id: Long): TwilioNumberDto {
        return numberService.getNumberById(id)
    }

    @GetMapping("/many",produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Search Twilio number by id")
    fun getManyNumbers(@RequestParam(required = false) active: Boolean = true, @RequestParam vararg id: Long): List<TwilioNumberDto> {
        return numberService.getManyNums(active, id)
    }
}