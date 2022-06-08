package com.twelvet.framework.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 发送Email工具
 */
@Scope("prototype")
@Component
public class EmailBeanUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 发送Helper
	 */
	private MimeMessageHelper messageHelper;

	/**
	 * 信息
	 */
	private MimeMessage mimeMessage;

	/**
	 * 初始化信息
	 */
	@PostConstruct
	public void init() {
		mimeMessage = javaMailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(mimeMessage);
	}

	/**
	 * 设置发送人账号
	 * @param from 发送人信息
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils from(String from) throws MessagingException {
		messageHelper.setFrom(from);
		return this;
	}

	/**
	 * 设置收件人
	 * @param toEmail 收件人
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils toEmail(String toEmail) throws MessagingException {
		messageHelper.setTo(toEmail);
		return this;
	}

	/**
	 * 设置标题
	 * @param subject 主题
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils subject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
		return this;
	}

	/**
	 * 设置文本
	 * @param text 文本
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils text(String text) throws MessagingException {
		messageHelper.setText(text);
		return this;
	}

	/**
	 * 设置文本
	 * @param text 文本
	 * @param isHtml 是否为html格式邮件
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils text(String text, boolean isHtml) throws MessagingException {
		messageHelper.setText(text, isHtml);
		return this;
	}

	/**
	 * 邮件抄送人
	 * @param bcc 邮件抄送人
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils bcc(String bcc) throws MessagingException {
		messageHelper.setBcc(bcc);
		return this;
	}

	/**
	 * 隐秘抄送人
	 * @param cc 隐秘抄送人
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils cc(String cc) throws MessagingException {
		messageHelper.setCc(cc);
		return this;
	}

	/**
	 * 发送时间
	 * @param date 发送时间
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils sentDate(Date date) throws MessagingException {
		messageHelper.setSentDate(date);
		return this;
	}

	/**
	 * 添加附件
	 * @param filName 文件名称
	 * @param file 文件
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils addAttachment(String filName, File file) throws MessagingException {
		messageHelper.addAttachment(filName, file);
		return this;
	}

	/**
	 * 添加图片
	 * @param filName 名称
	 * @param file 文件
	 * @return EmailBeanUtils
	 */
	public EmailBeanUtils addInline(String filName, File file) throws MessagingException {
		messageHelper.addInline(filName, file);
		return this;
	}

	/**
	 * 发送邮件
	 */
	public void sendMail() {
		javaMailSender.send(mimeMessage);
	}

}
