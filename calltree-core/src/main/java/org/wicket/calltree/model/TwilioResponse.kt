package org.wicket.calltree.model

/**
 * @author Alessandro Arosio - 04/04/2020 09:53
 */

data class Base(val sid: String?, val date_created: String?, val date_updated: String?,
                val date_sent: Any?, val account_sid: String?, val to: String?,
                val from: String?, val messaging_service_sid: Any?, val body: String?,
                val status: String?, val num_segments: String?, val num_media: String?,
                val direction: String?, val api_version: String?, val price: Any?,
                val price_unit: Any?, val error_code: Any?, val error_message: Any?,
                val uri: String?, val subresource_uris: Subresource_uris?)

data class Subresource_uris(val media: String?)