package api.services;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public abstract class BaseService {

    protected final Logger logger = LogManager.getLogger(this);

    protected abstract RequestSpecification getRequestSpec();

    protected Response get(String url) {
        return get(ImmutableMap.of(), url);
    }

    protected Response get(Map<String, ?> params, String url) {
        return request(params, url);
    }

    protected Response request(Map<String, ?> params, String url) {
        return getRequestSpec()
                .params(params)
                .when()
                .request(Method.GET, url)
                .then()
                .extract().response();
    }

    protected Response request(Method method, Object body, String url) {
        return getRequestSpec()
                .when()
                .body(body)
                .request(method, url)
                .then()
                .extract().response();
    }
}