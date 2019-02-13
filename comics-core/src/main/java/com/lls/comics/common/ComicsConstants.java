package com.lls.comics.common;

/************************************
 * ComicsConstants
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public class ComicsConstants {
    
    public static final String FRAMEWORK_NAME = "comics";
    public static final String PROTOCOL_SEPARATOR = "://";
    public static final String PATH_SEPARATOR = "/";

    public static final String DEFAULT_VERSION = "1.0";
    public static final String ASYNC_SUFFIX = "Async";
    public static final String METHOD_CONFIG_SUFFIX = "method_config.";

    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;

    public static final String DEFAULT_CHARACTER = "utf-8";

}
