package org.wicket.calltree.services

import org.springframework.data.domain.Page
import org.wicket.calltree.dto.BcpEventDto
import org.wicket.calltree.dto.Response
import org.wicket.calltree.model.BcpStartRequest
import org.wicket.calltree.models.BcpEvent

/**
 * @author Alessandro Arosio - 11/04/2020 13:16
 */
interface CallTreeService {
  fun initiateCalls(bcpStartRequest: BcpStartRequest) : List<Response>

  fun replyToSms(body: String) : String

  fun endEvent(bcpEventDto: BcpEventDto)

  fun checkEvent(): List<BcpEventDto>

  fun pagedEvents(page: Int, size: Int): Page<BcpEvent>
}