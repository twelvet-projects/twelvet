package com.twelvet;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author twelvet
 * <p>
 * 测试分布式可重入锁
 */
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    public void cloudReentrantLockTest() {


    }

}
