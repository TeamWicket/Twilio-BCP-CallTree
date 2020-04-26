package org.wicket.calltree.services.utils.helper

import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Component

/**
 * @author Alessandro Arosio - 11/04/2020 18:04
 */
@Component
class PhoneNumberMapperHelper {

  fun asString(phoneNumber: PhoneNumber) : String {
    return phoneNumber.endpoint
  }

  fun asPhoneNumber(string: String) : PhoneNumber {
    return PhoneNumber(string)
  }
}