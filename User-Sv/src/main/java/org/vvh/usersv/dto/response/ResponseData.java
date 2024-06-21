package org.vvh.usersv.dto.response;

import lombok.Getter;

@Getter
public class ResponseData<T> {
    private final boolean success;
    private final int status;
    private final String message;
    private T data;

    /**
     * Response data for the API to retrieve data successfully. For GET, POST only
     * @param status
     * @param message
     * @param data
     */
    public ResponseData(int status, String message, T data) {
        this.success = true;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Response data for the API executes data successfully for (PUT, PATCH, DELETE) or getting error
     * @param status
     * @param message
     */
    public ResponseData(int status, String message) {
        this.success = false;
        this.status = status;
        this.message = message;
    }
}
