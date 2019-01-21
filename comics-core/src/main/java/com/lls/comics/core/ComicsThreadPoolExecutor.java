package com.lls.comics.core;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/************************************
 * ComicsThreadPoolExecutor
 * @author liliangshan
 * @date 2019/1/21
 ************************************/
public class ComicsThreadPoolExecutor extends ThreadPoolExecutor {

  public static final int DEFAULT_MIN_THREADS = 20;
  public static final int DEFAULT_MAX_THREADS = 200;
  public static final int DEFAULT_MAX_IDLE_TIME = 60 * 1000;

  protected AtomicInteger submittedCount;
  private int maxSubmittedCount;

  public ComicsThreadPoolExecutor() {
    this(DEFAULT_MIN_THREADS, DEFAULT_MAX_THREADS);
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
    this(corePoolSize, maximumPoolSize, maximumPoolSize);
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize,  long keepAliveTime, TimeUnit unit) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, maximumPoolSize);
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity) {
    this(corePoolSize, maximumPoolSize, queueCapacity, Executors.defaultThreadFactory());
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity,  ThreadFactory threadFactory) {
    this(corePoolSize, maximumPoolSize, DEFAULT_MAX_IDLE_TIME, TimeUnit.MILLISECONDS, queueCapacity, threadFactory);
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  int queueCapacity) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, queueCapacity, Executors.defaultThreadFactory());
  }

  public ComicsThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  int queueCapacity,  ThreadFactory threadFactory) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, queueCapacity, threadFactory, new AbortPolicy());
  }

  public ComicsThreadPoolExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit,
                                int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    super(coreThreads, maxThreads, keepAliveTime, unit, new ComicsExecutorQueue(), threadFactory, handler);
    ((ComicsExecutorQueue) getQueue()).setComicsThreadPoolExecutor(this);

    submittedCount = new AtomicInteger(0);

    // 最大并发任务限制： 队列buffer数 + 最大线程数
    maxSubmittedCount = queueCapacity + maxThreads;
  }

  @Override
  public void execute(Runnable command) {
    int count = submittedCount.incrementAndGet();
    if (count > maxSubmittedCount) {
      submittedCount.decrementAndGet();
      getRejectedExecutionHandler().rejectedExecution(command, this);
    }

    try {
      super.execute(command);
    } catch (RejectedExecutionException e) {
      if (!(((ComicsExecutorQueue) getQueue()).force(command))) {
        submittedCount.decrementAndGet();
        getRejectedExecutionHandler().rejectedExecution(command, this);
      }
    }
  }

  public int getSubmittedCount() {
    return submittedCount.get();
  }

  public int getMaxSubmittedCount() {
    return maxSubmittedCount;
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    submittedCount.decrementAndGet();
  }
}
