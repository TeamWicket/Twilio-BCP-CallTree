package org.wicket.calltree.services

import org.wicket.calltree.dto.TwilioNumberDto

interface TwilioNumberService {
    fun getAllNumbers(): List<TwilioNumberDto>
}