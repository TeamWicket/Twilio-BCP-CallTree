package org.wicket.calltree.services

import org.springframework.stereotype.Service
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.mappers.TwilioNumberMapper
import org.wicket.calltree.repository.TwilioNumberRepository
import java.util.stream.Collectors

@Service
class TwilioNumberServiceImpl(private val numberRepository: TwilioNumberRepository,
                              private val twilioNumberMapper: TwilioNumberMapper) : TwilioNumberService {

    override fun getAllNumbers(): List<TwilioNumberDto> {
        return numberRepository.findAll().stream()
                .map { twilioNumberMapper.entityToDto(it) }
                .collect(Collectors.toList())
    }
}