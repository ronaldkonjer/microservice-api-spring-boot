package eu.comparegroup.services.web.cg

import eu.comparegroup.services.web.cg.CgBackendApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(CgBackendApplication::class.java)
	}

}
