package com.lls.comics.channel;

import com.lls.comics.exception.ComicsNetworkException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.common.URL;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.ReentrantLock;

/************************************
 * DefaultNettyChannel
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class DefaultNettyChannel implements NettyChannel {

    private static final Logger logger = LoggerFactory.getLogging(NettyChannel.class);

    private ReentrantLock connectLock = new ReentrantLock();
    private int timeout;
    private volatile Channel channel;
    private URL URL;

    public DefaultNettyChannel(ChannelHandlerContext context) {
        this.channel = context.channel();
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public ChannelHandler writeAndFlush(Object message) throws ComicsNetworkException {
        return null;
    }

    @Override
    public void write(Object message) throws ComicsNetworkException {
        writeAndFlush(message);
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }
}
