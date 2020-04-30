package org.wicket.calltree.model

class BcpContactStats(val fromTwilioNumber: String,
                      val textSent: String?,
                      val dateCreated: String,
                      val toContact: String,
                      val inboundTimestamp: String,
                      val inboundText: String?,
                      val eventName: String)