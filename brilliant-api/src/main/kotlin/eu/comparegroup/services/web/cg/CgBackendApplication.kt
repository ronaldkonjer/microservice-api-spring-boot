package eu.comparegroup.services.web.cg

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
			scanBasePackages = [
		"eu.comparegroup.backbone.core.springboot.api.utils",
		"eu.comparegroup.sp.domain.*",
		"eu.comparegroup.services.web.cg.*"
]
//	], exclude = [
//		DataSourceAutoConfiguration::class,
//		DataSourceTransactionManagerAutoConfiguration::class,
//		HibernateJpaAutoConfiguration::class // do not use the database
//	]
)
class CgBackendApplication

private val LOGGER = KotlinLogging.logger {}

fun main(args: Array<String>) {
	runApplication<CgBackendApplication>(*args)
	LOGGER.info("application init success")
}
