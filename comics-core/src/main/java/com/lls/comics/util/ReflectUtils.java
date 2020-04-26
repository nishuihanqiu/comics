package com.lls.comics.util;

import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
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

    public static String getMethodParameterDesc(Method method) {
        if (method.getParameterTypes().length == 0) {
            return EMPTY_PARAM;
        }

        StringBuilder builder = new StringBuilder();
        Class<?>[] classes = method.getParameterTypes();
        for (Class<?> clz : classes) {
            String className = getName(clz);
            builder.append(className).append(PARAM_CLASS_SPLIT);
        }
        return builder.substring(0, builder.length() - 1);
    }

    public static String getMethodDesc(Method method) {
        String methodParamDesc = getMethodParameterDesc(method);
        return getMethodDesc(method.getName(), methodParamDesc);
    }

    public static String getMethodDesc(String methodName, String paramDesc) {
        if (paramDesc == null) {
            return methodName + "()";
        } else {
            return methodName + "(" + paramDesc + ")";
        }
    }

    public static String getName(Class<?> clz) {
        if (clz == null) {
            return null;
        }
        String className = class2nameCaches.get(clz);
        if (className != null) {
            return className;
        }

        className = getNameWithoutCache(clz);
        class2nameCaches.putIfAbsent(clz, className);
        return className;
    }

    public static String getNameWithoutCache(Class<?> clz) {
        if (!clz.isArray()) {
            return clz.getName();
        }

        StringBuilder builder = new StringBuilder();
        while (clz.isArray()) {
            builder.append("[]");
            clz = clz.getComponentType();
        }
        return clz.getName() + builder.toString();
    }

    /**
     * 获取clz public method
     *
     * <pre>
     *      1）不包含构造函数
     *      2）不包含Object.class
     *      3）包含该clz的父类的所有public方法
     * </pre>
     *
     * @param clz
     * @return List<Method>
     */
    public static List<Method> getPublicMethods(Class<?> clz) {
        Method[] methods = clz.getMethods();
        List<Method> results = new ArrayList<>();

        for (Method method : methods) {
            boolean isPublic = Modifier.isPublic(method.getModifiers());
            boolean isNotObjectClass = method.getDeclaringClass() != Object.class;
            if (isPublic && isNotObjectClass) {
                results.add(method);
            }
        }
        return results;
    }

    public static Object getEmptyObject(Class<?> returnType) {
        return getEmptyObject(returnType, new HashMap<>(), 0);
    }

    private static Object getEmptyObject(Class<?> returnType, Map<Class<?>, Object> emptyInstances, int level) {
        if (level > 2) return null;
        if (returnType == null) {
            return null;
        } else if (returnType == boolean.class || returnType == Boolean.class) {
            return false;
        } else if (returnType == char.class || returnType == Character.class) {
            return '\0';
        } else if (returnType == byte.class || returnType == Byte.class) {
            return (byte) 0;
        } else if (returnType == short.class || returnType == Short.class) {
            return (short) 0;
        } else if (returnType == int.class || returnType == Integer.class) {
            return 0;
        } else if (returnType == long.class || returnType == Long.class) {
            return 0L;
        } else if (returnType == float.class || returnType == Float.class) {
            return 0F;
        } else if (returnType == double.class || returnType == Double.class) {
            return 0D;
        } else if (returnType.isArray()) {
            return Array.newInstance(returnType.getComponentType(), 0);
        } else if (returnType.isAssignableFrom(ArrayList.class)) {
            return new ArrayList<Object>(0);
        } else if (returnType.isAssignableFrom(HashSet.class)) {
            return new HashSet<Object>(0);
        } else if (returnType.isAssignableFrom(HashMap.class)) {
            return new HashMap<Object, Object>(0);
        } else if (String.class.equals(returnType)) {
            return "";
        } else if (!returnType.isInterface()) {
            try {
                Object value = emptyInstances.get(returnType);
                if (value == null) {
                    value = returnType.newInstance();
                    emptyInstances.put(returnType, value);
                }
                Class<?> cls = value.getClass();
                while (cls != null && cls != Object.class) {
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        Object property = getEmptyObject(field.getType(), emptyInstances, level + 1);
                        if (property != null) {
                            try {
                                if (!field.isAccessible()) {
                                    field.setAccessible(true);
                                }
                                field.set(value, property);
                            } catch (Throwable e) {}
                        }
                    }
                    cls = cls.getSuperclass();
                }
                return value;
            } catch (Throwable e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
