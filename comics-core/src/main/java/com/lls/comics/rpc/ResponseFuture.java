package com.lls.comics.rpc;

/************************************
 * ResponseFuture
 * @author liliangshan
 * @date 2019/1/22
 ************************************/
public interface ResponseFuture extends Future, Response {

  void onSuccess(Response response);

  void onFailure(Response response);

  long getCreatedTimeMills();

  void setReturnType(Class<?> clazz);

}
