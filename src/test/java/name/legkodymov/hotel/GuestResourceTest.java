package name.legkodymov.hotel;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(GuestResource.class)
public class GuestResourceTest {

    public static final String $_SIZE = "$.size()";

    @Test
    public void testGetGuests() {
        given()
            .get()
            .then().statusCode(200)
            .body($_SIZE, is(0));
    }

    @Test
    public void testAddAndDeleteGuest() {
        Response response = given()
            .body("{\"name\": \"Ivan\", \"phone\": \"55555\"}")
            .when().contentType(MediaType.APPLICATION_JSON)
            .post();

        response
            .then()
            .statusCode(200)
            .body($_SIZE, is(1),
                "name", containsInAnyOrder("Ivan"),
                "phone", containsInAnyOrder("55555"));

        List<Long> ids = response.jsonPath().getList("id");
        if (ids.isEmpty()) {
            System.out.println("Id not found");
        } else {
            System.out.println("Guest id is " + ids.get(0));
        }

        given()
            .body("{\"name\": \"Ivan\", \"phone\": \"55555\", \"id\": " + ids.get(0) + "}")
            .when().contentType(MediaType.APPLICATION_JSON)
            .delete()
            .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

}
