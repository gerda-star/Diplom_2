package site.nomoreparties.stellarburgers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

abstract class BaseHttpClient {
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    private RequestSpecification baseRequestWithToken(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addHeader("Authorization", token)
                .build();
    }

    public ValidatableResponse doPostRequest(String url, Object body) {
        RequestSpecification request = given(baseRequest());
        request.body(body);
        return request.post(url).then();
    }
    public ValidatableResponse doGetRequest(String url, String token ) {
        RequestSpecification request = given(baseRequestWithToken(token));
        return request.get(url).then();
    }

    public ValidatableResponse doDeleteRequest(String url, String token ) {
        RequestSpecification request = given(baseRequestWithToken(token));
        return request.delete(url).then();
    }
    public  ValidatableResponse doPatchRequest(String url, Object body, String token){
        RequestSpecification request = given(baseRequestWithToken(token));
        request.body(body);
        return request.patch(url).then();
    }
}
