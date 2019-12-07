package stepdef;

import cucumber.api.java.en.*;

import api.dto.AbstractDTO;
import dto.data.Booking;
import dto.response.CreateBookingResponse;
import service.booking.BookingService;
import service.ping.PingService;

import static com.google.common.truth.Truth.assertThat;

public class BookingStepdefs {

    private BookingService service = null;
    private Booking booking;
    private AbstractDTO response;
    private int bookingIdToBeUpdated, bookingIdToBeDeleted = 0;

    @Given("^booking service is up$")
    public void bookingServiceIsUp() {
        assertThat(new PingService().ping())
                .isEqualTo("Created");
    }

    @When("^User creates booking$")
    public void userCreatesBooking() {
        // given some booking data
        service = new BookingService();
        booking = Booking.newInstance();

        // when creating the booking
        response = service.createBooking(booking);
    }

    @Then("^User should be successfully created$")
    public void userShouldSuccessfullyCreate() {
        // the booking returned matches the input and is persisted
        assertThat(((CreateBookingResponse)response).booking)
                .isEqualTo(booking);
        assertThat(service.getBooking(((CreateBookingResponse)response).bookingid))
                .isEqualTo(booking);
    }

    @Then("^Update the created booking$")
    public void updateTheCreatedBooking() {
        bookingIdToBeUpdated = ((CreateBookingResponse) response).bookingid;
        Booking bookingUpdate = Booking.newInstance();

        // and an auth token
        String authToken = new BookingService().createAuthToken(
                "admin", "password123");

        service.updateBooking(bookingUpdate, bookingIdToBeUpdated, authToken);
    }

    @Then("^Booking should be updated successfully$")
    public void bookingShouldBeUpdatedSuccessfully() {
        Booking booking = service.getBooking(bookingIdToBeUpdated);

        assertThat(service.getBooking(bookingIdToBeUpdated)).isEqualTo(booking);
    }

    @And("^Delete the created booking$")
    public void deleteTheCreatedBooking() {
        bookingIdToBeDeleted = ((CreateBookingResponse) response).bookingid;
        // and an auth token
        String authToken = new BookingService().createAuthToken(
                "admin", "password123");
        // when deleting
        service.delete(bookingIdToBeDeleted, authToken);
    }

    @Then("^Booking should be deleted successfully$")
    public void bookingShouldBeDeletedSuccessfully() {
        assertThat(service.doesBookingExist(bookingIdToBeDeleted)).isFalse();
    }

    @And("^Get the same booking$")
    public void getTheSameBooking() {
        assertThat(service.getBooking(((CreateBookingResponse)response).bookingid))
                .isEqualTo(booking);
    }
}

