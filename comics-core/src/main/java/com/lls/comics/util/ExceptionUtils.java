package com.lls.comics.util;

import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

/************************************
 * ExceptionUtils
 * @author liliangshan
 * @date 2020/4/26
 ************************************/
public class ExceptionUtils {

    private static final Logger logger = LoggerFactory.getLogging(ExceptionUtils.class);

    public static final StackTraceElement[] REMOTE_MOCK_STACK = new StackTraceElement[]{
            new StackTraceElement("remoteClass", "remoteMethod", "remoteFile", 1)
    };

    public static void setMockStackTrace(Throwable e) {
        if (e != null) {
            try {
                e.setStackTrace(REMOTE_MOCK_STACK);
            } catch (Exception e1) {
                logger.warn("replace remote exception stack fail!" + e1.getMessage());
            }
        }
    }

}
