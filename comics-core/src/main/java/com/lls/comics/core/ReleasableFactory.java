package com.lls.comics.core;

/************************************
 * ReleasableFactory
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ReleasableFactory<T> {

    void release(T resource);

}
