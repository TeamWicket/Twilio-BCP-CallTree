package org.wicket.calltree.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.BcpMessageDto;
import org.wicket.calltree.mappers.BcpMessageMapper;
import org.wicket.calltree.models.BcpMessage;
import org.wicket.calltree.repository.BcpMessageRepository;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BcpMessageServiceImplTest {
    private static final Long EVENT_ID = 1L;
    private static final String FROM_NUMBER = "0137484848";
    private static final String TO_NUMBER = "0137484848";

    @Mock
    private BcpMessage bcpMessage;
    @Mock
    private BcpMessageDto bcpMessageDto;
    @Mock
    private BcpMessageMapper messageMapper;
    @Mock
    private BcpMessageRepository bcpMessageRepository;
    @InjectMocks
    private BcpMessageServiceImpl bcpMessageService;

    @BeforeEach
    public void setUp() {
        when(bcpMessageRepository.findAllByBcpEvent_Id(EVENT_ID))
                .thenReturn(singletonList(bcpMessage));
        when(bcpMessageRepository.findFirstByRecipientNumberAndBcpEvent_TwilioNumber_TwilioNumberAndBcpEvent_IsActive(FROM_NUMBER, TO_NUMBER, true))
                .thenReturn(bcpMessage);

        when(messageMapper.entityToDto(bcpMessage)).thenReturn(bcpMessageDto);
    }

    @Test
    void findMessagesByBcpEvent_WithValidEventId_ShouldReturnListOfMessages() {
        List<BcpMessageDto> responses = bcpMessageService.findMessagesByBcpEvent(EVENT_ID);

        assertThat(responses).contains(bcpMessageDto);
    }

    @Test
    void findActiveMessagesByRecipientNumber_WithValidEventId_ShouldReturnMessage() {
        BcpMessageDto response = bcpMessageService.findActiveMessagesByRecipientNumber(FROM_NUMBER, TO_NUMBER);

        assertThat(response).isEqualTo(bcpMessageDto);
    }
}
