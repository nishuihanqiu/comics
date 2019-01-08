package com.lls.comics.channel;

import java.net.InetSocketAddress;

/************************************
 * Channel
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public interface Channel {

    boolean connect();

    boolean isConnected();

    boolean close();

    boolean isClosed();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();



}
