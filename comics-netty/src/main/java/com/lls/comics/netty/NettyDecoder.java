package com.lls.comics.netty;

import com.lls.comics.codec.Codec;
import com.lls.comics.common.URL;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/************************************
 * NettyDecoder
 * @author liliangshan
 * @date 2019-02-14
 ************************************/
public class NettyDecoder extends ByteToMessageDecoder {

    private Codec codec;
    private URL url;
    private int maxContentLength = 0;

    public NettyDecoder(Codec codec, URL url, int maxContentLength) {
        this.codec = codec;
        this.url = url;
        this.maxContentLength = maxContentLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }

}
