package com.lls.comics.node;

import com.lls.comics.common.URL;
import com.lls.comics.exception.ComicsNetworkException;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import com.lls.comics.util.ComicsUtils;

import java.util.concurrent.atomic.AtomicInteger;

/************************************
 * AbstractReferer
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public abstract class AbstractReferer<T> extends AbstractNode implements Referer<T> {

    protected Class<T> clz;
    protected AtomicInteger activeRefererCount = new AtomicInteger(0);
    protected URL serviceUrl;

    public AbstractReferer(Class<T> clz, URL url) {
        super(url);
        this.clz = clz;
        this.serviceUrl = url;
    }

    public AbstractReferer(URL url, Class<T> clz, URL serviceUrl) {
        super(url);
        this.clz = clz;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public Class<T> getInterface() {
        return clz;
    }

    @Override
    public Response call(Request request) {
        if (!isAvailable()) {
            throw new ComicsNetworkException(this.getClass().getSimpleName() + " call error: node is available, url="
                    + url.getUri() + " " + ComicsUtils.toString(request));
        }
        incrActiveCount(request);
        Response response = null;
        try {
            response = doCall(request);
            return response;
        } finally {
            decrActiveCount(request, response);
        }
    }

    @Override
    public int activeRefererCount() {
        return activeRefererCount.get();
    }

    protected void incrActiveCount(Request request) {
        activeRefererCount.incrementAndGet();
    }

    protected void decrActiveCount(Request request, Response response) {
        activeRefererCount.decrementAndGet();
    }

    protected abstract Response doCall(Request request);

    @Override
    public String desc() {
        return "[" + this.getClass().getSimpleName() + "] url=" + url;
    }

    @Override
    public URL getServiceUrl() {
        return serviceUrl;
    }
}
