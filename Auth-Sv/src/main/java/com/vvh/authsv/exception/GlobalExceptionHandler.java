package com.vvh.authsv.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        ExceptionErrorResponse errorResponse = getBadRequestError(webRequest);
        errorResponse.setMessage(Arrays.toString(ex.getDetailMessageArguments()));
        return errorResponse;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionErrorResponse handleMethodConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        ExceptionErrorResponse errorResponse = getBadRequestError(webRequest);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest webRequest) {
        ExceptionErrorResponse errorResponse = getBadRequestError(webRequest);
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionErrorResponse handlerMethodValidationException(HandlerMethodValidationException ex, WebRequest webRequest) {
        ExceptionErrorResponse errorResponse = getBadRequestError(webRequest);
        errorResponse.setMessage(Arrays.toString(ex.getDetailMessageArguments()));
        return errorResponse;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionErrorResponse handleMethodResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        String errorPath = webRequest.getDescription(false).replace("uri=", "");
        return ExceptionErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .path(errorPath)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    private ExceptionErrorResponse getBadRequestError(WebRequest webRequest) {
        String errorPath = webRequest.getDescription(false).replace("uri=", "");
        return ExceptionErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(errorPath)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(value = {AlreadyExistsException.class})
//    public BaseResponseError alreadyExistsException(AlreadyExistsException ex){
//        return new BaseResponseError(BAD_REQUEST, ex.getMessage(), null);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public BaseResponseError methodArgumentNotValidException(MethodArgumentNotValidException ex){
//        BindingResult bindingResult = ex.getBindingResult();
//        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
//
//        return BaseResponseError.builder()
//                .error(processFieldErrors(fieldErrorList))
//                .build();
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    @ExceptionHandler(value = {Exception.class})
//    public BaseResponseError exception(Exception ex) {
//        return new BaseResponseError(INTERNAL_SERVER_ERROR, ex.getMessage(), null);
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    @ExceptionHandler(value = {AuthenticationException.class})
//    public BaseResponseError authenticationException(AuthenticationException ex){
//        return new BaseResponseError(AUTHORIZED, ex.getMessage(), null);
//    }
//
//    private BaseResponseError.Error processFieldErrors(List<FieldError> fieldErrorList){
//        BaseResponseError.Error error = BaseResponseError.Error.builder()
//                .code(VALIDATION_ERROR)
//                .message(ErrorCodeStatus.getDetailErrorMessage(VALIDATION_ERROR))
//                .build();
//
//        List<BaseResponseError.ErrorDetail> errorDetailList = new ArrayList<>();
//        for(FieldError fieldError: fieldErrorList){
//            BaseResponseError.ErrorDetail errorDetail = BaseResponseError.ErrorDetail.builder()
//                    .id(fieldError.getField())
//                    .message(fieldError.getDefaultMessage())
//                    .build();
//            errorDetailList.add(errorDetail);
//        }
//        error.setErrorDetails(errorDetailList);
//        return error;
//    }
}
