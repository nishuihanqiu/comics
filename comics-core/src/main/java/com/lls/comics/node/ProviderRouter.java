package com.lls.comics.node;

import com.lls.comics.channel.SocketChannel;
import com.lls.comics.exception.ComicsArgumentException;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.exception.ComicsRequestException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.rpc.*;
import com.lls.comics.serializer.SerializerContext;
import com.lls.comics.util.ComicsUtils;
import com.lls.comics.util.ReflectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/************************************
 * ProviderRouter
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class ProviderRouter implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogging(ProviderRouter.class);

    protected Map<String, Provider<?>> providers = new HashMap<>();
    protected AtomicInteger methodCounter = new AtomicInteger(0);

    public ProviderRouter() {
    }

    protected Response call(Request request, Provider<?> provider) {
        try {
            return provider.call(request);
        } catch (Exception e) {
            return ComicsUtils.buildErrorResponse(request, new ComicsException("provider call process error", e));
        }
    }

    private void processLazyDeserialize(Request request, Method method) {
        if (method != null && request.getArguments() != null && request.getArguments().length == 1
                && request.getArguments()[0] instanceof SerializerContext
                && request instanceof DefaultRequest) {
            try {
                Object[] args = ((SerializerContext) request.getArguments()[0]).multiDeserialize(method.getParameterTypes());
                ((DefaultRequest) request).setArguments(args);
            } catch (IOException e) {
                throw new ComicsException("deserialized argument failed : " + request.toString() + ", error:" + e.getMessage());
            }
        }
    }

    private void fillParameterDesc(Request request, Method method) {
        if (method != null && StringUtils.isBlank(request.getArgumentDesc())
                && request instanceof DefaultRequest) {
            DefaultRequest dr = (DefaultRequest) request;
            dr.setArgumentDesc(ReflectUtils.getMethodParameterDesc(method));
            dr.setMethodName(method.getName());
        }
    }

    public synchronized void addProvider(Provider<?> provider) {
        String serviceKey = ComicsUtils.getServiceKey(provider.getUrl());
        if (providers.containsKey(serviceKey)) {
            throw new ComicsArgumentException(serviceKey + " provider has already exist");
        }
        providers.put(serviceKey, provider);
        // 获取该service暴露的方法数：
        List<Method> methods = ReflectUtils.getPublicMethods(provider.getInterface());
        methodCounter.addAndGet(methods.size());
        logger.info("provider_router add a provider --> url=" + provider.getUrl() +
                " now public method count = " + methodCounter.get());
    }

    public synchronized void removeProvider(Provider<?> provider) {
        String serviceKey = ComicsUtils.getServiceKey(provider.getUrl());
        providers.remove(serviceKey);
        List<Method> methods = ReflectUtils.getPublicMethods(provider.getInterface());
        methodCounter.getAndSet(methodCounter.get() - methods.size());
        logger.info("provider_router remove a provider --> url=" + provider.getUrl() +
                " now public method count = " + methodCounter.get());
    }

    public int getPublicMethodCount() {
        return methodCounter.get();
    }

    @Override
    public Object handle(SocketChannel channel, Object message) {
        if (channel == null || message == null) {
            throw new ComicsArgumentException("Provider Router handle(channel, message) params is null.");
        }
        if (!(message instanceof Request)) {
            throw new ComicsArgumentException("Provider Router handle message type not support. ==> " + message.getClass());
        }

        Request request = (Request) message;
        String serviceKey = ComicsUtils.getServiceKey(request);
        Provider<?> provider = providers.get(serviceKey);
        if (provider == null) {
            logger.error(this.getClass().getSimpleName() + " handle error: provider not exist serviceKey=" +
                    serviceKey + " " + ComicsUtils.toString(request));
            ComicsException exception = new ComicsRequestException(this.getClass().getSimpleName() + " handler Error: provider not exist serviceKey="
                    + serviceKey + " " + ComicsUtils.toString(request));

            return ComicsUtils.buildErrorResponse(request, exception);
        }

        Method method = provider.getMethod(request.getMethodName(), request.getArgumentDesc());
        this.fillParameterDesc(request, method);
        this.processLazyDeserialize(request, method);
        Response response = this.call(request, provider);
        response.setSerializeNumber(request.getSerializeNumber());
        response.setVersion(request.getVersion());
        return response;
    }

}
