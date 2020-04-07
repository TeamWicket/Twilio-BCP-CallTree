package org.wicket.calltree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wicket.calltree.models.Contact;

/**
 * @author Alessandro Arosio - 05/04/2020 14:38
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
