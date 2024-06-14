package com.vvh.authsv.dto.response;

public class ResponseError extends ResponseData{
    public ResponseError(int code, String message) {
        super(code, message);
    }
}
