package com.lls.comics.exception;

/************************************
 * ComicsException
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public class ComicsException extends RuntimeException {

    private static final long serialVersionUID = 12399938089400L;

    private String errorCode;
    private String message;
    private static final String DEFAULT_ERROR_CODE = "COMICS_EXCEPTION";

    public ComicsException() {
        this("base comics exception.");
    }

    public ComicsException(String message) {
        this(message, DEFAULT_ERROR_CODE);
    }

    public ComicsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public ComicsException(String message, Throwable cause) {
        this(message, cause, DEFAULT_ERROR_CODE);
    }

    public ComicsException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ComicsException(Throwable cause) {
        this(cause, DEFAULT_ERROR_CODE);
    }

    public ComicsException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
