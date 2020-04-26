package com.lls.comics.exception;

/************************************
 * ComicsArgumentException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsArgumentException extends ComicsException {

    private static final long serialVersionUID = 123349380890908L;

    public ComicsArgumentException() {
        super(ComicsExceptionConstants.SERVICE_ARGUMENT_ERROR_CODE);
    }

    public ComicsArgumentException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsArgumentException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_ARGUMENT_ERROR);
    }

    public ComicsArgumentException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsArgumentException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_ARGUMENT_ERROR);
    }

    public ComicsArgumentException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsArgumentException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_ARGUMENT_ERROR);
    }

    public ComicsArgumentException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }

}
