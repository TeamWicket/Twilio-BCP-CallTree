package org.wicket.calltree.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "twilio_numbers")
public class TwilioNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Nullable
    private Long id;

    @Column(name = "twilio_number", unique = true)
    @NotNull
    @NotBlank
    private String twilioNumber;

    @Column(name = "is_available")
    @NotNull
    private Boolean isAvailable;
}
