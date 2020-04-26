package com.lls.comics.node;

import com.lls.comics.common.URL;

/************************************
 * Node
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface Node {

    void init();

    void destroy();

    boolean isAvailable();

    String desc();

    URL getUrl();


}
