package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.validators.NotNullForNonChampion;

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
@NotNullForNonChampion(fieldName = "role", fieldValue = Role.CHAMPION, dependFieldName = "pointOfContactId")
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

    private Long pointOfContactId;
}
