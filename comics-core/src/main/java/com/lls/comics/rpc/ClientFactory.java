package com.lls.comics.rpc;

import com.lls.comics.common.URL;
import com.lls.comics.core.ReleasableFactory;

/************************************
 * ClientFactory
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ClientFactory extends ReleasableFactory<Client> {

    Client createClient(URL url);

}
