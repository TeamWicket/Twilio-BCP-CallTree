package org.wicket.calltree.model

import org.wicket.calltree.enums.Role

/**
 * @author Alessandro Arosio - 11/04/2020 17:21
 */
data class BcpStartRequest(val text: String, val toRoles: Role = Role.REPORTER)
