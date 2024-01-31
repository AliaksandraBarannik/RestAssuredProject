import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objects.Booking;
import objects.Bookingdates;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import utils.FileReader;

public class BaseTest {
    protected RequestSpecification spec;
    protected Bookingdates bookingDates;
    protected Booking booking;

    @BeforeMethod
    protected void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }

    protected Response createBookingWithSerialization() {
        booking = FileReader.serializeJsonFromResource(Booking.class, "booking.json");
        Response response = RestAssured
                .given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .post("/booking");
        return response;
    }

    protected Response createBooking() {
        bookingDates = new Bookingdates("2018-01-01", "2019-01-01");
        booking = new Booking("Sasha", "Barannik", 111,
                false, bookingDates, "Breakfast");
        JSONObject body = new JSONObject();
        body.put("firstname", "Sasha");
        body.put("lastname", "Barannik");
        body.put("totalprice", 111);
        body.put("depositpaid", false);
        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        Response response = RestAssured
                .given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/booking");
        return response;
    }
}
