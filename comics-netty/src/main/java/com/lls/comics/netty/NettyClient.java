package com.lls.comics.netty;

import com.lls.comics.channel.ChannelFactory;
import com.lls.comics.common.URL;
import com.lls.comics.rpc.AbstractSharedPoolClient;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import io.netty.bootstrap.Bootstrap;

/************************************
 * NettyClient
 * @author liliangshan
 * @date 2019-02-14
 ************************************/
public class NettyClient extends AbstractSharedPoolClient {

    private Bootstrap bootstrap;

    public NettyClient(URL url) {
        super(url);
    }

    @Override
    protected ChannelFactory createChannelFactory() {
        return null;
    }

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public Response send(Request request) {
        return null;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}