package dto.response;

import api.dto.AbstractDTO;
import dto.data.Booking;

public class CreateBookingResponse extends AbstractDTO<CreateBookingResponse> {
    public Booking booking;
    public int bookingid;
}
