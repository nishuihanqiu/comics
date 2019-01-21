package com.lls.comics.channel;

/************************************
 * ChannelState
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public enum ChannelState {

  /** 未初始化状态 **/
  UN_INIT(0),
  /** 初始化完成 **/
  INIT(1),
  /** 存活可用状态 **/
  CONNECTED(2),
  /** 不存活可用状态 **/
  UN_CONNECTED(3),
  /** 关闭状态 **/
  CLOSE(4);

  public final int value;

  ChannelState(int value) {
    this.value = value;
  }

  public boolean isConnectedState() {
    return this == CONNECTED;
  }

  public boolean isUnConnectedState() {
    return this == UN_CONNECTED;
  }

  public boolean isCloseState() {
    return this == CLOSE;
  }

  public boolean isInitState() {
    return this == INIT;
  }

  public boolean isUnInitState() {
    return this == UN_INIT;
  }


}
