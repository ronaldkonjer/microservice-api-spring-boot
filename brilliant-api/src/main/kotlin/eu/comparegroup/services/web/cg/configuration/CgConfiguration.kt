package eu.comparegroup.services.web.cg.configuration

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "my")
class CgConfiguration {

	@JsonProperty
	var distributedSessionCache = false

	@JsonProperty
	var artifactId: @Valid @NotNull String? = null

	@JsonProperty
	var shopPerformanceEndpoint: @Valid @NotNull String? = null

	@JsonProperty
	var adminPassword: @Valid @NotNull String? = null

	@JsonProperty
	val assetCachePolicy: @NotEmpty String? = "maximumSize=10000, expireAfterAccess=5s"

	@JsonProperty
	val cookieAuthenticationCachePolicy: @NotEmpty String? = "maximumSize=10000, expireAfterAccess=600s"

	@JsonProperty
	val oAuthSuccessUrl = ""
}