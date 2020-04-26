package com.lls.comics.rpc;

/************************************
 * ClientManager
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ClientManager {

    void init();

    void destroy();

    void addClient(Client client);

    void removeClient(Client client);

}
