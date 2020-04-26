package com.lls.comics.node;

import java.lang.reflect.Method;

/************************************
 * Provider
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface Provider<T> extends Caller<T> {

    Method getMethod(String methodName, String desc);

    T getImplement();

}
