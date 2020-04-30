package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author Alessandro Arosio - 15/04/2020 22:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BcpEventDto {

    @Nullable
    private Long id;

    @NotNull
    private String eventName;

    @Nullable
    @NotBlank
    private String timestamp = ZonedDateTime.now().toString();

    @NotNull
    @NotBlank
    private TwilioNumberDto twilioNumber;

    private Boolean isActive;

    @Nullable
    private Long version;
}
