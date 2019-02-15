package com.lls.comics.codec;

import com.lls.comics.common.URL;
import com.lls.comics.core.extension.Scope;
import com.lls.comics.core.extension.Spi;

import java.io.IOException;

/************************************
 * Codec
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
@Spi(scope = Scope.PROTOTYPE)
public interface Codec {

    byte[] encode(URL url, Object message) throws IOException;

    Object decode(URL url, byte messageType, byte[] bytes) throws IOException;

}
