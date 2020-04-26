package com.lls.comics.serializer;

import com.lls.comics.core.extension.Scope;
import com.lls.comics.core.extension.Spi;

import java.io.IOException;

/************************************
 * Serializer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
@Spi(scope = Scope.PROTOTYPE)
public interface Serializer {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;

    byte[] multiSerialize(Object[] data) throws IOException;

    Object[] multiDeserialize(byte[] data, Class<?>[] classes) throws IOException;

    int getSerializerVersion();

}
