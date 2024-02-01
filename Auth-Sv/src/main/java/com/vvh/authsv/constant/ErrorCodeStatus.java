package com.vvh.authsv.constant;

import org.apache.http.HttpStatus;

public class ErrorCodeStatus implements HttpStatus {

    /*
    * information(1xx)
    * successful(2xx)
    * redirection(3xx)
    * clientError(4xx): Loi trong qua trinh xu ly yeu cau va loi den tu client
    * serverError(5xx): loi trong qua trinh xu ly yeu cau va loi den tu server
    * */
    public static final int VALIDATION_ERROR = 2;
    public static final int BAD_REQUEST = 400;
    public static final int AUTHORIZED = 401;
    public static final int FORBIDDEN = 402;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIME_OUT = 504;

    public static String getDetailErrorMessage(int errorCode){
        return switch (errorCode) {
            case VALIDATION_ERROR -> "Tham số không hợp lệ!";
            case BAD_REQUEST -> "Yêu cầu không hợp lệ!";
            case AUTHORIZED -> "Yêu cầu xác thực!";
            case FORBIDDEN -> "Không có quyền truy cập!";
            case NOT_FOUND -> "Không tìm thấy tài nguyên yêu cầu";
            case METHOD_NOT_ALLOWED -> "Yêu cầu không được hỗ trợ!";
            case INTERNAL_SERVER_ERROR -> "Lỗi máy chủ!";
            case SERVICE_UNAVAILABLE -> "Dịch vụ không có sẵn!";
            case GATEWAY_TIME_OUT -> "Hết thời gian truy cập!";
            default -> "Lỗi không xác định!";
        };
    }
}
