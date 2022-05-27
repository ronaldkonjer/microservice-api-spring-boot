import java.io.IOException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class CORSResponseFilter : ContainerResponseFilter {

	override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
		val headers = responseContext.headers
		headers.add("Access-Control-Allow-Origin", "*")
		//headers.add("Access-Control-Allow-Origin", "http://abcd.org"); //allows CORS requests only coming from abcd.org
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
		headers.add("Access-Control-Allow-Headers", "x-requested-with")
		headers.add("Access-Control-Max-Age", "3600")
	}
}