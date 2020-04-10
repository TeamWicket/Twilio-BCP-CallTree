package org.wicket.calltree.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.exceptions.ContactException;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.models.Contact;
import org.wicket.calltree.repository.ContactRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Alessandro Arosio - 07/04/2020 20:23
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository repository;
    private final ContactMapper mapper;

    @Override
    public List<ContactDto> saveList(List<ContactDto> dtoList) {
        List<Contact> contactList = dtoList.stream()
                .map(mapper::dtoToContact)
                .collect(Collectors.toList());
        List<Contact> persistedList = repository.saveAll(contactList);
        return persistedList.stream()
                .map(mapper::contactToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto saveOrUpdate(ContactDto contactDto) {
        Optional<Contact> contact = Optional.empty();

        if (contactDto.getId() != null) {
            contact = repository.findById(contactDto.getId());
        }

        if (contact.isPresent()) {
            Contact contactToUpdate = mapper.dtoToContact(contactDto);
            Contact updatedContact = repository.save(contactToUpdate);
            return mapper.contactToDto(updatedContact);
        }
        Contact newContact = mapper.dtoToContact(contactDto);
        Contact savedContact = repository.save(newContact);

        return mapper.contactToDto(savedContact);
    }

    @Override
    public void deleteContact(Long id) {
        Optional<Contact> contact = repository.findById(id);
        contact.ifPresent(repository::delete);
    }

    @Override
    public List<ContactDto> getAllContacts() {
        return repository.findAll().stream()
                .map(mapper::contactToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContact(Long id) {
        Optional<Contact> contact = repository.findById(id);
        return contact.map(mapper::contactToDto)
                .orElseThrow(() -> new ContactException("Contact not found wit ID: " + id));
    }
}
