package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.model.BcpStats;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandro Arosio - 20/04/2020 22:12
 */
@SpringBootTest
class StatsServiceImplIT {

    @Autowired
    StatsService statsService;

    @Test
    void calculateStats() {
        TwilioNumberDto twilioNumber = new TwilioNumberDto(1L, "+0132456", true);
        long time = 5L;

        BcpStats bcpStats = statsService.calculateStats(twilioNumber.getId(), time);

        assertNotNull(bcpStats);
    }
}