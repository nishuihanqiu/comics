package com.lls.comics.node;

import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.exception.ComicsArgumentException;
import com.lls.comics.exception.ComicsException;
import com.lls.comics.exception.ComicsExceptionConstants;
import com.lls.comics.exception.ComicsRequestException;
import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;
import com.lls.comics.rpc.DefaultResponse;
import com.lls.comics.rpc.Request;
import com.lls.comics.rpc.Response;
import com.lls.comics.util.ExceptionUtils;

import java.lang.reflect.Method;

/************************************
 * DefaultProvider
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class DefaultProvider<T> extends AbstractProvider<T> {

    private static final Logger logger = LoggerFactory.getLogging(DefaultProvider.class);

    protected T proxyImplement;

    public DefaultProvider(T proxyImplement, Class<T> clz, URL url) {
        super(clz, url);
        this.proxyImplement = proxyImplement;
    }

    @Override
    protected Response invoke(Request request) {
        DefaultResponse response = new DefaultResponse();
        Method method = this.getMethod(request.getMethodName(), request.getArgumentDesc());
        if (method == null) {
            ComicsException exception = new ComicsArgumentException("Service method not exist: "+ request.getInterfaceName() + "." + request.getMethodName()
                    + "(" + request.getArgumentDesc() + ")", ComicsExceptionConstants.SERVICE_NOT_FOUND);
            response.setException(exception);
            return response;
        }
        boolean defaultExceptionStack = URLParamType.TRANS_EXCEPTION_STACK.getBooleanValue();
        try {
            Object value = method.invoke(proxyImplement, request.getArguments());
            response.setValue(value);
        } catch (Exception e) {
            if (e.getCause() != null) {
                response.setException(new ComicsRequestException("comics request exception."));
            } else {
                response.setException(new ComicsRequestException("comics request exception.", e.getCause()));
            }

            boolean logged = true;
            for (Class<?> clz : method.getExceptionTypes()) {
                if (clz.isInstance(response.getException().getCause())) {
                    logged = false;
                    defaultExceptionStack = false;
                    break;
                }
            }
            if (logged) {
                logger.error("Exception caught when during method invocation. request:" + request.toString(), e);
            } else {
                logger.info("Exception caught when during method invocation. request:" + request.toString() + ", exception:" + response.getException().getCause().toString());
            }
        } catch (Throwable t) {
            // 如果服务发生Error，将Error转化为Exception，防止拖垮调用方
            if (t.getCause() != null) {
                response.setException(new ComicsException("provider has encountered a fatal error!", t.getCause()));
            } else {
                response.setException(new ComicsException("provider has encountered a fatal error!", t));
            }
            //对于Throwable,也记录日志
            logger.error("Exception caught when during method invocation. request:" + request.toString(), t);
        }

        if (response.getException() != null) {
            //是否传输业务异常栈
            boolean transExceptionStack = this.url.getBooleanArgument(URLParamType.TRANS_EXCEPTION_STACK.getName(), defaultExceptionStack);
            if (!transExceptionStack) {//不传输业务异常栈
                ExceptionUtils.setMockStackTrace(response.getException().getCause());
            }
        }

        response.setAttachments(request.getAttachments());
        return response;
    }

    @Override
    public T getImplement() {
        return proxyImplement;
    }


}
