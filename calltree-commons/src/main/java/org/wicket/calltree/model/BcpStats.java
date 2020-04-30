package org.wicket.calltree.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcpStats {

    private Double totalAverage;
    private Integer messagesSent;
    private Integer messagesReceived;
    private Double replyPercentageWithinXMinutes;
}
