package eu.comparegroup.services.web.cg.controller;

import eu.comparegroup.backbone.core.springboot.api.security.AuthorizeRequest;
import eu.comparegroup.services.web.cg.CgBackendApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		classes = CgBackendApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class JerseyBackendControllerTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void hello() {
		String myAccessToken = AuthorizeRequest.generateTokenOnly("cg-spring-boot-vuejs", System.currentTimeMillis());
		System.out.println(myAccessToken);
		when()
				.get("/api/v1/hello?accessToken="+ myAccessToken)
				.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.assertThat()
				.body(is(equalTo(JerseyBackendController.HELLO_TEXT)));
	}


}