package org.wicket.calltree.models;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.CallingOption;
import org.wicket.calltree.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alessandro Arosio - 05/04/2020 14:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private List<CallingOption> callingOption;

    @Nullable
    private Long pointOfContactId;
}
