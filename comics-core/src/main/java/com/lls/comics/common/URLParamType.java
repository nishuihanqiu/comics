package com.lls.comics.common;

/************************************
 * URLParamType
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public enum URLParamType {

    VERSION("version", ComicsConstants.DEFAULT_VERSION),
    REQUEST_ID_FROM_CLIENT("requestIdFromClient", 0), //request id from http interface
    GROUP("group", "default_rpc"),
    APPLICATION("application", ComicsConstants.FRAMEWORK_NAME),
    MODULE("module", ComicsConstants.FRAMEWORK_NAME),
    /** pool min conn number **/
    MIN_CLIENT_CONNECTION("minClientConnection", 2),
    /** pool max conn number **/
    MAX_CLIENT_CONNECTION("maxClientConnection", 10),
    ASYNC_INITCONNECTION("asyncInitConnection", false),

    /* ************************* SPI start ***************************** */
    /** serializer **/
    SERIALIZER("serializer", "hessian2"),
    /** codec **/
    CODEC("codec", "comics"),;


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
