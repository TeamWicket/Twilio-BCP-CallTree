package org.wicket.calltree.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.wicket.calltree.models.BcpEvent
import org.wicket.calltree.models.BcpMessage
import org.wicket.calltree.models.TwilioNumber
import java.util.*
import javax.validation.constraints.NotNull

interface BcpMessageRepository : JpaRepository<BcpMessage, Long> {
    fun findFirstByRecipientNumberAndBcpEvent_TwilioNumber_TwilioNumberAndBcpEvent_IsActive(recipientNumber: String,
                                                                                            twilioNumber: String,
                                                                                            @NotNull isActive:Boolean): BcpMessage
    fun findAllByBcpEvent_Id(bcpEventId: Long): List<BcpMessage>
    fun findAllByBcpEvent_Id(bcpEventId: Long, pageable: Pageable): Page<BcpMessage>
}

interface BcpEventRepository : JpaRepository<BcpEvent, Long> {
    fun findByTwilioNumber_Id(twilioNumberId: Long): Optional<BcpEvent>
    fun findTopByOrderByIdDesc(): Optional<BcpEvent>
}

interface TwilioNumberRepository : JpaRepository<TwilioNumber, Long> {
    fun findAllByIsAvailable(isAvailable: @NotNull Boolean): List<TwilioNumber>
}