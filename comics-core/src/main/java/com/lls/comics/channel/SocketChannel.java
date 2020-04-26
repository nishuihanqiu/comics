package com.lls.comics.channel;

import com.lls.comics.common.URL;
import com.lls.comics.exception.ComicsNetworkException;

import java.net.InetSocketAddress;

/************************************
 * SocketChannel
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public interface SocketChannel {

    boolean connect();

    boolean isConnected();

    boolean open();

    void write(Object message) throws ComicsNetworkException;

    boolean close();

    boolean isClosed();

    boolean isAvailable();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    URL getURL();


}
