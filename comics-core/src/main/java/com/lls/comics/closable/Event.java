package com.lls.comics.closable;

import java.util.HashMap;
import java.util.Map;

/************************************
 * Event
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public class Event {

    private String topic;
    private Map<String, Object> params;

    public Event(String topic) {
        this(topic, null);
    }

    public Event(String topic, Map<String, Object> params) {
        this.topic = topic;
        this.params = params;
        if (params == null) {
            this.params = new HashMap<>();
        }
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public Object remove(String key) {
        return params.remove(key);
    }

    public Object get(String key) {
        return params.get(key);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
