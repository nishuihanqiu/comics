package com.lls.comics.rpc;

import java.util.Map;

/************************************
 * Request
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public interface Request {

    String getInterfaceName();

    String getMethodName();

    String getArgumentDesc();

    Object[] getArguments();

    Map<String, String> getAttachments();

    void setAttachment(String name, String value);

    long getRequestId();

    int getRetries();

    void setRetries(int retries);

    byte getVersion();

    void setVersion(byte version);

    void setSerializeNumber(int number);

    int getSerializeNumber();

}
