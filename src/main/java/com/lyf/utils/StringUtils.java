package com.lyf.utils;

/**
 * 字符串判断
 */
public class StringUtils {
    /**
     * 字符串判断，不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return str != null && !"".equals(str);
    }
}
