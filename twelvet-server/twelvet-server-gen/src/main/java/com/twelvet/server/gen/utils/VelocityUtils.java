package com.twelvet.server.gen.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.twelvet.api.gen.constant.GenConstants;
import com.twelvet.api.gen.domain.GenTable;
import com.twelvet.api.gen.domain.GenTableColumn;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 模板工具类
 */
public class VelocityUtils {

	/**
	 * 项目空间路径
	 */
	private static final String PROJECT_PATH = "main/java";

	/**
	 * mybatis空间路径
	 */
	private static final String MYBATIS_PATH = "main/resources/mapper";

	/**
	 * 默认上级菜单，系统工具
	 */
	private static final String DEFAULT_PARENT_MENU_ID = "3";

	/**
	 * 默认配置信息
	 */
	private static final String CONFIG_PATH = "template/config.json";

	public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
		String options = genTable.getOptions();
		Map<String, String> paramsObj = JacksonUtils.readValue(options, Map.class);
		String parentMenuId = getParentMenuId(paramsObj);
		context.put("parentMenuId", parentMenuId);
	}

	public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
		String options = genTable.getOptions();
		Map<String, String> paramsObj = JacksonUtils.readValue(options, Map.class);
		String treeCode = getTreecode(paramsObj);
		String treeParentCode = getTreeParentCode(paramsObj);
		String treeName = getTreeName(paramsObj);

		context.put("treeCode", treeCode);
		context.put("treeParentCode", treeParentCode);
		context.put("treeName", treeName);
		context.put("expandColumn", getExpandColumn(genTable));
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			context.put("tree_parent_code", paramsObj.get(GenConstants.TREE_PARENT_CODE));
		}
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			context.put("tree_name", paramsObj.get(GenConstants.TREE_NAME));
		}
	}

	public static void setSubVelocityContext(Map<String, Object> dataModel, GenTable genTable) {
		GenTable subTable = genTable.getSubTable();
		String subTableName = genTable.getSubTableName();
		String subTableFkName = genTable.getSubTableFkName();
		String subClassName = genTable.getSubTable().getClassName();
		String subTableFkClassName = StringUtils.convertToCamelCase(subTableFkName);

		dataModel.put("subTable", subTable);
		dataModel.put("subTableName", subTableName);
		dataModel.put("subTableFkName", subTableFkName);
		dataModel.put("subTableFkClassName", subTableFkClassName);
		dataModel.put("subTableFkclassName", StringUtils.uncapitalize(subTableFkClassName));
		dataModel.put("subClassName", subClassName);
		dataModel.put("subclassName", StringUtils.uncapitalize(subClassName));
		dataModel.put("subImportList", getImportList(genTable.getSubTable()));
	}

	/**
	 * 获取包前缀
	 * @param packageName 包名称
	 * @return 包前缀名称
	 */
	public static String getPackagePrefix(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		return StringUtils.substring(packageName, 0, lastIndex);
	}

	/**
	 * 根据列类型获取导入包
	 * @param genTable 业务表对象
	 * @return 返回需要导入的包列表
	 */
	public static HashSet<String> getImportList(GenTable genTable) {
		List<GenTableColumn> columns = genTable.getColumns();
		GenTable subGenTable = genTable.getSubTable();
		HashSet<String> importList = new HashSet<>();
		if (StringUtils.isNotNull(subGenTable)) {
			importList.add("java.util.List");
		}
		for (GenTableColumn column : columns) {
			if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			}
			else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
				importList.add("java.math.BigDecimal");
			}
		}
		return importList;
	}

	/**
	 * 获取权限前缀
	 * @param moduleName 模块名称
	 * @param businessName 业务名称
	 * @return 返回权限前缀
	 */
	public static String getPermissionPrefix(String moduleName, String businessName) {
		return StringUtils.format("{}:{}", moduleName, businessName);
	}

	/**
	 * 获取上级菜单ID字段
	 * @param paramsObj 生成其他选项
	 * @return 上级菜单ID字段
	 */
	public static String getParentMenuId(Map<String, String> paramsObj) {
		if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)) {
			return paramsObj.get(GenConstants.PARENT_MENU_ID);
		}
		return DEFAULT_PARENT_MENU_ID;
	}

	/**
	 * 获取树编码
	 * @param paramsObj 生成其他选项
	 * @return 树编码
	 */
	public static String getTreecode(Map<String, String> paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
			return StringUtils.toCamelCase(paramsObj.get(GenConstants.TREE_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取树父编码
	 * @param paramsObj 生成其他选项
	 * @return 树父编码
	 */
	public static String getTreeParentCode(Map<String, String> paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			return StringUtils.toCamelCase(paramsObj.get(GenConstants.TREE_PARENT_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取树名称
	 * @param paramsObj 生成其他选项
	 * @return 树名称
	 */
	public static String getTreeName(Map<String, String> paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			return StringUtils.toCamelCase(paramsObj.get(GenConstants.TREE_NAME));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取需要在哪一列上面显示展开按钮
	 * @param genTable 业务表对象
	 * @return 展开按钮列序号
	 */
	public static int getExpandColumn(GenTable genTable) {
		String options = genTable.getOptions();
		Map<String, String> paramsObj = JacksonUtils.readValue(options, Map.class);
		String treeName = paramsObj.get(GenConstants.TREE_NAME);
		int num = 0;
		for (GenTableColumn column : genTable.getColumns()) {
			if (column.isList()) {
				num++;
				String columnName = column.getColumnName();
				if (columnName.equals(treeName)) {
					break;
				}
			}
		}
		return num;
	}

	/**
	 * 渲染文本
	 * @param str 模板
	 * @param dataModel 模板数据
	 * @return 渲染后的模板
	 */
	public static String renderStr(String str, Map<String, Object> dataModel) {
		// 设置velocity资源加载器
		Velocity.init();
		StringWriter stringWriter = new StringWriter();
		VelocityContext context = new VelocityContext(dataModel);
		// 函数库
		Velocity.evaluate(context, stringWriter, "renderStr", str);
		return stringWriter.toString();
	}

	/**
	 * 获取生成配置信息
	 * @return 配置信息
	 */
	public static Map<String, Map<String, Object>> getGeneratorConfig() {
		ClassPathResource classPathResource = new ClassPathResource(CONFIG_PATH);
		return JacksonUtils.readValue(IoUtil.readUtf8(classPathResource.getStream()), Map.class);
	}

}
