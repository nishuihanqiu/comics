package com.lls.comics.exception;

/************************************
 * ComicsRequestException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsRequestException extends ComicsException {

    private static final long serialVersionUID = 678930048383400L;

    public ComicsRequestException(String message) {
        super(message);
    }

    public ComicsRequestException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsRequestException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsRequestException(Throwable cause) {
        super(cause);
    }

    public ComicsRequestException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
