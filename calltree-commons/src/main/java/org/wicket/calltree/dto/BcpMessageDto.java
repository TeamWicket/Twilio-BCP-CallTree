package org.wicket.calltree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wicket.calltree.enums.SmsStatus;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * @author Alessandro Arosio - 13/04/2020 22:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcpMessageDto {

    @Nullable
    private Long id;

    @NotNull
    private BcpEventDto bcpEvent;

    @NotNull
    private String dateCreated;

    private String dateUpdated;

    private String dateSent;

    @NotNull
    private String outboundMessage;

    @NotNull
    private SmsStatus smsStatus;

    @NotNull
    private String recipientNumber;

    private String recipientCountry;

    private String recipientMessage;

    private String recipientTimestamp;

    private String errorMessage;

    private Long version;
}
