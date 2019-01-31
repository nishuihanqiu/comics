package com.lls.comics.rpc;

/************************************
 * Future
 * @author liliangshan
 * @date 2019/1/22
 ************************************/
public interface Future {

  boolean cancel();

  boolean isCanceled();

  boolean isDone();

  boolean isSuccessful();

  Object getValue();

  Exception getException();

  void addListener(FutureListener listener);

}
