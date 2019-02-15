package com.lls.comics.rpc;

import com.lls.comics.common.URL;

import java.net.InetSocketAddress;

/************************************
 * Server
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
public interface Server {

    URL getURL();

    boolean connect();

    void close();

    boolean isClosed();

    boolean isConnected();

    boolean isActive();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

}
