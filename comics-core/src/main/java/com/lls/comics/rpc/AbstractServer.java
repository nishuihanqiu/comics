package com.lls.comics.rpc;

import com.lls.comics.channel.ChannelState;
import com.lls.comics.codec.Codec;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.extension.ExtensionLoader;

import java.net.InetSocketAddress;

/************************************
 * AbstractServer
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
public abstract class AbstractServer implements Server {

    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;
    protected URL url;
    protected Codec codec;
    protected volatile ChannelState state = ChannelState.UN_INIT;

    public AbstractServer() {
    }

    public AbstractServer(URL url) {
        this.url = url;
        this.codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(
            url.getArgument(URLParamType.CODEC.getName(), URLParamType.CODEC.getValue())
        );
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public URL getURL() {
        return url;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    public Codec getCodec() {
        return codec;
    }
}
