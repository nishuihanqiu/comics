package com.lls.comics.logging;

/************************************
 * LoggerFactory
 * @author liliangshan
 * @date 2018/12/27
 ************************************/
public class LoggerFactory {


    public static Logger getLogging(String name) {
        return new DefaultLogger(name);
    }

    public static Logger getLogging(Class<?> clazz) {
        return getLogging(clazz.getName());
    }
}
