package com.twelvet.server.gen.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.druid.DruidConfig;
import com.twelvet.api.gen.domain.GenDatasourceConf;
import com.twelvet.framework.datasource.enums.DsConfTypeEnum;
import com.twelvet.framework.datasource.enums.DsJdbcUrlEnum;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.gen.mapper.GenDatasourceConfMapper;
import com.twelvet.server.gen.service.IGenDatasourceConfService;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据源Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Service
public class GenDatasourceConfServiceImpl implements IGenDatasourceConfService {

	private final static Logger log = LoggerFactory.getLogger(GenDatasourceConfServiceImpl.class);

	@Autowired
	private GenDatasourceConfMapper genDatasourceConfMapper;

	@Autowired
	private StringEncryptor stringEncryptor;

	@Autowired
	private DefaultDataSourceCreator defaultDataSourceCreator;

	/**
	 * 查询数据源
	 * @param id 数据源主键
	 * @return 数据源
	 */
	@Override
	public GenDatasourceConf selectGenDatasourceConfById(Long id) {
		return genDatasourceConfMapper.selectGenDatasourceConfById(id);
	}

	/**
	 * 查询数据源列表
	 * @param genDatasourceConf 数据源
	 * @return 数据源
	 */
	@Override
	public List<GenDatasourceConf> selectGenDatasourceConfList(GenDatasourceConf genDatasourceConf) {
		return genDatasourceConfMapper.selectGenDatasourceConfList(genDatasourceConf);
	}

	/**
	 * 新增数据源
	 * @param genDatasourceConf 数据源
	 * @return 结果
	 */
	@Override
	public int insertGenDatasourceConf(GenDatasourceConf genDatasourceConf) {
		// 校验配置合法性
		if (!checkDataSource(genDatasourceConf)) {
			return 0;
		}

		// 添加动态数据源
		addDynamicDataSource(genDatasourceConf);

		// 更新数据库配置
		genDatasourceConf.setPassword(stringEncryptor.encrypt(genDatasourceConf.getPassword()));
		genDatasourceConf.setCreateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		genDatasourceConf.setCreateBy(loginUsername);
		genDatasourceConf.setUpdateBy(loginUsername);
		return genDatasourceConfMapper.insertGenDatasourceConf(genDatasourceConf);
	}

	/**
	 * 修改数据源
	 * @param genDatasourceConf 数据源
	 * @return 结果
	 */
	@Override
	public int updateGenDatasourceConf(GenDatasourceConf genDatasourceConf) {
		GenDatasourceConf genDatasourceConfDb = genDatasourceConfMapper
			.selectGenDatasourceConfById(genDatasourceConf.getId());
		if (StringUtils.isEmpty(genDatasourceConf.getPassword())) {
			genDatasourceConf.setPassword(stringEncryptor.decrypt(genDatasourceConfDb.getPassword()));
		}
		if (!checkDataSource(genDatasourceConf)) {
			return 0;
		}
		// 先移除
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		dynamicRoutingDataSource.removeDataSource(genDatasourceConfDb.getName());

		// 再添加
		addDynamicDataSource(genDatasourceConf);

		// 更新数据库配置
		genDatasourceConf.setPassword(stringEncryptor.encrypt(genDatasourceConf.getPassword()));
		genDatasourceConf.setUpdateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		genDatasourceConf.setCreateBy(loginUsername);
		genDatasourceConf.setUpdateBy(loginUsername);
		return genDatasourceConfMapper.updateGenDatasourceConf(genDatasourceConf);
	}

	/**
	 * 校验数据源配置是否有效
	 * @param genDatasourceConf 数据源信息
	 * @return 有效/无效
	 */
	@Override
	public Boolean checkDataSource(GenDatasourceConf genDatasourceConf) {
		String url;
		// JDBC 配置形式
		if (DsConfTypeEnum.JDBC.getType().equals(genDatasourceConf.getConfType())) {
			url = genDatasourceConf.getUrl();
		}
		else if (DsJdbcUrlEnum.MSSQL.getDbName().equals(genDatasourceConf.getDsType())) {
			// 主机形式 sql server 特殊处理
			DsJdbcUrlEnum urlEnum = DsJdbcUrlEnum.get(genDatasourceConf.getDsType());
			url = String.format(urlEnum.getUrl(), genDatasourceConf.getHost(), genDatasourceConf.getPort(),
					genDatasourceConf.getDsName());
		}
		else {
			DsJdbcUrlEnum urlEnum = DsJdbcUrlEnum.get(genDatasourceConf.getDsType());
			url = String.format(urlEnum.getUrl(), genDatasourceConf.getHost(), genDatasourceConf.getPort(),
					genDatasourceConf.getDsName());
		}

		genDatasourceConf.setUrl(url);

		try (Connection connection = DriverManager.getConnection(url, genDatasourceConf.getUsername(),
				genDatasourceConf.getPassword())) {
		}
		catch (SQLException e) {
			log.error("数据源配置 {} , 获取链接失败，请检查账号或数据库驱动正确", genDatasourceConf.getName(), e);
			throw new RuntimeException("数据库配置错误，链接失败");
		}
		return Boolean.TRUE;
	}

	/**
	 * 添加动态数据源
	 * @param genDatasourceConf 数据源信息
	 */
	@Override
	public void addDynamicDataSource(GenDatasourceConf genDatasourceConf) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		dataSourceProperty.setPoolName(genDatasourceConf.getName());
		dataSourceProperty.setUrl(genDatasourceConf.getUrl());
		dataSourceProperty.setUsername(genDatasourceConf.getUsername());
		dataSourceProperty.setPassword(genDatasourceConf.getPassword());

		// 增加 ValidationQuery 参数
		DruidConfig druidConfig = new DruidConfig();
		dataSourceProperty.setDruid(druidConfig);
		DataSource dataSource = defaultDataSourceCreator.createDataSource(dataSourceProperty);

		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
	}

	/**
	 * 批量删除数据源
	 * @param ids 需要删除的数据源主键
	 * @return 结果
	 */
	@Override
	public int deleteGenDatasourceConfByIds(Long[] ids) {
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		for (Long id : ids) {
			String name = genDatasourceConfMapper.selectGenDatasourceConfById(id).getName();
			dynamicRoutingDataSource.removeDataSource(name);
		}
		return genDatasourceConfMapper.deleteGenDatasourceConfByIds(ids);
	}

	/**
	 * 删除数据源信息
	 * @param id 数据源主键
	 * @return 结果
	 */
	@Override
	public int deleteGenDatasourceConfById(Long id) {
		DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder.getBean(DynamicRoutingDataSource.class);
		String name = genDatasourceConfMapper.selectGenDatasourceConfById(id).getName();
		dynamicRoutingDataSource.removeDataSource(name);
		return genDatasourceConfMapper.deleteGenDatasourceConfById(id);
	}

}
