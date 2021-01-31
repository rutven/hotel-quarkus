package name.legkodymov.hotel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(FruitResource.class)
public class FruitResourceTest {

    @Test
    public void testGetFruits() {
        given().get()
            .then().statusCode(200)
            .body("$.size()", is(2),
                "name", containsInAnyOrder("Apple", "Pineapple"),
                "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }

    @Test
    public void testAddAndDeleteFruit() {
        given()
            .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .when()
            .post()
            .then()
            .statusCode(200)
            .body("$.size()", is(3),
                "name", containsInAnyOrder("Apple", "Pineapple", "Pear"),
                "description",
                containsInAnyOrder("Winter fruit", "Tropical fruit", "Winter fruit"));

        given()
            .body("{\"name\": \"Pear\", \"description\": \"Winter fruit\"}")
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .when()
            .delete()
            .then()
            .statusCode(200)
            .body("$.size()", is(2),
                "name", containsInAnyOrder("Apple", "Pineapple"),
                "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }

}
