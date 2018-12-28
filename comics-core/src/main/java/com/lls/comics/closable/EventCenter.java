package com.lls.comics.closable;

/************************************
 * EventCenter
 * @author liliangshan
 * @date 2018/12/28
 ************************************/
public interface EventCenter {

    void subscribe(EventSubscriber subscriber, String... topics);

    void unsubscribe(String topic, EventSubscriber subscriber);

    void pubAsync(Event event);

    void pubSync(Event event);

}
