package com.twelvet.server.job.task;

import com.twelvet.framework.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 定时任务调度测试
 */
@Component("twtTask")
public class TWTTask {

	public void twtMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
	}

	public void twtParams(String params) {
		System.out.println("执行有参方法：" + params);
	}

	public void twtNoParams() {
		System.out.println("执行无参方法");
	}

}
