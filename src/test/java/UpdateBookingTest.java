import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Configuration;

public class UpdateBookingTest extends BaseTest {

    //Use put request
    @Test
    public void updateBookingTest() {
        Response response = createBooking();
        String bookingId = response.jsonPath().getString("bookingid");
        JSONObject body = new JSONObject();
        body.put("firstname", "Masha");
        body.put("lastname", "Voronzova");
        body.put("totalprice", 200);
        body.put("depositpaid", true);
        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2020-01-01");
        bookingdates.put("checkout", "2021-01-01");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Dinner");

        Response updatedResponse = RestAssured
                .given()
                .spec(spec)
                .auth()
                .preemptive()
                .basic(Configuration.getUser(), Configuration.getPassword())
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put("/booking/" + bookingId);
        System.out.println(updatedResponse);
        Assert.assertEquals(updatedResponse.getStatusCode(), 200, "Status code is wrong");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedResponse.jsonPath().getString("firstname"), "Masha", "First name is incorrect");
        softAssert.assertEquals(updatedResponse.jsonPath().getInt("totalprice"), 200, "Total price is incorrect");
        softAssert.assertTrue(updatedResponse.jsonPath().getBoolean("depositpaid"), "Deposit value is incorrect");

        softAssert.assertAll();
    }

    //Use patch request
    //Use path parameters
    @Test
    public void updateSomeFieldsInBookingTest() {
        Response response = createBooking();
        response.print();
        String bookingId = response.jsonPath().getString("bookingid");
        JSONObject body = new JSONObject();
        body.put("firstname", "Oleg");
        body.put("lastname", "Kuznecov");

        Response updatedResponse = RestAssured
                .given()
                .spec(spec)
                .pathParam("bookingId", bookingId)
                .auth()
                .preemptive()
                .basic(Configuration.getUser(), Configuration.getPassword())
                .contentType(ContentType.JSON)
                .body(body.toString())
                .patch("/booking/{bookingId}");
        System.out.println(updatedResponse);
        Assert.assertEquals(updatedResponse.getStatusCode(), 200, "Status code is wrong");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedResponse.jsonPath().getString("firstname"), "Oleg", "First name is incorrect");
        softAssert.assertEquals(updatedResponse.jsonPath().getInt("totalprice"), 111, "Total price is incorrect");
        softAssert.assertFalse(updatedResponse.jsonPath().getBoolean("depositpaid"), "Deposit value is incorrect");

        softAssert.assertAll();
    }
}
