package stepdef;

import api.utils.properties.Property;
import cucumber.api.java.en.*;

import dto.data.Booking;
import dto.response.CreateBookingResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import service.booking.BookingService;
import service.ping.PingService;

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
                Property.USERNAME.getValue(), Property.PASSWORD.getValue());

        Response response = service.updateBooking(bookingUpdate, bookingIdToBeUpdated, authToken);

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK,
                String.format("Update failed with status code %d", response.statusCode()));
    }

    @Then("^Booking should be updated successfully$")
    public void bookingShouldBeUpdatedSuccessfully() {
        Booking booking = service.getBooking(bookingIdToBeUpdated);

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
                Property.USERNAME.getValue(), Property.PASSWORD.getValue());
        // when deleting
        delete = service.delete(bookingIdToBeDeleted, authToken);
    }

    @Then("^Booking should be deleted successfully$")
    public void bookingShouldBeDeletedSuccessfully() {
        Assert.assertEquals(
                delete.statusCode(),
                HttpStatus.SC_CREATED,
                String.format("Booking deletion failed with status code %d", delete.statusCode())
        );

        Assert.assertFalse(
                service.doesBookingExist(bookingIdToBeDeleted),
                "Booking is not successfully deleted"
        );
    }

    @And("^Get the same booking$")
    public void getTheSameBooking() {
        Assert.assertEquals(
                service.getBooking(response.bookingid),
                booking,
                "Created booking is not accessible"
        );
    }

    @And("^Deleted booking should not be accessible$")
    public void deletedBookingShouldNotBeAccessible() {
        Assert.assertEquals(
                service.getDeletedBooking(bookingIdToBeDeleted).statusCode(),
                HttpStatus.SC_NOT_FOUND,
                String.format("Deleted booking is accessible with code %d",
                        service.getDeletedBooking(bookingIdToBeDeleted).statusCode() ));
    }
}