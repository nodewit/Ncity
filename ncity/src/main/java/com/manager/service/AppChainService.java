package com.manager.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;
import com.manager.bean.AppParameterEntity;
import com.manager.bean.ChainEntity;
import com.manager.dao.AppChainDao;
import com.manager.dao.AppParamsDao;
import com.manager.utils.Constants;
import com.manager.utils.Page;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

/**
 * 内容数据-服务层
 * 
 * @author 艾克 2018年11月6日 15点11分
 */
@Service
public class AppChainService {

	@Autowired
	private AppChainDao appChainDao;
	@Value("${ipfsPath}")
	private String ipfsPath;
	@Value("${uploadDirPath}")
	private String uploadDirPath;
	@Value("${yuming}")
	private String yuming;
	@Value("${url}")
	private String path;

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryChainByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Page page = appChainDao.queryChainByPage(pageNumber, pageSize, params);
		if (params.get("typeId") != null && params.get("typeId").toString().equals(Constants.PROMISE + "")) {
			List<Map<String, Object>> list = page.getList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> chain = list.get(i);
				Map<String, Object> map = this.queryChainData(chain.get("address").toString());
				if (map.get("files") != null) {
					JSONArray json = JSONArray.parseArray(map.get("files").toString());
					Map<String, Object> result = (Map<String, Object>) json.get(0);
					if (result != null && result.get("ipfsPath") != null) {
						String hashPath = result.get("ipfsPath").toString();
						String type = result.get("type").toString();
						String uid = result.get("uid") == null ? UUID.randomUUID().toString()
								: result.get("uid").toString();
						try {
							IPFS ipfs = new IPFS(ipfsPath);
							ipfs.refs.local();
							// 查询IPFS里面的文件(通过HASH值查询)
							Multihash filePointer = Multihash.fromBase58(hashPath);
							// 通过HASH值查询文件转为byte[]
							byte[] fileContents = ipfs.cat(filePointer);
							File f = new File(uploadDirPath);
							if (!f.isDirectory()) {
								f.mkdir();
							}
							// 通过文件流输出
							InputStream inputStream = new ByteArrayInputStream(fileContents);
							OutputStream stream = new BufferedOutputStream(
									new FileOutputStream(new File(uploadDirPath + uid + "." + type)));
							byte[] buffer = new byte[400];
							int length = 0;
							while ((length = inputStream.read(buffer)) > 0) {
								stream.write(buffer, 0, length);
							}
							stream.flush();
							stream.close();
							chain.put("filePath", yuming + "/" + uid + "." + type);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				//获取点赞数量
				int likeNum = appChainDao.countActivity(null, Constants.ADD_LIKE, Long.parseLong(chain.get("type_id").toString()), Long.parseLong(chain.get("id").toString()), null);
				//获取评论数量
				int commentNum = appChainDao.queryCommentCount(Long.parseLong(chain.get("id").toString()), Long.parseLong(chain.get("type_id").toString()));
				chain.put("likeNum", likeNum);
				chain.put("commentNum", commentNum);
				resultList.add(chain);
			}
			page.setList(resultList);
		}else{
			List<Map<String, Object>> list = page.getList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> chain = list.get(i);
				//获取点赞数量
				int likeNum = appChainDao.countActivity(null, Constants.ADD_LIKE, Long.parseLong(chain.get("type_id").toString()), Long.parseLong(chain.get("id").toString()), null);
				//获取评论数量
				int commentNum = appChainDao.queryCommentCount(Long.parseLong(chain.get("id").toString()), Long.parseLong(chain.get("type_id").toString()));
				chain.put("likeNum", likeNum);
				chain.put("commentNum", commentNum);
				resultList.add(chain);
			}
		}
		return page;
	}

	/**
	 * 查询实体
	 * 
	 * @param id
	 *            实体ID
	 * @return
	 */
	public ChainEntity queryChainById(long id) {
		ChainEntity chain = appChainDao.queryChainById(id);
		Map<String, Object> map = this.queryChainData(chain.getAddress());
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (map.get("files") != null) {
			JSONArray json = JSONArray.parseArray(map.get("files").toString());
			for (int j = 0; j < json.size(); j++) {
				Map<String, Object> result = (Map<String, Object>) json.get(j);
				if (result != null && result.get("ipfsPath") != null) {
					String hashPath = result.get("ipfsPath").toString();
					String type = result.get("type").toString();
					String uid = result.get("uid") == null ? UUID.randomUUID().toString()
							: result.get("uid").toString();
					try {
						IPFS ipfs = new IPFS(ipfsPath);
						ipfs.refs.local();
						// 查询IPFS里面的文件(通过HASH值查询)
						Multihash filePointer = Multihash.fromBase58(hashPath);
						// 通过HASH值查询文件转为byte[]
						byte[] fileContents = ipfs.cat(filePointer);
						File f = new File(uploadDirPath);
						if (!f.isDirectory()) {
							f.mkdir();
						}
						// 通过文件流输出
						InputStream inputStream = new ByteArrayInputStream(fileContents);
						OutputStream stream = new BufferedOutputStream(
								new FileOutputStream(new File(uploadDirPath + uid + "." + type)));
						byte[] buffer = new byte[400];
						int length = 0;
						while ((length = inputStream.read(buffer)) > 0) {
							stream.write(buffer, 0, length);
						}
						stream.flush();
						stream.close();
						result.put("filePath", yuming + "/" + uid + "." + type);
						resultList.add(result);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		map.put("files", resultList);
		chain.setData(map);
		return chain;
	}

	/**
	 * 获取区块里的数据
	 * 
	 * @param id
	 *            哈希ID
	 * @return
	 */
	private Map<String, Object> queryChainData(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查询数据
		String chainData = HttpRequest.get(path + "/api/v1/assets?search=" + id).body();
		if (chainData != null && chainData.length() != 0) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) JSONArray.parse(chainData);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = (Map<String, Object>) list.get(0).get("data");
				result.put("description", map.get("description"));
				result.put("files", map.get("files"));
			}
		}
		return result;
	}
	
	
	/**
	 * 灵钻分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryDiamondByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return appChainDao.queryDiamondByPage(pageNumber, pageSize, params);
	}
	
	/**
	 * 灵力分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryIntegralByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return appChainDao.queryIntegralByPage(pageNumber, pageSize, params);
	}
}
