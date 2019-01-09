package com.lls.comics.channel;

/************************************
 * ChannelHandlerFuture
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public interface ChannelHandlerFuture {

  boolean isSuccess();

  Throwable cause();

}
