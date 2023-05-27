package com.twelvet.server.system.controller;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 缓存监控
 */
@Tag(description = "RedisController", name = "缓存监控")
@RestController
@RequestMapping("/monitor/redis")
public class RedisController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 获取Redis信息
	 */
	@Operation(summary = "获取Redis信息")
	@PreAuthorize("@role.hasPermi('monitor:redis:query')")
	@GetMapping()
	public AjaxResult getInfo() {
		Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
		Properties commandStats = (Properties) redisTemplate
			.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
		Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

		Map<String, Object> result = new HashMap<>(4);
		result.put("info", info);
		result.put("dbSize", dbSize);

		List<Map<String, String>> pieList = new ArrayList<>();
		if (commandStats != null) {
			commandStats.stringPropertyNames().forEach(key -> {
				Map<String, String> data = new HashMap<>(2);
				String property = commandStats.getProperty(key);
				data.put("name", StringUtils.removeStart(key, "cmdstat_"));
				data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
				pieList.add(data);
			});
		}
		result.put("commandStats", pieList);

		// 插入时间
		String time = DateUtils.dateTimeNow("HH:mm:ss");
		result.put("time", time);
		return AjaxResult.success(result);
	}

}
