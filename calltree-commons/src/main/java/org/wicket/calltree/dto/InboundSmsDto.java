package org.wicket.calltree.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author Alessandro Arosio - 13/04/2020 10:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class InboundSmsDto {

    @Nullable
    private Long id;

    private String toCountry;

    @NotNull
    private String smsStatus;

    @NotNull
    private String body;
    private String fromCountry;

    @NotNull
    private String fromContactNumber;

    @NotNull
    private String toTwilioNumber;

    @NotNull
    private BcpEventDto bcpEvent;

    @NotNull
    private String timestamp;

    private Long version;
}
