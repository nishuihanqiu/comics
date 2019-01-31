package com.lls.comics.rpc;

import com.lls.comics.channel.ChannelState;
import com.lls.comics.codec.Codec;
import com.lls.comics.common.URL;

import java.net.InetSocketAddress;

/************************************
 * AbstractClient
 * @author liliangshan
 * @date 2019/1/24
 ************************************/
public abstract class AbstractClient implements Client {

    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    protected volatile ChannelState state = ChannelState.UN_INIT;

    public AbstractClient(URL url) {
        this.url = url;

    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }
}
