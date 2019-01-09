package com.lls.comics.channel;

import com.lls.comics.exception.ComicsNetworkException;

import java.net.InetSocketAddress;

/************************************
 * Channel
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public interface Channel {

    boolean connect();

    boolean isConnected();

    boolean isOpen();

    void write(Object message) throws ComicsNetworkException;

    boolean close();

    boolean isClosed();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();



}
