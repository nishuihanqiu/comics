package com.lls.comics.serializer;

import java.io.IOException;

/************************************
 * SerializerContext
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class SerializerContext {

    private Serializer serializer;
    private byte[] serializedBytes;

    public SerializerContext(Serializer serializer) {
        this(serializer, null);
    }

    public SerializerContext(Serializer serializer, byte[] serializedBytes) {
        this.serializer = serializer;
        this.serializedBytes = serializedBytes;
    }

    public byte[] serialize(Object obj) throws IOException {
        this.serializedBytes = serializer.serialize(obj);
        return this.serializedBytes;
    }

    public <T> T deserialize(Class<T> clz) throws IOException {
        if (this.serializedBytes == null) {
            return null;
        }
        return serializer.deserialize(this.serializedBytes, clz);
    }

    public byte[] multiSerialize(Object[] data) throws IOException {
        this.serializedBytes = serializer.multiSerialize(data);
        return this.serializedBytes;
    }

    public Object[] multiDeserialize(Class<?>[] classes) throws IOException {
        if (this.serializedBytes == null) {
            return null;
        }
        return serializer.multiDeserialize(this.serializedBytes, classes);
    }

}
