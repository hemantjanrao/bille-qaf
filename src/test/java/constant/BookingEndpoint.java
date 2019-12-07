package constant;

import api.Endpoint;
import api.utils.properties.Property;

public enum BookingEndpoint implements Endpoint {

    BASE_URI(Property.APP_URL.getValue()),
    PING("/ping"),
    BOOKING("/booking"),
    BOOKING_ID("/booking/%d"),
    AUTH("/auth");

    private String url;

    BookingEndpoint(String url) {
        this.url = url;
    }

    public String getUrl(Object... params) {
        return String.format(url, params);
    }

}
