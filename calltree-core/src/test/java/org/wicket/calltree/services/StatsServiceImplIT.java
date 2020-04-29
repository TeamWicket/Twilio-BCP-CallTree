package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.model.BcpStats;

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
        TwilioNumberDto twilioNumber = new TwilioNumberDto(1L, "+0132456", true);
        long time = 5L;

        BcpStats bcpStats = statsService.calculateStats(twilioNumber.getId(), time);

        assertNotNull(bcpStats);
    }
}
