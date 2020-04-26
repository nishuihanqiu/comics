package com.lls.comics.exception;

/************************************
 * ComicsException
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public class ComicsException extends RuntimeException {

    private static final long serialVersionUID = 12399938089400L;

    protected String errorMessage = "";
    protected ComicsExceptionMessage exceptionMessage = ComicsExceptionConstants.FRAMEWORK_DEFAULT_ERROR;

    public ComicsException() {
        super();
    }

    public ComicsException(ComicsExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public ComicsException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public ComicsException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message);
        this.errorMessage = message;
        this.exceptionMessage = exceptionMessage;
    }

    public ComicsException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public ComicsException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause);
        this.errorMessage = message;
        this.exceptionMessage = exceptionMessage;
    }

    public ComicsException(Throwable cause) {
        super(cause);
    }

    public ComicsException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause);
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        String message = getOriginMessage();
        return "error_message: " + message + ", status: " + exceptionMessage.getStatus() + ", error_code: " + exceptionMessage.getErrorCode()
                + ",r=";
    }

    public String getOriginMessage(){
        if (exceptionMessage == null) {
            return super.getMessage();
        }

        String message;

        if (errorMessage != null && !"".equals(errorMessage)) {
            message = errorMessage;
        } else {
            message = exceptionMessage.getMessage();
        }
        return message;
    }

    public int getStatus() {
        return exceptionMessage == null ? 0 : exceptionMessage.getStatus();
    }

    public String getErrorCode() {
        return exceptionMessage == null ? "" : exceptionMessage.getErrorCode();
    }

    public ComicsExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

}
