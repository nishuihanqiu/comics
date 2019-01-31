package com.lls.comics.rpc;

/************************************
 * FutureListener
 * @author liliangshan
 * @date 2019/1/22
 ************************************/
public interface FutureListener {

  void onCompleted(Future future) throws Exception;

}
