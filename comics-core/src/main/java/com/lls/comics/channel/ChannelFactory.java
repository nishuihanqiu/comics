package com.lls.comics.channel;

/************************************
 * ChannelFactory
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public interface ChannelFactory<T extends Channel> {

  T createChannel();

  boolean rebuildChannel(T channel);

}
