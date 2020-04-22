package org.wicket.calltree.services

import org.wicket.calltree.dto.TwilioNumberDto

interface TwilioNumberService {
    fun getAllNumbers(): List<TwilioNumberDto>
    fun saveNewNumber(newNumberDto: TwilioNumberDto): TwilioNumberDto
    fun deleteNumber(numberDto: TwilioNumberDto)
    fun getAvailableNumbers(): List<TwilioNumberDto>
    fun findByNumber(number: String): TwilioNumberDto
}