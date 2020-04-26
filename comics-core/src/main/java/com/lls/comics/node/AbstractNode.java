package com.lls.comics.node;

import com.lls.comics.common.URL;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.exception.ComicsExceptionConstants;
import com.lls.comics.exception.ComicsNetworkException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * AbstractNode
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public abstract class AbstractNode implements Node {

    private static final Logger logger = LoggerFactory.getLogging(AbstractNode.class);
    protected URL url;
    protected volatile AtomicBoolean inited = new AtomicBoolean(false);
    protected volatile AtomicBoolean available = new AtomicBoolean(false);

    public AbstractNode(URL url) {
        this.url = url;
    }

    @Override
    public void init() {
        if (inited.get()) {
            logger.warn(this.getClass().getName() + " node has already inited, description:{}", desc());
            return;
        }
        if (inited.compareAndSet(false, true)) {
            boolean result = doInit();
            if (!result) {
                inited.set(false);
                logger.error(this.getClass().getSimpleName() + " node init Error: " + desc());
                throw new ComicsNetworkException(this.getClass().getSimpleName() + " node init error: " + desc(),
                        ComicsExceptionConstants.FRAMEWORK_INIT_ERROR);
            }
            logger.info(this.getClass().getSimpleName() + " node init Success: " + desc());
            available.compareAndSet(false, true);
        }
    }

    protected abstract boolean doInit();

    @Override
    public boolean isAvailable() {
        return available.get();
    }

    @Override
    public URL getUrl() {
        return url;
    }

}
