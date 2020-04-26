package com.lls.comics.exception;

/************************************
 * ComicsNetworkException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsNetworkException extends ComicsException {

    private static final long serialVersionUID = 234509240288899L;

    public ComicsNetworkException() {
        super(ComicsExceptionConstants.SERVICE_NETWORK_ERROR);
    }

    public ComicsNetworkException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsNetworkException(String message) {
        super(message, ComicsExceptionConstants.SERVICE_NETWORK_ERROR);
    }

    public ComicsNetworkException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsNetworkException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.SERVICE_NETWORK_ERROR);
    }

    public ComicsNetworkException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsNetworkException(Throwable cause) {
        super(cause, ComicsExceptionConstants.SERVICE_NETWORK_ERROR);
    }

    public ComicsNetworkException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }

}
