package com.lls.comics.serializer;

/************************************
 * SerializerVersion
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public enum SerializerVersion {

    HESSIAN_1(0),
    HESSIAN_2(1),
    JACK_SON(2),
    FAST_JSON(3),
    PROTOBUF(4),
    PROTOSTUFF(5);

    private final int version;

    SerializerVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public static SerializerVersion match(int version) {
        for (SerializerVersion item : SerializerVersion.values()) {
            if (item.getVersion() == version) {
                return item;
            }
        }
        return null;
    }
}
