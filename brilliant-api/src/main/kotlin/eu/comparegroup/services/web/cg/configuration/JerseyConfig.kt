package eu.comparegroup.services.web.cg.configuration

import CORSResponseFilter
import eu.comparegroup.services.web.cg.configuration.mapper.NotFoundExceptionMapper
import eu.comparegroup.services.web.cg.controller.JerseyBackendController
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.Collections
import javax.ws.rs.ApplicationPath

@Component
@Configuration
@ApplicationPath("/")
class JerseyConfig : ResourceConfig() {

	init {
//		register(JerseyBackendController::class)
//		register(CORSResponseFilter::class)
		register(CORSResponseFilter::class.java)
		//return custom
		register(NotFoundExceptionMapper::class.java)
		register(JerseyBackendController::class.java)
		//		register(packages("eu.comparegroup.services.web.cg.controller"));

		//		register(CustomExceptionMapper.class);
		//		register(CustomJsonMappingExceptionMapper.class);
		//		register(CustomJsonParseExceptionMapper.class);
		//		register(CustomValidationExceptionMapper.class);

		//https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/howto-jersey.htmls
		setProperties(
			Collections.singletonMap(
				"jersey.config.server.response.setStatusOverSendError", true
			)
		)
	}
}