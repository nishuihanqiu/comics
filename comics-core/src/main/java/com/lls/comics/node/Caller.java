package com.lls.comics.node;

import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;

/************************************
 * Caller
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface Caller<T> extends Node {

    Class<T> getInterface();

    Response call(Request request);

}
