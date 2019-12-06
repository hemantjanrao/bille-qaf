package api.services;

import api.dto.AbstractDTO;
import com.google.common.collect.ImmutableMap;
import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class BaseService {

    protected static final Logger logger = LogManager.getLogger();

    protected abstract RequestSpecification getRequestSpec();

    //protected abstract ResponseSpecification getResponseSpec();

    protected ExtractableResponse get(String url) {
        return get(ImmutableMap.of(), url);
    }

    protected ExtractableResponse get(Map<String, ?> params, String url) {
        return request(params, url);
    }

    protected ExtractableResponse delete(String url) {
        return request(url);
    }

    protected ExtractableResponse request(Map<String, ?> params, String url) {
        return getRequestSpec()
                .params(params)
                .when()
                .request(Method.GET, url)
                .then()
                //.spec(getResponseSpec())
                .extract();
    }

    protected ExtractableResponse request(Method method, Object body, String url) {
        return getRequestSpec()
                .when()
                .body(body)
                .request(method, url)
                .then()
                //.spec(getResponseSpec())
                .extract();
    }

    protected ExtractableResponse request(String url) {
        return getRequestSpec()
                .when()
                .request(Method.DELETE, url)
                .then()
                .extract();
    }

    protected <T extends AbstractDTO> T getParsedResponse(
            ExtractableResponse responseObject,
            Class<T> responseClass){
        return responseObject.as(responseClass);
    }

}