package org.wicket.calltree.services.utils;

import com.twilio.rest.api.v2010.account.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.services.utils.helper.MapperHelper;

@Mapper(uses = MapperHelper.class)
public interface MessageMapper {

    @Mapping(target = "bcpEvent", ignore = true)
    @Mapping(target = "smsStatus", ignore = true)
    Response messageToResponse(Message message);

}
