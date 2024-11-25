package com.twelvet.server.gen.service;

import java.util.List;

import com.twelvet.api.gen.domain.GenDatasourceConf;

/**
 * 数据源Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface IGenDatasourceConfService {

	/**
	 * 查询数据源
	 * @param id 数据源主键
	 * @return 数据源
	 */
	public GenDatasourceConf selectGenDatasourceConfById(Long id);

	/**
	 * 查询数据源列表
	 * @param genDatasourceConf 数据源
	 * @return 数据源集合
	 */
	public List<GenDatasourceConf> selectGenDatasourceConfList(GenDatasourceConf genDatasourceConf);

	/**
	 * 新增数据源
	 * @param genDatasourceConf 数据源
	 * @return 结果
	 */
	public int insertGenDatasourceConf(GenDatasourceConf genDatasourceConf);

	/**
	 * 修改数据源
	 * @param genDatasourceConf 数据源
	 * @return 结果
	 */
	public int updateGenDatasourceConf(GenDatasourceConf genDatasourceConf);

	/**
	 * 校验数据源配置是否有效
	 * @param datasourceConf 数据源信息
	 * @return 有效/无效
	 */
	Boolean checkDataSource(GenDatasourceConf datasourceConf);

	/**
	 * 更新动态数据的数据源列表
	 * @param datasourceConf GenDatasourceConf
	 */
	void addDynamicDataSource(GenDatasourceConf datasourceConf);

	/**
	 * 批量删除数据源
	 * @param ids 需要删除的数据源主键集合
	 * @return 结果
	 */
	public int deleteGenDatasourceConfByIds(Long[] ids);

	/**
	 * 删除数据源信息
	 * @param id 数据源主键
	 * @return 结果
	 */
	public int deleteGenDatasourceConfById(Long id);

}
