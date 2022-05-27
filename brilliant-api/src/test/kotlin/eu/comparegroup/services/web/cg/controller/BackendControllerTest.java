package eu.comparegroup.services.web.cg.controller;

import eu.comparegroup.services.web.cg.CgBackendApplication;
import eu.comparegroup.services.web.cg.domain.User;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(
		classes = CgBackendApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BackendControllerTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	public void saysHello() {
		when()
				.get("/api/v1/hello")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.assertThat()
				.body(is(equalTo(JerseyBackendController.HELLO_TEXT)));
	}

	@Test
	public void addNewUserAndRetrieveItBack() {
		User norbertSiegmund = new User("Norbert", "Siegmund");

		Long userId =
				given()
						.pathParam("firstName", "Norbert")
						.pathParam("lastName", "Siegmund")
						.header("Content-Type","application/json" )
						.header("Accept","application/json" )
						.when()
						.post("/api/v1/user/{firstName}/{lastName}")
						.then()
						.log().all()
						.statusCode(is(HttpStatus.SC_CREATED))
						.extract()
						.body().as(Long.class);

		User responseUser =
				given()
						.pathParam("id", userId)
						.header("Content-Type","application/json" )
						.header("Accept","application/json" )
						.when()
						.get("/api/v1/user/{id}")
						.then()
						.statusCode(HttpStatus.SC_OK)
						.assertThat()
						.extract().as(User.class);

		// Did Norbert came back?
		assertThat(responseUser.getFirstName(), is("Norbert"));
		assertThat(responseUser.getLastName(), is("Siegmund"));
	}

	@Test
	public void user_api_should_give_http_404_not_found_when_user_not_present_in_db() {
		Long someId = 200L;
		given()
				.pathParam("id", someId)
				.when()
				.get("/api/v1/user/{id}")
				.then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void secured_api_should_react_with_unauthorized_per_default() {
		given()
				.when()
				.get("/api/v1/secured")
				.then()
				.log().all()
				.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void secured_api_should_give_http_200_when_authorized() {

		given()
				.auth().basic("admin", "nimda")
				.when()
				.get("/api/v1/secured")
				.then()
				.statusCode(HttpStatus.SC_OK)
				.assertThat()
				.body(is(equalTo(JerseyBackendController.SECURED_TEXT)));
	}

}
