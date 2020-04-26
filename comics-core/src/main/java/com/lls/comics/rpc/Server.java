package com.lls.comics.rpc;

import com.lls.comics.channel.SocketChannel;

import java.net.InetSocketAddress;
import java.util.Collection;

/************************************
 * Server
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
public interface Server extends SocketChannel {

    boolean isBound();

    Collection<SocketChannel> getChannels();

    SocketChannel getChannel(InetSocketAddress remoteAddress);

}
