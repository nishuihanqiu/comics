package com.lls.comics.rpc;

import com.lls.comics.common.URL;
import com.lls.comics.exception.ComicsResponseException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/************************************
 * DefaultResponseFuture
 * @author liliangshan
 * @date 2019/1/22
 ************************************/
public class DefaultResponseFuture implements ResponseFuture {

    private static final Logger logger = LoggerFactory.getLogging(ResponseFuture.class);

    protected volatile FutureState state = FutureState.DOING;
    protected final Object lock = new Object();
    protected Object result = null;
    protected Exception exception = null;

    protected long createdTimeMills = System.currentTimeMillis();
    protected long processTime = 0;
    protected int timeout = 0;
    protected Request request;
    protected List<FutureListener> listeners;
    protected URL serverURL;
    protected Class returnType;

    public DefaultResponseFuture(Request request, int timeout, URL serverURL) {
        this.timeout = timeout;
        this.request = request;
        this.serverURL = serverURL;
    }

    @Override
    public void onSuccess(Response response) {
        this.result = response.getValue();
        this.createdTimeMills = response.getCreatedTimeMills();
        done();
    }

    @Override
    public void onFailure(Response response) {
        this.exception = response.getException();
        this.createdTimeMills = response.getCreatedTimeMills();

        done();
    }

    protected boolean done() {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            state = FutureState.DONE;
            lock.notifyAll();
        }

        notifyListeners();
        return true;
    }


    private boolean isDoing() {
        return state.isDoingState();
    }

    @Override
    public long getCreatedTimeMills() {
        return createdTimeMills;
    }

    @Override
    public void setCreatedTimeMills(long createdTimeMills) {
        this.createdTimeMills = createdTimeMills;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public Map<String, String> getAttachments() {
        return Collections.emptyMap();
    }

    @Override
    public void setAttachment(String key, String value) {

    }

    @Override
    public void setReturnType(Class<?> clazz) {
        this.returnType = clazz;
    }

    @Override
    public boolean cancel() {
        Exception e = new ComicsResponseException(this.getClass().getName() + "task cancel: serverPort=" +
            serverURL.getPort() + " " + request.toString() + " cost=" + (System.currentTimeMillis() - createdTimeMills));
        return cancel(e);
    }

    protected boolean cancel(Exception e) {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            state = FutureState.CANCELED;
            exception = e;
            lock.notifyAll();
        }

        notifyListeners();
        return true;
    }

    @Override
    public boolean isCanceled() {
        return state.isCanceledState();
    }

    @Override
    public boolean isDone() {
        return state.isDoneState();
    }

    @Override
    public boolean isSuccessful() {
        return isDone() && (exception == null);
    }

    @Override
    public Object getValue() {
        synchronized (lock) {
            if (!isDoing()) {
                return getValueOrThrowable();
            }

            if (timeout <= 0) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    cancel(new ComicsResponseException(this.getClass().getName() + " getValue interrupted exception: " +
                        request.toString() + " cost=" + (System.currentTimeMillis() - createdTimeMills), e));
                }

                return getValueOrThrowable();
            }

            long waitTime = timeout - (System.currentTimeMillis() - createdTimeMills);
            if (waitTime > 0) {
                while (true) {
                    try {
                        lock.wait(waitTime);
                    } catch (InterruptedException e) {
                        logger.error("getValue wait error:", e);
                    }
                    if (!isDoing()) {
                        break;
                    }
                    waitTime = timeout - (System.currentTimeMillis() - createdTimeMills);
                    if (waitTime <= 0) {
                        break;
                    }
                }
            }
            if (isDoing()) {
                timeoutCancel();
            }
            return getValueOrThrowable();
        }
    }

    private void timeoutCancel() {
        this.processTime = System.currentTimeMillis() - createdTimeMills;
        synchronized (lock) {
            if (!isDoing()) {
                return;
            }

            state = FutureState.CANCELED;
            exception = new ComicsResponseException(this.getClass().getName() + " request timeout: serverPort=" +
                serverURL.getPort() + " " + request.toString() + " cost=" + (System.currentTimeMillis() - createdTimeMills),
                "service request timeout");
            lock.notifyAll();
        }

        notifyListeners();
    }

    private Object getValueOrThrowable() {
        if (exception != null) {
            throw (exception instanceof RuntimeException) ? (RuntimeException) exception :
                new ComicsResponseException(exception.getMessage(), exception);
        }

        return result;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public long getRequestId() {
        return request.getRequestId();
    }

    @Override
    public void addListener(FutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("FutureListener is null.");
        }

        boolean notifyNow = false;
        synchronized (lock) {
            if (!isDoing()) {
                notifyNow = true;
            } else {
                if (listeners == null) {
                    listeners = new ArrayList<>(1);
                }

                listeners.add(listener);
            }
        }

        if (notifyNow) {
            notifyListener(listener);
        }
    }

    private void notifyListeners() {
        if (listeners != null) {
            for (FutureListener listener : listeners) {
                notifyListener(listener);
            }
        }
    }

    private void notifyListener(FutureListener listener) {
        try {
            listener.onCompleted(this);
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "notifyListener error:", e);
        }
    }

    public FutureState getState() {
        return state;
    }

    public Request getRequest() {
        return request;
    }


}
