package org.wicket.calltree.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wicket.calltree.dto.TwilioNumberDto
import org.wicket.calltree.mappers.TwilioNumberMapper
import org.wicket.calltree.models.TwilioNumber
import org.wicket.calltree.repository.TwilioNumberRepository
import java.util.*
import java.util.Collections.singletonList

@ExtendWith(SpringExtension::class)
class TwilioNumberServiceImplTest {

    @Mock
    lateinit var twilioNumber: TwilioNumber
    @Mock
    lateinit var twilioNumberDto: TwilioNumberDto
    @Mock
    lateinit var twilioNumberMapper: TwilioNumberMapper
    @Mock
    lateinit var numberRepository: TwilioNumberRepository
    @InjectMocks
    lateinit var numberService: TwilioNumberServiceImpl

    @Test
    internal fun getAllNumbers_ReturnsTheValueOfNumberRepository() {
        val page = 1
        val size = 1
        val pageRequest: PageRequest = PageRequest.of(page, size)
        val pageResult: Page<TwilioNumber> = PageImpl(singletonList(twilioNumber))

        `when`(numberRepository.findAllByActiveIsTrue(pageRequest)).thenReturn(pageResult)

        val result = numberService.getAllNumbers(page, size)
        val number = result.get().findFirst().get()

        assertThat(number).isEqualTo(twilioNumber)
    }

    @Test
    internal fun saveNumber_WithExistingNumber_CallsSaveWithExistingNumber() {
        val inputNumber = "+4478637469383"

        `when`(twilioNumberDto.twilioNumber).thenReturn(inputNumber)
        `when`(numberRepository.findFirstByTwilioNumber(inputNumber)).thenReturn(Optional.of(twilioNumber))
        `when`(twilioNumberMapper.entityToDto(any())).thenReturn(twilioNumberDto)

        numberService.saveNumber(twilioNumberDto)

        verify(numberRepository, atLeastOnce()).save(twilioNumber)
    }

    @Test
    internal fun saveNumber_WithNewNumber_CallsSaveWithConvertedNumber() {
        val inputNumber = "+4478637469383"
        val newNumber = mock(TwilioNumber::class.java)

        `when`(twilioNumberDto.twilioNumber).thenReturn(inputNumber)
        `when`(numberRepository.findFirstByTwilioNumber(inputNumber)).thenReturn(Optional.empty())
        `when`(twilioNumberMapper.dtoToEntity(twilioNumberDto)).thenReturn(newNumber)
        `when`(twilioNumberMapper.entityToDto(any())).thenReturn(twilioNumberDto)

        numberService.saveNumber(twilioNumberDto)

        verify(numberRepository, atLeastOnce()).save(newNumber)
    }

    @Test
    internal fun getAvailableNumbers_ReturnsAllAvailableAndActiveNumbers() {
        `when`(numberRepository.findAllByIsAvailableIsTrueAndActiveIsTrue())
                .thenReturn(singletonList(twilioNumber))
        `when`(twilioNumberMapper.entityToDto(twilioNumber)).thenReturn(twilioNumberDto)

        val result = numberService.getAvailableNumbers()

        assertThat(result).hasSize(1)
        assertThat(result).contains(twilioNumberDto)
    }

    @Test
    internal fun getNumberById_ReturnsValueFromRepository() {
        val id = 1L

        `when`(numberRepository.findByIdAndActiveIsTrue(id)).thenReturn(Optional.of(twilioNumber))
        `when`(twilioNumberMapper.entityToDto(twilioNumber)).thenReturn(twilioNumberDto)

        val result = numberService.getNumberById(id)

        assertThat(result).isEqualTo(twilioNumberDto)
    }

    @Test
    internal fun getManyNumbers_ReturnsMatchingValuesFromRepository() {
        val id = 1L
        val otherId = 2L
        val badId = 3L
        val otherNumber = mock(TwilioNumber::class.java)
        val badNumber = mock(TwilioNumber::class.java)
        val otherDto = mock(TwilioNumberDto::class.java)

        `when`(twilioNumber.id).thenReturn(id)
        `when`(otherNumber.id).thenReturn(otherId)
        `when`(badNumber.id).thenReturn(badId)

        `when`(numberRepository.findAllByActiveIsTrue()).thenReturn(listOf(twilioNumber, otherNumber, badNumber))
        `when`(twilioNumberMapper.entityToDto(twilioNumber)).thenReturn(twilioNumberDto)
        `when`(twilioNumberMapper.entityToDto(otherNumber)).thenReturn(otherDto)

        val result = numberService.getManyNumbers(true, id, otherId)

        assertThat(result).hasSize(2)
        assertThat(result).containsAll(listOf(twilioNumberDto, otherDto))
    }
}