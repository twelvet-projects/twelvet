package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysPost;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 岗位信息操作处理
 */
@RestController
@RequestMapping("/post")
public class SysPostController extends TWTController {

	@Autowired
	private ISysPostService iSysPostService;

	/**
	 * 新增岗位
	 * @param sysPost SysPost
	 * @return AjaxResult
	 */
	@Log(service = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping
	@PreAuthorize("@role.hasPermi('system:post:insert')")
	public AjaxResult insert(@Validated @RequestBody SysPost sysPost) {
		if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostNameUnique(sysPost))) {
			return AjaxResult.error("新增岗位'" + sysPost.getPostName() + "'失败，岗位名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostCodeUnique(sysPost))) {
			return AjaxResult.error("新增岗位'" + sysPost.getPostName() + "'失败，岗位编码已存在");
		}
		sysPost.setCreateBy(SecurityUtils.getUsername());
		return json(iSysPostService.insertPost(sysPost));
	}

	/**
	 * 删除岗位
	 * @param postIds 唯一ID数组
	 * @return AjaxResult
	 */
	@Log(service = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
	@PreAuthorize("@role.hasPermi('system:post:remove')")
	public AjaxResult remove(@PathVariable Long[] postIds) {
		return json(iSysPostService.deletePostByIds(postIds));
	}

	/**
	 * 修改岗位
	 * @param sysPost SysPost
	 * @return AjaxResult
	 */
	@Log(service = "岗位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@PreAuthorize("@role.hasPermi('system:post:update')")
	public AjaxResult update(@Validated @RequestBody SysPost sysPost) {
		if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostNameUnique(sysPost))) {
			return AjaxResult.error("修改岗位'" + sysPost.getPostName() + "'失败，岗位名称已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(iSysPostService.checkPostCodeUnique(sysPost))) {
			return AjaxResult.error("修改岗位'" + sysPost.getPostName() + "'失败，岗位编码已存在");
		}
		sysPost.setUpdateBy(SecurityUtils.getUsername());
		return json(iSysPostService.updatePost(sysPost));
	}

	/**
	 * 获取岗位列表
	 * @param post SysPost
	 * @return AjaxResult
	 */
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('system:post:list')")
	public AjaxResult pageQuery(SysPost post) {
		PageUtils.startPage();
		List<SysPost> list = iSysPostService.selectPostList(post);
		return AjaxResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 根据岗位编号获取详细信息
	 * @param postId 唯一ID
	 * @return AjaxResult
	 */
	@GetMapping("/{postId}")
	@PreAuthorize("@role.hasPermi('system:post:query')")
	public AjaxResult getByPostId(@PathVariable Long postId) {
		return AjaxResult.success(iSysPostService.selectPostById(postId));
	}

	/**
	 * 获取岗位选择框列表
	 * @return AjaxResult
	 */
	@GetMapping("/optionSelect")
	public AjaxResult optionSelect() {
		List<SysPost> posts = iSysPostService.selectPostAll();
		return AjaxResult.success(posts);
	}

	/**
	 * 数据导出
	 * @param response HttpServletResponse
	 * @param sysPost SysPost
	 */
	@Log(service = "岗位管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@PreAuthorize("@role.hasPermi('system:post:export')")
	public void export(HttpServletResponse response, @RequestBody SysPost sysPost) {
		List<SysPost> list = iSysPostService.selectPostList(sysPost);
		ExcelUtils<SysPost> excelUtils = new ExcelUtils<>(SysPost.class);
		excelUtils.exportExcel(response, list, "岗位数据");
	}

}
