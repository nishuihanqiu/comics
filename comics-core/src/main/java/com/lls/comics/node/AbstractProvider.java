package com.lls.comics.node;

import com.lls.comics.common.URL;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import com.lls.comics.util.ReflectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/************************************
 * AbstractProvider
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public abstract class AbstractProvider<T> implements Provider<T> {

    protected Class<T> clz;
    protected URL url;
    protected AtomicBoolean alive = new AtomicBoolean(false);
    protected AtomicBoolean closed = new AtomicBoolean(false);
    protected Map<String, Method> methodMap = new HashMap<>();

    public AbstractProvider(Class<T> clz, URL url) {
        this.clz = clz;
        this.url = url;
        initMethodMap(clz);
    }

    @Override
    public Response call(Request request) {
        return invoke(request);
    }

    protected abstract Response invoke(Request request);

    @Override
    public void init() {
        alive.compareAndSet(false, true);
    }

    @Override
    public void destroy() {
        closed.compareAndSet(false, true);
        alive.compareAndSet(true, false);
    }

    @Override
    public boolean isAvailable() {
        return alive.get();
    }

    @Override
    public String desc() {
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public Class<T> getInterface() {
        return clz;
    }

    @Override
    public Method getMethod(String methodName, String desc) {
        Method method = null;
        String name = ReflectUtils.getMethodDesc(methodName, desc);
        method = methodMap.get(name);
        if (method == null && StringUtils.isBlank(name)) {
            method = methodMap.get(methodName);
            if (method == null) {
                method = methodMap.get(methodName.substring(0, 1).toLowerCase() + methodName.substring(1));
            }
        }
        return method;
    }

    private void initMethodMap(Class<T> clz) {
        Method[] methods = clz.getMethods();
        List<String> duplicatedMethodNames = new ArrayList<>();
        for (Method method : methods) {
            String methodDesc = ReflectUtils.getMethodDesc(method);
            methodMap.put(methodDesc, method);
            if (methodMap.get(method.getName()) == null) {
                methodMap.put(method.getName(), method);
            } else {
                duplicatedMethodNames.add(method.getName());
            }
        }
    }
}
