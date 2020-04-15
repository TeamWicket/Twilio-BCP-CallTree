package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.wicket.calltree.dto.BcpEventDto;
import org.wicket.calltree.models.BcpEvent;

/**
 * @author Alessandro Arosio - 15/04/2020 22:59
 */
@Mapper
public interface BcpEventMapper {
    BcpEventDto entityToDto(BcpEvent bcpEvent);

    BcpEvent dtoToEntity(BcpEventDto bcpEventDto);
}
