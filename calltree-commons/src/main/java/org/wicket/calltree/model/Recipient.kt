package org.wicket.calltree.model

import com.twilio.type.PhoneNumber

/**
 * @author Alessandro Arosio - 11/04/2020 14:07
 */
data class Recipient(val receiver: PhoneNumber, val message: String) {
  val sender: PhoneNumber = PhoneNumber("+12029527819")
}