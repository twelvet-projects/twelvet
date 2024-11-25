package com.twelvet.framework.core.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: java 8 时间默认序列化
 */
public class JavaTimeModule extends SimpleModule {

	public JavaTimeModule() {
		super(PackageVersion.VERSION);

		// ======================= 时间序列化规则 ===============================
		// yyyy-MM-dd HH:mm:ss
		this.addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
		// yyyy-MM-dd
		this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
		// HH:mm:ss
		this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
		// Instant 类型序列化
		this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
		// Duration 类型序列化
		this.addSerializer(Duration.class, DurationSerializer.INSTANCE);

		// ======================= 时间反序列化规则 ==============================
		// yyyy-MM-dd HH:mm:ss
		this.addDeserializer(LocalDateTime.class,
				new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
		// yyyy-MM-dd
		this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
		// HH:mm:ss
		this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
		// Instant 反序列化
		this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
		// Duration 反序列化
		this.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
	}

}
