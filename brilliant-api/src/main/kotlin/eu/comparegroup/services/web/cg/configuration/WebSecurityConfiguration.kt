package eu.comparegroup.services.web.cg.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

//@Configuration
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//				.antMatchers("/**")
//				.permitAll();
////				.anyRequest()
////				.authenticated();
////				.and()
////				.oauth2Login()
////				.loginPage("/login");
//	}
//}
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
			.and()
				.httpBasic()
			.and()
				.authorizeRequests() //		        .antMatchers("*").permitAll()
				.antMatchers("/actuator/**")
				.permitAll()
				.antMatchers("/api/hello")
				.permitAll()
				.antMatchers("/api/user/**")
				.permitAll() // allow every URI, that begins with '/api/user/'
				.antMatchers("/api/secured")
				.authenticated()
				.antMatchers("/api/v1/hello")
				.permitAll()
				.antMatchers("/api/v1/user/**")
				.permitAll()
				.antMatchers("/api/v1/secured")
				.authenticated()
				.antMatchers("/api/v1/**")
				.permitAll()
				.anyRequest()
				.authenticated() // protect all other requests
			.and()
			// by default uses a Bean by the name of corsConfigurationSource
				.cors()
			.and()
				.csrf().disable(); // We need to add CORS support to Spring Security (see https://stackoverflow.com/a/67583232/4964553); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
	} //@Override
	//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//    auth.inMemoryAuthentication()
	//            .withUser("foo").password("{noop}bar").roles("USER");
	//}

	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource? {
		val source = UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
		return source
	}
}