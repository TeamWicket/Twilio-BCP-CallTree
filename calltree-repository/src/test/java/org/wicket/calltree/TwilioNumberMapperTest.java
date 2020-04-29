package org.wicket.calltree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.models.TwilioNumber;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class TwilioNumberMapperTest extends AbstractEventMapperTest {

    @Test
    void entityToDto_WithId_ReturnsDtoWithId() {
        TwilioNumberDto twilioNumberDto = twilioNumberMapper.entityToDto(twilioNumber);

        assertThat(twilioNumberDto.getId()).isEqualTo(NUMBER_ID);
    }

    @Test
    void entityToDto_WithNumber_ReturnsDtoWithNumber() {
        TwilioNumberDto twilioNumberDto = twilioNumberMapper.entityToDto(twilioNumber);

        assertThat(twilioNumberDto.getTwilioNumber()).isEqualTo(NUMBER);
    }

    @Test
    void entityToDto_WithIsAvailable_ReturnsDtoWithIsAvailable() {
        TwilioNumberDto twilioNumberDto = twilioNumberMapper.entityToDto(twilioNumber);

        assertThat(twilioNumberDto.getIsAvailable()).isTrue();
    }

    @Test
    void dtoToEntity_WithId_ReturnsEntityWithId() {
        TwilioNumber twilioNumber = twilioNumberMapper.dtoToEntity(twilioNumberDto);

        assertThat(twilioNumber.getId()).isEqualTo(NUMBER_ID);
    }

    @Test
    void dtoToEntity_WithNumber_ReturnsEntityWithNumber() {
        TwilioNumber twilioNumber = twilioNumberMapper.dtoToEntity(twilioNumberDto);

        assertThat(twilioNumber.getTwilioNumber()).isEqualTo(NUMBER);
    }

    @Test
    void dtoToEntity_WithIsAvailable_ReturnsEntityWithIsAvailable() {
        TwilioNumber twilioNumber = twilioNumberMapper.dtoToEntity(twilioNumberDto);

        assertThat(twilioNumber.getIsAvailable()).isTrue();
    }
}
