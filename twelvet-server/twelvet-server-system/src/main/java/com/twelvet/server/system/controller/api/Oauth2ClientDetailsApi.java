package com.twelvet.server.system.controller.api;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.$;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.system.service.ISysClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 终端配置 信息操作处理
 */
@RestController
@RequestMapping("/client")
public class Oauth2ClientDetailsApi extends TWTController {

	@Autowired
	private ISysClientDetailsService sysClientDetailsService;

	/**
	 * 获取终端配置详细信息
	 * @param clientId 终端ID
	 * @return AjaxResult
	 */
	@GetMapping(value = "/{clientId}")
	public R<SysClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId) {
		return R.ok(sysClientDetailsService.selectSysClientDetailsById(clientId));
	}

}
