package com.lls.comics.rpc;

import com.lls.comics.channel.SocketChannel;
import com.lls.comics.channel.ChannelFactory;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.ComicsThreadPoolExecutor;
import com.lls.comics.core.DefaultThreadFactory;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.util.MathUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/************************************
 * AbstractSharedPoolClient
 * @author liliangshan
 * @date 2019-02-13
 ************************************/
public abstract class AbstractSharedPoolClient extends AbstractClient {

    private static Logger logger = LoggerFactory.getLogging(AbstractSharedPoolClient.class);

    private static final ThreadPoolExecutor executor = new ComicsThreadPoolExecutor(1, 300,
            20000, new DefaultThreadFactory("AbstractPoolClient-initPool-", true));
    private final AtomicInteger idx = new AtomicInteger();
    protected ChannelFactory<SocketChannel> factory;
    protected ArrayList<SocketChannel> channels;
    protected int connections;

    public AbstractSharedPoolClient(URL url) {
        super(url);
        connections = url.getIntArgument(URLParamType.MIN_CLIENT_CONNECTION.getName(), URLParamType.MIN_CLIENT_CONNECTION.getIntValue());
        if (connections <= 0) {
            connections = URLParamType.MIN_CLIENT_CONNECTION.getIntValue();
        }
    }

    protected void initSharedPool() {
        factory = createChannelFactory();
        createChannels();
    }

    protected abstract ChannelFactory<SocketChannel> createChannelFactory();

    protected void initConnections(boolean async) {
        if (!async) {
            createConnections();
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                createConnections();
            }
        });

    }

    private void createChannels() {
        channels = new ArrayList<>(connections);
        for (int i = 0; i < connections; i++) {
            channels.add(factory.createChannel());
        }
        initConnections(url.getBooleanArgument(URLParamType.ASYNC_INIT_CONNECTION.getName(), URLParamType.ASYNC_INIT_CONNECTION.getBooleanValue()));
    }

    private void createConnections() {
        for (SocketChannel channel : channels) {
            try {
                channel.connect();
            } catch (Exception e) {
                logger.error(this.getClass().getName() + ": channel connect error: url:" + url.getUri(), e);
            }
        }
    }

    protected SocketChannel getChannel() throws ComicsException {
        int index = MathUtils.getNonNegative(idx.getAndIncrement());
        SocketChannel channel;

        for (int i = index; i < connections + index; i++) {
            channel = channels.get(i % connections);
            if (channel.isConnected()) {
                return channel;
            }
            factory.rebuildChannel(channel, i != connections + 1);
        }

        String message = this.getClass().getSimpleName() + "get channel error: url=" + url.getUri();
        logger.error(message);
        throw new ComicsException(message);
    }

    protected void closeChannels() {
        for (SocketChannel channel : channels) {
            if (!channel.isClosed() && channel.isConnected()) {
                channel.close();
            }
        }
    }

}
