package com.lls.comics.exception;

/************************************
 * ComicsRequestException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsRequestException extends ComicsException {

    private static final long serialVersionUID = 678930048383400L;

    public ComicsRequestException() {
        super(ComicsExceptionConstants.SERVICE_REQUEST_ERROR);
    }

    public ComicsRequestException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsRequestException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_REQUEST_ERROR);
    }

    public ComicsRequestException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsRequestException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_REQUEST_ERROR);
    }

    public ComicsRequestException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsRequestException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_REQUEST_ERROR);
    }

    public ComicsRequestException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }
}
