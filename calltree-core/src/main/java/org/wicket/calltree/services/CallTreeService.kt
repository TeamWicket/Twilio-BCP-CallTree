package org.wicket.calltree.services

import org.springframework.data.domain.Page
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.model.BcpStartRequest
import org.wicket.calltree.models.BcpEvent

interface CallTreeService {
  fun initiateCalls(bcpStartRequest: BcpStartRequest) : Long

  fun replyToSms(body: String) : String

  fun endEvent(bcpEventDto: BcpEventDto)

  fun pagedEvents(page: Int, size: Int): Page<BcpEvent>
}