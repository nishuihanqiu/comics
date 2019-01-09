package com.lls.comics.common;

import com.lls.comics.exception.ComicsArgumentException;
import com.lls.comics.exception.ComicsIllegalStateException;
import com.lls.comics.util.ComicsUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/************************************
 * URL
 * @author liliangshan
 * @date 2018/12/29
 ************************************/
public class URL {

    private String protocol;

    private String host;

    private int port;

    private String path;

    private Map<String, String> arguments;

    private Map<String, Number> numbers;

    public URL(String protocol, String host, int port, String path) {
        this(protocol, host, port, path, new HashMap<>());
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> arguments) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = ComicsUtils.removeAsyncSuffix(path);
        this.arguments = arguments;
    }

    public static URL valueOf(String url) {
        if (StringUtils.isBlank(url)) {
            throw new ComicsArgumentException("url is null");
        }

        String protocol = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> arguments = new HashMap<>();

        int i = url.indexOf("?");
        if (i >= 0) {
            String[] parts = url.substring(i + 1).split("\\&");
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j > 0) {
                        arguments.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        arguments.put(part, part);
                    }
                }
            }

            url = url.substring(0, i);
        }

        i = url.indexOf("://");
        if (i == 0) {
            throw new ComicsIllegalStateException("protocol is null.");
        } else if (i > 0) {
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            i = url.indexOf(":/");
            if (i == 0) {
                throw new ComicsIllegalStateException("protocol is null.");
            } else if (i > 0) {
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }

        i = url.indexOf("/");
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }

        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }

        if (url.length() > 0) host = url;
        return new URL(protocol, host, port, path, arguments);
    }

    private String generateIpAddress(String host, int defaultPort) {
        if (defaultPort <= 0) {
            return host;
        }

        int idx = host.indexOf(":");
        if (idx < 0) {
            return host + ":" + defaultPort;
        }

        int port = Integer.parseInt(host.substring(idx + 1));
        if (port <= 0) {
            return host.substring(0, idx + 1) + defaultPort;
        }
        return host;
    }

    public URL copyURL() {
        Map<String, String> arguments = new HashMap<>();
        if (this.arguments != null) {
            arguments.putAll(this.arguments);
        }
        return new URL(protocol, host, port, path, arguments);
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = ComicsUtils.removeAsyncSuffix(path);
    }

    public String getVersion() {
        return getArgument(URLParamType.VERSION.getName(), URLParamType.VERSION.getValue());
    }

    public String getGroup() {
        return getArgument(URLParamType.GROUP.getName(), URLParamType.GROUP.getValue());
    }

    public String getApplication() {
        return getArgument(URLParamType.APPLICATION.getName(), URLParamType.APPLICATION.getValue());
    }

    public String getModule() {
        return getArgument(URLParamType.MODULE.getName(), URLParamType.MODULE.getValue());
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public String getArgument(String name) {
        return arguments.get(name);
    }

    public String getArgument(String name, String defaultValue) {
        String value = getArgument(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public String getMethodArgument(String methodName, String desc, String name) {
        String value = getArgument(ComicsConstants.METHOD_CONFIG_SUFFIX + methodName + "(" + desc + ")." + name);
        if (value == null || value.length() == 0) {
            return getArgument(name);
        }
        return value;
    }

    public String getMethodArgument(String methodName, String desc, String name, String defaultValue) {
        String value = getMethodArgument(methodName, desc, name);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public void putArgument(String name, String value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        }
        arguments.put(name, value);
    }

    public void removeArgument(String name, String value) {
        if (name != null) {
            arguments.remove(name);
        }
    }

    public void putArguments(Map<String, String> arguments) {
        arguments.putAll(arguments);
    }

    public void putArgumentIfAbsent(String name, String value) {
        if (hasArgument(name)) {
            return;
        }
        arguments.put(name, value);
    }

    public boolean hasArgument(String name) {
        return StringUtils.isNotBlank(getArgument(name));
    }

    public Boolean getBooleanArgument(String name, boolean defaultValue) {
        String value = getArgument(name);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public Boolean getMethodBooleanArgument(String methodName, String desc, String name, boolean defaultValue) {
        String value = getMethodArgument(methodName, desc, name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public Integer getIntArgument(String name, int defaultValue) {
        String value = getArgument(name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public Integer getMethodIntArgument(String methodName, String desc, String name, int defaultValue) {
        String key = methodName + "(" + desc + ")." + name;
        Number number = getNumbers().get(key);
        if (number != null) {
            return number.intValue();
        }

        String value = getMethodArgument(methodName, desc, name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        int result = Integer.parseInt(value);
        getNumbers().put(key, result);
        return result;
    }

    public Long getLongArgument(String name, long defaultValue) {
        Number number = getNumbers().get(name);
        if (number != null) {
            return number.longValue();
        }

        String value = getArgument(name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        long l = Long.parseLong(value);
        getNumbers().put(name, l);
        return l;
    }

    public Long getMethodLongArgument(String methodName, String desc, String name, long defaultValue) {
        String key = methodName + "(" + desc + ")." + name;
        Number number = getNumbers().get(key);
        if (number != null) {
            return number.longValue();
        }
        String value = getMethodArgument(methodName, desc, name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        long l = Long.parseLong(value);
        getNumbers().put(key, l);
        return l;
    }

    public Float getFloatArgument(String name, float defaultValue) {
        Number number = getNumbers().get(name);
        if (number != null) {
            return number.floatValue();
        }

        String value = getArgument(name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        float f = Float.parseFloat(value);
        getNumbers().put(name, f);
        return f;
    }

    public Float getMethodFloatArgument(String methodName, String desc, String name, float defaultValue) {
        String key = methodName + "(" + desc + ")." + name;
        Number number = getNumbers().get(key);
        if (number != null) {
            return number.floatValue();
        }

        String value = getMethodArgument(methodName, desc, name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        float f = Float.parseFloat(value);
        getNumbers().put(key, f);
        return f;
    }

    public Boolean getBooleanArgument(String name) {
        String value = getArgument(name);
        if (value == null) {
            return null;
        }

        return Boolean.parseBoolean(value);
    }

    public String getUri() {
        return protocol + ComicsConstants.PROTOCOL_SEPARATOR + host + ":" + port +
            ComicsConstants.PATH_SEPARATOR + path;
    }

    public String getSimpleString() {
        return getUri() + "?group=" + getGroup();
    }

    private Map<String, Number> getNumbers() {
        return numbers;
    }

    public String getServerPort() {
        return generateIpAddress(host, port);
    }

    @Override
    public int hashCode() {
        int factor = 31;
        int rs = 1;
        rs = factor * rs + ObjectUtils.hashCode(protocol);
        rs = factor * rs + ObjectUtils.hashCode(host);
        rs = factor * rs + ObjectUtils.hashCode(port);
        rs = factor * rs + ObjectUtils.hashCode(path);
        rs = factor * rs + ObjectUtils.hashCode(arguments);
        return rs;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof URL)) {
            return false;
        }
        URL url = (URL) obj;
        if (!ObjectUtils.equals(url.host, this.host)) {
            return false;
        }
        if (!ObjectUtils.equals(url.port, this.port)) {
            return false;
        }
        if (!ObjectUtils.equals(url.path, this.path)) {
            return false;
        }
        if (!ObjectUtils.equals(url.protocol, this.protocol)) {
            return false;
        }
        return ObjectUtils.equals(url.arguments, this.arguments);
    }
}
