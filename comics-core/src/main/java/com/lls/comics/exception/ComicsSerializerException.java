package com.lls.comics.exception;

/************************************
 * ComicsSerializerException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsSerializerException extends ComicsException {

    private static final long serialVersionUID = 6783849000123007L;

    public ComicsSerializerException(String message) {
        super(message);
    }

    public ComicsSerializerException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsSerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsSerializerException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsSerializerException(Throwable cause) {
        super(cause);
    }

    public ComicsSerializerException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
