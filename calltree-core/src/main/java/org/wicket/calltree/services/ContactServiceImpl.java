package org.wicket.calltree.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.mappers.ContactMapper;
import org.wicket.calltree.repository.ContactRepository;

import java.util.List;

/**
 * @author Alessandro Arosio - 07/04/2020 20:23
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository repository;
    private final ContactMapper mapper;

    @Override
    public ContactDto saveOrUpdate(ContactDto contactDto) {
        return null;
    }

    @Override
    public void deleteContact(ContactDto contactDto) {

    }

    @Override
    public List<ContactDto> getAllContacts() {
        return null;
    }

    @Override
    public ContactDto getContact(ContactDto contactDto) {
        return null;
    }
}
