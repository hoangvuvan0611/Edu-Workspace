package com.vvh.authsv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class ResponseData <T> {
    private final boolean success;
    private final int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * Response data for the api to retrieve data successfully. For GET, POST only
     * @param status
     * @param message
     * @param data
     */
    public ResponseData(boolean success,int status, String message, T data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Response data for the api executes successfully. For PUT, PATCH, DELETE of getting error
     * @param status
     * @param message
     */
    public ResponseData(boolean success,int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }
}
