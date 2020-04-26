package com.lls.comics.exception;

/************************************
 * ComicsSerializerException
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class ComicsSerializerException extends ComicsException {

    private static final long serialVersionUID = 6783849000123007L;

    public ComicsSerializerException() {
        super(ComicsExceptionConstants.FRAMEWORK_SERIALIZER_ERROR);
    }

    public ComicsSerializerException(ComicsExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public ComicsSerializerException(String message) {
        super(message, ComicsExceptionConstants.FRAMEWORK_SERIALIZER_ERROR);
    }

    public ComicsSerializerException(String message, ComicsExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public ComicsSerializerException(String message, Throwable cause) {
        super(message, cause, ComicsExceptionConstants.FRAMEWORK_SERIALIZER_ERROR);
    }

    public ComicsSerializerException(String message, Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(message, cause, exceptionMessage);
    }

    public ComicsSerializerException(Throwable cause) {
        super(cause, ComicsExceptionConstants.FRAMEWORK_SERIALIZER_ERROR);
    }

    public ComicsSerializerException(Throwable cause, ComicsExceptionMessage exceptionMessage) {
        super(cause, exceptionMessage);
    }
}
