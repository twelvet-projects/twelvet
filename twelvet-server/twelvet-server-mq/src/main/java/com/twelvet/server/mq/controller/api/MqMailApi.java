package com.twelvet.server.mq.controller.api;

import com.twelvet.server.mq.service.MqMailService;
import org.springframework.beans.factory.annotation.Autowired;
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



}
