package com.lls.comics.util;

import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/************************************
 * ReflectUtils
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public class ReflectUtils {

    public static final String PARAM_CLASS_SPLIT = ",";
    public static final String EMPTY_PARAM = "void";
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    private static final ConcurrentMap<String, Class<?>> name2classCaches = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, String> class2nameCaches = new ConcurrentHashMap<>();
    private static final int PRIMITIVE_CLASS_NAME_MAX_LENGTH = 7;
    private static final String[] PRIMITIVE_NAMES = new String[]{"boolean", "byte", "char", "double",
        "float", "int", "long", "short", "void"};
    private static final Class<?>[] PRIMITIVE_CLASSES = new Class[]{boolean.class, byte.class,
        char.class, double.class, float.class, int.class, long.class, short.class, Void.TYPE};

    public static Class<?>[] forNames(String clazzNameList) throws ClassNotFoundException {
        if (clazzNameList == null || clazzNameList.isEmpty() || EMPTY_PARAM.equals(clazzNameList)) {
            return EMPTY_CLASS_ARRAY;
        }

        String[] classNames = clazzNameList.split(PARAM_CLASS_SPLIT);
        Class<?>[] classTypes = new Class<?>[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            String className = classNames[i];
            classTypes[i] = forName(className);
        }
        return classTypes;
    }

    public static Class<?> forName(String className) throws ClassNotFoundException {
        if (className == null || className.isEmpty()) {
            return null;
        }

        Class<?> clazz = name2classCaches.get(className);
        if (clazz != null) {
            return clazz;
        }
        clazz = forNameWithoutCache(className);
        name2classCaches.putIfAbsent(className, clazz);
        return clazz;
    }

    private static Class<?> forNameWithoutCache(String className) throws ClassNotFoundException {
        if (!className.endsWith("[]")) {
            Class<?> clz = getPrimitiveClass(className);
            clz = (clz != null) ? clz : Class.forName(className, true, Thread.currentThread().getContextClassLoader());
            return clz;
        }

        int dimensionSize = 0;
        while (className.endsWith("[]")) {
            dimensionSize++;
            className = className.substring(0, className.length() - 2);
        }
        int[] dimensions = new int[dimensionSize];
        Class<?> clz = getPrimitiveClass(className);
        if (clz == null) {
            clz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        }
        return Array.newInstance(clz, dimensionSize).getClass();
    }

    public static Class<?> getPrimitiveClass(String name) {
        if (name.length() <= PRIMITIVE_CLASS_NAME_MAX_LENGTH) {
            int index = Arrays.binarySearch(PRIMITIVE_NAMES, name);
            if (index >= 0) {
                return PRIMITIVE_CLASSES[index];
            }
        }
        return null;
    }

}
