package com.lls.comics.channel;

import java.util.EventListener;

/************************************
 * ChannelHandlerListener
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public interface ChannelHandlerListener extends EventListener {

    void onCompleted(ChannelHandlerFuture future) throws Exception;

}
