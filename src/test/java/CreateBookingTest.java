import io.restassured.response.Response;
import objects.BookingId;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest {

    @Test
    public void createBookingTest() {
        Response response = createBooking();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.jsonPath().getString("booking.firstname"), "Sasha", "First name is incorrect");
        softAssert.assertEquals(response.jsonPath().getInt("booking.totalprice"), 111, "Total price is incorrect");
        softAssert.assertFalse(response.jsonPath().getBoolean("booking.depositpaid"), "Deposit value is incorrect");

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest() {
        Response response = createBookingWithSerialization();
        BookingId bookingId = response.as(BookingId.class);
        Assert.assertEquals(bookingId.getBooking().toString(), booking.toString());
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is wrong");
    }

    @Test
    public void createBookingWithPOJOInSimpleWayTest() {
        BookingId bookingId = createBookingWithSerialization()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(BookingId.class);
        Assert.assertEquals(bookingId.getBooking().toString(), booking.toString());
    }
}
