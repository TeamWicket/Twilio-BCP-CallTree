package org.wicket.calltree.controllers.v1

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
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

  @GetMapping
  @Operation(summary = "Fetch all contacts")
  fun fetchAllContacts() : List<ContactDto> {
    return contactService.allContacts
  }

  @GetMapping("/{id}")
  @Operation(summary = "Fetch single contact by ID")
  fun fetchContact(@PathVariable @Valid id: Long) : ContactDto {
    return contactService.getContact(id)
  }

  @GetMapping("/role/{role}")
  @Operation(summary = "Fetch all contacts belonging to specific ROLE")
  fun fetchContactsOfOneRole(@PathVariable role: String) : List<ContactDto> {
    return contactService.getAllSelectedRole(Role.valueOf(role.toUpperCase()))
  }

  @GetMapping("/tree/{role}")
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Save contact")
  fun saveContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update contact")
  fun updateContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }
}