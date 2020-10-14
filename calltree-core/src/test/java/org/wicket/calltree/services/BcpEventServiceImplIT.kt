package org.wicket.calltree.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.exceptions.BcpEventException
import org.wicket.calltree.mappers.TwilioNumberMapper
import org.wicket.calltree.models.TwilioNumber
import org.wicket.calltree.repository.TwilioNumberRepository
import kotlin.test.assertEquals

@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BcpEventServiceImplIT {
    lateinit var twilioNumberDto: TwilioNumberDto
    lateinit var persistedNumber: TwilioNumber

    @Autowired
    lateinit var bcpEventService: BcpEventService

    @Autowired
    lateinit var twilioNumberMapper: TwilioNumberMapper

    @Autowired
    lateinit var twilioNumberRepository: TwilioNumberRepository

    @BeforeAll
    internal fun beforeAll() {
        val twilioNumber = TwilioNumber(null, "+0987", true)
        persistedNumber = twilioNumberRepository.save(twilioNumber)
        twilioNumberDto = twilioNumberMapper.entityToDto(persistedNumber)
    }

    @Order(0)
    @Test
    internal fun testGetAllEvents() {
        assertThat(bcpEventService.getAllEvents()).hasSize(1)
    }

    @Order(1)
    @Test
    fun saveEvent() {
        val newEvent = BcpEventDto(null, "JUNIT", null, twilioNumberDto, true, null)
        bcpEventService.saveEvent(newEvent)
        assertThat(bcpEventService.getAllEvents()).hasSize(2)
    }

    @Order(2)
    @Test
    fun getEventByNumber() {
        val event = bcpEventService.getEventByNumber(twilioNumberDto.id ?: 0)
        assertEquals("JUNIT", event.eventName)

    }

    @Order(3)
    @Test
    internal fun testGetEventByNumber_EventNotFound_ThrowsException() {
        assertThrows<BcpEventException> { bcpEventService.getEventByNumber(twilioNumberDto.id ?: 0) }

    }

    @AfterAll
    internal fun afterAll() {
        twilioNumberRepository.delete(persistedNumber);
    }
}