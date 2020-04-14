package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.wicket.calltree.dto.OutboundSmsDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.models.OutboundSms;

/**
 * @author Alessandro Arosio - 13/04/2020 22:27
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutboundSmsMapper {
    OutboundSmsDto entityToDto(OutboundSms outboundSms);
    OutboundSms dtoToEntity(OutboundSmsDto dto);

    @Mapping(source = "from", target = "fromNumber")
    @Mapping(source = "to", target = "toNumber")
    OutboundSms responseToEntity(Response response);
}
