package org.wicket.calltree.controllers.v1

import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.wicket.calltree.dto.ContactDto
import org.wicket.calltree.services.ContactService
import javax.validation.Valid

/**
 * @author Alessandro Arosio - 10/04/2020 10:16
 */
@RestController
@RequestMapping("/api/v1/contacts")
class ContactController(private val contactService: ContactService) {

  @GetMapping("/all")
  fun fetchAllContacts() : List<ContactDto> {
    return contactService.allContacts
  }

  @GetMapping("/{id}")
  fun fetchContact(@PathVariable @Valid id: Long) : ContactDto {
    return contactService.getContact(id)
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun removeContact(@RequestBody @Valid contactDto: ContactDto) {
    contactService.deleteContact(contactDto.id)
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun saveContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }

  @PutMapping
  fun updateContact(@RequestBody @Valid contactDto: ContactDto) : ContactDto {
    return contactService.saveOrUpdate(contactDto)
  }
}