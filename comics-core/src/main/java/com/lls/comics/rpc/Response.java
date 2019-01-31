package com.lls.comics.rpc;

import java.util.Map;

/************************************
 * Response
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public interface Response {

    Object getValue();

    Exception getException();

    long getRequestId();

    long getCreatedTimeMills();

    void setCreatedTimeMills(long createdTimeMills);

    int getTimeout();

    Map<String, String> getAttachments();

    void setAttachment(String key, String value);

}
