package com.twelvet.server.es.controller;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.server.es.service.EsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * <p>
 * 公共搜素控制器
 */
@RestController
@RequestMapping("/common")
public class EsCommonController {

    @Autowired
    private EsCommonService esCommonService;

    /**
     * 公共搜素
     *
     * @return AjaxResult
     */
    @GetMapping
    public AjaxResult search() {
        return AjaxResult.success(esCommonService.search());
    }

}
