package com.lls.comics.exception;

/************************************
 * ComicsNetworkException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsNetworkException extends ComicsException {

    private static final long serialVersionUID = 234509240288899L;

    public ComicsNetworkException(String message) {
        super(message);
    }

    public ComicsNetworkException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsNetworkException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsNetworkException(Throwable cause) {
        super(cause);
    }

    public ComicsNetworkException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
