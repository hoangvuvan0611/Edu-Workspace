package com.vvh.apigateway.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseError extends BaseResponse{

    private Error error;

    public BaseResponseError(int code, String message, List<ErrorDetail> errorDetails){
        super(false);
        error = new Error(code, message, errorDetails);
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error{
        private int code;
        private String message;
        private List<ErrorDetail> errorDetails;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail{
        private String id;
        private String message;
    }
}
