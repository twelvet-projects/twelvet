package com.twelvet.server.mq;

import com.twelvet.MqApplication;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.server.mq.service.SysLoginLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author twelvet
 * <p>
 * MQ Test
 */
@SpringBootTest(classes = MqApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMq {

    @Autowired
    private SysLoginLogService sysLoginLogService;


    @Test
    public void test() {
        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        sysLoginLogService.sendSysLoginLog(sysLoginInfo);
    }

}
