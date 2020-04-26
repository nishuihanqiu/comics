package com.lls.comics.core;

/************************************
 * ObjectFactory
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ObjectFactory<T> {

    T makeObject();

    boolean rebuildObject(T obj);


}
