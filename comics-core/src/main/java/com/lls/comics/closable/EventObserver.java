package com.lls.comics.closable;

import java.util.EventListener;

/************************************
 * EventObserver
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public interface EventObserver extends EventListener {

    void onEvent(Event event);

}
