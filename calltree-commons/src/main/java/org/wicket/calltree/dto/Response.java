package org.wicket.calltree.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

/**
 * @author Alessandro Arosio - 11/04/2020 16:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @JsonProperty("account_sid")
    private String accountSid;
    @JsonProperty("api_version")
    private String apiVersion;
    @JsonProperty("body")
    private String body;
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonProperty("date_sent")
    private String dateSent;
    @JsonProperty("date_updated")
    private String dateUpdated;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("from")
    private String from;
    @JsonProperty("messaging_service_sid")
    private String messagingServiceSid;
    @JsonProperty("num_media")
    private String numMedia;
    @JsonProperty("num_segments")
    private String numSegments;
    @JsonProperty("price")
    private Currency price;
    @JsonProperty("price_unit")
    private Currency priceUnit;
    @JsonProperty("sid")
    private String sid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("to")
    private String to;
    @JsonProperty("uri")
    private String uri;
    private BcpEventDto bcpEvent;
}
