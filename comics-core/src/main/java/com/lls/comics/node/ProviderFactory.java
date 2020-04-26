package com.lls.comics.node;

import com.lls.comics.common.URL;

/************************************
 * ProviderFactory
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ProviderFactory {

    <T> Provider<T> makeProvider(T proxy, URL url, Class<T> clz);

}
