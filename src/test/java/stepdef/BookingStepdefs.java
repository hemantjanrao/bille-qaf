package stepdef;

import com.sun.javafx.binding.StringFormatter;
import cucumber.api.java.en.*;

import dto.data.Booking;
import dto.response.CreateBookingResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import service.booking.BookingService;
import service.ping.PingService;

import static com.google.common.truth.Truth.assertThat;

public class BookingStepdefs {

    private BookingService service = null;
    private Booking booking, bookingUpdate;
    private CreateBookingResponse response;
    private int bookingIdToBeUpdated, bookingIdToBeDeleted = 0;
    private Response delete;

    @Given("^booking service is up$")
    public void bookingServiceIsUp() {
        Assert.assertEquals(
                new PingService().ping(),
                "Created", "Service is not running");
    }

    @When("^Booking creates booking$")
    public void userCreatesBooking() {
        // given some booking data
        service = new BookingService();
        booking = Booking.newInstance();

        // when creating the booking
        response = service.createBooking(booking);
    }

    @Then("^Booking should be successfully created$")
    public void userShouldSuccessfullyCreate() {
        Assert.assertEquals(
                service.getBooking(response.bookingid),
                booking,
                String.format("Booking %s is unsuccessful", booking)
        );
    }

    @Then("^Update the created booking$")
    public void updateTheCreatedBooking() {
        bookingIdToBeUpdated = response.bookingid;
        bookingUpdate = Booking.newInstance();

        // and an auth token
        String authToken = new BookingService().createAuthToken(
                "admin", "password123");

        Response response = service.updateBooking(bookingUpdate, bookingIdToBeUpdated, authToken);

        Assert.assertEquals(response.statusCode(), 200,
                String.format("Update failed with status code %d", response.statusCode()));
    }

    @Then("^Booking should be updated successfully$")
    public void bookingShouldBeUpdatedSuccessfully() {
        Booking booking = service.getBooking(bookingIdToBeUpdated);

        //assertThat(service.getBooking(bookingIdToBeUpdated)).isEqualTo(booking);
        Assert.assertEquals(
                booking,
                bookingUpdate,
                String.format("Created booking is %s and updated booking is %s", booking, bookingUpdate)
        );
    }

    @And("^Delete the created booking$")
    public void deleteTheCreatedBooking() {
        bookingIdToBeDeleted = response.bookingid;
        // and an auth token
        String authToken = new BookingService().createAuthToken(
                "admin", "password123");
        // when deleting
        delete = service.delete(bookingIdToBeDeleted, authToken);
    }

    @Then("^Booking should be deleted successfully$")
    public void bookingShouldBeDeletedSuccessfully() {
        assertThat(delete.statusCode()).isEqualTo(201);
        assertThat(service.doesBookingExist(bookingIdToBeDeleted)).isFalse();
    }

    @And("^Get the same booking$")
    public void getTheSameBooking() {
        assertThat(service.getBooking(response.bookingid))
                .isEqualTo(booking);
    }

    @And("^Deleted booking should not be accessible$")
    public void deletedBookingShouldNotBeAccessible() {
        assertThat(service.getDeletedBooking(bookingIdToBeDeleted).statusCode()).isEqualTo(404);
    }
}

