package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alessandro Arosio - 12/04/2020 09:30
 */
@SpringBootTest
public class ContactServiceImplIT {

    @Autowired
    ContactService contactService;

    @Test
    void testFindSelectedRole_ReturnOnlyContactsWithSelectedRole() {
        List<ContactDto> allSelectedRole = contactService.getAllSelectedRole(Role.LEADER);

        assertThat(allSelectedRole).hasSize(1);
        assertEquals("Ralph", allSelectedRole.get(0).getFirstName());
        assertEquals("Johnson", allSelectedRole.get(0).getLastName());
        assertThat(allSelectedRole.get(0).getCallingOption()).hasSize(2);
        assertEquals(Role.LEADER, allSelectedRole.get(0).getRole());
    }

    @Test
    void testGetAllTreeUntilRoleLeader_ReturnsAllUntilLeader() {
        List<ContactDto> tree = contactService.getCalltreeUntilRole(Role.LEADER);
        List<ContactDto> reporters = contactService.getAllSelectedRole(Role.REPORTER);

        assertThat(reporters).hasSize(1);
        assertEquals(Role.REPORTER, reporters.get(0).getRole());

        assertThat(tree).hasSize(2);
        assertThat(tree).doesNotContain(reporters.get(0));
    }
}
