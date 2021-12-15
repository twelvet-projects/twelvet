package com.twelvet;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.framework.utils.JacksonUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

    private final static Logger log = LoggerFactory.getLogger(UtilsTest.class);

    @Test
    public void util() {
        log.error("params: {}", "${jndi:ldap://127.0.0.1:1389/Log4jTest}");
    }

}
