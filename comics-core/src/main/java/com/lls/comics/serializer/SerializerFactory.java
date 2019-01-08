package com.lls.comics.serializer;

/************************************
 * SerializerFactory
 * @author liliangshan
 * @date 2018/12/25
 ************************************/
public class SerializerFactory {

    private static Serializer newSerializer() {
        return new HessianSerializer();
    }

    public static Serializer newSerializer(int version) {
        Serializer serializer;
        SerializerVersion serializerVersion = SerializerVersion.match(version);
        if (serializerVersion == null) {
            return newSerializer();
        }
        switch (serializerVersion) {
            case HESSIAN_1:
                serializer = new HessianSerializer();
                break;
            case HESSIAN_2:
                serializer = new Hessian2Serializer();
                break;
            case JACK_SON:
                serializer = new JacksonSerializer();
                break;
            case FAST_JSON:
                serializer = new FastJsonSerializer();
                break;
            case PROTOSTUFF:
                serializer = new ProtostuffSerializer();
                break;
            case PROTOBUF:
                serializer = new ProtobufSerializer();
                break;
            default:
                serializer = newSerializer();
        }
        return serializer;
    }
}
