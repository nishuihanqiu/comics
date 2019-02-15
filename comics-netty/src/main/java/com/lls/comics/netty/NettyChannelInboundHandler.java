package com.lls.comics.netty;

import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/************************************
 * NettyChannelInboundHandler
 * @author liliangshan
 * @date 2019-02-15
 ************************************/
@ChannelHandler.Sharable
public class NettyChannelInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogging(NettyChannelInboundHandler.class);
    private ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<>();
    private int maxChannel;

    public NettyChannelInboundHandler(int maxChannel) {
        this.maxChannel = maxChannel;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelKey = this.getChannelKey((InetSocketAddress) channel.localAddress(),
            (InetSocketAddress) channel.remoteAddress());
        if (channels.size() >= maxChannel) {
            logger.warn("channel connected size:{} out of limit:{}", channels.size(), maxChannel);
            channel.close();
            return;
        }

        channels.put(channelKey, channel);
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelKey = this.getChannelKey((InetSocketAddress) channel.localAddress(),
            (InetSocketAddress) channel.remoteAddress());
        channels.remove(channelKey);
        ctx.fireChannelUnregistered();
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    private String getChannelKey(InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        String key = "";
        if (localAddress == null || localAddress.getAddress() == null) {
            key += "null-";
        } else {
            key += localAddress.getAddress().getHostAddress() + ":" + localAddress.getPort() + "-";
        }

        if (remoteAddress == null || remoteAddress.getAddress() == null) {
            key += "null-";
        } else {
            key += remoteAddress.getAddress().getHostAddress() + ":" + remoteAddress.getPort() + "-";
        }
        return key;
    }

    public void close() {
        for (Map.Entry<String, Channel> entry : channels.entrySet()) {
            try {
                Channel channel = entry.getValue();
                if (channel != null && channel.isOpen()) {
                    channel.close();
                }
            } catch (Exception e) {
                logger.error("netty channel inbound handler close channel failed. channelKey:"+ entry.getKey(), e);
            }
        }
    }
}
