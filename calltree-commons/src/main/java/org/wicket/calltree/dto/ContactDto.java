package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.Role;
import org.wicket.calltree.validators.NotNullForNonChampion;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private Long pointOfContactId;

    private Long version;
}
