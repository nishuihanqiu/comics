package com.lls.comics.serializer;

import java.io.IOException;

/************************************
 * SerializerContext
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class SerializerContext implements Serializer {

    private Serializer serializer;

    public SerializerContext(Serializer serializer) {
        this.serializer = serializer;
    }


    @Override
    public byte[] serialize(Object obj) throws IOException {
        return serializer.serialize(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return serializer.deserialize(bytes, clz);
    }

    @Override
    public byte[] batchSerialize(Object[] data) throws IOException {
        return serializer.batchSerialize(data);
    }

    @Override
    public Object[] batchDeserialize(byte[] data, Class<?>[] classes) throws IOException {
        return serializer.batchDeserialize(data, classes);
    }

    @Override
    public int getSerializerVersion() {
        return serializer.getSerializerVersion();
    }

}
