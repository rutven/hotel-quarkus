package org.acme.resteasy;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ExampleResource.class)
public class ExampleResourceTest {

    private static final String TEST_NAME = "test";

    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/hello")
            .then().statusCode(200).body(is("hello"));
    }

    @Test
    public void testGreetingsEndpoint() {
        given()
            .when().get("/greeting/" + TEST_NAME)
            .then().statusCode(200).body(is("Hello " + TEST_NAME + "!"));
    }

}