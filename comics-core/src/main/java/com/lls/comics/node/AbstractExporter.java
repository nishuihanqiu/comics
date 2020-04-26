package com.lls.comics.node;

import com.lls.comics.common.URL;

/************************************
 * AbstractExporter
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public abstract class AbstractExporter<T> extends AbstractNode implements Exporter<T> {

    protected Provider<T> provider;

    public AbstractExporter(Provider<T> provider, URL url) {
        super(url);
        this.provider = provider;
    }

    @Override
    public Provider<T> getProvider() {
        return provider;
    }

    @Override
    public String desc() {
        return "[" + this.getClass().getSimpleName() + "] url=" + url;
    }

}
