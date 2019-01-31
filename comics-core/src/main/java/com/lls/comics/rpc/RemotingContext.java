package com.lls.comics.rpc;

import com.lls.comics.common.URLParamType;

import java.util.HashMap;
import java.util.Map;

/************************************
 * RemotingContext
 * @author liliangshan
 * @date 2019/1/23
 ************************************/
public class RemotingContext {

  private Map<Object, Object> attributes = new HashMap<>();
  private Map<String, String> attachments = new HashMap<>();
  private Request request;
  private Response response;
  private String clientRequestId = null;

  private static final ThreadLocal<RemotingContext> localContext = new ThreadLocal<RemotingContext>() {
    @Override
    protected RemotingContext initialValue() {
      return new RemotingContext();
    }
  };

  public static RemotingContext getContext() {
    return localContext.get();
  }

  public static RemotingContext newInstance(Request request) {
    RemotingContext context = newInstance();
    if (request != null) {
      context.setRequest(request);
      context.setClientRequestId(request.getAttachments().get(URLParamType.REQUEST_ID_FROM_CLIENT.getName()));
    }
    return context;
  }

  public static RemotingContext newInstance() {
    RemotingContext context = new RemotingContext();
    localContext.set(context);
    return context;
  }

  public static void destory() {
    localContext.remove();
  }


  public void setRequest(Request request) {
    this.request = request;
  }

  public Request getRequest() {
    return request;
  }

  public String getRequestId() {
    if (clientRequestId != null) {
      return clientRequestId;
    }
    return request == null ? null : String.valueOf(request.getRequestId());
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public Response getResponse() {
    return response;
  }

  public void setClientRequestId(String clientRequestId) {
    this.clientRequestId = clientRequestId;
  }

  public String getClientRequestId() {
    return clientRequestId;
  }

  public Map<String, String> getAttachments() {
    return attachments;
  }

  public void putAttachment(String key, String value) {
    attachments.put(key, value);
  }

  public String getAttachment(String key) {
    return attachments.get(key);
  }

  public void removeAttachment(String key) {
    attachments.remove(key);
  }

  public Map<Object, Object> getAttributes() {
    return attributes;
  }

  public void putAttribute(Object key, Object value) {
    attributes.put(key, value);
  }

  public Object getAttribute(Object key) {
    return attributes.get(key);
  }

  public void removeAttribute(Object key) {
    attributes.remove(key);
  }

}
