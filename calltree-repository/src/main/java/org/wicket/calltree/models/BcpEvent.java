package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author Alessandro Arosio - 15/04/2020 22:44
 */
@Entity
@Table(name = "bcp_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BcpEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "event_name")
    @NotNull
    @NotBlank
    private String eventName;

    @Column(name = "timestamp")
    @Nullable
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @Column(name = "twilio_number")
    @NotNull
    @NotBlank
    private String twilioNumber;

    @Version
    private Long version;
}
