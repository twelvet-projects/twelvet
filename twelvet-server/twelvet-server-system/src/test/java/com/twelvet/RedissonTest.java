package com.twelvet;

import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    public void cloudReentrantLockTest() {



    }

}
