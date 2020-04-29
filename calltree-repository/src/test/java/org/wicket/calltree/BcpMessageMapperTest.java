package org.wicket.calltree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.BcpMessageDto;
import org.wicket.calltree.enums.SmsStatus;
import org.wicket.calltree.models.BcpMessage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BcpMessageMapperTest extends AbstractEventMapperTest {

    @Test
    void entityToDto_WithId_ReturnsDtoWithSameId() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getId()).isEqualTo(MESSAGE_ID);
    }

    @Test
    void entityToDto_WithDateCreated_ReturnsDtoWithSameDateCreated() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getDateCreated()).isEqualTo(DATE_CREATED);
    }

    @Test
    void entityToDto_WithDateUpdated_ReturnsDtoWithSameDateUpdated() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getDateUpdated()).isEqualTo(DATE_UPDATED);
    }

    @Test
    void entityToDto_WithDateSent_ReturnsDtoWithSameDateSent() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getDateSent()).isEqualTo(DATE_SENT);
    }

    @Test
    void entityToDto_WithOutboundMessage_ReturnsDtoWithSameOutboundMessage() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getOutboundMessage()).isEqualTo(OUTBOUND_MESSAGE);
    }

    @Test
    void entityToDto_WithSmsStatus_ReturnsDtoWithSameSmsStatus() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getSmsStatus()).isEqualTo(SmsStatus.RECEIVED);
    }

    @Test
    void entityToDto_WithRecipientNumber_ReturnsDtoWithSameRecipientNumber() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getRecipientNumber()).isEqualTo(RECIPIENT_NUMBER);
    }

    @Test
    void entityToDto_WithRecipientCountry_ReturnsDtoWithSameRecipientCountry() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getRecipientCountry()).isEqualTo(RECIPIENT_COUNTRY);
    }

    @Test
    void entityToDto_WithRecipientMessage_ReturnsDtoWithSameRecipientMessage() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getRecipientMessage()).isEqualTo(RECIPIENT_MESSAGE);
    }

    @Test
    void entityToDto_WithRecipientTimestamp_ReturnsDtoWithSameRecipientTimestamp() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getRecipientTimestamp()).isEqualTo(RECIPIENT_TIMESTAMP);
    }

    @Test
    void entityToDto_WithErrorMessage_ReturnsDtoWithSameErrorMessage() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void entityToDto_WithVersion_ReturnsDtoWithSameVersion() {
        BcpMessageDto messageDto = bcpMessageMapper.entityToDto(bcpMessage);

        assertThat(messageDto.getVersion()).isEqualTo(MESSAGE_VERSION);
    }


    @Test
    void dtoToEntity_WithId_ReturnsMessageWithSameId() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getId()).isEqualTo(MESSAGE_ID);
    }

    @Test
    void dtoToEntity_WithDateCreated_ReturnsMessageWithSameDateCreated() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getDateCreated()).isEqualTo(DATE_CREATED);
    }

    @Test
    void dtoToEntity_WithDateUpdated_ReturnsMessageWithSameDateUpdated() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getDateUpdated()).isEqualTo(DATE_UPDATED);
    }

    @Test
    void dtoToEntity_WithDateSent_ReturnsMessageWithSameDateSent() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getDateSent()).isEqualTo(DATE_SENT);
    }

    @Test
    void dtoToEntity_WithOutboundMessage_ReturnsMessageWithSameOutboundMessage() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getOutboundMessage()).isEqualTo(OUTBOUND_MESSAGE);
    }

    @Test
    void dtoToEntity_WithSmsStatus_ReturnsMessageWithSameSmsStatus() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getSmsStatus()).isEqualTo(SmsStatus.RECEIVED);
    }

    @Test
    void dtoToEntity_WithRecipientNumber_ReturnsMessageWithSameRecipientNumber() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getRecipientNumber()).isEqualTo(RECIPIENT_NUMBER);
    }

    @Test
    void dtoToEntity_WithRecipientCountry_ReturnsMessageWithSameRecipientCountry() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getRecipientCountry()).isEqualTo(RECIPIENT_COUNTRY);
    }

    @Test
    void dtoToEntity_WithRecipientMessage_ReturnsMessageWithSameRecipientMessage() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getRecipientMessage()).isEqualTo(RECIPIENT_MESSAGE);
    }

    @Test
    void dtoToEntity_WithRecipientTimestamp_ReturnsMessageWithSameRecipientTimestamp() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getRecipientTimestamp()).isEqualTo(RECIPIENT_TIMESTAMP);
    }

    @Test
    void dtoToEntity_WithErrorMessage_ReturnsMessageWithSameErrorMessage() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    void dtoToEntity_WithVersion_ReturnsMessageWithSameVersion() {
        BcpMessage message = bcpMessageMapper.dtoToEntity(bcpMessageDto);

        assertThat(message.getVersion()).isEqualTo(MESSAGE_VERSION);
    }
}
