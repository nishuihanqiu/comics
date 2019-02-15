package com.lls.comics.rpc;

import com.lls.comics.channel.ChannelState;
import com.lls.comics.codec.Codec;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.extension.ExtensionLoader;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

import java.net.InetSocketAddress;

/************************************
 * AbstractClient
 * @author liliangshan
 * @date 2019/1/24
 ************************************/
public abstract class AbstractClient implements Client {

    private static final Logger logger = LoggerFactory.getLogging(Client.class);

    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    protected volatile ChannelState state = ChannelState.UN_INIT;

    public AbstractClient(URL url) {
        this.url = url;
        this.codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(url.getArgument(URLParamType.CODEC.getName(),
            URLParamType.CODEC.getValue()));
        logger.info("client init. url:" + url.getHost() + "-" + url.getPath() + ", use codec." + codec.getClass().getSimpleName());
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}
