package com.twelvet.api.gen.domain;

import com.twelvet.api.gen.constant.GenConstants;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serial;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 业务表 gen_table
 */
@Schema(description = "业务表")
public class GenTable extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@Schema(description = "编号")
	private Long tableId;

	/**
	 * 数据源名称
	 */
	@Schema(description = "数据源名称")
	private String dsName;

	/**
	 * 数据源类型
	 */
	@Schema(description = "数据源类型")
	private String dbType;

	/**
	 * 表名称
	 */
	@Schema(description = "表名称")
	@NotBlank(message = "表名称不能为空")
	private String tableName;

	/**
	 * 表描述
	 */
	@Schema(description = "表描述")
	@NotBlank(message = "表描述不能为空")
	private String tableComment;

	/**
	 * 关联父表的表名
	 */
	@Schema(description = "关联父表的表名")
	private String subTableName;

	/**
	 * 本表关联父表的外键名
	 */
	@Schema(description = "本表关联父表的外键名")
	private String subTableFkName;

	/**
	 * 实体类名称(首字母大写)
	 */
	@Schema(description = "实体类名称")
	@NotBlank(message = "实体类名称不能为空")
	private String className;

	/**
	 * 使用的模板分组ID
	 */
	@Schema(description = "使用的模板分组ID")
	private Long tplGroupId;

	/**
	 * 生成包路径
	 */
	@Schema(description = "生成包路径")
	@NotBlank(message = "生成包路径不能为空")
	private String packageName;

	/**
	 * 生成模块名
	 */
	@Schema(description = "生成模块名")
	@NotBlank(message = "生成模块名不能为空")
	private String moduleName;

	/**
	 * 生成业务名
	 */
	@Schema(description = "生成业务名")
	@NotBlank(message = "生成业务名不能为空")
	private String businessName;

	/**
	 * 生成功能名
	 */
	@Schema(description = "生成功能名")
	@NotBlank(message = "生成功能名不能为空")
	private String functionName;

	/**
	 * 生成作者
	 */
	@Schema(description = "生成作者")
	@NotBlank(message = "作者不能为空")
	private String functionAuthor;

	/**
	 * 生成代码方式（0zip压缩包 1自定义路径）
	 */
	@Schema(description = "生成代码方式")
	private String genType;

	/**
	 * 生成路径（不填默认项目路径）
	 */
	@Schema(description = "生成路径")
	private String genPath;

	/**
	 * 主键信息
	 */
	@Schema(description = "主键信息")
	private GenTableColumn pkColumn;

	/**
	 * 子表信息
	 */
	@Schema(description = "子表信息")
	private GenTable subTable;

	/**
	 * 表列信息
	 */
	@Schema(description = "表列信息")
	@Valid
	private List<GenTableColumn> columns;

	/**
	 * 其它生成选项
	 */
	@Schema(description = "其它生成选项")
	private String options;

	/**
	 * 树编码字段
	 */
	@Schema(description = "树编码字段")
	private String treeCode;

	/**
	 * 树父编码字段
	 */
	@Schema(description = "树父编码字段")
	private String treeParentCode;

	/**
	 * 树名称字段
	 */
	@Schema(description = "树名称字段")
	private String treeName;

	/**
	 * 上级菜单ID字段
	 */
	@Schema(description = "上级菜单ID字段")
	private String parentMenuId;

	/**
	 * 上级菜单名称字段
	 */
	@Schema(description = "上级菜单名称字段")
	private String parentMenuName;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getSubTableName() {
		return subTableName;
	}

	public void setSubTableName(String subTableName) {
		this.subTableName = subTableName;
	}

	public String getSubTableFkName() {
		return subTableFkName;
	}

	public void setSubTableFkName(String subTableFkName) {
		this.subTableFkName = subTableFkName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getTplGroupId() {
		return tplGroupId;
	}

	public void setTplGroupId(Long tplGroupId) {
		this.tplGroupId = tplGroupId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionAuthor() {
		return functionAuthor;
	}

	public void setFunctionAuthor(String functionAuthor) {
		this.functionAuthor = functionAuthor;
	}

	public String getGenType() {
		return genType;
	}

	public void setGenType(String genType) {
		this.genType = genType;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public GenTableColumn getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(GenTableColumn pkColumn) {
		this.pkColumn = pkColumn;
	}

	public GenTable getSubTable() {
		return subTable;
	}

	public void setSubTable(GenTable subTable) {
		this.subTable = subTable;
	}

	public List<GenTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<GenTableColumn> columns) {
		this.columns = columns;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getTreeParentCode() {
		return treeParentCode;
	}

	public void setTreeParentCode(String treeParentCode) {
		this.treeParentCode = treeParentCode;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public boolean isSub() {
		return isSub(this.tplGroupId);
	}

	public static boolean isSub(Long tplGroupId) {
		return GenConstants.TPL_SUB.equals(tplGroupId);
	}

	public boolean isTree() {
		return isTree(this.tplGroupId);
	}

	public static boolean isTree(Long tplGroupId) {
		return GenConstants.TPL_TREE.equals(tplGroupId);
	}

	public boolean isCrud() {
		return isCrud(this.tplGroupId);
	}

	public static boolean isCrud(Long tplGroupId) {
		return GenConstants.TPL_CRUD.equals(tplGroupId);
	}

	public boolean isSuperColumn(String javaField) {
		return isSuperColumn(this.tplGroupId, javaField);
	}

	public static boolean isSuperColumn(Long tplGroupId, String javaField) {
		if (isTree(tplGroupId)) {
			return StringUtils.equalsAnyIgnoreCase(javaField,
					ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
		}
		return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
	}

}
