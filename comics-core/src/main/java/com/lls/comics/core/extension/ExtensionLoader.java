package com.lls.comics.core.extension;

import com.lls.comics.common.ComicsConstants;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/************************************
 * ExtensionLoader
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public class ExtensionLoader<T> {

    private static ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<>();

    private ConcurrentMap<String, T> singletonInstances = null;
    private ConcurrentMap<String, Class<T>> extensionClasses = null;
    private Class<T> type;
    private volatile boolean initialized = false;

    private static final String PREFIX = "META-INF/extensions/";
    private ClassLoader classLoader;

    private static final Logger logger = LoggerFactory.getLogging(ExtensionLoader.class);

    public ExtensionLoader(Class<T> type) {
        this(type, Thread.currentThread().getContextClassLoader());
    }

    public ExtensionLoader(Class<T> type, ClassLoader classLoader) {
        this.type = type;
        this.classLoader = classLoader;
    }

    private void checkInit() {
        if (!initialized) {
            loadExtensionClasses();
        }
    }

    public Class<T> getExtensionClass(String name) {
        checkInit();
        return extensionClasses.get(name);
    }

    public T getExtension(String name) {
        checkInit();

        if (name == null) {
            return null;
        }

        try {
            Spi spi = type.getAnnotation(Spi.class);
            if (spi.scope() == Scope.SINGLETON) {
                return getSingletonInstance(name);
            }

            Class<T> clz = extensionClasses.get(name);
            if (clz == null) {
                return null;
            }
            return clz.newInstance();
        } catch (Exception e) {
            throw new ComicsException(type.getName() + " error when get extension: " + name, e);
        }
    }

    private T getSingletonInstance(String name) throws InstantiationException, IllegalAccessException {
        T obj = singletonInstances.get(name);
        if (obj != null) {
            return obj;
        }

        Class<T> clz = extensionClasses.get(name);
        if (clz == null) {
            return null;
        }

        synchronized (singletonInstances) {
            obj = singletonInstances.get(name);
            if (obj != null) {
                return obj;
            }

            obj = clz.newInstance();
            singletonInstances.put(name, obj);
        }

        return obj;
    }

    public void addExtensionClass(Class<T> clz) {
        if (clz == null) {
            return;
        }

        checkInit();
        checkExtensionType(clz);

        String spiName = getSpiName(clz);

        synchronized (extensionClasses) {
            if (extensionClasses.containsKey(spiName)) {
                throw new ComicsException(type.getName() + " add extension error: spiName:" + spiName + " already exists");
            }
            extensionClasses.put(spiName, clz);
        }
    }

    private synchronized void loadExtensionClasses() {
        if (initialized) {
            return;
        }
        extensionClasses = loadExtensionClasses(PREFIX);
        singletonInstances = new ConcurrentHashMap<>();
        initialized = true;
    }

    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        checkInterfaceType(type);
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = initExtensionLoader(type);
        }
        return loader;
    }

    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> initExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = new ExtensionLoader<T>(type);

            extensionLoaders.putIfAbsent(type, loader);

            loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        }

        return loader;
    }

    public List<T> getExtensions(String key) {
        checkInit();
        if (extensionClasses.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> extClasses = new ArrayList<>(extensionClasses.size());
        for (Map.Entry<String, Class<T>> entry : extensionClasses.entrySet()) {
            Activation activation = entry.getValue().getAnnotation(Activation.class);
            if (StringUtils.isBlank(key)) {
                extClasses.add(getExtension(entry.getKey()));
            } else if (activation != null) {
                for (String k : activation.key()) {
                    if (key.equals(k)) {
                        extClasses.add(getExtension(entry.getKey()));
                        break;
                    }
                }
            }
        }

        extClasses.sort(new SequenceComparator<>());
        return extClasses;
    }

    private static <T> void checkInterfaceType(Class<T> clz) {
        if (clz == null) {
            throw new ComicsException(clz.getName() + ":error extension type is null.");
        }
        if (!clz.isInterface()) {
            throw new ComicsException(clz.getName() + ": error extension type is not interface.");
        }
        if (!isSpiType(clz)) {
            throw new ComicsException(clz.getName() + ": error extension type without @Spi annotation.");
        }
    }

    private void checkExtensionType(Class<T> clz) {
        checkClassPublic(clz);
        checkConstructorPublic(clz);
        checkClassInherit(clz);
    }

    private void checkClassPublic(Class<T> clz) {
        if (!Modifier.isPublic(clz.getModifiers())) {
            throw new ComicsException(clz.getName() + " : error is not a public class.");
        }
    }

    private void checkConstructorPublic(Class<T> clz) {
        Constructor<?>[] constructors = clz.getConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new ComicsException(clz.getName() + " : error has no public no-args constructor.");
        }
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                return;
            }
        }

        throw new ComicsException(clz.getName() + " error has no public no-args constructor.");
    }

    private void checkClassInherit(Class<T> clz) {
        if (!type.isAssignableFrom(clz)) {
            throw new ComicsException(clz.getName() + ":error is not instance of " + type.getName());
        }
    }

    private String getSpiName(Class<T> clz) {
        SpiMeta spiMeta = clz.getAnnotation(SpiMeta.class);
        return spiMeta != null && !"".equals(spiMeta.name()) ? spiMeta.name() : clz.getSimpleName();
    }

    private static <T> boolean isSpiType(Class<T> clz) {
        return clz.isAnnotationPresent(Spi.class);
    }

    private ConcurrentMap<String, Class<T>> loadExtensionClasses(String prefix) {
        String fullName = prefix + type.getName();
        List<String> classNames = new ArrayList<>();

        try {
            Enumeration<URL> urls;
            if (classLoader == null) {
                urls = ClassLoader.getSystemResources(fullName);
            } else {
                urls = classLoader.getResources(fullName);
            }

            if (urls == null || !urls.hasMoreElements()) {
                return new ConcurrentHashMap<>();
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                parseURL(type, url, classNames);
            }
        } catch (Exception e) {
            throw new ComicsException(type.getName() + "ExtensionLoader loadExtensionClasses error, prefix:" + prefix +
                " type: " + type.getName(), e);
        }

        return loadClasses(classNames);
    }

    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Class<T>> loadClasses(List<String> classNames) {
        ConcurrentMap<String, Class<T>> map = new ConcurrentHashMap<>();
        for (String className : classNames) {
            try {
                Class<T> clz;
                if (classLoader == null) {
                    clz = (Class<T>) Class.forName(className);
                } else {
                    clz = (Class<T>) Class.forName(className, true, classLoader);
                }

                checkExtensionType(clz);
                String spiName = getSpiName(clz);
                if (map.containsKey(spiName)) {
                    throw new ComicsException(type.getName() + " loadClasses error spiName:" + spiName + "already exists");
                } else {
                    map.put(spiName, clz);
                }
            } catch (Exception e) {
                logger.error(type.getName() + " loadClasses error", e);
            }
        }

        return map;
    }

    private void parseURL(Class<T> type, URL url, List<String> classNames) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = url.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, ComicsConstants.DEFAULT_CHARACTER));

            String line = null;
            int indexNumber = 0;

            while ((line = bufferedReader.readLine()) != null) {
                indexNumber++;
                parseLine(type, url, line, indexNumber, classNames);
            }
        } catch (Exception e) {
            logger.error(type.getName() + " error reading spi configuration file. ", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error(type.getName() + "error closing spi configuration file reader", e);
            }
        }
    }

    private void parseLine(Class<T> type, URL url, String line, int lineNumber, List<String> names) {
        int ci = line.indexOf('#');
        if (ci > 0) {
            line = line.substring(0, ci);
        }
        line = line.trim();
        if (line.length() <= 0) {
            return;
        }

        if ((line.indexOf(' ') >= 0) || (line.indexOf('\t') >= 0)) {
            throw new ComicsException(type.getName() + ":" + url + ":" + line + ": Illegal spi configuration-file syntax");
        }

        int cp = line.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
            throw new ComicsException(type.getName() + ":" + url + ":" + line + ": illegal spi provider class name");
        }

        for (int i = Character.charCount(cp); i < line.length(); i += Character.charCount(cp)) {
            cp = line.codePointAt(i);
            if (!Character.isJavaIdentifierPart(cp) && (cp != '.')) {
                throw new ComicsException(type.getName() + ":" + url + ":" + line + ": illegal spi provider class name");
            }
        }

        if (!names.contains(line)) {
            names.add(line);
        }
    }
}
