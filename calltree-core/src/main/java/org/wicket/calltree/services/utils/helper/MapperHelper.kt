package org.wicket.calltree.services.utils.helper

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Component
import org.wicket.calltree.enums.SmsStatus

@Component
class MapperHelper {

  fun asString(phoneNumber: PhoneNumber) : String {
    return phoneNumber.endpoint
  }
}