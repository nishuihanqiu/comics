package com.lls.comics.serializer;

import com.lls.comics.core.extension.SpiMeta;

import java.io.IOException;

/************************************
 * ProtobufSerializer
 * @author liliangshan
 * @date 2018/12/13
 ************************************/
@SpiMeta(name = "protobuf")
public class ProtobufSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return null;
    }

    @Override
    public byte[] multiSerialize(Object[] data) throws IOException {
        return new byte[0];
    }

    @Override
    public Object[] multiDeserialize(byte[] data, Class<?>[] classes) throws IOException {
        return new Object[0];
    }

    @Override
    public int getSerializerVersion() {
        return 0;
    }

}
