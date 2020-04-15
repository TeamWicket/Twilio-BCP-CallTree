package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
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
public class BcpEventDto {

    @Nullable
    private Long id;

    @NotNull
    private String eventName;

    @Nullable
    @NotBlank
    private String timestamp = ZonedDateTime.now().toString();

    @Nullable
    private Long version;
}
