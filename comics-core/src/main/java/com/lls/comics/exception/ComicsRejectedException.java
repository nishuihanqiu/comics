package com.lls.comics.exception;

/************************************
 * ComicsRejectedException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsRejectedException extends ComicsException {

    private static final long serialVersionUID = 4509359900889933L;

    public ComicsRejectedException(String message) {
        super(message);
    }

    public ComicsRejectedException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ComicsRejectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicsRejectedException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ComicsRejectedException(Throwable cause) {
        super(cause);
    }

    public ComicsRejectedException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }

}
