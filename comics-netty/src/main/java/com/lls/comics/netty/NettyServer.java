package com.lls.comics.netty;

import com.lls.comics.channel.ChannelState;
import com.lls.comics.common.ComicsConstants;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.ComicsThreadPoolExecutor;
import com.lls.comics.core.DefaultThreadFactory;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.rpc.AbstractServer;
import com.lls.comics.rpc.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * NettyServer
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
public class NettyServer extends AbstractServer {
    private static Logger logger = LoggerFactory.getLogging(NettyServer.class);
    private NettyChannelInboundHandler inboundHandler;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;
    private MessageHandler messageHandler;
    private ComicsThreadPoolExecutor comicsThreadPoolExecutor;
    protected AtomicBoolean connected = new AtomicBoolean(false);


    public NettyServer(URL url, MessageHandler messageHandler) {
        super(url);
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean connect() {
        if (this.isConnected()) {
            logger.warn("netty server have connected. url:{}", url);
            return this.isConnected();
        }
        if (connected.compareAndSet(false, true)) {
            try {

                if (bossGroup == null) {
                    bossGroup = new NioEventLoopGroup(1);
                    workerGroup = new NioEventLoopGroup();
                }
                logger.info("netty server is connecting. url:{}", url);
                this.beforeConnect();
                this.connecting();
                state = ChannelState.CONNECTED;
                logger.info("netty server connect successfully. url:{}", url);
            } catch (Exception e) {
                logger.error("netty server connect error. url:" + url, e);
            }
        }
        return state.isConnectedState();
    }

    private void beforeConnect() {
        boolean shareChannel = url.getBooleanArgument(URLParamType.SHARE_CHANNEL.getName(),
            URLParamType.SHARE_CHANNEL.getBooleanValue());
        int maxServerConnection = url.getIntArgument(URLParamType.MAX_SERVER_CONNECTION.getName(),
            URLParamType.MAX_SERVER_CONNECTION.getIntValue());
        int workQueueSize = url.getIntArgument(URLParamType.WORKER_QUEUE_SIZE.getName(),
            URLParamType.WORKER_QUEUE_SIZE.getIntValue());

        int minWorkerThread, maxWorkerThread;
        if (shareChannel) {
            minWorkerThread = url.getIntArgument(URLParamType.MIN_WORKER_THREAD.getName(),
                ComicsConstants.NETTY_SHARE_CHANNEL_MIN_WORKDER);
            maxWorkerThread = url.getIntArgument(URLParamType.MAX_WORKER_THREAD.getName(),
                ComicsConstants.NETTY_SHARE_CHANNEL_MAX_WORKDER);
        } else {
            minWorkerThread = url.getIntArgument(URLParamType.MIN_WORKER_THREAD.getName(),
                ComicsConstants.NETTY_NOT_SHARE_CHANNEL_MIN_WORKDER);
            maxWorkerThread = url.getIntArgument(URLParamType.MAX_WORKER_THREAD.getName(),
                ComicsConstants.NETTY_NOT_SHARE_CHANNEL_MAX_WORKDER);
        }

        if (comicsThreadPoolExecutor == null || comicsThreadPoolExecutor.isShutdown()) {
            comicsThreadPoolExecutor = new ComicsThreadPoolExecutor(minWorkerThread, maxWorkerThread, workQueueSize,
                new DefaultThreadFactory("NettyServer-" + url.getServerPort(), true));
        }
        comicsThreadPoolExecutor.prestartAllCoreThreads();
        inboundHandler = new NettyChannelInboundHandler(maxServerConnection);
    }

    private void connecting() {
        int maxContentLength = url.getIntArgument(URLParamType.MAX_CONTENT_LENGTH.getName(),
            URLParamType.MAX_CONTENT_LENGTH.getIntValue());

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("channel_inbound_handler", inboundHandler);
                    pipeline.addLast("decoder", new NettyDecoder(codec, url, maxContentLength));
                    pipeline.addLast("encoder", new NettyEncoder());
                    pipeline.addLast("channel_handler", null);
                }
            });
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(url.getPort()));
        channelFuture.syncUninterruptibly();

        serverChannel = channelFuture.channel();
    }

    @Override
    public void close() {
        if (isClosed()) {
            return;
        }
        if (state.isUnInitState()) {
            return;
        }

        if (!connected.compareAndSet(true, false)) {
            return;
        }

        try {
            if (serverChannel != null) {
                serverChannel.close();
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
                // set null for gc
                bossGroup = null;
                workerGroup = null;
            }

            if (inboundHandler != null) {
                inboundHandler.close();
            }
            if (comicsThreadPoolExecutor != null) {
                comicsThreadPoolExecutor.shutdownNow();
            }
            // 设置close状态
            state = ChannelState.CLOSE;
            logger.info("netty server close successfully. url:{}", url);
        } catch (Exception e) {
            logger.error("netty server close failed. url:" + url, e);
        }
    }

    @Override
    public boolean isClosed() {
        return state.isCloseState();
    }

    @Override
    public boolean isConnected() {
        return state.isConnectedState();
    }

    @Override
    public boolean isActive() {
        return serverChannel != null && serverChannel.isActive();
    }

}
