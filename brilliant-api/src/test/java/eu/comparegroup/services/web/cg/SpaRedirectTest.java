package eu.comparegroup.services.web.cg;

import eu.comparegroup.services.web.cg.CgBackendApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		classes = CgBackendApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Disabled
class SpaRedirectTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	void redirectSpa() {
		ValidatableResponse response = RestAssured.when().get("/path").then();
		response.log().all();
		assertSpaResponse(response);
		response = RestAssured.when().get("/nested/path").then();
		assertSpaResponse(response);
		response = RestAssured.when().get("/deep/nested/path/with/many/may/deep").then();
		assertSpaResponse(response);
	}

	@Test
	void it_should_not_interfere_with_api() {
		ValidatableResponse response = RestAssured
				.when()
					.get("/api/v1/hello")
				.then();
		response.log().all();
		assertNotSpaResponse(response);
	}

//	@Test
//	void it_should_not_interfere_with_api() {
//		ValidatableResponse response = RestAssured.when().get("/api/v1").then();
//		response.log().all();
//		assertNotSpaResponse(response);
//	}

	@Test
	void it_should_not_interfere_with_actuator() {
		RestAssured.when().get("/actuator")
				.then().log().all()
				.assertThat()
				.contentType(ContentType.JSON).log().all();
	}

	private static void assertSpaResponse(ValidatableResponse response) {
		response.statusCode(HttpStatus.SC_OK)
				.assertThat()
				.body(containsString("<!DOCTYPE html>"))
				.body(containsString("<script src=/static/js/chunk-vendors"))
				.body(containsString("<title>frontend</title><link href=/static/css/app"))
				.body(containsString("<link href=/static/js/app"))
				.body(containsString("<script src=/static/js/app"));
	}

	private void assertNotSpaResponse(ValidatableResponse response) {
		response.statusCode(HttpStatus.SC_NOT_FOUND)
				.assertThat()
				.body("status", is(404))
				.body("error", is("HTTP 404 Not Found"))
				.body("path", is("/api/v1"));
	}

//	private void assertNotSpaResponse(ValidatableResponse response) {
//		response.statusCode(HttpStatus.SC_NOT_FOUND)
//				.assertThat()
//				.body("status", is(404))
//				.body("error", is("Not Found"))
//				.body("path", is("/api/v1"));
//	}
}