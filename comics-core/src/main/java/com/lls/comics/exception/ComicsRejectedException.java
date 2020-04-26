package com.lls.comics.exception;

/************************************
 * ComicsRejectedException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsRejectedException extends ComicsException {

    private static final long serialVersionUID = 4509359900889933L;

    public ComicsRejectedException() {
        super(ComicsExceptionConstants.SERVICE_REJECT_ERROR);
    }

    public ComicsRejectedException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsRejectedException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_REJECT_ERROR);
    }

    public ComicsRejectedException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsRejectedException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_REJECT_ERROR);
    }

    public ComicsRejectedException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsRejectedException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_REJECT_ERROR);
    }

    public ComicsRejectedException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }

}
