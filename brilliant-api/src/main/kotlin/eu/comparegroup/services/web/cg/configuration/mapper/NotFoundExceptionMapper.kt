package eu.comparegroup.services.web.cg.configuration.mapper

import eu.comparegroup.services.web.cg.exception.UserNotFoundException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper : ExceptionMapper<UserNotFoundException?> {

	override fun toResponse(exception: UserNotFoundException?): Response {
		return Response.status(Response.Status.NOT_FOUND)
			.entity(exception!!.message)
			.build()
	}


}