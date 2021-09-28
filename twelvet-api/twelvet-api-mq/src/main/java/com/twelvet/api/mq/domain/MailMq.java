package com.twelvet.api.mq.domain;

import com.twelvet.framework.core.application.domain.BaseEntity;

import java.util.Date;
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

    /**
     * 邮件到达时间
     */
    private Date sendDate;

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "MailMq{" +
                "toMail='" + toMail + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", isHtml=" + isHtml +
                ", bcc=" + bcc +
                ", cc=" + cc +
                ", sendDate=" + sendDate +
                '}';
    }
}
