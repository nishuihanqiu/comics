package com.lls.comics.closable;

/************************************
 * Hook
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public interface Hook {

    void execute(boolean sync);

    void register(Closable closable);

    void register(Closable closable, int priority);

    boolean isExecuted();



}
