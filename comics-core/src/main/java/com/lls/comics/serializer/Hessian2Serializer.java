package com.lls.comics.serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.lls.comics.core.extension.SpiMeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/************************************
 * Hessian2Serializer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
@SpiMeta(name = "hessian2")
public class Hessian2Serializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(outputStream);
        hessian2Output.writeObject(obj);
        hessian2Output.flush();
        return outputStream.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(inputStream);
        return (T) hessian2Input.readObject();
    }

    @Override
    public byte[] multiSerialize(Object[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(outputStream);
        for (Object item : data) {
            hessian2Output.writeObject(item);
        }
        hessian2Output.flush();
        return outputStream.toByteArray();
    }

    @Override
    public Object[] multiDeserialize(byte[] data, Class<?>[] classes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Hessian2Input hessian2Input = new Hessian2Input(inputStream);
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = hessian2Input.readObject(classes[i]);
        }
        return objects;
    }

    @Override
    public int getSerializerVersion() {
        return SerializerVersion.HESSIAN_2.getVersion();
    }

}
