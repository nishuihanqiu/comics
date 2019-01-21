package com.lls.comics.core;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionException;

/************************************
 * ComicsExecutorQueue
 * @author liliangshan
 * @date 2019/1/21
 ************************************/
class ComicsExecutorQueue extends LinkedTransferQueue<Runnable> {

  private static final long serialVersionUID = -365236489761004839L;

  ComicsThreadPoolExecutor executor;

  ComicsExecutorQueue() {
    super();
  }

  void setComicsThreadPoolExecutor(ComicsThreadPoolExecutor executor) {
    this.executor = executor;
  }

  boolean force(Runnable o) {
    if (executor.isShutdown()) {
      throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
    }

    return super.offer(o);
  }

  @Override
  public boolean offer(Runnable o) {
      int poolSize = executor.getPoolSize();
      if (poolSize == executor.getMaximumPoolSize()) {
        return super.offer(o);
      }

      if (executor.getMaxSubmittedCount() <= poolSize) {
        return super.offer(o);
      }

      if (poolSize < executor.getMaxSubmittedCount()) {
        return false;
      }

      return super.offer(o);
  }

}
