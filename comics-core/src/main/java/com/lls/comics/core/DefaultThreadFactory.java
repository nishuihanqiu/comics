package com.lls.comics.core;

import com.lls.comics.common.ComicsConstants;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/************************************
 * DefaultThreadFactory
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public class DefaultThreadFactory implements ThreadFactory {

    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final AtomicInteger currentThreadNumber = new AtomicInteger(1);
    private final String prefix;
    private int priority;
    private boolean isDaemon;

    public DefaultThreadFactory() {
        this(ComicsConstants.FRAMEWORK_NAME);
    }

    public DefaultThreadFactory(String prefix) {
        this(prefix, false);
    }

    public DefaultThreadFactory(String prefix, boolean isDaemon) {
        this(prefix, isDaemon, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String prefix, boolean isDaemon, int priority) {
        SecurityManager manager = System.getSecurityManager();
        this.threadGroup = manager != null ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.prefix = prefix + "-" + poolNumber.getAndIncrement() + "-thread-";
        this.isDaemon = isDaemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r, prefix + currentThreadNumber.getAndIncrement(), 0);
        thread.setPriority(priority);
        thread.setDaemon(isDaemon);
        return thread;
    }


}
