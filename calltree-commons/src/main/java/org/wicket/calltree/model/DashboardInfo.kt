package org.wicket.calltree.model

/**
 * @author Alessandro Arosio - 03/06/2020 22:09
 */
class DashboardInfo(val eventName: String?,
                    val messagesSent: Int?,
                    val messagesReceived: Int?,
                    val eventDate: String?,
                    val activeEvents: Int?,
                    val terminatedEvents: Int?,
                    val balance: Double?,
                    val status: String,
                    val totalContacts: Int?,
                    val twilioNumbers: Int?)