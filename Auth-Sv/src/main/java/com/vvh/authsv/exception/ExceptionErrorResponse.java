package com.vvh.authsv.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionErrorResponse {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
