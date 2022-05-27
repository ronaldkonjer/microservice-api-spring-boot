package eu.comparegroup.services.web.cg.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.text.SimpleDateFormat
import java.util.TimeZone
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Provider
class JacksonConfig : ContextResolver<ObjectMapper> {

	companion object {

		private val mapper = ObjectMapper()

		init {
			// Allow single quotes
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
			// Allow content with a comment symbol / * or / /
			mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true)
			// Allow attribute names without quotes
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
			// Set timeZone
			mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"))
			mapper.dateFormat =
				SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

			// Serialization configuration
			// does not contain null attributes
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true)

			// self-reference when reporting an error
			mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true)

			// deserialization configuration
			// json is not allowed to contain attributes not included in the class
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
		}
	}

	override fun getContext(type: Class<*>?): ObjectMapper {
		return mapper
	}
}