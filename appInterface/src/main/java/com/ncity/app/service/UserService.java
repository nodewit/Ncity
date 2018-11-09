package com.ncity.app.service;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.ncity.app.entity.ActivityIntegralEntity;
import com.ncity.app.entity.AppParameterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.json.strategy.MetaDataDeserializer;
import com.bigchaindb.json.strategy.MetaDataSerializer;
import com.bigchaindb.model.Account;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.MetaData;
import com.bigchaindb.model.MetaDatas;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.util.KeyPairUtils;
import com.ncity.app.dao.UserDao;
import com.ncity.app.entity.AppLoginLogEntity;
import com.ncity.app.entity.AppUserEntity;
import com.ncity.app.entity.CommentEntity;
import com.ncity.app.entity.DiamondLogEntity;
import com.ncity.app.entity.SuggestEntity;
import com.ncity.app.entity.VersionEntity;
import com.ncity.app.uitls.Constants;
import com.ncity.app.uitls.JpaUtils;
import com.ncity.app.uitls.Page;
import com.ncity.app.uitls.RedisUtils;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

/**
 * 用户服务层
 * 
 * @author 艾克 2018年10月15日 11点43分
 */
@Service
public class UserService {
	@Value("${url}")
	private String path;
	@Autowired
	private UserDao userDao;

	/**
	 * 手机登录
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            验证码
	 * @param ua
	 *            系统版本信息
	 * @return
	 */
	public Map<String, Object> login(String mobile, String code, String ua) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", Constants.SYS_NORMAL);
		result.put("message", "登录成功");
		// 检验验证码是否为空
		if (code == null || code.length() == 0) {
			result.put("code", Constants.NO_CODE_ERROR);
			result.put("message", "请填写验证码");
		}
		// 检验手机是否为空
		if (mobile == null || mobile.length() == 0) {
			result.put("code", Constants.NO_MOBILE_ERROR);
			result.put("message", "请填写手机号");
		}
		// 从缓存里拿验证码
		String oldCode = RedisUtils.get(mobile);
		if (oldCode == null || !code.equals(oldCode)) {
			result.put("code", Constants.CODE_ERROR);
			result.put("message", "验证码错误");
		}
		// 通过手机号查询用户实体
		AppUserEntity entity = userDao.queryUserEntityByMobile(mobile);
		// 检验用户是否存在
		if (entity == null) {
			result.put("code", Constants.NO_INFO_ERROR);
			result.put("message", "手机号不存在，请注册");
		} else {
			// 保存登录日志
			String token = this.saveUserLoginLog(entity.getUuid(), ua);
			Map map = new HashMap();
			map.put("token", token);
			map.put("uuid", entity.getUuid());
			result.put("data", map);
			// 删除缓存
			RedisUtils.delete(mobile);
		}
		return result;
	}

	/**
	 * 微信登录
	 *
	 * @param openid
	 *            微信唯一ID
	 * @param ua
	 *            系统信息
	 * @return
	 */
	public Map<String, Object> wxLogin(String openid, String ua) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", Constants.SYS_NORMAL);
		result.put("message", "登录成功");
		// 通过微信查询用户实体
		AppUserEntity entity = userDao.queryUserEntityByOpenid(openid);
		// 检验用户是否存在
		if (entity == null) {
			result.put("code", Constants.NO_INFO_ERROR);
			result.put("message", "账号不存在，请注册");
			result.put("data", null);
		} else {
			// 保存登录日志
			String token = this.saveUserLoginLog(entity.getUuid(), ua);
			Map map = new HashMap();
			map.put("token", token);
			map.put("uuid", entity.getUuid());
			result.put("data", map);
		}
		result.put("data", entity);
		return result;
	}

	/**
	 * 保存登录日志
	 * 
	 * @param uuid
	 * @param ua
	 */
	public String saveUserLoginLog(String uuid, String ua) {
		// 获取唯一token
		String token = UUID.randomUUID().toString().replace("-", "");
		// 分割系统信息(格式：设备类型（1、ios,2、android）|网络类型（1、wifi,2、流量）|版本号|渠道ID)|token
		String[] systemInfo = new String[1];
		if (ua != null) {
			systemInfo = ua.split("\\|");
		}
		AppLoginLogEntity log = new AppLoginLogEntity();
		log.setUuid(uuid);
		log.setCreateTime(new Date().getTime());
		log.setToken(token);
		log.setUpdateTime(new Date().getTime());
		log.setExpiredTime(new Date().getTime() + (7 * 86400000l));
		log.setFlag(Constants.VAILD_FLAG);
		if (systemInfo.length == 4) {
			log.setAppVersion(systemInfo[2]);
			log.setCannelId(systemInfo[3]);
			log.setNetType(Integer.parseInt(systemInfo[1]));
			log.setPlantform(Integer.parseInt(systemInfo[0]));
		}
		// 保存登录日志
		userDao.saveOrUpdate(log);

		// 判断是否保存成功
		if (log.getId() == null) {
			return null;
		}
		// 将token保存到缓存里
		if (ua != null) {
			RedisUtils.set(token, ua, 7, TimeUnit.DAYS);
		}
		return token;
	}

	/**
	 * 注册
	 * 
	 * @param appUserEntity
	 *            用户实体
	 * @param code
	 *            验证码
	 * @param ua
	 *            系统信息
	 * @return
	 */
	public Map<String, Object> register(AppUserEntity appUserEntity, String code, String ua) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", Constants.SYS_NORMAL);
		result.put("message", "登录成功");
		// 判断手机号是否注册
		AppUserEntity oldAe = userDao.queryUserEntityByMobile(appUserEntity.getMobile());
		if (oldAe != null) {
			result.put("code", Constants.MOBILE_ERROR);
			result.put("message", "手机已注册");
		} else {
			// 从缓存里拿验证码
			String oldCode = RedisUtils.get(appUserEntity.getMobile());
			if (!code.equals(oldCode)) {
				result.put("code", Constants.CODE_ERROR);
				result.put("message", "验证码错误");
			} else {

				// 判断邀请码是否存在
				String InvitationCode = appUserEntity.getInvitationCode();
				if (InvitationCode == null || InvitationCode.equals("")) {
					// 保存用户
					AppUserEntity ae = this.saveAppUser(appUserEntity);

					if (ae.getId() != null) {
						// 直接登录
						result = this.login(appUserEntity.getMobile(), oldCode, ua);
					} else {
						result.put("code", Constants.SYS_ERROR);
						result.put("message", "注册失败");
					}
				} else {
					boolean flag = isExistRandom(InvitationCode);
					if (flag == true) {
						// 为邀请码所属用户增加灵力
						updateAppUser(appUserEntity, "invite");

						// 保存用户
						AppUserEntity ae = this.saveAppUser(appUserEntity);
						if (ae.getId() != null) {
							// 直接登录
							result = this.login(appUserEntity.getMobile(), oldCode, ua);
						} else {
							result.put("code", Constants.SYS_ERROR);
							result.put("message", "注册失败");
						}
					} else {
						result.put("code", Constants.NO_INVITATIONCODE);
						result.put("message", "邀请码不存在");
					}
				}

			}
		}
		return result;
	}

	/**
	 * 绑定
	 *
	 * @param appUserEntity
	 *            用户实体
	 * @param code
	 *            验证码
	 * @param ua
	 *            系统信息
	 * @return
	 */
	public Map<String, Object> bind(AppUserEntity appUserEntity, String code, String ua) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", Constants.SYS_NORMAL);
		result.put("message", "绑定成功");
		// 判断手机号是否注册
		AppUserEntity oldAe = userDao.queryUserEntityByMobile(appUserEntity.getMobile());
		if (oldAe == null) {
			result.put("code", Constants.MOBILE_ERROR);
			result.put("message", "手机未注册，无法绑定");
		} else {
			// 从缓存里拿验证码
			String oldCode = RedisUtils.get(appUserEntity.getMobile());
			if (!code.equals(oldCode)) {
				result.put("code", Constants.CODE_ERROR);
				result.put("message", "验证码错误");
			} else {
				oldAe.setOpenid(appUserEntity.getOpenid());
				// 修改用户openid
				userDao.saveOrUpdate(oldAe);

				// 直接登录
				result = wxLogin(appUserEntity.getOpenid(), ua);

				// 删除缓存
				RedisUtils.delete(appUserEntity.getMobile());
			}
		}

		return result;
	}

	/**
	 * 判断邀请码是否存在
	 * 
	 * @param random
	 * @return
	 */
	public boolean isExistRandom(String random) {
		boolean flag = false;
		StringBuilder sql = new StringBuilder(" select count(1) as total from t_app_user t where t.flag=1 ");
		sql.append(" and t.invitation_code = ? ");
		Map<String, Object> map = userDao.findFirst(sql.toString(), HashMap.class, random);
		int count = Integer.parseInt(map.get("total").toString());
		if (count != 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 保存用户实体
	 * 
	 * @param appUserEntity
	 * @return
	 */
	public AppUserEntity saveAppUser(AppUserEntity appUserEntity) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		appUserEntity.setFlag(Constants.VAILD_FLAG);
		// 为新注册用户设置基础灵力
		appUserEntity.setIntegral(Integer.parseInt(userDao.queryParameterEntityByField("give away").getValue()));
		String random = JpaUtils.getRandomString(6);
		while (isExistRandom(random)) {
			break;
		}
		appUserEntity.setInvitationCode(random.toUpperCase());
		appUserEntity.setRegisteredTime(new Date().getTime());
		appUserEntity.setDiamond(BigDecimal.valueOf(0));
		appUserEntity.setUuid(uuid);
		// 获取公钥和私钥
		net.i2p.crypto.eddsa.KeyPairGenerator edDsaKpg = new net.i2p.crypto.eddsa.KeyPairGenerator();
		KeyPair keyPair = edDsaKpg.generateKeyPair();
		appUserEntity.setPrivateKey(KeyPairUtils.encodePrivateKeyBase64(keyPair));
		appUserEntity.setPublicKey(KeyPairUtils.encodePublicKeyInBase58((EdDSAPublicKey) keyPair.getPublic()));

		// 设置访问地址
		BigchainDbConfigBuilder.baseUrl(path).setup();
		// 初始资产
		Map<String, String> assetData = new TreeMap<String, String>() {
			{
				put("value", "0");
			}
		};
		// 初始元数据
		MetaData metaData = new MetaData();
		metaData.setMetaData("value", "0");
		metaData.setMetaData("date", new Date().getTime() + "");
		// 初始块
		try {
			Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(assetData, TreeMap.class)
					.addMetaData(metaData).addMetaDataClassSerializer(MetaData.class, new MetaDataSerializer())
					.addMetaDataClassDeserializer(MetaDatas.class, new MetaDataDeserializer())
					.operation(Operations.CREATE)
					.buildAndSignOnly((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate());
			TransactionsApi.sendTransaction(transaction);
			appUserEntity.setInitAddress(transaction.getId());
			userDao.saveOrUpdate(appUserEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 插入灵力日志
		ActivityIntegralEntity activityIntegralEntity = new ActivityIntegralEntity();
		activityIntegralEntity
				.setIntegral(Integer.parseInt(userDao.queryParameterEntityByField("give away").getValue()));
		activityIntegralEntity.setUuid(appUserEntity.getUuid());
		activityIntegralEntity.setType(Constants.NEW_REGISTER);// 签到
		saveActivityIntegral(activityIntegralEntity);
		return appUserEntity;
	}

	/**
	 * 邀请用户，增加灵力或灵钻
	 *
	 * @param appUserEntity
	 * @param field
	 * @return
	 */
	public AppUserEntity updateAppUser(AppUserEntity appUserEntity, String field) {
		// 通过parameter查询“t_app_parameter”表（增加多少灵力）
		AppParameterEntity appParameterEntity = userDao.queryParameterEntityByField(field);

		// 通过邀请码查询用户实体
		AppUserEntity appUserEntityByIC = userDao.queryUserEntityByInvitationCode(appUserEntity.getInvitationCode());
		// 增加用户灵力
		Integer value = appUserEntityByIC.getIntegral() + Integer.parseInt(appParameterEntity.getValue());
		appUserEntityByIC.setIntegral(value);

		// 插入灵力日志
		ActivityIntegralEntity activityIntegralEntity = new ActivityIntegralEntity();
		activityIntegralEntity.setIntegral(Integer.parseInt(appParameterEntity.getValue()));
		activityIntegralEntity.setUuid(appUserEntityByIC.getUuid());
		activityIntegralEntity.setType(Constants.TYPE_INVITE);// 邀请用户

		saveActivityIntegral(activityIntegralEntity);

		// 修改用户信息
		userDao.saveOrUpdate(appUserEntityByIC);
		return appUserEntity;
	}

	/**
	 * 插入灵力日志
	 *
	 * @param activityIntegralEntity
	 * @return
	 */
	public ActivityIntegralEntity saveActivityIntegral(ActivityIntegralEntity activityIntegralEntity) {
		activityIntegralEntity.setCreateTime(new Date().getTime());
		activityIntegralEntity.setUpdateTime(new Date().getTime());
		activityIntegralEntity.setFlag(Constants.VAILD_FLAG);
		userDao.saveOrUpdate(activityIntegralEntity);
		return activityIntegralEntity;
	}

	/**
	 * 查询用户信息
	 *
	 * @param uuid
	 * @return
	 */
	public AppUserEntity queryUserEntityByUuid(String uuid) {
		AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
		return user;
	}

	/**
	 * 保存灵力日志
	 * 
	 * @param uuid
	 * @param type
	 * @param field
	 *            配置表的字段名称
	 * @param typeId
	 *            时光机/一诺千金
	 * @param ObjectId
	 *            对象ID
	 */
	public void saveActivityIntegral(String uuid, int type, String field, Long typeId, Long objectId) {
		AppParameterEntity appParameterEntity = userDao.queryParameterEntityByField(field);
		ActivityIntegralEntity integral = new ActivityIntegralEntity();
		integral.setIntegral(Integer.parseInt(appParameterEntity.getValue()));
		integral.setType(type);
		integral.setUuid(uuid);
		integral.setTypeId(typeId);
		integral.setObjectId(objectId);
		this.saveActivityIntegral(integral);
		// 更新用户灵力字段
		AppUserEntity user = userDao.queryUserEntityByUuid(uuid);
		user.setIntegral(user.getIntegral() + Integer.parseInt(appParameterEntity.getValue()));
		userDao.saveOrUpdate(user);
	}

	/**
	 * 灵力日志分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ActivityIntegralEntity> queryActivityIntegralByPage(int pageNumber, int pageSize,
			Map<String, Object> params) {
		Page<ActivityIntegralEntity> page = userDao.page(pageNumber, pageSize, params);
		List<ActivityIntegralEntity> list = page.getList();
		return list;
	}

	/**
	 * 是否签到
	 * 
	 * @param uuid
	 * @return
	 */
	public boolean isSignIn(String uuid) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date());
			Date date = sdf.parse(time);
			ActivityIntegralEntity integral = userDao.queryActivityIntegralEntity(uuid, date.getTime(),
					Constants.SIGN_IN, Constants.VAILD_FLAG, null, null);
			if (integral != null) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存反馈
	 * 
	 * @param uuid
	 * @param content
	 */
	public void saveSuggest(String uuid, String content) {
		SuggestEntity suggest = new SuggestEntity();
		suggest.setContent(content);
		suggest.setCreateTime(new Date().getTime());
		suggest.setFlag(Constants.VAILD_FLAG);
		suggest.setUuid(uuid);
		userDao.saveOrUpdate(suggest);
	}

	/**
	 * 检测最新版本
	 * 
	 * @return
	 */
	public VersionEntity queryVersion(String type) {
		return userDao.queryVersion(type);
	}

	/**
	 * 点赞
	 * 
	 * @param uuid
	 * @param like
	 *            1-点赞 0-取消
	 * @param typeId
	 *            时光机/一诺千金
	 * @param objectId
	 *            对象ID
	 */
	public void saveLike(String uuid, int like, Long typeId, Long objectId) {
		// 获取时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		Date today = null;
		try {
			today = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ActivityIntegralEntity integral = userDao.queryActivityIntegralEntity(uuid, null, Constants.ADD_LIKE, null, objectId, typeId);
		if (integral == null && like == Constants.IS_LIKE) {
			AppParameterEntity params = userDao.queryParameterEntityByField("max_like");
			int max = Integer.parseInt(params.getValue());
			int count = userDao.countActivity(uuid, Constants.ADD_LIKE, typeId, null, today.getTime());
			if (count < max) {
				this.saveActivityIntegral(uuid, Constants.ADD_LIKE, "add_like", typeId, objectId);
			} else {
				ActivityIntegralEntity entity = new ActivityIntegralEntity();
				entity.setIntegral(0);
				entity.setObjectId(objectId);
				entity.setType(Constants.ADD_LIKE);
				entity.setTypeId(typeId);
				entity.setUuid(uuid);
				this.saveActivityIntegral(entity);
			}
		} else if (integral != null && like == Constants.IS_LIKE) {
			integral.setFlag(Constants.VAILD_FLAG);
			integral.setUpdateTime(new Date().getTime());
			userDao.saveOrUpdate(integral);
		} else if (integral != null && like == Constants.NO_LIKE) {
			integral.setFlag(Constants.DELEYE_FLAG);
			integral.setUpdateTime(new Date().getTime());
			userDao.saveOrUpdate(integral);
		}
	}

	/**
	 * 保存评论
	 * 
	 * @param uuid
	 * @param type
	 * @param objectId
	 * @param toId
	 * @param comment
	 */
	public void saveComment(String uuid, Long typeId, Long objectId, Long toId, String comment) {
		CommentEntity entity = new CommentEntity();
		entity.setComment(comment);
		entity.setCreateTime(new Date().getTime());
		entity.setFlag(Constants.VAILD_FLAG);
		entity.setFromUuid(uuid);
		entity.setToId(toId);
		entity.setObjectId(objectId);
		entity.setType(Integer.parseInt(typeId.toString()));
		entity.setUpdateTime(new Date().getTime());
		userDao.saveOrUpdate(entity);
		// 获取时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		Date today = null;
		try {
			today = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AppParameterEntity params = userDao.queryParameterEntityByField("max_comment");
		int max = Integer.parseInt(params.getValue());
		int count = userDao.countActivity(uuid, Constants.ADD_COMMENT, typeId, null, today.getTime());
		if (count < max) {
			this.saveActivityIntegral(uuid, Constants.ADD_COMMENT, "add_comment", typeId, objectId);
		}
	}
	
	/**
	 * 评论分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CommentEntity> queryCommentListByPage(int pageNumber, int pageSize,
			Map<String, Object> params) {
		Page<CommentEntity> page = userDao.pageComment(pageNumber, pageSize, params);
		List<CommentEntity> list = page.getList();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				CommentEntity entity = list.get(i);
				AppUserEntity user = userDao.queryUserEntityByUuid(entity.getFromUuid());
				entity.setName(user.getName());
				entity.setPhotoUrl(user.getHeadUrl());
				List<CommentEntity> commentList = userDao.queryCommentList(entity.getId());
				if(commentList != null){
					for (int j = 0; j < commentList.size(); j++) {
						CommentEntity e = commentList.get(j);
						AppUserEntity u = userDao.queryUserEntityByUuid(e.getFromUuid());
						e.setName(u.getName());
						e.setPhotoUrl(u.getHeadUrl());
					}
				}
				entity.setCommentList(commentList);
			}
		}
		return list;
	}
	
}
