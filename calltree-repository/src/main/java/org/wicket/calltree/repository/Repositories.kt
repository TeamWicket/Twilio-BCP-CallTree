package org.wicket.calltree.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wicket.calltree.models.InboundSms
import org.wicket.calltree.models.OutboundSms

/**
 * @author Alessandro Arosio - 13/04/2020 22:35
 */

interface InboundSmsRepository: JpaRepository<InboundSms, Long>

interface OutBoundSmsRepository: JpaRepository<OutboundSms, Long>