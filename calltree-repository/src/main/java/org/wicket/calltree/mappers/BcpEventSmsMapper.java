package org.wicket.calltree.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.wicket.calltree.dto.BcpMessageDto;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.models.BcpMessage;

/**
 * @author Alessandro Arosio - 13/04/2020 22:27
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BcpEventSmsMapper {
    BcpMessageDto entityToDto(BcpMessage bcpEventSms);
    BcpMessage dtoToEntity(BcpMessageDto dto);

    @Mapping(source = "to", target = "recipientNumber")
    @Mapping(source = "body", target = "outboundMessage")
    BcpMessage responseToEntity(Response response);
}
