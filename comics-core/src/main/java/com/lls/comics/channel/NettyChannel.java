package com.lls.comics.channel;

import com.lls.comics.exception.ComicsNetworkException;

/************************************
 * NettyChannel
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public interface NettyChannel extends Channel {

    ChannelHandler writeAndFlush(Object message) throws ComicsNetworkException;



}
