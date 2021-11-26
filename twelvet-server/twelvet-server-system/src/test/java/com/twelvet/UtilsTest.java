package com.twelvet;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.framework.utils.JacksonUtils;
import org.junit.Test;

import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

    @Test
    public void util() {
        SysDept sysDept = new SysDept();
        sysDept.setDeptName("测试");
        byte[] bytes = JacksonUtils.toJsonAsBytes(sysDept);
        Map<String, Object> stringObjectMap = JacksonUtils.readMap(bytes);
        stringObjectMap.forEach((s, o) -> System.out.println(o));
    }

}
