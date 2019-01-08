package com.lls.comics.exception;

/************************************
 * ComicsArgumentException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsArgumentException extends ComicsException {

    private static final long serialVersionUID = 123349380890908L;

    public ComicsArgumentException(String message) {
        super(message);
    }

    public ComicsArgumentException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsArgumentException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsArgumentException(Throwable cause) {
        super(cause);
    }

    public ComicsArgumentException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
