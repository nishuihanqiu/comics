package com.lls.comics.rpc;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/************************************
 * DefaultRequest
 * @author liliangshan
 * @date 2019/1/21
 ************************************/
public class DefaultRequest implements Serializable, Request {

    private static final long serialVersionUID = -438399538895938389L;

    private String interfaceName;
    private String methodName;
    private String argumentDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;
    private long requestId;
    private byte version = VersionEnum.VERSION_1.getVersion();
    private int serializeNumber = 0;// default serialization is hession2

    public DefaultRequest() {
    }

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getArgumentDesc() {
        return argumentDesc;
    }

    public void setArgumentDesc(String argumentDesc) {
        this.argumentDesc = argumentDesc;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.EMPTY_MAP;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public void setAttachment(String name, String value) {
        if (attachments == null) {
            attachments = new HashMap<>();
        }
        attachments.put(name, value);
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public int getRetries() {
        return retries;
    }

    @Override
    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public byte getVersion() {
        return version;
    }

    @Override
    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    public int getSerializeNumber() {
        return serializeNumber;
    }

    @Override
    public void setSerializeNumber(int serializeNumber) {
        this.serializeNumber = serializeNumber;
    }

    @Override
    public String toString() {
        return interfaceName + "." + methodName + "(" + argumentDesc + ")" + "requestId:" + requestId;
    }
}
