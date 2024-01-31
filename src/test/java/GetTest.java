import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetTest extends BaseTest {

    //Get tests can be failed due to data on the site can be changed
    @Test
    public void checkGetTest() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .get("/booking/1");
        response.print();
        String firstName = response.jsonPath().getString("firstname");
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        Assert.assertEquals(firstName, "Sally", "The first name is wrong");
        Assert.assertEquals(checkin, "2013-02-23", "Check-in date is wrong");
    }

    @Test
    public void checkBookingAmountTest() {
        Response response = RestAssured
                .given()
                .spec(spec)
                .get("/booking/1");
        response.print();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Expected status code is 200, expected status code is" + actualStatusCode);
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertEquals(bookingIds.size(), 100, "Booking ids: actual size is " + bookingIds.size());
    }

    //Use query parameters
    @Test
    public void checkGetWithFilterTest() {
        spec.queryParam("firstname", "Susan");
        spec.queryParam("lastname", "Brown");
        Response response = RestAssured
                .given()
                .spec(spec)
                .get("/booking");
        response.print();
        List<Integer> bookings = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookings.isEmpty());
    }
}
