package org.wicket.calltree.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alessandro Arosio - 20/04/2020 18:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcpStats {

    private Double average;
    private Integer messagesSent;
    private Integer messagesReceived;
    private Double replyPercentageWithinXMinutes;
}
