package org.wicket.calltree.services.utils;

import com.twilio.rest.api.v2010.account.Message;
import org.mapstruct.Mapper;
import org.wicket.calltree.dto.Response;
import org.wicket.calltree.services.utils.helper.PhoneNumberMapperHelper;

/**
 * @author Alessandro Arosio - 11/04/2020 15:30
 */
@Mapper(uses = PhoneNumberMapperHelper.class)
public interface MessageMapper {

    Response messageToResponse(Message message);

}
