package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "bcp_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "twilio_numbers")
    @NotNull
    private TwilioNumber twilioNumber;

    @Column(name = "is_active")
    @NotNull
    private Boolean isActive;

    @Version
    private Long version;
}
