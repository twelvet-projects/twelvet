package com.twelvet.api.mq.domain;

import com.twelvet.framework.core.application.domain.BaseEntity;

import java.util.List;

/**
 * @author twelvet
 * <p>
 * 邮件MQ实体
 */
public class MailMq extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发送至邮件
     */
    private String toMail;

    /**
     * 标题
     */
    private String subject;

    /**
     * 邮件信息
     */
    private String text;

    /**
     * 是否为html
     */
    private boolean isHtml;

    /**
     * 抄送人
     */
    private List<String> bcc;

    /**
     * 隐秘抄送人
     */
    private List<String> cc;

    

}
