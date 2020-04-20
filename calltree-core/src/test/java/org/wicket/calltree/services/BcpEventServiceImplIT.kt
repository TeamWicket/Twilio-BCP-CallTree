package org.wicket.calltree.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer.*
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.exceptions.BcpEventException
import kotlin.test.assertEquals

/**
 * @author Alessandro Arosio - 20/04/2020 17:38
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
internal class BcpEventServiceImplIT {

  @Autowired
  lateinit var bcpEventService: BcpEventService

  @Order(0)
  @Test
  internal fun testGetAllEvents() {
    assertThat(bcpEventService.getAllEvents()).hasSize(1)
  }

  @Order(1)
  @Test
  fun deleteEventByTwilioNumber() {
    val newEvent =  BcpEventDto(null, "JUNIT", null, "+0987", null)
    bcpEventService.saveEvent(newEvent)
    assertThat(bcpEventService.getAllEvents()).hasSize(2)
  }

  @Order(2)
  @Test
  fun getEventByNumber() {
    val event = bcpEventService.getEventByNumber("+0987")
    assertEquals("JUNIT", event.eventName)

  }

  @Order(3)
  @Test
  internal fun testGetEventByNumber_EventNotFound_ThrowsException() {
    assertThrows<BcpEventException> { bcpEventService.getEventByNumber("+323522352") }

  }
}