package org.wicket.calltree.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wicket.calltree.models.BcpEvent
import org.wicket.calltree.models.InboundSms
import org.wicket.calltree.models.BcpEventSms
import org.wicket.calltree.models.TwilioNumber
import java.util.*
import javax.validation.constraints.NotNull

/**
 * @author Alessandro Arosio - 13/04/2020 22:35
 */

interface BcpEventSmsRepository : JpaRepository<BcpEventSms, Long> {
    fun findAllByBcpEventAndRecipientNumber(event: BcpEvent, twilioNumber: String): List<BcpEventSms>
    fun findAllByBcpEvent_Id(bcpEventId: Long): List<BcpEventSms>
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