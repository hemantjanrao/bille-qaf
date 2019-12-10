package service;

import api.services.BaseService;
import constant.BookingEndpoint;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractBookerService extends BaseService {

    @Override
    protected RequestSpecification getRequestSpec() {

        return RestAssured.given()
                .baseUri(BookingEndpoint.BASE_URI.getUrl())
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .accept("application/json");
    }

    protected Response post(Object body, String url) {
        return request(Method.POST, body, url);
    }
}
