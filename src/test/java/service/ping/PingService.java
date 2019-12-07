package service.ping;

import constant.BookingEndpoint;
import io.restassured.RestAssured;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import service.AbstractBookerService;

public class PingService extends AbstractBookerService {

    public String ping() {
        return get(BookingEndpoint.PING.getUrl())
                .body().asString();
    }

    protected ResponseSpecification getResponseSpec() {
        return RestAssured.expect().response()
                .statusCode(HttpStatus.SC_CREATED);
    }
}
