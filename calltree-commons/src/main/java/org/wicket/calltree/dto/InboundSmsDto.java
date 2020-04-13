package org.wicket.calltree.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alessandro Arosio - 13/04/2020 10:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class InboundSmsDto {

    private String toCountry;
    private String smsStatus;
    private String body;
    private String fromCountry;
    private String from;
}
