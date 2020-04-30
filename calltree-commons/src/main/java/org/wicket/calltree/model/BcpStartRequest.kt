package org.wicket.calltree.model

import org.wicket.calltree.enums.Role
import java.time.ZonedDateTime

data class BcpStartRequest(val text: String,
                           val toRoles: Role = Role.REPORTER,
                           val eventName: String,
                           val twilioNumberId: Long,
                           val timestamp: ZonedDateTime = ZonedDateTime.now())
