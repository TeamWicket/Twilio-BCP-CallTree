package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.ContactDto
import org.wicket.calltree.enums.Role
import org.wicket.calltree.services.ContactService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/contacts")
class ContactController(private val contactService: ContactService) {

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Fetch all contacts")
  fun fetchAllContacts(@RequestParam(required = false) _end: Int?,
                       @RequestParam(required = false) _order: String?,
                       @RequestParam(required = false) _sort: String?,
                       @RequestParam(required = false) _start: Int?) : ResponseEntity<List<ContactDto>?> {

    val headers = HttpHeaders()
    val num = contactService.numContacts
    headers.add("X-Total-Count", num.toString())

    return ResponseEntity.accepted().headers(headers).body(contactService.getAllContacts(_order, _sort, _start, _end ))
  }

  @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Fetch single contact by ID")
  fun fetchContact(@PathVariable @Valid id: Long) : ContactDto {
    return contactService.getContact(id)
  }

  @GetMapping("/role/{role}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Fetch all contacts belonging to specific ROLE")
  fun fetchContactsOfOneRole(@PathVariable role: String) : List<ContactDto> {
    return contactService.getAllSelectedRole(Role.valueOf(role.toUpperCase()))
  }

  @GetMapping("/tree/{role}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Fetch all contacts from MANAGERS to selected ROLE")
  fun fetchTreeUntilRole(@PathVariable role: String) : List<ContactDto> {
    return contactService.getCalltreeUntilRole(Role.valueOf(role.toUpperCase()))
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Remove contact")
  fun removeContact(@PathVariable @Valid id:Long) {
    contactService.deleteContact(id)
  }

  @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Save contact")
  fun saveContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }

  @PutMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Update contact")
  fun updateContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }

  @GetMapping("/many", produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Get many contacts by ID")
  fun getMany(@RequestParam vararg id: Long): List<ContactDto> {
    return contactService.fetchManyContactsById(id)
  }
}