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

    override fun saveNewNumber(newNumberDto: TwilioNumberDto): TwilioNumberDto {
        val number = twilioNumberMapper.dtoToEntity(newNumberDto)
        val persisted = numberRepository.save(number)
        return twilioNumberMapper.entityToDto(persisted)
    }

    override fun deleteNumber(numberDto: TwilioNumberDto) {
        val findById = numberRepository.findById(numberDto.id)
        findById.ifPresent { numberRepository.delete(it) }
    }

    override fun getAvailableNumbers(): List<TwilioNumberDto> {
        val numbersAvailable = numberRepository.findAllByIsAvailable(true)
        return numbersAvailable.stream()
            .map { twilioNumberMapper.entityToDto(it) }
            .collect(Collectors.toList())
    }

    override fun getNumberById(id: Long): TwilioNumberDto {
        val number = numberRepository.findById(id)
        number.get().let { return twilioNumberMapper.entityToDto(it) }
    }
}