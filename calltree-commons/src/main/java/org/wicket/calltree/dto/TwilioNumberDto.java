package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwilioNumberDto {
    @Nullable
    private Long id;

    @NotNull
    @NotBlank
    private String twilioNumber;

    @Nullable
    private Boolean isAvailable;
}
