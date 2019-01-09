package com.lls.comics.util;

import com.lls.comics.common.ComicsConstants;

/************************************
 * ComicsUtils
 * @author liliangshan
 * @date 2019/1/9
 ************************************/
public class ComicsUtils {


  public static String removeAsyncSuffix(String path) {
    if (path != null && path.endsWith(ComicsConstants.ASYNC_SUFFIX)) {
      return path.substring(0, path.length() - ComicsConstants.ASYNC_SUFFIX.length());
    }
    return path;
  }

}
