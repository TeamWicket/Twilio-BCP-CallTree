package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author Alessandro Arosio - 13/04/2020 22:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundSmsDto {

    @Nullable
    private Long id;

    @NotNull
    private String dateCreated;

    private String dateUpdated;

    private String dateSent;

    private String fromNumber;

    @NotNull
    private String toNumber;

    @NotNull
    private String status;

    private Long version;
}
