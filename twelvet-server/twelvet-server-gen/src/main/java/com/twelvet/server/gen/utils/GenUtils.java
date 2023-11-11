package com.twelvet.server.gen.utils;

import cn.hutool.core.util.StrUtil;
import com.twelvet.api.gen.constant.GenConstants;
import com.twelvet.api.gen.domain.GenDatasourceConf;
import com.twelvet.api.gen.domain.GenFieldType;
import com.twelvet.api.gen.domain.GenTable;
import com.twelvet.api.gen.domain.GenTableColumn;
import com.twelvet.framework.datasource.enums.DsJdbcUrlEnum;
import com.twelvet.framework.datasource.support.DataSourceConstants;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.gen.config.GenConfig;
import com.twelvet.server.gen.mapper.GenDatasourceConfMapper;
import com.twelvet.server.gen.mapper.GenFieldTypeMapper;
import com.twelvet.server.gen.mapper.GenTableMapper;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 代码生成器 工具类
 */
public class GenUtils {

	/**
	 * 初始化表信息
	 */
	public static void initTable(GenTable genTable, String operName) {
		genTable.setClassName(convertClassName(genTable.getTableName()));
		genTable.setPackageName(GenConfig.getPackageName());
		genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
		genTable.setBusinessName(getBusinessName(genTable.getTableName()));
		genTable.setFunctionName(replaceText(genTable.getTableComment()));
		genTable.setFunctionAuthor(GenConfig.getAuthor());
		genTable.setCreateBy(operName);
	}

	/**
	 * 初始化列属性字段
	 */
	public static void initColumnField(List<GenTableColumn> genTableColumns, GenTable table) {
		ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
		GenFieldTypeMapper genFieldTypeMapper = applicationContext.getBean(GenFieldTypeMapper.class);
		List<GenFieldType> genFieldTypes = genFieldTypeMapper.selectGenFieldTypeListAll();
		Map<String, GenFieldType> fieldTypeMap = new LinkedHashMap<>(genFieldTypes.size());
		genFieldTypes.forEach(
				fieldTypeMapping -> fieldTypeMap.put(fieldTypeMapping.getColumnType().toLowerCase(), fieldTypeMapping));

		for (GenTableColumn column : genTableColumns) {
			String dataType = getDbType(column.getColumnType());
			String columnName = column.getColumnName();
			column.setTableId(table.getTableId());
			column.setCreateBy(table.getCreateBy());
			// 设置java字段名
			column.setJavaField(StringUtils.toCamelCase(columnName));

			// 获取字段对应的类型
			GenFieldType fieldTypeMapping = fieldTypeMap.getOrDefault(dataType, null);
			if (fieldTypeMapping == null) {
				// 设置默认类型
				column.setJavaType(GenConstants.TYPE_STRING);
			}
			else {
				column.setJavaType(fieldTypeMapping.getAttrType());
			}

			if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType)
					|| arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
				// 字符串长度超过500设置为文本域
				Integer columnLength = getColumnLength(column.getColumnType());
				String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)
						? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
				column.setHtmlType(htmlType);
			}
			else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
				column.setHtmlType(GenConstants.HTML_DATETIME);
			}
			else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
				column.setHtmlType(GenConstants.HTML_INPUT);
			}

			// 插入字段（默认所有字段都需要插入）
			column.setIsInsert(GenConstants.REQUIRE);

			// 编辑字段
			if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
				column.setIsEdit(GenConstants.REQUIRE);
			}
			// 列表字段
			if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
				column.setIsList(GenConstants.REQUIRE);
			}
			// 查询字段
			if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
				column.setIsQuery(GenConstants.REQUIRE);
			}

			// 查询字段类型
			if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
				column.setQueryType(GenConstants.QUERY_LIKE);
			}
			// 状态字段设置单选框
			if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
				column.setHtmlType(GenConstants.HTML_RADIO);
			}
			// 类型&性别字段设置下拉框
			else if (StringUtils.endsWithIgnoreCase(columnName, "type")
					|| StringUtils.endsWithIgnoreCase(columnName, "sex")) {
				column.setHtmlType(GenConstants.HTML_SELECT);
			}
			// 图片字段设置图片上传控件
			else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
				column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
			}
			// 文件字段设置文件上传控件
			else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
				column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
			}
			// 内容字段设置富文本控件
			else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
				column.setHtmlType(GenConstants.HTML_EDITOR);
			}
		}
	}

	/**
	 * 校验数组是否包含指定值
	 * @param arr 数组
	 * @param targetValue 值
	 * @return 是否包含
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 获取业务名
	 * @param tableName 表名
	 * @return 业务名
	 */
	public static String getBusinessName(String tableName) {
		int lastIndex = tableName.lastIndexOf("_");
		int nameLength = tableName.length();
		return StringUtils.substring(tableName, lastIndex + 1, nameLength);
	}

	/**
	 * 表名转换成Java类名
	 * @param tableName 表名称
	 * @return 类名
	 */
	public static String convertClassName(String tableName) {
		boolean autoRemovePre = GenConfig.getAutoRemovePre();
		String tablePrefix = GenConfig.getTablePrefix();
		if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
			String[] searchList = StringUtils.split(tablePrefix, ",");
			tableName = replaceFirst(tableName, searchList);
		}
		return StringUtils.convertToCamelCase(tableName);
	}

	/**
	 * 批量替换前缀
	 * @param replacementm 替换值
	 * @param searchList 替换列表
	 * @return String
	 */
	public static String replaceFirst(String replacementm, String[] searchList) {
		String text = replacementm;
		for (String searchString : searchList) {
			if (replacementm.startsWith(searchString)) {
				text = replacementm.replaceFirst(searchString, "");
				break;
			}
		}
		return text;
	}

	/**
	 * 关键字替换
	 * @param text 需要被替换的名字
	 * @return 替换后的名字
	 */
	public static String replaceText(String text) {
		return RegExUtils.replaceAll(text, "(?:表|twelvet)", "");
	}

	/**
	 * 获取数据库类型字段
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static String getDbType(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			return StringUtils.substringBefore(columnType, "(");
		}
		else {
			return columnType;
		}
	}

	/**
	 * 获取字段长度
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static Integer getColumnLength(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			String length = StringUtils.substringBetween(columnType, "(", ")");
			return Integer.valueOf(length);
		}
		else {
			return 0;
		}
	}

	/**
	 * 获取功能名 sys_a_b sysAb
	 * @param tableName 表名
	 * @return 功能名
	 */
	public static String getFunctionName(String tableName) {
		return StrUtil.toCamelCase(tableName);
	}

	/**
	 * 获取模块名称
	 * @param packageName 包名
	 * @return 功能名
	 */
	public static String getModuleName(String packageName) {
		return StrUtil.subAfter(packageName, ".", true);
	}

	/**
	 * 获取数据源对应方言的mapper
	 * @param dsName 数据源名称
	 * @return GeneratorMapper
	 */
	public static GenTableMapper getMapper(String dsName) {
		String dbConfType;
		// 获取目标数据源数据库类型
		if (DataSourceConstants.DS_MASTER.equals(dsName)) {
			dbConfType = DsJdbcUrlEnum.MYSQL.getDbName();
		}
		else {
			GenDatasourceConfMapper datasourceConfMapper = SpringContextHolder.getBean(GenDatasourceConfMapper.class);
			GenDatasourceConf datasourceConf = datasourceConfMapper.selectGenDatasourceConfByName(dsName);

			// 默认MYSQL 数据源
			if (datasourceConf == null) {
				dbConfType = DsJdbcUrlEnum.MYSQL.getDbName();
			}
			else {
				dbConfType = datasourceConf.getDsType();
			}
		}

		// 获取全部数据实现
		ApplicationContext context = SpringContextHolder.getApplicationContext();
		Map<String, GenTableMapper> beansOfType = context.getBeansOfType(GenTableMapper.class);

		// 根据数据类型选择mapper
		for (String key : beansOfType.keySet()) {
			if (StrUtil.containsIgnoreCase(key, dbConfType)) {
				return beansOfType.get(key);
			}
		}

		throw new IllegalArgumentException("dsName 不合法: " + dsName);
	}

	/**
	 * 获取数据源对应方言的mapper
	 * @return GeneratorMapper
	 */
	public static GenTableMapper getMapper() {
		return getMapper(DataSourceConstants.DS_MASTER);
	}

}
