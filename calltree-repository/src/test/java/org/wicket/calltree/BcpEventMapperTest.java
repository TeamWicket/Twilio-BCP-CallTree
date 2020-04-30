package org.wicket.calltree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.BcpEventDto;
import org.wicket.calltree.models.BcpEvent;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BcpEventMapperTest extends AbstractEventMapperTest {

    @Test
    void entityToDto_WithId_ReturnsDtoWithSameId() {
        BcpEventDto eventDto = bcpEventMapper.entityToDto(bcpEvent);

        assertThat(eventDto.getId()).isEqualTo(EVENT_ID);
    }

    @Test
    void entityToDto_WithName_ReturnsDtoWithSameName() {
        BcpEventDto eventDto = bcpEventMapper.entityToDto(bcpEvent);

        assertThat(eventDto.getEventName()).isEqualTo(EVENT_NAME);
    }

    @Test
    void entityToDto_WithIsActive_ReturnsDtoWithSameIsActive() {
        BcpEventDto eventDto = bcpEventMapper.entityToDto(bcpEvent);

        assertThat(eventDto.getIsActive()).isTrue();
    }

    @Test
    void entityToDto_WithVersion_ReturnsDtoWithSameVersion() {
        BcpEventDto eventDto = bcpEventMapper.entityToDto(bcpEvent);

        assertThat(eventDto.getVersion()).isEqualTo(EVENT_VERSION);
    }

    @Test
    void dtoToEntity_WithId_ReturnsEventWithSameId() {
        BcpEvent event = bcpEventMapper.dtoToEntity(bcpEventDto);

        assertThat(event.getId()).isEqualTo(EVENT_ID);
    }

    @Test
    void dtoToEntity_WithName_ReturnsEventWithSameName() {
        BcpEvent event = bcpEventMapper.dtoToEntity(bcpEventDto);

        assertThat(event.getEventName()).isEqualTo(EVENT_NAME);
    }

    @Test
    void dtoToEntity_WithIsActive_ReturnsEventWithSameIsActive() {
        BcpEvent event = bcpEventMapper.dtoToEntity(bcpEventDto);

        assertThat(event.getIsActive()).isTrue();
    }

    @Test
    void dtoToEntity_WithVersion_ReturnsEventWithSameVersion() {
        BcpEvent event = bcpEventMapper.dtoToEntity(bcpEventDto);

        assertThat(event.getVersion()).isEqualTo(EVENT_VERSION);
    }
}
