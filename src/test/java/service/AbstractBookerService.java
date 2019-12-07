package service;

import api.dto.AbstractDTO;
import api.services.BaseService;
import constant.BookingEndpoint;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
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

    protected ExtractableResponse post(Object body, String url) {
        return request(Method.POST, body, url);
    }

    protected RequestSpecification update(Object body, String url) {
        return (RequestSpecification) request(Method.PUT, body, url);
    }

    public <T extends AbstractDTO> T getParsed(ExtractableResponse response, Class<T> theClass){
        return getParsedResponse(response, theClass);
    }
}
