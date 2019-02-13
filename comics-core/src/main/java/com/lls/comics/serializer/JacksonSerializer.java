package com.lls.comics.serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lls.comics.core.extension.SpiMeta;

import java.io.IOException;
import java.util.List;

/************************************
 * JacksonSerializer
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
@SpiMeta(name = "jackson")
public class JacksonSerializer implements Serializer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return objectMapper.writeValueAsBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return objectMapper.readValue(bytes, clz);
    }

    @Override
    public byte[] batchSerialize(Object[] data) throws IOException {
        return objectMapper.writeValueAsBytes(data);
    }

    @Override
    public Object[] batchDeserialize(byte[] data, Class<?>[] classes) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, classes);
        return objectMapper.readValue(data, javaType);
    }

    @Override
    public int getSerializerVersion() {
        return SerializerVersion.JACK_SON.getVersion();
    }

}
