package org.wicket.calltree.services.utils

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

/**
 * @author Alessandro Arosio - 13/04/2020 20:13
 */
fun zonedDateTimeDifference(sent: String, received: String, unit: ChronoUnit) : Long {
  val timeSent = ZonedDateTime.parse(sent)
  val timeReceived = ZonedDateTime.parse(received)
  return unit.between(timeSent, timeReceived)
}