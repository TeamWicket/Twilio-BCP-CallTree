package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;

import java.util.List;

/**
 * @author Alessandro Arosio - 04/04/2020 09:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private List<CallingOption> callingOption;
    private Long pointOfContactId;
}
