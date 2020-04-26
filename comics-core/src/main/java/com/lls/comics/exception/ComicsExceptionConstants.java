package com.lls.comics.exception;

/************************************
 * ComicsExceptionConstants
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class ComicsExceptionConstants {

    public static final String DEFAULT_ERROR_CODE = "DEFAULT_ERROR_CODE";
    // service error status 503
    public static final String SERVICE_DEFAULT_ERROR_CODE = "SERVICE_DEFAULT_ERROR_CODE";
    public static final String SERVICE_REJECT_ERROR_CODE = "SERVICE_REJECT_ERROR_CODE";
    public static final String SERVICE_TIMEOUT_ERROR_CODE = "SERVICE_TIMEOUT_ERROR_CODE";
    public static final String SERVICE_TASK_CANCEL_ERROR_CODE = "SERVICE_TASK_CANCEL_ERROR_CODE";
    public static final String SERVICE_ARGUMENT_ERROR_CODE = "SERVICE_ARGUMENT_ERROR_CODE";
    public static final String SERVICE_ILLEGAL_STATE_ERROR_CODE = "SERVICE_ILLEGAL_STATE_ERROR_CODE";
    public static final String SERVICE_NETWORK_ERROR_CODE = "SERVICE_NETWORK_ERROR_CODE";
    public static final String SERVICE_REQUEST_ERROR_CODE = "SERVICE_REQUEST_ERROR_CODE";
    public static final String SERVICE_RESPONSE_ERROR_CODE = "SERVICE_RESPONSE_ERROR_CODE";
    // service error status 404
    public static final String SERVICE_NOT_FOUND_ERROR_CODE = "SERVICE_NOT_FOUND_ERROR_CODE";
    // service error status 403
    public static final String SERVICE_REQUEST_LENGTH_OUT_OF_LIMIT_ERROR_CODE = "SERVICE_REQUEST_LENGTH_OUT_OF_LIMIT_ERROR_CODE";
    // framework error
    public static final String FRAMEWORK_DEFAULT_ERROR_CODE = "FRAMEWORK_DEFAULT_ERROR_CODE";
    public static final String FRAMEWORK_ENCODE_ERROR_CODE = "FRAMEWORK_ENCODE_ERROR_CODE";
    public static final String FRAMEWORK_DECODE_ERROR_CODE = "FRAMEWORK_DECODE_ERROR_CODE";
    public static final String FRAMEWORK_INIT_ERROR_CODE = "FRAMEWORK_INIT_ERROR_CODE";
    public static final String FRAMEWORK_EXPORT_ERROR_CODE = "FRAMEWORK_EXPORT_ERROR_CODE";
    public static final String FRAMEWORK_SERVER_ERROR_CODE = "FRAMEWORK_SERVER_ERROR_CODE";
    public static final String FRAMEWORK_REFER_ERROR_CODE = "FRAMEWORK_REFER_ERROR_CODE";
    public static final String FRAMEWORK_REGISTER_ERROR_CODE = "FRAMEWORK_REGISTER_ERROR_CODE";
    public static final String FRAMEWORK_SERIALIZER_ERROR_CODE = "FRAMEWORK_SERIALIZER_ERROR_CODE";
    // biz exception
    public static final String BIZ_DEFAULT_ERROR_CODE = "BIZ_DEFAULT_ERROR_CODE";
    /**
     * service error start
     **/
    public static final ComicsExceptionMessage SERVICE_DEFAULT_ERROR = new ComicsExceptionMessage(503,
            SERVICE_DEFAULT_ERROR_CODE, "service error");
    public static final ComicsExceptionMessage SERVICE_REJECT_ERROR = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service reject error");
    public static final ComicsExceptionMessage SERVICE_ARGUMENT_ERROR = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service argument error");
    public static final ComicsExceptionMessage SERVICE_ILLEGAL_STATE = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service illegal state error");
    public static final ComicsExceptionMessage SERVICE_NETWORK_ERROR = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service network error");
    public static final ComicsExceptionMessage SERVICE_REQUEST_ERROR = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service request error");
    public static final ComicsExceptionMessage SERVICE_RESPONSE_ERROR = new ComicsExceptionMessage(503,
            SERVICE_REJECT_ERROR_CODE, "service response error");


    public static final ComicsExceptionMessage SERVICE_NOT_FOUND = new ComicsExceptionMessage(404,
            SERVICE_NOT_FOUND_ERROR_CODE, "service not found");
    public static final ComicsExceptionMessage SERVICE_TIMEOUT = new ComicsExceptionMessage(503,
            SERVICE_TIMEOUT_ERROR_CODE, "service timeout");
    public static final ComicsExceptionMessage SERVICE_TASK_CANCEL = new ComicsExceptionMessage(503,
            SERVICE_TASK_CANCEL_ERROR_CODE, "service task cancel");
    public static final ComicsExceptionMessage SERVICE_REQUEST_LENGTH_OUT_OF_LIMIT = new ComicsExceptionMessage(403,
            SERVICE_REQUEST_LENGTH_OUT_OF_LIMIT_ERROR_CODE, "service request data length over of limit");

    public static final ComicsExceptionMessage FRAMEWORK_DEFAULT_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_DEFAULT_ERROR_CODE, "framework default error");
    public static final ComicsExceptionMessage FRAMEWORK_ENCODE_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_ENCODE_ERROR_CODE, "framework encode error");
    public static final ComicsExceptionMessage FRAMEWORK_DECODE_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_DECODE_ERROR_CODE, "framework decode error");
    public static final ComicsExceptionMessage FRAMEWORK_INIT_ERROR = new ComicsExceptionMessage(500,
            FRAMEWORK_INIT_ERROR_CODE, "framework init error");
    public static final ComicsExceptionMessage FRAMEWORK_EXPORT_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_EXPORT_ERROR_CODE, "framework export error");
    public static final ComicsExceptionMessage FRAMEWORK_REFER_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_REFER_ERROR_CODE, "framework refer error");
    public static final ComicsExceptionMessage FRAMEWORK_SERIALIZER_ERROR = new ComicsExceptionMessage(503,
            FRAMEWORK_SERIALIZER_ERROR_CODE, "framework serializer error");
    public static final ComicsExceptionMessage BIZ_DEFAULT_ERROR = new ComicsExceptionMessage(503,
            BIZ_DEFAULT_ERROR_CODE, "business default error");

    private ComicsExceptionConstants() {

    }

}
