package com.twelvet;

import com.twelvet.framework.utils.TWTUtils;
import org.junit.Test;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

    @Test
    public void util() {
        System.out.println(TWTUtils.chineseNumberTransInt("十万八千七八九十一"));
    }

}
