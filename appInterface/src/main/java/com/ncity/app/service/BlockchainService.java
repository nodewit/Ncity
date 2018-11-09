package com.ncity.app.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.AbstractDocument.Content;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.json.strategy.MetaDataDeserializer;
import com.bigchaindb.json.strategy.MetaDataSerializer;
import com.bigchaindb.model.FulFill;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.MetaData;
import com.bigchaindb.model.MetaDatas;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.util.KeyPairUtils;
import com.github.kevinsawicki.http.HttpRequest;
import com.ncity.app.action.BlockchainAction;
import com.ncity.app.dao.ChainDao;
import com.ncity.app.dao.DiamondDao;
import com.ncity.app.dao.UserDao;
import com.ncity.app.entity.ActivityIntegralEntity;
import com.ncity.app.entity.AppParameterEntity;
import com.ncity.app.entity.AppUserEntity;
import com.ncity.app.entity.ChainEntity;
import com.ncity.app.entity.DiamondLogEntity;
import com.ncity.app.uitls.Constants;
import com.ncity.app.uitls.Page;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

/**
 * 区块链服务层
 * 
 * @author 艾克 2018年10月19日 13点59分
 */
@Service
public class BlockchainService {
	private Logger logger = Logger.getLogger(BlockchainService.class);
	@Value("${url}")
	private String path;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	@Autowired
	private DiamondDao diamondDao;
	@Autowired
	private ChainDao chainDao;
	// 初始私钥
	private String initPrivateKeyBase64 = "MC4CAQAwBQYDK2VwBCIEIKQio3E85U6/k4riS0+uoX+ou+M6N0PQdaPyJizEyh9k";
	// 初始公钥
	private String initPublicKey = "7thwLyeUGZVWULqjSCE6eXMHeSuxy9Kg1chwbw7sFfr3";
	// 初始块
	private String initTransactionId = "ed4a2b9ec3454b0c4b5260484167613c47a495b8d2c2dca24fa8b35da00de9c4";
	@Value("${ipfsPath}")
	private String ipfsPath;
	@Value("${uploadDirPath}")
	private String uploadDirPath;
	@Value("${yuming}")
	private String yuming;

	/**
	 * 获取用户的灵钻
	 * 
	 * @param uuid
	 * @return
	 */
	public double getUserDiamond(String uuid) {
		return this.sum(null, uuid);
	}

	/**
	 * 获取用户灵钻总量
	 *
	 * @param publicKey
	 * @return
	 */
	public double sum(String publicKey, String uuid) {
		// 用户灵钻总量
		double num = 0;
		// 未花费
		String noSpent = "";
		if (publicKey != null && uuid == null) {
			noSpent = HttpRequest.get(path + "/api/v1/outputs?public_key=" + publicKey + "&spent=false").body();
			List<Map<String, Object>> noSpentList = (List<Map<String, Object>>) JSONArray.parse(noSpent);
			if (noSpentList != null) {
				String transaction_id = noSpentList.get(0).get("transaction_id").toString();
				// 查询总量
				String metaData = HttpRequest.get(path + "/api/v1/metadata?search=" + transaction_id).body();
				List list = JSONArray.parseArray(metaData);
				JSONObject map = (JSONObject) list.get(0);
				JSONObject json = JSONObject.parseObject(map.get("metadata").toString());
				String total = json.getString("value");
				num = Double.parseDouble(total);
			}
		} else if (publicKey == null && uuid != null) {
			AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
			try {
				noSpent = HttpRequest
						.get(path + "/api/v1/transactions?asset_id=" + user.getInitAddress() + "&operation=TRANSFER")
						.body();
				List<Map<String, Object>> noSpentList = (List<Map<String, Object>>) JSONArray.parse(noSpent);
				if (noSpentList != null) {
					String metaData = noSpentList.get(noSpentList.size() - 1).get("metadata").toString();
					JSONObject map = JSONObject.parseObject(metaData);
					String total = map.getString("value");
					num = Double.parseDouble(total);
				}
			} catch (Exception e) {
				num = 0;
			}
		}
		return num;
	}
	
	/**
	 * 锁定灵钻
	 */
	public void lockingDiamondLog(String uuid, int diamondLogId) {
		DiamondLogEntity log = diamondDao.queryDiamondLogById(diamondLogId, uuid);
		log.setReceive(Constants.LOCKING);
		diamondDao.saveOrUpdate(log);
	}

	/**
	 * 领取灵钻
	 * 
	 * @param uuid
	 */
	public void updateDiamondLog(String uuid, int diamondLogId) {
		AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
		DiamondLogEntity log = diamondDao.queryDiamondLogById(diamondLogId, uuid);
		String transaction_id = "";
		if (log != null) {
			try {
				// 未花费
				String noSpent = HttpRequest
						.get(path + "/api/v1/transactions?asset_id=" + user.getInitAddress() + "&operation=TRANSFER")
						.body();
				List<Map<String, Object>> noSpentList = (List<Map<String, Object>>) JSONArray.parse(noSpent);
				transaction_id = noSpentList.get(noSpentList.size() - 1).get("id").toString();
			} catch (Exception e) {
				transaction_id = user.getInitAddress();
			}
			
			double total = this.sum(null, user.getUuid());
			// 设置余额
			MetaData data = new MetaData();
			data.setMetaData("value", (total + log.getDiamond()) + "");
			data.setMetaData("date", new Date().getTime() + "");
			// 设置主要参数
			FulFill fullFill = new FulFill();
			fullFill.setOutputIndex(0);
			fullFill.setTransactionId(transaction_id);
			// 设置访问地址
			BigchainDbConfigBuilder.baseUrl(path).setup();
			// 生成密钥对
			KeyPair userKeyPair = KeyPairUtils.decodeKeyPair(user.getPrivateKey());
			// 新增事务
			try {
				Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(user.getInitAddress(), String.class)
						.operation(Operations.TRANSFER)
						.addInput(null, fullFill, (EdDSAPublicKey) userKeyPair.getPublic())
						.addOutput("1", (EdDSAPublicKey) userKeyPair.getPublic()).addMetaData(data)
						.buildAndSignOnly((EdDSAPublicKey) userKeyPair.getPublic(),
								(EdDSAPrivateKey) userKeyPair.getPrivate());
				
				TransactionsApi.sendTransaction(transaction);
				//如果20次没有查到就退出
				int count = 20000;
				while (count>0) {
					// 检验是否存入
					String isflag = HttpRequest.get(path + "/api/v1/metadata?search=" + transaction.getId()).body();
					List<Map<String, Object>> isflagList = (List<Map<String, Object>>) JSONArray.parse(isflag);
					if(isflagList != null && isflagList.size() > 0){
						updateTotal(log.getDiamond());
						// 更新数据库
						diamondDao.updateDiamondLog(log);
						break;
					}
					//count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 修改总体灵钻数量
	 */
	private void updateTotal(double diamond) {
		KeyPair keyPair = KeyPairUtils.decodeKeyPair(initPrivateKeyBase64);
		// 未花费
		String noSpent = HttpRequest.get(path + "/api/v1/outputs?public_key=" + initPublicKey + "&spent=false").body();
		List<Map<String, Object>> noSpentList = (List<Map<String, Object>>) JSONArray.parse(noSpent);
		String transaction_id = noSpentList.get(0).get("transaction_id").toString();
		double total = this.sum(initPublicKey, null);
		// 设置余额
		MetaData data = new MetaData();
		data.setMetaData("value", (total - diamond) + "");
		data.setMetaData("date", new Date().getTime() + "");
		// 设置主要参数
		FulFill fullFill = new FulFill();
		fullFill.setOutputIndex(0);
		fullFill.setTransactionId(transaction_id);
		// 设置访问地址
		BigchainDbConfigBuilder.baseUrl(path).setup();
		// 修改总体灵钻数量
		try {
			Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(initTransactionId, String.class)
					.operation(Operations.TRANSFER).addInput(null, fullFill, (EdDSAPublicKey) keyPair.getPublic())
					.addOutput("1", (EdDSAPublicKey) keyPair.getPublic()).addMetaData(data)
					.buildAndSignOnly((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate());
			TransactionsApi.sendTransaction(transaction);
			//如果20次没有查到就退出
			int count = 20000;
			while (count>0) {
				// 检验是否存入
				String isflag = HttpRequest.get(path + "/api/v1/metadata?search=" + transaction.getId()).body();
				List<Map<String, Object>> isflagList = (List<Map<String, Object>>) JSONArray.parse(isflag);
				if(isflagList != null && isflagList.size() > 0){
					break;
				}
				//count--;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<DiamondLogEntity> queryDiamondLogByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Page<DiamondLogEntity> page = diamondDao.page(pageNumber, pageSize, params);
		return page.getList();
	}

	/**
	 * 保存图片或音频
	 * 
	 * @param uuid
	 */
	public void saveImageAndVoice(String uuid, int type, long typeId, Map<String, Object> assetData) {
		AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
		KeyPair keyPair = KeyPairUtils.decodeKeyPair(user.getPrivateKey());
		// 设置访问地址
		BigchainDbConfigBuilder.baseUrl(path).setup();
		try {
			Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(assetData, TreeMap.class)
					.addMetaData(null).addMetaDataClassSerializer(MetaData.class, new MetaDataSerializer())
					.addMetaDataClassDeserializer(MetaDatas.class, new MetaDataDeserializer())
					.operation(Operations.CREATE)
					.buildAndSignOnly((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate());
			TransactionsApi.sendTransaction(transaction);
			// 保存存储记录
			ChainEntity chain = new ChainEntity();
			chain.setFlag(Constants.VAILD_FLAG);
			chain.setCreateTime(new Date().getTime());
			chain.setAddress(transaction.getId());
			chain.setType(type);
			chain.setTypeId(typeId);
			chain.setUuid(uuid);
			chain.setUpdateTime(new Date().getTime());
			diamondDao.saveOrUpdate(chain);
			//获取时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date());
			Date today = sdf.parse(time);
			//增加灵力
			if(typeId == Constants.TIME_MACHINE){
				AppParameterEntity params = userDao.queryParameterEntityByField("max_time");
				int max = Integer.parseInt(params.getValue());
				int count = userDao.countActivity(uuid, Constants.ADD_TIME_MACHINE, typeId, null, today.getTime());
				if(count < max){
					userService.saveActivityIntegral(uuid, type, "add_time", typeId, null);
				}
			}else if(typeId == Constants.PROMISE) {
				AppParameterEntity params = userDao.queryParameterEntityByField("max_voice");
				int max = Integer.parseInt(params.getValue());
				int count = userDao.countActivity(uuid, Constants.ADD_PROMISE, typeId, null, today.getTime());
				if(count < max){
					userService.saveActivityIntegral(uuid, type, "add_voice", typeId, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ChainEntity> queryListChain(int pageNumber, int pageSize, Map<String, Object> params,
			HttpServletRequest request) {
		Page<ChainEntity> page = chainDao.page(pageNumber, pageSize, params);
		List<ChainEntity> list = page.getList();
		for (int i = 0; i < list.size(); i++) {
			ChainEntity chain = list.get(i);
			AppUserEntity user = userDao.queryUserEntityByUuid(chain.getUuid());
			Map<String, Object> map = this.queryChainData(chain.getAddress());
			List resultList = new ArrayList();
			if(map.get("files") != null){
				JSONArray json = JSONArray.parseArray(map.get("files").toString());
				for (int j = 0; j < json.size(); j++) {
					Map result = (Map) json.get(j);
					if(result != null && result.get("ipfsPath") != null){
						String hashPath = result.get("ipfsPath").toString();
						String type = result.get("type").toString();
						String uid = result.get("uid")==null?UUID.randomUUID().toString():result.get("uid").toString();
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
			chain.setName(user.getName());
			chain.setPhotoUrl(user.getHeadUrl());
			
			//获取点赞数量
			int likeNum = userDao.countActivity(null, Constants.ADD_LIKE, chain.getTypeId(), chain.getId(), null);
			//获取评论数量
			int commentNum = userDao.queryCommentCount(chain.getId(), chain.getTypeId());
			chain.setLikeNum(likeNum);
			chain.setCommentNum(commentNum);
			//是否点赞
			ActivityIntegralEntity integral = userDao.queryActivityIntegralEntity(params.get("uuid").toString(), null, Constants.ADD_LIKE, Constants.VAILD_FLAG,
					chain.getId(),chain.getTypeId());
			if(integral == null){
				chain.setIsLike(Constants.NO_LIKE);
			}else {
				chain.setIsLike(Constants.IS_LIKE);
			}
		}
		return list;
	}
	
	
	/**
	 * 证书分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ChainEntity> queryListChainProve(int pageNumber, int pageSize, Map<String, Object> params,
			HttpServletRequest request) {
		Page<ChainEntity> page = chainDao.page(pageNumber, pageSize, params);
		List<ChainEntity> list = page.getList();
		for (int i = 0; i < list.size(); i++) {
			ChainEntity chain = list.get(i);
			AppUserEntity user = userDao.queryUserEntityByUuid(chain.getUuid());
			String chainData = HttpRequest.get(path + "/api/v1/blocks?transaction_id=" + chain.getAddress()).body();
			if(chainData != null && chainData.length() != 0){
				chainData = chainData.replace("[", "");
				chainData = chainData.replace("]", "");
				chainData = chainData.replaceAll("\\n", "");
			}
			chain.setData(chainData);
			chain.setName(user.getName());
			chain.setPhotoUrl(user.getHeadUrl());
		}
		return list;
	}
	

	/**
	 * 查询实体
	 * 
	 * @param id
	 *            实体ID
	 * @param uuid
	 *            用户uuid
	 * @return
	 */
	public ChainEntity queryChainById(long id, String uuid, HttpServletRequest request) {
		AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
		ChainEntity chain = chainDao.queryChainById(id, uuid);
		Map<String, Object> map = this.queryChainData(chain.getAddress());
		List resultList = new ArrayList();
		if(map.get("files") != null){
			JSONArray json = JSONArray.parseArray(map.get("files").toString());
			for (int j = 0; j < json.size(); j++) {
				Map result = (Map) json.get(j);
				if(result != null && result.get("ipfsPath") != null){
					String hashPath = result.get("ipfsPath").toString();
					String type = result.get("type").toString();
					String uid = result.get("uid")==null?UUID.randomUUID().toString():result.get("uid").toString();
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
		chain.setName(user.getName());
		chain.setPhotoUrl(user.getHeadUrl());
		return chain;
	}

	/**
	 * 获取区块里的数据
	 * 
	 * @param id
	 *            哈希ID
	 * @return
	 */
	private Map queryChainData(String id) {
		Map result = new HashMap();
		// 查询数据
		String chainData = HttpRequest.get(path + "/api/v1/assets?search=" + id).body();
		if(chainData != null && chainData.length() != 0){
			List<Map<String, Object>> list = (List<Map<String, Object>>) JSONArray.parse(chainData);
			if(list != null && list.size() > 0){
				Map<String, Object> map = (Map<String, Object>) list.get(0).get("data");
				result.put("description", map.get("description"));
				result.put("files", map.get("files"));
			}
		}
		return result;
	}
}