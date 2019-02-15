package com.lls.comics.rpc;

import com.lls.comics.common.URL;

import java.net.InetSocketAddress;

/************************************
 * Client
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public interface Client {

    URL getURL();

    boolean open();

    void close();

    boolean isClosed();

    boolean isAvailable();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    Response send(Request request);

}
