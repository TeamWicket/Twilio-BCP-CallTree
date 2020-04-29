package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.model.BcpStats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Alessandro Arosio - 26/04/2020 20:36
 */
@SpringBootTest
public class StatsServiceImplIT {

    @Autowired
    StatsService statsService;

    @Test
    void calculateStats_WithValidRequest_ReturnsStats() {
        BcpStats bcpStats = statsService.calculateStats(1L, 5L);

        assertNotNull(bcpStats);
        assertEquals(2, bcpStats.getMessagesSent());
        assertEquals(1, bcpStats.getMessagesReceived());
        assertEquals(50.00, bcpStats.getReplyPercentageWithinXMinutes());
    }
}
