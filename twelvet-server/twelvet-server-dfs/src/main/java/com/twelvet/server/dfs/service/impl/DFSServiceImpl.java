package com.twelvet.server.dfs.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.utils.file.FileUtils;
import com.twelvet.server.dfs.config.QiNiuConfig;
import com.twelvet.server.dfs.mapper.DFSMapper;
import com.twelvet.server.dfs.service.IDFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: FastDFS文件存储
 */
@Primary
@Service
/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 文件上传实现类
 */
public class DFSServiceImpl implements IDFSService {

	private static final Logger logger = LoggerFactory.getLogger(DFSServiceImpl.class);

	@Autowired
	private QiNiuConfig qiNiuConfig;

	@Autowired
	private DFSMapper dfsMapper;

	private UploadManager uploadManager;

	private String token;

	private BucketManager bucketManager;

	@PostConstruct
	public void init() {
		// 若不指定 Region 或 Region.autoRegion() ，则会使用 自动判断 区域，使用相应域名处理。
		// 如果可以明确 区域 的话，最好指定固定区域，这样可以少一步网络请求，少一步出错的可能。
		uploadManager = new UploadManager(new Configuration(Region.autoRegion()));

		Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());

		bucketManager = new BucketManager(auth, new Configuration(Region.autoRegion()));

		token = auth.uploadToken(qiNiuConfig.getBucketName());
	}

	/**
	 * FastDfs多文件文件上传接口
	 * @param files 上传的文件
	 * @return 访问地址
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<SysDfs> uploadFiles(MultipartFile[] files) {

		List<SysDfs> fileDfs = new ArrayList<>();

		try {
			for (MultipartFile file : files) {
				String originalFilename = file.getOriginalFilename();

				String key = FileUtils.defaultUploadPath(originalFilename);

				InputStream inputStream = file.getInputStream();

				uploadManager.put(inputStream, key, token, null, null);

				SysDfs sysDfs = new SysDfs();

				long size = file.getSize();

				sysDfs.setSize(size);
				sysDfs.setPath("/" + key);
				sysDfs.setType(FileUtils.getSuffix(originalFilename));
				sysDfs.setFileName(FileUtils.getName(originalFilename));
				sysDfs.setOriginalFileName(originalFilename);
				fileDfs.add(sysDfs);
			}
			dfsMapper.batchSysDfs(fileDfs);

			return fileDfs;

		}
		catch (Exception e) {
			logger.error("文件上传异常", e);
			throw new TWTException("文件上传异常");
		}
	}

	/**
	 * FastDfs文件上传接口
	 * @param file 上传的文件
	 * @return 访问地址
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SysDfs uploadFile(MultipartFile file) {

		try {
			String originalFilename = file.getOriginalFilename();

			String key = FileUtils.defaultUploadPath(originalFilename);

			InputStream inputStream = file.getInputStream();

			uploadManager.put(inputStream, key, token, null, null);

			SysDfs sysDfs = new SysDfs();

			long size = file.getSize();

			sysDfs.setSize(size);
			sysDfs.setPath("/" + key);
			sysDfs.setType(FileUtils.getSuffix(originalFilename));
			sysDfs.setFileName(FileUtils.getName(originalFilename));
			sysDfs.setOriginalFileName(originalFilename);

			dfsMapper.insertSysDfs(sysDfs);

			return sysDfs;
		}
		catch (Exception e) {
			logger.error("文件上传异常", e);
			throw new TWTException("文件上传异常");
		}
	}

	/**
	 * 删除文件
	 * @param fileIds 文件地址
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteFile(Long[] fileIds) {
		try {
			List<SysDfs> sysDfsList = dfsMapper.selectDfsListByFileIds(fileIds);
			for (SysDfs sysDfs : sysDfsList) {
				String key = sysDfs.getPath();
				bucketManager.delete(qiNiuConfig.getBucketName(), key.substring(1));
			}
			// 删除数据库信息
			dfsMapper.deleteSysDfsByFileIds(fileIds);
		}
		catch (QiniuException e) {
			logger.error("删除文件出错", e);
			throw new TWTException("删除文件出错");
		}
	}

	/**
	 * 分页查询
	 * @param sysDfs SysDfs
	 * @return List<SysDfs>
	 */
	@Override
	public List<SysDfs> selectSysDfsList(SysDfs sysDfs) {
		return dfsMapper.selectDfsList(sysDfs);
	}

}
