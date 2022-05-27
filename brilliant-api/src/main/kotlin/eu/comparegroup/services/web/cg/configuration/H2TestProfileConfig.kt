//package eu.comparegroup.services.web.cg.configuration
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Profile
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
//import org.springframework.jdbc.datasource.DriverManagerDataSource
//import org.springframework.transaction.annotation.EnableTransactionManagement
//import javax.sql.DataSource
//
//@Configuration
//@EnableJpaRepositories(basePackages = ["eu.comparegroup.services.web.cg.repository"])
//@EnableTransactionManagement
//class H2TestProfileJPAConfig {
//
//	@Bean
//	@Profile("test")
//	fun dataSource(): DataSource {
//		val dataSource = DriverManagerDataSource()
//		dataSource.setDriverClassName("org.h2.Driver")
//		dataSource.url = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
//		dataSource.username = "sa"
//		dataSource.password = "sa"
//		return dataSource
//	} // configure entityManagerFactory
//	// configure transactionManager
//	// configure additional Hibernate properties
//}