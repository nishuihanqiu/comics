package com.lls.comics.exception;

import java.io.Serializable;

/************************************
 * ComicsExceptionMessage
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class ComicsExceptionMessage implements Serializable {

    private static final long serialVersionUID = 3005459300380103048L;

    private int status;
    private String errorCode;
    private String message;

    public ComicsExceptionMessage(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
