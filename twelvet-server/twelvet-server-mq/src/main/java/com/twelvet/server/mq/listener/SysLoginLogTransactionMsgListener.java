package com.twelvet.server.mq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统登录事务监听
 */
@RocketMQTransactionListener(txProducerGroup = "tx-group")
public class SysLoginLogTransactionMsgListener implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事务
     * 如果本地事务返回UNKNOWN，会进行事务补偿，自动执行下面的checkLocalTransaction方法
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("执行本地事务=====");
        //模拟提交事务
        //return RocketMQLocalTransactionState.COMMIT;
        //模拟回滚事务
        //return RocketMQLocalTransactionState.ROLLBACK;
        //让去check本地事务状态 进行事务补偿
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 检测本地事务状态
     * 事务补偿过程
     * 当消息服务器没有收到消息生产者的事务提交或者回滚确认时，会主动要求消息生产者进行确认，
     * 消息生产者便会去检测本地事务状态，该过程称为事务补偿过程
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println("执行事务补偿======");
        //事务补偿提交
        return RocketMQLocalTransactionState.COMMIT;
        //事务补偿回滚
        //return RocketMQLocalTransactionState.ROLLBACK;
        //如果事务补偿过程还是UNKNOWN 就会一直进行事务补偿，60s一次
        //return RocketMQLocalTransactionState.UNKNOWN;
    }

}
