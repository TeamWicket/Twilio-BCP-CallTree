package org.wicket.calltree.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

/**
 * @author Alessandro Arosio - 04/04/2020 09:53
 */

data class Response(val sid: String?, @JsonProperty("date_created") val dateCreated: String?,
                @JsonProperty("date_updated") val dateUpdated: String?,
                @JsonProperty("date_sent") val dateSent: String?,
                @JsonProperty("account_sid") val accountSid: String?, val to: String?,
                val from: String?, @JsonProperty("num_media") val numMedia: String?,
                @JsonProperty("messaging_service_sid") val messagingServiceSid: String?, val body: String?,
                val status: String?, @JsonProperty("num_segments") val numSegments: String?,
                @JsonProperty("api_version") val apiVersion: String?, val price: BigDecimal?,
                @JsonProperty("price_unit") val priceUnit: BigDecimal?,
                @JsonProperty("error_code") val errorCode: String?, val direction: String?,
                @JsonProperty("error_message") val errorMessage: String?,
                val uri: String?, @JsonProperty("subresource_uris")  val subResourceUris: SubResourceUris?)

data class SubResourceUris(val media: String?)