package com.lls.comics.rpc;


import com.lls.comics.channel.SocketChannel;

/************************************
 * Client
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public interface Client extends SocketChannel {

    Response send(Request request);

    void heartbeat(Object message);

}
