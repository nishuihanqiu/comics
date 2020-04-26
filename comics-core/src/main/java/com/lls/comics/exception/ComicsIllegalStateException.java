package com.lls.comics.exception;

/************************************
 * ComicsIllegalStateException
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public class ComicsIllegalStateException extends ComicsException {

    private static final long serialVersionUID = 90000123808400L;

    public ComicsIllegalStateException() {
        super(ComicsExceptionConstants.SERVICE_ILLEGAL_STATE);
    }

    public ComicsIllegalStateException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsIllegalStateException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_ILLEGAL_STATE);
    }

    public ComicsIllegalStateException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsIllegalStateException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_ILLEGAL_STATE);
    }

    public ComicsIllegalStateException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsIllegalStateException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_ILLEGAL_STATE);
    }

    public ComicsIllegalStateException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }

}
