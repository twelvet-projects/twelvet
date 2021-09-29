package com.twelvet.server.mq.controller.api;

import com.twelvet.api.mq.domain.MaillMq;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.server.mq.service.MqMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * <p>
 * 发送邮件MQ API
 */
@RestController
@RequestMapping("/api/mail")
public class MqMailApi {

    @Autowired
    private MqMailService mqMailService;

    /**
     * 发送通用验证码
     * @return R<Boolean>
     */
    @AuthIgnore
    @PostMapping
    public R<Boolean> sendCode(@RequestBody MaillMq maillMq) {
        mqMailService.send(maillMq);
        return R.ok(true);
    }

}
