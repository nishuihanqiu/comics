package com.lls.comics.exception;

/************************************
 * ComicsIllegalStateException
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public class ComicsIllegalStateException extends IllegalStateException {

    public ComicsIllegalStateException() {
        this("comics service state error.");
    }

    public ComicsIllegalStateException(String message) {
        this(message, null);
    }

    public ComicsIllegalStateException(Throwable cause) {
        super("comics service state error.", cause);
    }

    public ComicsIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }

}
