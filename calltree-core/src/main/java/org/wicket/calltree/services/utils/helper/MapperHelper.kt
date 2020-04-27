package org.wicket.calltree.services.utils.helper

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Component
import org.wicket.calltree.enums.SmsStatus

/**
 * @author Alessandro Arosio - 11/04/2020 18:04
 */
@Component
class MapperHelper {

  fun asString(phoneNumber: PhoneNumber) : String {
    return phoneNumber.endpoint
  }

  fun asPhoneNumber(string: String) : PhoneNumber {
    return PhoneNumber(string)
  }

  fun asTwilioStatus(smsStatus: SmsStatus): Message.Status {
    return Message.Status.valueOf(smsStatus.name)
  }

  fun asSmsStatus(smsStatus: Message.Status): SmsStatus {
    return SmsStatus.valueOf(smsStatus.name)
  }
}