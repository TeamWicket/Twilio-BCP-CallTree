package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.ContactDto
import org.wicket.calltree.enums.Role
import org.wicket.calltree.services.ContactService
import javax.validation.Valid

/**
 * @author Alessandro Arosio - 10/04/2020 10:16
 */
@RestController
@RequestMapping("/api/v1/contacts")
class ContactController(private val contactService: ContactService) {

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Fetch all contacts")
  fun fetchAllContacts(@RequestParam(required = false) orderDirection: String?,
                       @RequestParam(required = false) orderByValue: String?,
                       @RequestParam(required = false) page: Int?,
                       @RequestParam(required = false) size: Int?) : List<ContactDto> {

    return contactService.getAllContacts(orderDirection, orderByValue, page, size)
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

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Remove contact")
  fun removeContact(@RequestBody @Valid contactDto: ContactDto) {
    contactService.deleteContact(contactDto.id)
  }

  @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Save contact")
  fun saveContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }

  @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  @Operation(summary = "Update contact")
  fun updateContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }
}