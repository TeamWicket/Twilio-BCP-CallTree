package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.OutboundSmsDto;
import org.wicket.calltree.models.OutboundSms;

/**
 * @author Alessandro Arosio - 13/04/2020 22:27
 */
@Mapper
public interface OutboundSmsMapper {
    OutboundSmsDto entityToDto(OutboundSms outboundSms);
    OutboundSms dtoToEntity(OutboundSmsDto dto);
}
