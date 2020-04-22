package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.models.TwilioNumber;

@Mapper
public interface TwilioNumberMapper {
    TwilioNumberDto entityToDto(TwilioNumber twilioNumber);

    TwilioNumber dtoToEntity(TwilioNumberDto twilioNumberDto);
}
