package com.twelvet;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.framework.redis.service.annotation.TwSynchronized;
import com.twelvet.server.system.service.ISysClientDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * <p>
 * 测试分布式可重入锁
 */
@SpringBootTest(classes = SystemApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ISysClientDetailsService iSysClientDetailsService;

    @Test
    public void cloudReentrantLockTest() {
        SysClientDetails sysClientDetails = iSysClientDetailsService.selectSysClientDetailsById("twelvet");
        System.out.println(sysClientDetails);
    }

    @Test
    public void demo() {
        System.out.println("demo：执行");
        SysClientDetails sysClientDetails = iSysClientDetailsService.selectSysClientDetailsById("twelvet");
        System.out.println(sysClientDetails);
    }

}
