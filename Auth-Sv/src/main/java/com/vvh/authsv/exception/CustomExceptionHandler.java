package com.vvh.authsv.exception;

import com.vvh.authsv.constant.ErrorCodeStatus;
import com.vvh.authsv.response.BaseResponseError;
import jakarta.ws.rs.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static com.vvh.authsv.constant.ErrorCodeStatus.*;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {AlreadyExistsException.class})
    public BaseResponseError alreadyExistsException(AlreadyExistsException ex){
        return new BaseResponseError(BAD_REQUEST, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponseError methodArgumentNotValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        return BaseResponseError.builder()
                .error(processFieldErrors(fieldErrorList))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public BaseResponseError exception(Exception ex) {
        return new BaseResponseError(INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {AuthenticationException.class})
    public BaseResponseError authenticationException(AuthenticationException ex){
        return new BaseResponseError(AUTHORIZED, ex.getMessage(), null);
    }

    private BaseResponseError.Error processFieldErrors(List<FieldError> fieldErrorList){
        BaseResponseError.Error error = BaseResponseError.Error.builder()
                .code(VALIDATION_ERROR)
                .message(ErrorCodeStatus.getDetailErrorMessage(VALIDATION_ERROR))
                .build();

        List<BaseResponseError.ErrorDetail> errorDetailList = new ArrayList<>();
        for(FieldError fieldError: fieldErrorList){
            BaseResponseError.ErrorDetail errorDetail = BaseResponseError.ErrorDetail.builder()
                    .id(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
            errorDetailList.add(errorDetail);
        }
        error.setErrorDetails(errorDetailList);
        return error;
    }
}
