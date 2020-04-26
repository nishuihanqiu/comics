package com.lls.comics.exception;

/************************************
 * ComicsResponseException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsResponseException extends ComicsException {

    private static final long serialVersionUID = 90448836938089400L;

    public ComicsResponseException() {
        super(ComicsExceptionConstants.SERVICE_RESPONSE_ERROR);
    }

    public ComicsResponseException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsResponseException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_RESPONSE_ERROR);
    }

    public ComicsResponseException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsResponseException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_RESPONSE_ERROR);
    }

    public ComicsResponseException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsResponseException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_RESPONSE_ERROR);
    }

    public ComicsResponseException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }
}
