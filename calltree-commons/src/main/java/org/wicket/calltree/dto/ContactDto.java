package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Alessandro Arosio - 04/04/2020 09:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @Nullable
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Role role;

    @NotNull
    @Size(min = 1)
    private List<CallingOption> callingOption;

    @Nullable
    private Long pointOfContactId;
}
