package org.wicket.calltree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.models.Contact;

import java.util.List;
import java.util.Optional;

/**
 * @author Alessandro Arosio - 05/04/2020 14:38
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByRoleEquals(Role role);

    Optional<Contact> findByPhoneNumber(String phoneNumber);
}
