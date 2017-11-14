package com.epam.jdi.http.requests;

import com.epam.jdi.tools.DataClass;
import com.jayway.restassured.response.Response;

/**
 * Created by Roman_Iovlev on 10/22/2017.
 */
public class ResponseStatus extends DataClass<ResponseStatus> {
    private ResponseStatusType type;
    private String text;
    private int code;

    public ResponseStatusType type() { return type; }
    public String text() { return text; }
    public int code() { return code; }

    public ResponseStatus(Response response) {
        code = response.statusCode();
        type = ResponseStatusType.getStatusType(code);
        text = response.statusLine();
    }


}
