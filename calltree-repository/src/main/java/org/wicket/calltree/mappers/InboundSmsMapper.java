package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.InboundSmsDto;
import org.wicket.calltree.models.InboundSms;

/**
 * @author Alessandro Arosio - 13/04/2020 22:25
 */
@Mapper
public interface InboundSmsMapper {
    InboundSmsDto entityToDto(InboundSms inboundSms);
    InboundSms dtoToEntity(InboundSmsDto dto);
}
