package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.wicket.calltree.dto.BcpEventSmsDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.models.BcpEventSms;

/**
 * @author Alessandro Arosio - 13/04/2020 22:27
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BcpEventSmsMapper {
    BcpEventSmsDto entityToDto(BcpEventSms bcpEventSms);
    BcpEventSms dtoToEntity(BcpEventSmsDto dto);

    @Mapping(source = "to", target = "recipientNumber")
    @Mapping(source = "body", target = "outboundMessage")
    BcpEventSms responseToEntity(Response response);
}
