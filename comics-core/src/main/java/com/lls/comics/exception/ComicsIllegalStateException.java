package com.lls.comics.exception;

/************************************
 * ComicsIllegalStateException
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public class ComicsIllegalStateException extends ComicsException {

    private static final long serialVersionUID = 90000123808400L;

    public ComicsIllegalStateException(String message) {
        super(message);
    }

    public ComicsIllegalStateException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsIllegalStateException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsIllegalStateException(Throwable cause) {
        super(cause);
    }

    public ComicsIllegalStateException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
