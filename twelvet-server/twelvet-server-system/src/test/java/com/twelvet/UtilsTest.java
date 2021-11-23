package com.twelvet;

import com.twelvet.framework.utils.ArabicNumeralsUtils;
import org.junit.Test;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

    @Test
    public void util() {
        System.out.println(ArabicNumeralsUtils.intTransIntChineseNumber(154564));
        System.out.println(ArabicNumeralsUtils.chineseTransIntNumber("十万八千七百九十一"));
    }

}
