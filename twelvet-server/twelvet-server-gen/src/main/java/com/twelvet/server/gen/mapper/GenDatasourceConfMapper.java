package com.twelvet.server.gen.mapper;

import com.twelvet.api.gen.domain.GenDatasourceConf;

import java.util.List;

/**
 * 数据源Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public interface GenDatasourceConfMapper {

	/**
	 * 查询数据源
	 * @param id 数据源主键
	 * @return 数据源
	 */
	public GenDatasourceConf selectGenDatasourceConfById(Long id);

	/**
	 * 根据别名查询数据源
	 * @param dsName 数据源别名
	 * @return 数据源
	 */
	public GenDatasourceConf selectGenDatasourceConfByName(String dsName);

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
	 * 删除数据源
	 * @param id 数据源主键
	 * @return 结果
	 */
	public int deleteGenDatasourceConfById(Long id);

	/**
	 * 批量删除数据源
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteGenDatasourceConfByIds(Long[] ids);

}
