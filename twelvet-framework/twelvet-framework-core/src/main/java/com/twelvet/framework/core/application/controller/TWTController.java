package com.twelvet.framework.core.application.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.web.page.PageDomain;
import com.twelvet.framework.core.web.page.TableDataInfo;
import com.twelvet.framework.core.web.page.TableSupport;
import com.twelvet.framework.utils.TWTUtils;
import com.twelvet.framework.utils.sql.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 基础控制器
 */
public class TWTController {

    protected final Logger logger = LoggerFactory.getLogger(TWTController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                //setValue(DateUtil.parseDate(text));
            }
        });
    }

    /**
     * 注入分页信息
     */
    protected void startPage() {
        // 清除分页bug
        PageHelper.clearPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getCurrent();
        Integer pageSize = pageDomain.getPageSize();
        if (TWTUtils.isNotEmpty(page) && TWTUtils.isNotEmpty(pageSize)) {
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(page, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     *
     * @param list 数据列表
     * @return 适应Json
     */
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRecords(list);
        PageInfo pageInfo = new PageInfo<>(list);
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult json(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
