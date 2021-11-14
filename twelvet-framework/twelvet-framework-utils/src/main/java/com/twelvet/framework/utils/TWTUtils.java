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
        if (object instanceof String) {
            return StringUtils.EMPTY.equals(object.toString().trim());
        }
        return object == null;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 中文数字转阿拉伯数字
     *
     * @param chineseNumber 中文数字
     * @return 阿拉伯数字
     */
    public static int chineseNumberTransInt(String chineseNumber) {
        int result = 0;
        // 存放一个单位的数字如：十万
        int temp = 1;
        // 判断是否有chArr
        int count = 0;
        char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] chArr = new char[]{'十', '百', '千', '万', '亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            // 判断是否是chArr
            boolean b = true;
            char c = chineseNumber.charAt(i);
            // 非单位，即数字
            for (int j = 0; j < cnArr.length; j++) {
                if (c == cnArr[j]) {
                    // 添加下一个单位之前，先把上一个单位值添加到结果中
                    if (0 != count) {
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            // 单位{'十','百','千','万','亿'}
            if (b) {
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            //遍历到最后一个字符
            if (i == chineseNumber.length() - 1) {
                result += temp;
            }
        }
        return result;
    }


}
