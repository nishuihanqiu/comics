package com.lls.comics.channel;

import com.lls.comics.exception.ComicsNetworkException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.common.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.ReentrantLock;

/************************************
 * DefaultNettyChannel
 * @author liliangshan
 * @date 2019/1/8
 ************************************/
public class DefaultNettyChannel implements NettyChannel {

    private static final Logger logger = LoggerFactory.getLogging(NettyChannel.class);

    private volatile ChannelState state = ChannelState.UN_INIT;
    private ReentrantLock connectLock = new ReentrantLock();
    private int timeout;
    private volatile Channel channel;
    private URL url;
    private Bootstrap bootstrap;
    private InetSocketAddress localAddress;
    private InetSocketAddress remoteAddress;

    public DefaultNettyChannel(URL url, Bootstrap bootstrap, int timeout) {
        this.url = url;
        this.bootstrap = bootstrap;
        this.remoteAddress = new InetSocketAddress(url.getHost(), url.getPort());
        this.timeout = timeout;
    }

    @Override
    public boolean connect() {
        connectLock.lock();
        ChannelFuture future = null;
        try {
            if (isConnected()) {
                logger.info("[channel] is connected to remote " + remoteAddress + ".");
                return false;
            }

            future = bootstrap.connect(remoteAddress);
            boolean result = future.awaitUninterruptibly(timeout);
            boolean success = future.isSuccess();
            if (result && success) {
                channel = future.channel();
                if (channel.localAddress() != null && channel.localAddress() instanceof InetSocketAddress) {
                    localAddress = (InetSocketAddress) channel.localAddress();
                }
                state = ChannelState.CONNECTED;
                return true;
            }

            if (future.cause() != null) {
                future.cancel(true);
                throw new ComicsNetworkException("NettyChannel failed to connect to server");
            } else {
                future.cancel(true);
                throw new ComicsNetworkException("NettyChannel connect to server timeout");
            }
        } catch (ComicsNetworkException e) {
            throw e;
        } catch (Exception e) {
            if (future != null) {
                future.channel().close();
            }
            throw new ComicsNetworkException("NettyChannel failed to connect to server");
        } finally {
            if (!isConnected() && future != null) {
                future.cancel(true);
                state = ChannelState.UN_CONNECTED;
            }
            connectLock.unlock();
        }
    }

    @Override
    public boolean isConnected() {
        return state.isConnectedState() && channel != null && channel.isActive();
    }

    @Override
    public boolean isOpen() {
        return state.isConnectedState() && channel != null && channel.isOpen();
    }

    @Override
    public ChannelHandler writeAndFlush(Object message) throws ComicsNetworkException {
        if (!isConnected()) {
            throw new ComicsNetworkException("netty channel is disconnected.");
        }
        return new NettyChannelHandler(channel.writeAndFlush(message));
    }

    @Override
    public void write(Object message) throws ComicsNetworkException {
        writeAndFlush(message);
    }

    @Override
    public boolean close() {
        connectLock.lock();
        try {
            state = ChannelState.CLOSE;
            if (channel != null) {
                channel.close();
            }
        } catch (Exception e) {
            logger.error("netty channel close error:" + e.getMessage(), e);
        } finally {
            connectLock.unlock();
        }
        return false;
    }

    @Override
    public boolean isClosed() {
        return state.isCloseState();
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
