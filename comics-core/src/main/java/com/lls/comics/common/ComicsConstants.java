package com.lls.comics.common;

import com.lls.comics.util.ReflectUtils;

import java.util.regex.Pattern;

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

    public static final String SEPARATOR_ACCESS_LOG = "|";
    public static final String COMMA_SEPARATOR = ",";
    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    public static final String REGISTRY_SEPARATOR = "|";
    public static final Pattern REGISTRY_SPLIT_PATTERN = Pattern.compile("\\s*[|;]+\\s*");
    public static final String SEMICOLON_SEPARATOR = ";";
    public static final Pattern SEMICOLON_SPLIT_PATTERN = Pattern.compile("\\s*[;]+\\s*");
    public static final String QUERY_PARAM_SEPARATOR = "&";
    public static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("\\s*[&]+\\s*");
    public static final String EQUAL_SIGN_SEPARATOR = "=";
    public static final Pattern EQUAL_SIGN_PATTERN = Pattern.compile("\\s*[=]\\s*");
    public static final String NODE_TYPE_SERVICE = "service";
    public static final String NODE_TYPE_REFERER = "referer";
    public static final String SCOPE_NONE = "none";
    public static final String SCOPE_LOCAL = "local";
    public static final String SCOPE_REMOTE = "remote";
    public static final String REGISTRY_PROTOCOL_LOCAL = "local";
    public static final String REGISTRY_PROTOCOL_DIRECT = "direct";
    public static final String REGISTRY_PROTOCOL_ZOOKEEPER = "zookeeper";
    public static final String PROTOCOL_IN_JVM = "injvm";
    public static final String PROTOCOL_COMICS = "comics";
    public static final String PROXY_JDK = "jdk";
    public static final String PROXY_COMMON = "common";
    public static final String PROXY_JAVASSIST = "javassist";
    public static final String PROTOCOL_SWITCHER_PREFIX = "protocol:";
    public static final String METHOD_CONFIG_PREFIX = "method_config.";
    public static final int MILLS = 1;
    public static final int SECOND_MILLS = 1000;
    public static final int MINUTE_MILLS = 60 * SECOND_MILLS;
    public static final String DEFAULT_VALUE = "default";
    public static final int DEFAULT_INT_VALUE = 0;
    public static final boolean DEFAULT_THROWS_EXCEPTION = true;
    public static final int SLOW_COST = 50; // 50ms
    public static final int STATISTIC_PERIOD = 30; // 30 seconds
    public static final String APPLICATION_STATISTIC = "statistic";
    public static final String REQUEST_REMOTE_ADDR = "requestRemoteAddress";
    public static final String CONTENT_LENGTH = "Content-Length";

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
    public static final int NETTY_SHARE_CHANNEL_MAX_WORKER = 800;
    // share channel min worker thread
    public static final int NETTY_SHARE_CHANNEL_MIN_WORKER = 40;
    // don't share channel max worker thread
    public static final int NETTY_NOT_SHARE_CHANNEL_MAX_WORKER = 200;
    // don't share channel min worker thread
    public static final int NETTY_NOT_SHARE_CHANNEL_MIN_WORKER = 20;
    public static final int NETTY_TIMEOUT_TIMER_PERIOD = 100;
    public static final byte NETTY_REQUEST_TYPE = 1;
    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;
    /**
     * heartbeat constants start
     */
    public static final int HEARTBEAT_PERIOD = 500;
    public static final String HEARTBEAT_INTERFACE_NAME = "com.lls.comics.rpc.heartbeat";
    public static final String HEARTBEAT_METHOD_NAME = "heartbeat";
    public static final String HEARTBEAT_PARAM = ReflectUtils.EMPTY_PARAM;
    /**
     * heartbeat constants end
     */

    public static final String ZOOKEEPER_REGISTRY_NAMESPACE = "/comics";
    public static final String ZOOKEEPER_REGISTRY_COMMAND = "/command";

    public static final String REGISTRY_HEARTBEAT_SWITCHER = "feature.configserver.heartbeat";
    public static final String COMICS_TRACE_INFO_SWITCHER = "feature.comics.trace.info";

    /**
     * 默认的consistent的hash的数量
     */
    public static final int DEFAULT_CONSISTENT_HASH_BASE_LOOP = 1000;

}
