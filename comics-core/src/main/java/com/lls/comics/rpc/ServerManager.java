package com.lls.comics.rpc;

/************************************
 * ServerManager
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ServerManager {

    void init();

    void destroy();

    void addServer(Server server);

    void removeServer(Server server);

}
