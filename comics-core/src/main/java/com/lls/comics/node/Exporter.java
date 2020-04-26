package com.lls.comics.node;

/************************************
 * Exporter
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface Exporter<T> extends Node {

    Provider<T> getProvider();

    void cancel();

}
