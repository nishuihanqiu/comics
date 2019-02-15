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

    public static final String DEFAULT_CHARACTER = "utf-8";

    // netty codec and channel
    public static final short NETTY_MAGIC_TYPE = (short) 0xF1F1;
    // netty header length
    public static final int NETTY_HEADER = 16;
    // netty server max executor thread
    public static final int NETTY_EXECUTOR_MAX_SIZE = 800;
    // netty thread idle time: 1 minute
    public static final int NETTY_THREAD_KEEPALIVE_TIME = 60 * 1000;
    // netty client max concurrent request
    public static final int NETTY_CLIENT_MAX_REQUEST = 20000;
    // share channel max worker thread
    public static final int NETTY_SHARE_CHANNEL_MAX_WORKDER = 800;
    // share channel min worker thread
    public static final int NETTY_SHARE_CHANNEL_MIN_WORKDER = 40;
    // don't share channel max worker thread
    public static final int NETTY_NOT_SHARE_CHANNEL_MAX_WORKDER = 200;
    // don't share channel min worker thread
    public static final int NETTY_NOT_SHARE_CHANNEL_MIN_WORKDER = 20;
    public static final int NETTY_TIMEOUT_TIMER_PERIOD = 100;
    public static final byte NETTY_REQUEST_TYPE = 1;
    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;

}
