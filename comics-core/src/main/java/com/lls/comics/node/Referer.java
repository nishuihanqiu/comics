package com.lls.comics.node;

import com.lls.comics.common.URL;

/************************************
 * Referer
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface Referer<T> extends Caller<T> {

    int activeRefererCount();

    URL getServiceUrl();

}
