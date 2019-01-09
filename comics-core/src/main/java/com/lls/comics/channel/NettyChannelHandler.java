package com.lls.comics.channel;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;


/************************************
 * NettyChannelHandler
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public class NettyChannelHandler implements ChannelHandler {

  private ChannelFuture channelFuture;

  public NettyChannelHandler(ChannelFuture channelFuture) {
    this.channelFuture = channelFuture;
  }

  @Override
  public ChannelHandler addListener(ChannelHandlerListener listener) {

    channelFuture.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        listener.onCompleted(new ChannelHandlerFuture() {
          @Override
          public boolean isSuccess() {
            return future.isSuccess();
          }

          @Override
          public Throwable cause() {
            return future.cause();
          }
        });
      }
    });

    return this;
  }


}
