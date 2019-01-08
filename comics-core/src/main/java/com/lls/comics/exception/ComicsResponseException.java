package com.lls.comics.exception;

/************************************
 * ComicsResponseException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsResponseException extends ComicsException {

    private static final long serialVersionUID = 90448836938089400L;

    public ComicsResponseException(String message) {
        super(message);
    }

    public ComicsResponseException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsResponseException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsResponseException(Throwable cause) {
        super(cause);
    }

    public ComicsResponseException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
