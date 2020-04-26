package com.lls.comics.util;

import com.lls.comics.common.ComicsConstants;
import com.lls.comics.common.URL;
import com.lls.comics.common.URLParamType;
import com.lls.comics.rpc.DefaultResponse;
import com.lls.comics.rpc.Request;

/************************************
 * ComicsUtils
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public class ComicsUtils {

    /**
     * 目前根据 group/interface/version 来唯一标示一个服务
     *
     * @param request
     * @return service key
     */
    public static String getServiceKey(Request request) {
        String version = getVersionFromRequest(request);
        String group = getGroupFromRequest(request);
        return getServiceKey(group, request.getInterfaceName(), version);
    }

    public static String getGroupFromRequest(Request request) {
      return getValueFromRequest(request, URLParamType.GROUP.getName(), URLParamType.GROUP.getValue());
    }

    public static String getVersionFromRequest(Request request) {
      return getValueFromRequest(request, URLParamType.VERSION.getName(), URLParamType.VERSION.getValue());
    }

    public static String getValueFromRequest(Request request, String key, String defaultValue) {
        String value = defaultValue;
        if (request.getAttachments() != null && request.getAttachments().containsKey(key)) {
            value = request.getAttachments().get(key);
        }
        return value;
    }

    public static String getServiceKey(URL url) {
      return getServiceKey(url.getGroup(), url.getPath(), url.getVersion());
    }

    public static String getServiceKey(String group, String interfaceName, String version) {
        return group + ComicsConstants.PATH_SEPARATOR + interfaceName + ComicsConstants.PATH_SEPARATOR + version;
    }

  /**
   * 输出请求的关键信息： requestId=** interface=** method=**(**)
   *
   * @param request
   * @return
   */
  public static String toString(Request request) {
    return "requestId=" + request.getRequestId() + " interface=" + request.getInterfaceName() + " method=" + request.getMethodName()
            + "(" + request.getArgumentDesc() + ")";
  }

    public static String removeAsyncSuffix(String path) {
        if (path != null && path.endsWith(ComicsConstants.ASYNC_SUFFIX)) {
            return path.substring(0, path.length() - ComicsConstants.ASYNC_SUFFIX.length());
        }
        return path;
    }

    public static DefaultResponse buildErrorResponse(Request request, Exception e) {
        return buildErrorResponse(request.getRequestId(), request.getVersion(), e);
    }

    public static DefaultResponse buildErrorResponse(long requestId, byte version, Exception e) {
        DefaultResponse response = new DefaultResponse();
        response.setRequestId(requestId);
        response.setVersion(version);
        response.setException(e);
        return response;
    }

}
