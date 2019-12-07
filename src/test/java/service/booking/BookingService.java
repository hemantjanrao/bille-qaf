package service.booking;

import com.google.common.collect.ImmutableMap;
import constant.BookingEndpoint;
import dto.data.Booking;
import dto.response.CreateBookingResponse;
import io.restassured.http.Method;
import io.restassured.response.Response;
import service.AbstractBookerService;

public class BookingService extends AbstractBookerService {

    public Booking getBooking(int id) {
        return get(BookingEndpoint.BOOKING_ID.getUrl(id))
                .as(Booking.class);
    }

    public Response getDeletedBooking(int id) {
        return getRequestSpec()
                .get(BookingEndpoint.BOOKING_ID.getUrl(id));
    }

    public CreateBookingResponse createBooking(Booking booking) {
        return post(booking, BookingEndpoint.BOOKING.getUrl())
                .as(CreateBookingResponse.class);
    }

    public String createAuthToken(String username, String password) {
        return post(
                ImmutableMap.of("username", username, "password", password),
                BookingEndpoint.AUTH.getUrl())
                .jsonPath().get("token");
    }

    public Response delete(int bookingID, String token) {
        return getRequestSpec()
                .cookie("token", token)
                .delete(BookingEndpoint.BOOKING_ID.getUrl(bookingID));
    }

    public boolean doesBookingExist(int bookingID) {
        int statusCode = getRequestSpec()
                .get(BookingEndpoint.BOOKING_ID.getUrl(bookingID))
                .then()
                .extract()
                .statusCode();
        if (statusCode == 200) {
            return true;
        } else if (statusCode == 404) {
            return false;
        } else {
            throw new IllegalStateException("Unexpected return code");
        }
    }

    public void updateBooking(Booking booking, int bookingID, String token) {
         getRequestSpec()
                .header("token",token)
                .when()
                .body(booking)
                .request(Method.PUT, BookingEndpoint.BOOKING_ID.getUrl(bookingID))
                .then()
                .extract().response();
    }
}
