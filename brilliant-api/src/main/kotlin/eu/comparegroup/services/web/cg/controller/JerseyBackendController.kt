package eu.comparegroup.services.web.cg.controller

import eu.comparegroup.backbone.core.springboot.api.resource.util.ResourceResponseUtil
import eu.comparegroup.backbone.core.springboot.api.resource.util.ResourceResponseUtil.generateResponse
import eu.comparegroup.backbone.core.springboot.api.security.AuthorizeRequest
import eu.comparegroup.services.web.cg.domain.User
import eu.comparegroup.services.web.cg.exception.UserNotFoundException
import eu.comparegroup.services.web.cg.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/v1")
//@RequestMapping("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Controller
//@CrossOrigin

class JerseyBackendController @Autowired constructor(
	private val userRepository: UserRepository,
	//	private val cgFacade: CgFacade
) {

	private val LOGGER = KotlinLogging.logger {}

	//	@GET
	//	@Path("/hello")
	//	@Produces(MediaType.APPLICATION_JSON)
	//	fun sayHello(): String {
	//		LOGGER.info("GET called on /hello resource")
	//		return HELLO_TEXT
	//	}

	@GET
	@Path("/hello")
	fun sayHello(
	): Response {
		val myAccessToken = AuthorizeRequest.generateTokenOnly("cg-spring-boot-vuejs", System.currentTimeMillis())
		//		return Response.ok().entity(HELLO_TEXT).build();
		return ResourceResponseUtil.generateResponse(myAccessToken) {
			LOGGER.info("GET called on /hello resource")
			HELLO_TEXT
		}
	}

	//	=> curl http://localhost:10270/api/v1/validate?token=hTJspZRH-YvHAoayPnPo8CnMda-4baX3ILH1hG2d2n58YF40GzZtph73zDlozJdIjwYrjOnpFZab_NdDC8vFtPkjL__TVUxxIAVGrCL53qgQUSTE8bPVYLAspryn6glBc99t5Rsrw-t6BCp_m_Xq0w\&accessToken=`generate-service-token.sh`

	//	@GET
	//	@Path("/validate")
	//	fun validateToken(
	//		@QueryParam("token") token: String?,
	////		@QueryParam(PARAM_ACCESS_TOKEN) accessToken: String?
	//	): Response {
	//		val myAccessToken = AuthorizeRequest.generateTokenOnly("cg-spring-boot-vuejs", System.currentTimeMillis())
	//		//		return Response.ok().entity(HELLO_TEXT).build();
	//		return ResourceResponseUtil.generateResponse(myAccessToken) {
	//			LOGGER.info("GET called on /validate resource")
	//			cgFacade.validateToken(token)
	//		}
	//	}

	@POST
	@Path("/user/{firstName}/{lastName}")
	fun addNewUser(
		@PathParam("lastName") lastName: String?,
		@PathParam("firstName") firstName: String?,
	): Response {
		val myAccessToken = AuthorizeRequest.generateTokenOnly("brilliant-api-spring-boot", System.currentTimeMillis())
		val savedUser: User =
			userRepository.save(User(firstName, lastName))
		LOGGER.info("$savedUser successfully saved into DB")
		return try {
			Response.status(Response.Status.CREATED).entity(savedUser.id).build()
		} catch (exception: Exception) {
			val methodName = Thread.currentThread().stackTrace[3].methodName
			LOGGER.error("There was an error in {}", methodName, exception)
			Response.ok(false)
				.entity(String.format("There was an error in %s", methodName) + ": " + exception.message)
				.status(
					Response.Status.INTERNAL_SERVER_ERROR
				)
				.type(MediaType.TEXT_PLAIN_TYPE)
				.build()
		}
	}

	@GET
	@Path("/user/{id}")
	fun getUserById(@PathParam("id") id: Long): User? {
		return userRepository.findById(id).map { user ->
			LOGGER.info("Reading user with id $id from database.")
			user
		}.orElseThrow { UserNotFoundException("The user with the id $id couldn't be found in the database.") }
	}

	@get:GET
	@get:Path("/secured")
	val secured: Response
		get() {
			LOGGER.info("GET successfully called on /secured resource")
			return Response
				.ok(true)
				.entity(SECURED_TEXT)
				.build()
		}

	companion object {

		//		private val LOGGER = LoggerFactory.getLogger(JerseyBackendController::class.java)
		const val PARAM_ACCESS_TOKEN = "accessToken"
		const val HELLO_TEXT = "Hello from Spring Boot Backend!"
		const val SECURED_TEXT = "Hello from the secured resource!"
	}
}