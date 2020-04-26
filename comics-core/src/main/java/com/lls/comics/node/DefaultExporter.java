package com.lls.comics.node;

import com.lls.comics.common.URL;

/************************************
 * DefaultExporter
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class DefaultExporter<T> extends AbstractExporter<T> {


    public DefaultExporter(Provider<T> provider, URL url) {
        super(provider, url);
    }

    @Override
    protected boolean doInit() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void destroy() {

    }
}
