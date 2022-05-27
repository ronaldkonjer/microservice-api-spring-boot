package eu.comparegroup.services.web.cg.configuration

import mu.KotlinLogging
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JettyConfig {

	private val LOGGER = KotlinLogging.logger {}

	/**
	 * for vue history mode set notfound redirect to index
	 */
	@Bean
	fun webServerFactory(
		@Value("\${server.port:8080}") port: String?,
		@Value("\${servlet.context-path:/}") contextPath: String?,
		@Value("\${jetty.threads.max:200}") maxThreads: String?,
		@Value("\${jetty.threads.min:10}") minThreads: String?,
		@Value("\${jetty.threadPool.idleTimeout:60000}") idleTimeout: String?
	): ConfigurableServletWebServerFactory {
		val factory = JettyServletWebServerFactory()
		LOGGER.info("Setting the Jetty specific configurations. started")
		factory.port = Integer.valueOf(port)
		//		factory.setContextPath("");
		val threadPool = QueuedThreadPool()
		threadPool.minThreads = Integer.valueOf(minThreads)
		threadPool.maxThreads = Integer.valueOf(maxThreads)
		threadPool.idleTimeout = Integer.valueOf(idleTimeout)
		factory.threadPool = threadPool
		//		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index"));
		LOGGER.info("Setting the Jetty specific configurations. ended")
		return factory
	}

//	companion object {
//
//		private const val LOGGER = LoggerFactory.getLogger(JettyConfig::class.java)
//	}
}