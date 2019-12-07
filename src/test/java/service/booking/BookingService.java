package service.booking;

import com.google.common.collect.ImmutableMap;
import constant.BookingEndpoint;
import dto.data.Booking;
import dto.data.BookingID;
import dto.response.CreateBookingResponse;
import io.restassured.http.Method;
import io.restassured.response.Response;
import service.AbstractBookerService;

import java.util.List;
import java.util.Map;

public class BookingService extends AbstractBookerService {

    public List<BookingID> listBookings() {
        return get(BookingEndpoint.BOOKING.getUrl())
                .jsonPath().getList(".", BookingID.class);
    }

    public Booking getBooking(int id) {
        return get(BookingEndpoint.BOOKING_ID.getUrl(id))
                .as(Booking.class);
    }

    public CreateBookingResponse createBooking(Booking booking) {
        return post(booking, BookingEndpoint.BOOKING.getUrl())
                .as(CreateBookingResponse.class);
    }

    public List<BookingID> search(Map<String, String> searchParams) {
        return request(Method.GET, searchParams, BookingEndpoint.BOOKING.getUrl())
                .jsonPath().getList(".", BookingID.class);
    }

    public String createAuthToken(String username, String password) {
        return post(
                ImmutableMap.of("username", username, "password", password),
                BookingEndpoint.AUTH.getUrl())
                .jsonPath().get("token");
    }

    public void delete(int bookingID, String token) {
        getRequestSpec()
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

    public Response updateBooking(Booking booking, int bookingID, String token) {
        return getRequestSpec()
                .cookie("token", token)
                .put(BookingEndpoint.BOOKING_ID.getUrl(bookingID))
        ;
    }

}
