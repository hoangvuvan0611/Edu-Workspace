package com.vvh.coresv.exception;

import com.vvh.coresv.constant.ErrorCodeStatus;
import com.vvh.coresv.response.base.BaseResponseError;
import jakarta.ws.rs.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

import static com.vvh.coresv.constant.ErrorCodeStatus.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = {NotFoundException.class})
    public BaseResponseError notFoundException(NotFoundException ex){
        return new BaseResponseError(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {AlreadyExistsException.class})
    public BaseResponseError alreadyExistsException(AlreadyExistsException ex){
        return new BaseResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
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

    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(value = {AuthenticationException.class})
    public BaseResponseError authenticationException(AuthenticationException ex){
        return new BaseResponseError(
                UNAUTHORIZED,
                ErrorCodeStatus.getDetailErrorMessage(AUTHORIZED),
                null
        );
    }

    private BaseResponseError.Error processFieldErrors(List<FieldError> fieldErrorList){
        HttpStatus httpStatus = HttpStatus.valueOf(VALIDATION_ERROR);
        BaseResponseError.Error error = BaseResponseError.Error.builder()
                .code(httpStatus)
                .message(getDetailErrorMessage(httpStatus.value()))
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
