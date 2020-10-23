package org.wicket.calltree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.models.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByRoleEquals(Role role);
    Optional<Contact> findByPhoneNumber(String phoneNumber);
    List<Contact> findAllContactsByIdIn(List<Long> ids);
}
