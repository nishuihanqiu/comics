package com.lls.comics.closable;

/************************************
 * EventSubscriber
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public class EventSubscriber {

    private String id;
    private EventObserver listener;

    public EventSubscriber(String id, EventObserver listener) {
        this.id = id;
        this.listener = listener;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setListener(EventObserver listener) {
        this.listener = listener;
    }

    public EventObserver getListener() {
        return listener;
    }

}
