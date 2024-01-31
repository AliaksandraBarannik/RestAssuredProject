import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteBookingTest extends BaseTest {

    @Test
    public void deleteBookingTest() {
        Response response = createBooking();
        response.print();
        String bookingId = response.jsonPath().getString("bookingid");

        Response deleteResponse = RestAssured
                .given()
                .spec(spec)
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .delete("/booking/" + bookingId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 201, "Status code is wrong");
        Response responseGet = RestAssured
                .given()
                .spec(spec)
                .get("/booking/" + bookingId);
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body is found");
    }
}
