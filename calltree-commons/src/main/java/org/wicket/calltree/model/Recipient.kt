package org.wicket.calltree.model

import com.twilio.type.PhoneNumber

data class Recipient(val receiver: PhoneNumber, val message: String) {
  val sender: PhoneNumber = PhoneNumber("+447380328921")
}