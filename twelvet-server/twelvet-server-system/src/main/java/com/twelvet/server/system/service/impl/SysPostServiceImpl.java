package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysPost;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.system.mapper.SysPostMapper;
import com.twelvet.server.system.mapper.SysUserPostMapper;
import com.twelvet.server.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志 服务层处理
 */
@Service
public class SysPostServiceImpl implements ISysPostService {

	@Autowired
	private SysPostMapper postMapper;

	@Autowired
	private SysUserPostMapper userPostMapper;

	/**
	 * 新增保存岗位信息
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public int insertPost(SysPost post) {
		return postMapper.insertPost(post);
	}

	/**
	 * 批量删除岗位信息
	 * @param postIds 需要删除的岗位ID
	 * @return 结果
	 */
	@Override
	public int deletePostByIds(Long[] postIds) {
		for (Long postId : postIds) {
			SysPost post = selectPostById(postId);
			if (countUserPostById(postId) > 0) {
				throw new TWTException(String.format("%1$s已分配,不能删除", post.getPostName()));
			}
		}
		return postMapper.deletePostByIds(postIds);
	}

	/**
	 * 修改保存岗位信息
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public int updatePost(SysPost post) {
		return postMapper.updatePost(post);
	}

	/**
	 * 查询岗位信息集合
	 * @param post 岗位信息
	 * @return 岗位信息集合
	 */
	@Override
	public List<SysPost> selectPostList(SysPost post) {
		return postMapper.selectPostList(post);
	}

	/**
	 * 根据用户ID获取岗位选择框列表
	 * @param userId 用户ID
	 * @return 选中岗位ID列表
	 */
	@Override
	public List<Long> selectPostListByUserId(Long userId) {
		return postMapper.selectPostListByUserId(userId);
	}

	/**
	 * 查询所有岗位
	 * @return 岗位列表
	 */
	@Override
	public List<SysPost> selectPostAll() {
		return postMapper.selectPostAll();
	}

	/**
	 * 通过岗位ID查询岗位信息
	 * @param postId 岗位ID
	 * @return 角色对象信息
	 */
	@Override
	public SysPost selectPostById(Long postId) {
		return postMapper.selectPostById(postId);
	}

	/**
	 * 校验岗位名称是否唯一
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public String checkPostNameUnique(SysPost post) {
		Long postId = Objects.isNull(post.getPostId()) ? -1L : post.getPostId();
		SysPost info = postMapper.checkPostNameUnique(post.getPostName());
		if (Objects.nonNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验岗位编码是否唯一
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public String checkPostCodeUnique(SysPost post) {
		Long postId = Objects.isNull(post.getPostId()) ? -1L : post.getPostId();
		SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
		if (Objects.nonNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 通过岗位ID查询岗位使用数量
	 * @param postId 岗位ID
	 * @return 结果
	 */
	@Override
	public int countUserPostById(Long postId) {
		return userPostMapper.countUserPostById(postId);
	}

}
