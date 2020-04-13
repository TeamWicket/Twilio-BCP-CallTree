package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alessandro Arosio - 13/04/2020 22:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundSmsDto {
    private String dateCreated;
    private String dateUpdated;
    private String dateSent;
    private String fromNumber;
    private String toNumber;
    private String status;
}
