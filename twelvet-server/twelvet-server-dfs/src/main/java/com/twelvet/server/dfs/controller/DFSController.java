package com.twelvet.server.dfs.controller;

import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.jdbc.web.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.dfs.service.IDFSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件请求处理
 */
@Api(value = "DFSController", tags = { "文件请求处理" })
@RestController
public class DFSController extends TWTController {

	@Autowired
	private IDFSService sysFileService;

	/**
	 * 多文件上传
	 * @param files MultipartFile[]
	 * @return JsonResult<List<SysDfs>>
	 */
	@ApiOperation(value = "多文件上传")
	@Log(service = "多文件上传", businessType = BusinessType.IMPORT)
	@PostMapping("/batchUpload")
	public JsonResult<List<SysDfs>> batchUpload(MultipartFile[] files) {
		// 上传并返回访问地址
		List<SysDfs> sysDfsList = sysFileService.uploadFiles(files);

		return JsonResult.success(sysDfsList);
	}

	/**
	 * 单文件上传
	 * @param file MultipartFile
	 * @return JsonResult<String>
	 */
	@ApiOperation(value = "单文件上传")
	@PostMapping("/commonUpload")
	@Log(service = "单文件上传", businessType = BusinessType.IMPORT)
	public JsonResult<String> commonUpload(MultipartFile file) {
		// 上传并返回访问地址
		SysDfs sysDfs = sysFileService.uploadFile(file);
		return JsonResult.success("上传成功", sysDfs.getPath());
	}

	/**
	 * 分页查询
	 * @param fileIds 文件地址
	 * @return JsonResult<String>
	 */
	@ApiOperation(value = "查询代码生成列表")
	@PreAuthorize("@role.hasPermi('dfs:dfs:remove')")
	@Log(service = "删除文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
	public JsonResult<String> deleteFile(@PathVariable Long[] fileIds) {
		sysFileService.deleteFile(fileIds);
		return JsonResult.success();
	}

	/**
	 * 分页查询
	 * @param sysDfs SysDfs
	 * @return JsonResult<TableDataInfo>
	 */
	@ApiOperation(value = "分页查询")
	@PreAuthorize("@role.hasPermi('dfs:dfs:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo> pageQuery(SysDfs sysDfs) {
		PageUtils.startPage();
		List<SysDfs> sysDfsList = sysFileService.selectSysDfsList(sysDfs);
		return JsonResult.success(PageUtils.getDataTable(sysDfsList));
	}

}
