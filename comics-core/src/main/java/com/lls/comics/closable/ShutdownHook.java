package com.lls.comics.closable;

import com.lls.comics.core.DefaultThreadFactory;
import com.lls.comics.exception.ComicsIllegalStateException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * ShutdownHook
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public class ShutdownHook implements Hook {

    private Logger logger = LoggerFactory.getLogging(ShutdownHook.class);

    private static final int defaultPriority = 20;
    private Thread shutdownThread;
    private EventCenter eventCenter;
    private AtomicBoolean executed = new AtomicBoolean(false);
    private ArrayList<ClosableObject> resources = new ArrayList<>();
    private ArrayList<ClosableObject> failedResources = new ArrayList<>();
    private static final Class lock = ShutdownHook.class;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ShutdownHook.this.closeAll();
        }
    };

    private ShutdownHook() {
        shutdownThread = new DefaultThreadFactory("ShutdownHook").newThread(runnable);
        logger.info("ShutdownHook created.");
    }

    public static ShutdownHook getInstance() {
        return Holder.INSTANCE;
    }

    private void closeAll() {
        synchronized (lock) {
            Collections.sort(resources);
            logger.info("ShutdownHook start to close all resources by priority");
            for (ClosableObject resource : resources) {
               try {
                   resource.closable.close();
                   logger.info("ShutdownHook close {} success.", resource.closable.getClass());
               } catch (Exception e) {
                   failedResources.add(resource);
                   logger.error("ShutdownHook close"+ resource.closable.getClass() +"failed", e);
               }
            }
            if (failedResources.isEmpty()) {
                logger.info("ShutdownHook succeed to close all resources.");
            }
            failedResources.clear(); // 可以提供重试机制
            resources.clear();
        }
    }

    @Override
    public boolean isExecuted() {
        return executed.get();
    }

    public void setEventCenter(EventCenter eventCenter) {
        this.eventCenter = eventCenter;
    }

    public EventCenter getEventCenter() {
        return eventCenter;
    }

    @Override
    public void execute(boolean sync) {
        if (executed.compareAndSet(false, true)) {
            if (sync) {
                runnable.run();
                return;
            }
            shutdownThread.start();
        }
    }

    @Override
    public void register(Closable closable) {
        register(closable, defaultPriority);
    }

    @Override
    public void register(Closable closable, int priority) {
        if (isExecuted()) {
            throw new ComicsIllegalStateException("ShutdownHook has executed, can not call register action.");
        }
        resources.add(new ClosableObject(closable, priority));
        logger.info("ShutdownHook register Closeable:[{}] success", closable.getClass());
    }

    private static class Holder {
        private static final ShutdownHook INSTANCE = new ShutdownHook();
    }

    private static class ClosableObject implements Comparable<ClosableObject> {
        private Closable closable;
        private int priority;

        ClosableObject(Closable closable, int priority) {
            this.closable = closable;
            this.priority = priority;
        }

        @Override
        public int compareTo(ClosableObject o) {
            return Integer.compare(o.priority, this.priority);
        }
    }

}
