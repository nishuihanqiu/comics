package com.lls.comics.netty;

import com.lls.comics.channel.SocketChannel;
import com.lls.comics.channel.ChannelHandler;
import com.lls.comics.exception.ComicsNetworkException;

/************************************
 * NettyChannel
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public interface NettyChannel extends SocketChannel {

    ChannelHandler writeAndFlush(Object message) throws ComicsNetworkException;



}
