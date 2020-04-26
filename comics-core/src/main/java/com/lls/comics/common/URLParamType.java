package com.lls.comics.common;

/************************************
 * URLParamType
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public enum URLParamType {

    VERSION("version", ComicsConstants.DEFAULT_VERSION),
    REQUEST_TIMEOUT("requestTimeout", 200),
    REQUEST_ID_FROM_CLIENT("requestIdFromClient", 0), //request id from http interface
    /** connect timeout **/
    CONNECT_TIMEOUT("connectTimeout", 1000),
    /** service min worker threads **/
    MIN_WORKER_THREAD("minWorkerThread", 20),
    /** service max worker threads **/
    MAX_WORKER_THREAD("maxWorkerThread", 200),
    /** pool min conn number **/
    MIN_CLIENT_CONNECTION("minClientConnection", 2),
    /** pool max conn number **/
    MAX_CLIENT_CONNECTION("maxClientConnection", 10),
    /** pool max conn number **/
    MAX_CONTENT_LENGTH("maxContentLength", 10 * 1024 * 1024),
    /** max server conn (all clients conn) **/
    MAX_SERVER_CONNECTION("maxServerConnection", 100000),
    /** pool conn manger streagy **/
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
    CLIENT_FACTORY("clientFactory", "comics"),
    SERVER_FACTORY("serverFactory", "comics"),
    /************************** SPI end ******************************/

    GROUP("group", "default_rpc"),
    CLIENT_GROUP("clientGroup", "default_rpc"),
    ACCESS_LOG("accessLog", false),

    ACTIVES("actives", 0),
    REFRESH_TIMESTAMP("refreshTimestamp", 0),
    NODE_TYPE("nodeType", ComicsConstants.NODE_TYPE_SERVICE),

    EXPORT("export", ""),
    EMBED("embed", ""),

    REGISTRY_RETRY_PERIOD("registryRetryPeriod", 30 * ComicsConstants.SECOND_MILLS),
    /* 注册中心不可用节点剔除方式 */
//    EXCISE("excise", Excise.excise_dynamic.getName()),
    CLUSTER("cluster", ComicsConstants.DEFAULT_VALUE),
    LOAD_BALANCE("loadbalance", "activeWeight"),
    HA_STRATEGY("haStrategy", "failover"),
    PROTOCOL("protocol", ComicsConstants.PROTOCOL_COMICS),
    PATH("path", ""),
    HOST("host", ""),
    PORT("port", 0),
    IO_THREADS("ioThreads", Runtime.getRuntime().availableProcessors() + 1),
    WORKER_QUEUE_SIZE("workerQueueSize", 0),
    ACCEPT_CONNECTIONS("acceptConnections", 0),
    PROXY("proxy", ComicsConstants.PROXY_JDK),
    FILTER("filter", ""),

    USE_GZ("useGz", false), // 是否开启gzip压缩
    MIN_GZ_SIZE("minGzSize", 1000), // 进行gz压缩的最小数据大小。超过此阈值才进行gz压缩

    APPLICATION("application", ComicsConstants.FRAMEWORK_NAME),
    MODULE("module", ComicsConstants.FRAMEWORK_NAME),

    RETRIES("retries", 0),
    ASYNC("async", false),
    MOCK("mock", "false"),
    MEAN("mean", "2"),
    P90("p90", "4"),
    P99("p99", "10"),
    P999("p999", "70"),
    ERROR_RATE("errorRate", "0.01"),
    CHECK("check", "true"),
    DIRECT_URL("directUrl", ""),
    REGISTRY_SESSION_TIMEOUT("registrySessionTimeout", 1 * ComicsConstants.MINUTE_MILLS),
    SLOW_THRESHOLD("slowThreshold", 200),

    REGISTER("register", true),
    SUBSCRIBE("subscribe", true),
    THROW_EXCEPTION("throwException", "true"),
    TRANS_EXCEPTION_STACK("transExceptionStack", true),

    LOCAL_SERVICE_ADDRESS("localServiceAddress", ""),
    // backupRequest
    BACKUP_REQUEST_DELAY_TIME("backupRequestDelayTime", 0),
    BACKUP_REQUEST_DELAY_RATIO("backupRequestDelayRatio", "0.4"),
    BACKUP_REQUEST_SWITCHER_NAME("backupRequestSwitcherName", ""),
    BACKUP_REQUEST_MAX_RETRY_RATIO("backupRequestMaxRetryRatio", "0.15"),

    // 切换group时，各个group的权重比。默认无权重
    WEIGHTS("weights", ""),





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
