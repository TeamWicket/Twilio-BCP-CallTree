package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.wicket.calltree.dto.TwilioNumberDto;
import org.wicket.calltree.models.TwilioNumber;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TwilioNumberMapper {
    TwilioNumberDto entityToDto(TwilioNumber twilioNumber);

    TwilioNumber dtoToEntity(TwilioNumberDto twilioNumberDto);
}
