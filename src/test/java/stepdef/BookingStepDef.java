package stepdef;

import api.dto.AbstractDTO;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.data.Booking;
import dto.response.CreateBookingResponse;
import service.booking.BookingService;
import service.ping.PingService;

import static com.google.common.truth.Truth.assertThat;

public class BookingStepDef {

    private BookingService service = null;
    private Booking booking, bookingUpdate = null;
    private AbstractDTO response, updatedResponse = null;
    private int bookingIdToBeUpdated = 0;

    @Given("booking service is up")
    public void featureIsAvailableToUser() {
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
        bookingUpdate = Booking.newInstance();

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
}
