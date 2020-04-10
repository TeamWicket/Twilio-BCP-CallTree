package org.wicket.calltree.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.services.ContactService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alessandro Arosio - 07/04/2020 22:25
 */
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/{id}")
    public ContactDto fetchContact(@PathVariable @Valid Long id) {
        return contactService.getContact(id);
    }

    @GetMapping("/all")
    public List<ContactDto> fetchAllContacts() {
        return contactService.getAllContacts();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeContact(@RequestBody @Valid ContactDto contactDto) {
        contactService.deleteContact(contactDto.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDto saveContact(@RequestBody @Valid ContactDto contactDto) {
        return contactService.saveOrUpdate(contactDto);
    }

    @PutMapping
    public ContactDto updateContact(@RequestBody @Valid ContactDto contactDto) {
        return contactService.saveOrUpdate(contactDto);
    }
}
