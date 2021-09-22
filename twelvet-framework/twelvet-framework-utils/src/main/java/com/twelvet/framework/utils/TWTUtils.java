package com.twelvet.framework.utils;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: twelvet工具类
 */
public class TWTUtils {

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
