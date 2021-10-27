package com.twelvet.framework.utils;

import com.twelvet.framework.utils.exception.TWTUtilsException;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: twelvet工具类
 */
public class TWTUtils {

    public TWTUtils() {
        throw new TWTUtilsException("This is a utility class and cannot be instantiated");
    }

    /**
     * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object object) {
        if(object instanceof String){
            return StringUtils.EMPTY.equals(object.toString().trim());
        }
        return object == null;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
