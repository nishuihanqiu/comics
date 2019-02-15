package com.lls.comics.netty;

import com.lls.comics.channel.ChannelFactory;
import com.lls.comics.core.ComicsThreadPoolExecutor;
import com.lls.comics.core.DefaultThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/************************************
 * NettyChannelFactory
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public class NettyChannelFactory implements ChannelFactory<NettyChannel> {

    private static final ExecutorService rebuildExecutorService = new ComicsThreadPoolExecutor(5, 30, 10L, TimeUnit.SECONDS, 100,
        new DefaultThreadFactory("netty-rebuild-executor-service", true),
        new ThreadPoolExecutor.CallerRunsPolicy());

    private NettyClient nettyClient;

    public NettyChannelFactory(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public NettyChannel createChannel() {
        return new DefaultNettyChannel(nettyClient.getURL(), nettyClient.getBootstrap());
    }

    @Override
    public boolean rebuildChannel(NettyChannel channel) {
        return false;
    }


}
