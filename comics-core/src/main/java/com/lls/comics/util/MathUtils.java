package com.lls.comics.util;

import com.lls.comics.logging.Logger;
import com.lls.comics.logging.LoggerFactory;

/************************************
 * MathUtils
 * @author liliangshan
 * @date 2019-02-14
 ************************************/
public class MathUtils {

    private static final Logger logger = LoggerFactory.getLogging(MathUtils.class);

    public static int parseInt(String intStr, int defaultValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            logger.error("parse int error:"+ e.getMessage(), e);
            return defaultValue;
        }
    }

    public static long parseLong(String longStr, long defaultValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            logger.error("parse long error:" + e.getMessage(), e);
            return defaultValue;
        }
    }

    public static int getNonNegative(int number) {
        return 0x7fffffff & number;
    }

}
