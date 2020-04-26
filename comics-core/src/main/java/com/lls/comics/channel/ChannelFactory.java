package com.lls.comics.channel;

/************************************
 * ChannelFactory
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public interface ChannelFactory<T extends SocketChannel> {

  T createChannel();

  boolean rebuildChannel(T channel, boolean async);

}
