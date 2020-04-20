package org.wicket.calltree.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.calltree.model.BcpStats;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alessandro Arosio - 20/04/2020 22:12
 */
@SpringBootTest
class CallTreeServiceImplIT {

    @Autowired
    CallTreeService callTreeService;

    @Test
    void calculateStats() {
        String twilioNumber = "+0132456";
        long time = 5L;

        BcpStats bcpStats = callTreeService.calculateStats(twilioNumber, time);

        assertNotNull(bcpStats);
    }
}