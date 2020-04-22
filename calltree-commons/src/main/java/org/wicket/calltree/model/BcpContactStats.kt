package org.wicket.calltree.model

/**
 * @author Alessandro Arosio - 21/04/2020 16:24
 */
class BcpContactStats(val fromTwilioNumber: String,
                      val textSent: String?,
                      val dateCreated: String,
                      val toContact: String,
                      val smsAcknowledged: String,
                      val inboundText: String?,
                      val eventName: String)