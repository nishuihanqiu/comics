package com.lls.comics.channel;

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

    @Override
    public NettyChannel createChannel() {
        return null;
    }

    @Override
    public boolean rebuildChannel(NettyChannel channel) {
        return false;
    }


}
