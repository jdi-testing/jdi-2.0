package com.epam.jdi.http.requests;

import com.epam.jdi.http.ExceptionHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.function.Function;

import static com.epam.jdi.http.requests.ResponseStatusType.ERROR;
import static com.epam.jdi.http.requests.ResponseStatusType.OK;
import static com.epam.jdi.tools.StringUtils.LINE_BREAK;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Roman_Iovlev on 12/19/2016.
 */
public class RestResponse {
    private Response raResponse;
    private long responseTimeMSec;

    public RestResponse(Response raResponse) {
        this(raResponse, 0);
    }
    public RestResponse(Response raResponse, long time) {
        this.raResponse = raResponse;
        responseTimeMSec = time;
    }

    public boolean verify(Function<RestResponse, Boolean> validator) {
        return validator.apply(this);
    }
    public void validate(Function<RestResponse, Boolean> validator) {
        if (!verify(validator))
            throw ExceptionHandler.exception("Bad raResponse: " + toString());
    }

    public ResponseStatus status() {
        return new ResponseStatus(raResponse);
    }
    public void isOk() {
        validate(r -> status().type() == OK);
    }
    public void hasErrors() {
        validate(r -> status().type() == ERROR);
    }

    public String body() {
        return raResponse.body().print();
    }
    public JsonObject jsonBody() {
        return (JsonObject) new JsonParser().parse(body());
    }
    public String jsonBody(String path) {
        if (isBlank(path))
            return jsonBody().toString();
        String[] steps = path.split("\\.");
        JsonObject json = jsonBody();
        for (int i = 0; i < steps.length - 1; i++)
            json = json.getAsJsonObject(steps[i]);
        try {
            return json.getAsJsonPrimitive(steps[steps.length-1]).getAsString();
        } catch (Exception ignore) {
            return json.getAsJsonObject(steps[steps.length - 1]).toString();
        }
    }
    public List<Header> headers() { return raResponse.getHeaders().asList(); }
    public String cookie(String name) { return raResponse.getCookie(name); }

    public Response raResponse() { return raResponse; }
    public long responseTime() { return responseTimeMSec; }

    public ValidatableResponse assertThat() { return raResponse.then(); }
    public void assertStatus(int code, ResponseStatusType type) {
        String errors = "";
        if (status().code() != code)
            errors += format("Wrong status code %s. Expected: %s", code, status().code()) + LINE_BREAK;
        if (!status().type().equals(type))
            errors += format("Wrong status type %s. Expected: %s", type, status().type());
        if (!errors.equals(""))
            throw ExceptionHandler.exception(errors);
    }
}
