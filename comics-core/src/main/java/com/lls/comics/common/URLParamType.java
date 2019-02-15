package com.lls.comics.common;

/************************************
 * URLParamType
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public enum URLParamType {

    VERSION("version", ComicsConstants.DEFAULT_VERSION),
    REQUEST_ID_FROM_CLIENT("requestIdFromClient", 0), //request id from http interface
    /** connect timeout **/
    CONNECT_TIMEOUT("connectTimeout", 1000),
    /** service min worker threads **/
    MIN_WORKER_THREAD("minWorkerThread", 20),
    /** service max worker threads **/
    MAX_WORKER_THREAD("maxWorkerThread", 200),
    GROUP("group", "default_rpc"),
    APPLICATION("application", ComicsConstants.FRAMEWORK_NAME),
    MODULE("module", ComicsConstants.FRAMEWORK_NAME),
    /** pool min conn number **/
    MIN_CLIENT_CONNECTION("minClientConnection", 2),
    /** pool max conn number **/
    MAX_CLIENT_CONNECTION("maxClientConnection", 10),
    /** pool max conn number **/
    MAX_CONTENT_LENGTH("maxContentLength", 10 * 1024 * 1024),
    /** max server conn (all clients conn) **/
    MAX_SERVER_CONNECTION("maxServerConnection", 100000),
    /** pool conn manger stragy **/
    POOL_LIFO("poolLifo", true),

    LAZY_INIT("lazyInit", false),
    /** multi referer share the same channel **/
    SHARE_CHANNEL("shareChannel", false),
    ASYNC_INIT_CONNECTION("asyncInitConnection", false),

    /* ************************* SPI start ***************************** */
    /** serializer **/
    SERIALIZER("serializer", "hessian2"),

    /** codec **/
    CODEC("codec", "comics"),

    WORKER_QUEUE_SIZE("workerQueueSize", 0),

    ;


    private String name;
    private String value;
    private long longValue;
    private int intValue;
    private boolean boolValue;

    URLParamType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    URLParamType(String name, long longValue) {
        this.name = name;
        this.longValue = longValue;
    }

    URLParamType(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    URLParamType(String name, boolean boolValue) {
        this.name = name;
        this.boolValue = boolValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public long getLongValue() {
        return longValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public boolean getBooleanValue() {
        return boolValue;
    }
}
