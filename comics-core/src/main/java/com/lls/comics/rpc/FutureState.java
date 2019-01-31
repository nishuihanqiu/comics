package com.lls.comics.rpc;

/************************************
 * FutureState
 * @author liliangshan
 * @date 2019/1/22
 ************************************/
public enum FutureState {

  DOING(0),
  DONE(1),
  CANCELED(2);

  public final int value;

  FutureState(int value) {
    this.value = value;
  }

  public boolean isCanceledState() {
    return this == CANCELED;
  }

  public boolean isDoneState() {
    return this == DONE;
  }

  public boolean isDoingState() {
    return this == DOING;
  }
}
