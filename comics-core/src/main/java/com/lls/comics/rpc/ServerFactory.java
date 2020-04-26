package com.lls.comics.rpc;

import com.lls.comics.common.URL;
import com.lls.comics.core.ReleasableFactory;

/************************************
 * ServerFactory
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public interface ServerFactory extends ReleasableFactory<Server> {

    Server createServer(URL url, MessageHandler handler);

}
