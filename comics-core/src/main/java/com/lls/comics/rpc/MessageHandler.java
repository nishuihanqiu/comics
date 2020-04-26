package com.lls.comics.rpc;

import com.lls.comics.channel.SocketChannel;

/************************************
 * MessageHandler
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
public interface MessageHandler {

    Object handle(SocketChannel channel, Object message);

}
