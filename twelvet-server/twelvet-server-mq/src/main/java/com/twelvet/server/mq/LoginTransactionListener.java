/*
package com.twelvet.server.mq;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.framework.core.exception.TWTException;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

*/
/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 、登录日志事物空置
 *//*

@RocketMQTransactionListener(txProducerGroup = "loginLog-out-0")
public class LoginTransactionListener implements RocketMQLocalTransactionListener {

    */
/**
     * 检查是否可以提交本地事物
     *
     * @param message
     * @param o
     * @return
     *//*

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        SysLoginInfo payload = (SysLoginInfo) message.getPayload();
        Long infoId = payload.getInfoId();
        if (!infoId.equals(1L)) {
            throw new TWTException("");
        }
        // 回滚事物
        // 提交事物
        return RocketMQLocalTransactionState.COMMIT;
    }

    */
/**
     * 回查事物
     *
     * @param message
     * @return
     *//*

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        SysLoginInfo payload = (SysLoginInfo) message.getPayload();
        Long infoId = payload.getInfoId();
        if (!infoId.equals(1L)) {
            // 回滚事物
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        // 提交事物
        return RocketMQLocalTransactionState.COMMIT;
    }

}
*/
