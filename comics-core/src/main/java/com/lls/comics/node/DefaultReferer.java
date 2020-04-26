package com.lls.comics.node;

import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.core.extension.ExtensionLoader;
import com.lls.comics.exception.ComicsNetworkException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.rpc.*;

/************************************
 * DefaultReferer
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class DefaultReferer<T> extends AbstractReferer<T> {

    private static final Logger logger = LoggerFactory.getLogging(DefaultReferer.class);
    protected Client client;
    protected ClientFactory factory;

    public DefaultReferer(URL url, Class<T> clz, URL serviceUrl) {
        super(url, clz, serviceUrl);
        factory = ExtensionLoader.getExtensionLoader(ClientFactory.class)
                .getExtension(url.getArgument(URLParamType.CLIENT_FACTORY.getName(), URLParamType.CLIENT_FACTORY.getValue()));
        client = factory.createClient(url);
    }


    @Override
    protected Response doCall(Request request) {
        try {
            request.setAttachment(URLParamType.GROUP.getName(), serviceUrl.getGroup());
            return client.send(request);
        } catch (Exception e) {
            throw new ComicsNetworkException("default referer call error: url=" + url.getUri(), e);
        }
    }

    @Override
    protected void decrActiveCount(Request request, Response response) {
        if (!(response instanceof Future)) {
            activeRefererCount.decrementAndGet();
            return;
        }

        Future future = (Future) response;
        future.addListener(new FutureListener() {
            @Override
            public void onCompleted(Future future) throws Exception {
                activeRefererCount.decrementAndGet();
            }
        });
    }

    @Override
    protected boolean doInit() {
        return client.open();
    }

    @Override
    public boolean isAvailable() {
        return client.isAvailable();
    }

    @Override
    public void destroy() {
        factory.release(client);
        logger.info("DefaultRpcReferer destroy client: url={}" + url);
    }
}
