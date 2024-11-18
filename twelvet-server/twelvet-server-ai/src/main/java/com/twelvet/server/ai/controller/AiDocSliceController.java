package com.twelvet.server.ai.controller;

import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.ai.service.IAiDocSliceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI知识库文档分片Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Tag(description = "AiDocSliceController", name = "AI知识库文档分片")
@RestController
@RequestMapping("/slice")
public class AiDocSliceController extends TWTController {

	@Autowired
	private IAiDocSliceService aiDocSliceService;

	/**
	 * 查询AI知识库文档分片分页
	 */
	@Operation(summary = "查询AI知识库文档分片分页")
	@PreAuthorize("@role.hasPermi('ai:slice:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiDocSlice>> pageQuery(AiDocSlice aiDocSlice) {
		PageUtils.startPage();
		List<AiDocSlice> list = aiDocSliceService.selectAiDocSliceList(aiDocSlice);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 修改AI知识库文档分片
	 */
	@Operation(summary = "修改AI知识库文档分片")
	@PreAuthorize("@role.hasPermi('system:slice:edit')")
	@Log(service = "AI知识库文档分片", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody AiDocSlice aiDocSlice) {
		return json(aiDocSliceService.updateAiDocSlice(aiDocSlice));
	}

	/**
	 * 删除AI知识库文档分片
	 */
	@Operation(summary = "删除AI知识库文档分片")
	@PreAuthorize("@role.hasPermi('ai:slice:remove')")
	@Log(service = "AI知识库文档分片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sliceIds}")
	public JsonResult<String> remove(@PathVariable Long[] sliceIds) {
		return json(aiDocSliceService.deleteAiDocSliceBySliceIds(sliceIds));
	}

}
