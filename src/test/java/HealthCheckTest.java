import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {
    @Test
    public void healthCheckTest() {
        given()
                .spec(spec)
                .when()
                .get("/ping")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void headersCookiesTest() {
        Header someHeader = new Header("some_header", "some_value");
        spec.header(someHeader);
        Cookie someCookie = new Cookie.Builder("some_cookie", "some_cookie_value").build();
        spec.cookie(someCookie);
        Response response = RestAssured.given(spec)
                .header("Test header name", "Test header value")
                .cookie("Test cookie name", "Test cookie value")
                .log().all().get("/ping");
        Headers headers = response.getHeaders();
        System.out.println(headers);
        String serverHeader1 = headers.get("Server").getValue();
        System.out.println(serverHeader1);
        String serverHeader2 = response.getHeader("Server");
        System.out.println(serverHeader2);
    }
}
