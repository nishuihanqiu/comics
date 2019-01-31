package com.lls.comics.rpc;

import com.lls.comics.exception.ComicsResponseException;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/************************************
 * DefaultResponse
 * @author liliangshan
 * @date 2019/1/21
 ************************************/
public class DefaultResponse implements Serializable, Response {

  private static final long serialVersionUID = -3339885938593888548L;

  private Object value;
  private Exception exception;
  private long requestId;
  private long createdTimeMills;
  private int timeout;

  private Map<String, String> attachments;

  public DefaultResponse() {
  }

  public DefaultResponse(long requestId) {
    this.requestId = requestId;
  }

  public DefaultResponse(Response response) {
    this.value = response.getValue();
    this.exception = response.getException();
    this.requestId = response.getRequestId();
    this.createdTimeMills = response.getCreatedTimeMills();
    this.timeout = response.getTimeout();
  }

  public DefaultResponse(Object value) {
    this.value = value;
  }

  @Override
  public Object getValue() {
    if (exception != null) {
      throw (exception instanceof RuntimeException) ? (RuntimeException) exception :
        new ComicsResponseException(exception.getMessage(), exception);
    }
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public Exception getException() {
    return null;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  @Override
  public long getRequestId() {
    return requestId;
  }

  public void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  @Override
  public long getCreatedTimeMills() {
    return createdTimeMills;
  }

  @Override
  public void setCreatedTimeMills(long createdTimeMills) {
    this.createdTimeMills = createdTimeMills;
  }

  @Override
  public int getTimeout() {
    return timeout;
  }

  @Override
  public Map<String, String> getAttachments() {
    return attachments != null ? attachments : Collections.emptyMap();
  }

  public void setAttachments(Map<String, String> attachments) {
    this.attachments = attachments;
  }

  @Override
  public void setAttachment(String key, String value) {
    if (attachments == null) {
      attachments = new HashMap<>();
    }
    attachments.put(key, value);
  }
}
