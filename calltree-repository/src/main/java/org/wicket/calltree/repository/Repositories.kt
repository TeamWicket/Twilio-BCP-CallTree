package org.wicket.calltree.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wicket.calltree.models.BcpEvent
import org.wicket.calltree.models.InboundSms
import org.wicket.calltree.models.OutboundSms
import org.wicket.calltree.models.TwilioNumber
import java.util.*
import javax.validation.constraints.NotNull

/**
 * @author Alessandro Arosio - 13/04/2020 22:35
 */

interface InboundSmsRepository : JpaRepository<InboundSms, Long> {
    fun findAllByToTwilioNumber(twilioNumber: String): List<InboundSms>
    fun deleteAllByToTwilioNumber(number: String)
}

interface OutBoundSmsRepository : JpaRepository<OutboundSms, Long> {
    fun findAllByBcpEventAndToNumber(event: BcpEvent, twilioNumber: String): List<OutboundSms>
    fun findAllByFromNumber(twilioNumber: String): List<OutboundSms>
}

interface BcpEventRepository : JpaRepository<BcpEvent, Long> {
    fun findByTwilioNumber_Id(twilioNumberId: Long): Optional<BcpEvent>
    fun findAllByEventNameAndTwilioNumber(eventName: String, twilioNumber: String): List<BcpEvent>
    fun countAllByEventNameAndTwilioNumber(eventName: String, twilioNumber: String): Int
}

interface TwilioNumberRepository : JpaRepository<TwilioNumber, Long> {
    fun findByTwilioNumber(number: String): Optional<TwilioNumber>
    fun findAllByIsAvailable(isAvailable: @NotNull Boolean): List<TwilioNumber>
}