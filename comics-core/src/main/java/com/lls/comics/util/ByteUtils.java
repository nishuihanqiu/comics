package com.lls.comics.util;

import java.util.ArrayList;
import java.util.List;

/************************************
 * ByteUtils
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public class ByteUtils {

    public static List<Byte> toList(byte[] array) {
        if (array == null) {
            return null;
        }

        List<Byte> bytes = new ArrayList<>(array.length);
        for (byte item : array) {
            bytes.add(item);
        }
        return bytes;
    }


    // 把short类型的value转为2个byte字节，放到byte数组的index开始的位置，高位在前
    public static void short2bytes(short value, byte[] bytes, int index) {
        bytes[index + 1] = (byte) value;
        bytes[index] = (byte) (value >>> 8);
    }

    public static short bytes2short(byte[] bytes, int index) {
        return (short) (((bytes[index + 1] & 0xFF)) + ((bytes[index] & 0xFF) << 8));
    }

    // 把int类型的value转为4个byte字节，放到byte数组的index开始的位置，高位在前
    public static void int2bytes(int value, byte[] bytes, int index) {
        bytes[index + 3] = (byte) value;
        bytes[index + 2] = (byte) (value >>> 8);
        bytes[index + 1] = (byte) (value >>> 16);
        bytes[index] = (byte) (value >>> 24);
    }

    public static int bytes2int(byte[] bytes, int index) {
        return (bytes[index + 3] & 0xFF) + ((bytes[index + 2] & 0xFF) << 8) + ((bytes[index + 1] & 0xFF) << 16) + ((bytes[index] & 0xFF) << 24);
    }

    // 把long类型的value转为8个byte字节，放到byte数组的index开始的位置，高位在前
    public static void long2bytes(long value, byte[] bytes, int index) {
        bytes[index + 7] = (byte) (value);
        bytes[index + 6] = (byte) (value >>> 8);
        bytes[index + 5] = (byte) (value >>> 16);
        bytes[index + 4] = (byte) (value >>> 24);
        bytes[index + 3] = (byte) (value >>> 32);
        bytes[index + 2] = (byte) (value >>> 40);
        bytes[index + 1] = (byte) (value >>> 48);
        bytes[index] = (byte) (value >>> 56);
    }

    public static long bytes2long(byte[] bytes, int index) {
        return (bytes[index + 7] & 0xFFL) + ((bytes[index + 6] & 0xFFL) << 8) +
            ((bytes[index + 5] & 0xFFL) << 16) + ((bytes[index + 4] & 0xFFL) << 24) +
            ((bytes[index + 3] & 0xFFL) << 32) + ((bytes[index + 2] & 0xFFL) << 40) +
            ((bytes[index + 1] & 0xFFL) << 48) + (((long)bytes[index]) << 56);
    }
}
