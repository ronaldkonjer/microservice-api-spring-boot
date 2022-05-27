//package eu.comparegroup.services.web.cg.configuration
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.filter.OncePerRequestFilter
//import java.io.IOException
//import java.util.regex.Pattern
//import javax.servlet.FilterChain
//import javax.servlet.ServletException
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//import mu.KotlinLogging
//
//@Configuration
//class SpaRedirectFilterConfiguration {
//
//	private val LOGGER = KotlinLogging.logger {}
//
//	@Bean
//	fun spaRedirectFiler(): FilterRegistrationBean<*> {
//		val registration = FilterRegistrationBean<OncePerRequestFilter>()
//		registration.filter = createRedirectFilter()
//		registration.addUrlPatterns("/*")
//		registration.setName("frontendRedirectFiler")
//		registration.order = 1
//		return registration
//	}
//
//	private fun createRedirectFilter(): OncePerRequestFilter {
//		return object : OncePerRequestFilter() {
//			// Forwards all routes except '/index.html', '/200.html', '/favicon.ico', '/sw.js' '/api/', '/api/**'
//			private val REGEX = "(?!/actuator|/api|/_nuxt|/static|/index\\.html|/200\\.html|/favicon\\.ico|/sw\\.js).*$"
//			private val pattern = Pattern.compile(REGEX)
//
//			@Throws(ServletException::class, IOException::class)
//			override fun doFilterInternal(
//				req: HttpServletRequest,
//				res: HttpServletResponse,
//				chain: FilterChain
//			) {
//				if (pattern.matcher(req.requestURI).matches() && req.requestURI != "/") {
//					// Delegate/Forward to `/` if `pattern` matches and it is not `/`
//					// Required because of 'mode: history'usage in frontend routing, see README for further details
//					LOGGER.info("URL {} entered directly into the Browser, redirecting...", req.requestURI)
//					val rd = req.getRequestDispatcher("/")
//					rd.forward(req, res)
//				} else {
//					LOGGER.info("executing doFilter cause {} does not match", req.requestURI)
//					chain.doFilter(req, res)
//				}
//			}
//		}
//	}
//}