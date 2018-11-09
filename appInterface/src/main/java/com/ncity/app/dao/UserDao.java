package com.ncity.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ncity.app.entity.ActivityIntegralEntity;
import com.ncity.app.entity.AppParameterEntity;
import com.ncity.app.entity.AppUserEntity;
import com.ncity.app.entity.CommentEntity;
import com.ncity.app.entity.VersionEntity;
import com.ncity.app.uitls.JpaUtils;
import com.ncity.app.uitls.Page;

/**
 * 用户数据操作
 * @author 艾克
 * 2018年10月15日 09点05分
 */
@Repository
public class UserDao extends JpaUtils {

	/**
	 * 通过手机号查询用户实体
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	public AppUserEntity queryUserEntityByMobile(String mobile) {
		StringBuilder sql = new StringBuilder(" select * from t_app_user t where t.flag=1 ");
		sql.append(" and t.mobile = ? ");
		AppUserEntity entity = this.findFirst(sql.toString(), AppUserEntity.class, mobile);
		return entity;
	}

	/**
	 * 通过微信openid查询用户实体
	 *
	 * @param openid
	 *            微信唯一号
	 * @return
	 */
	public AppUserEntity queryUserEntityByOpenid(String openid) {
		StringBuilder sql = new StringBuilder(" select * from t_app_user t where t.flag=1 ");
		sql.append(" and t.openid = ? ");
		AppUserEntity entity = this.findFirst(sql.toString(), AppUserEntity.class, openid);
		return entity;
	}

	/**
	 * 通过邀请码查询用户实体
	 *
	 * @param invitationCode
	 *            用户唯一邀请码
	 * @return
	 */
	public AppUserEntity queryUserEntityByInvitationCode(String invitationCode) {
		StringBuilder sql = new StringBuilder(" select * from t_app_user t where t.flag=1 ");
		sql.append(" and t.invitation_code = ? ");
		AppUserEntity entity = this.findFirst(sql.toString(), AppUserEntity.class, invitationCode);
		return entity;
	}

	/**
	 * 通过field查询参数
	 * @param field
	 * @return
	 */
	public AppParameterEntity queryParameterEntityByField(String field) {
		StringBuilder sql = new StringBuilder(" select * from t_app_parameter t where t.flag=1 ");
		sql.append(" and t.field = ? ");
		AppParameterEntity entity = this.findFirst(sql.toString(), AppParameterEntity.class, field);
		return entity;
	}
	
	/**
	 * 通过uuid查询用户
	 * @param uuid
	 * @return
	 */
	public AppUserEntity queryUserEntityByUuid(String uuid) {
		StringBuilder sql = new StringBuilder(" select * from t_app_user t where t.flag=1 ");
		sql.append(" and t.uuid = ? ");
		AppUserEntity entity = this.findFirst(sql.toString(), AppUserEntity.class, uuid);
		return entity;
	}
	
	/**
	 * 灵力列表
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page<ActivityIntegralEntity> page(int pageNumber, int pageSize, Map<String, Object> params) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_activity_integral t where t.flag=1 and t.integral > 0 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_activity_integral t where t.flag=1 and t.integral > 0 ");
		if(params.get("uuid") != null && params.get("uuid").toString().length() != 0){
			nativeSQL.append(" and t.uuid = :uuid ");
			nativeCountSQL.append(" and t.uuid = :uuid ");
		}
		if(params.get("createTime") != null && params.get("createTime").toString().length() != 0){
			nativeSQL.append(" and t.create_time > :createTime ");
			nativeCountSQL.append(" and t.create_time > :createTime ");
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), ActivityIntegralEntity.class, pageNumber, pageSize, params);
	}
	
	/**
	 * 查询灵力日志实体
	 * @return
	 */
	public ActivityIntegralEntity queryActivityIntegralEntity(String uuid, Long createTime, Integer type, Integer flag, Long objectId, Long typeId){
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select * from t_activity_integral t where 1=1 ");
		if(flag != null){
			nativeSQL.append(" and t.flag = :flag ");
			params.put("flag", flag);
		}
		if(uuid != null && uuid.length() != 0){
			nativeSQL.append(" and t.uuid = :uuid ");
			params.put("uuid", uuid);
		}
		if(type != null && type > 0){
			nativeSQL.append(" and t.type = :type ");
			params.put("type", type);
		}
		if(createTime != null){
			nativeSQL.append(" and t.create_time > :createTime ");
			params.put("createTime", createTime);
		}
		if(objectId != null){
			nativeSQL.append(" and t.object_id = :objectId ");
			params.put("objectId", objectId);
		}
		if(typeId != null){
			nativeSQL.append(" and t.type_id = :typeId ");
			params.put("typeId", typeId);
		}
		return this.findFirst(nativeSQL.toString(), ActivityIntegralEntity.class, params);
	}
	
	/**
	 * 检测最新版本
	 * @return
	 */
	public VersionEntity queryVersion(String type) {
		String sql = " select * from t_version where flag='1' and client_device_type = ?  order by (replace (version,'.', '')+0) desc limit 1 ";
		return this.findFirst(sql, VersionEntity.class, type);
	}
	
	/**
	 * 统计行为数量
	 * @param uuid
	 * @param type
	 * @param typeId
	 * @param objectId
	 * @return
	 */
	public int countActivity(String uuid, int type, Long typeId, Long objectId, Long createTime){
		Map<String,Object> params = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select count(1) as count from t_activity_integral t where t.flag=1 ");
		if(uuid != null && uuid.length() > 0){
			nativeSQL.append(" and t.uuid = :uuid ");
			params.put("uuid",uuid);
		}
		if(type > -1){
			nativeSQL.append(" and t.type = :type ");
			params.put("type",type);
		}
		if(typeId != null && typeId > 0){
			nativeSQL.append(" and t.type_id = :typeId ");
			params.put("typeId",typeId);
		}
		if(objectId != null && objectId > 0){
			nativeSQL.append(" and t.object_id = :objectId ");
			params.put("objectId",objectId);
		}
		if(createTime != null && createTime > 0){
			nativeSQL.append(" and t.create_time > :createTime ");
			params.put("createTime",createTime);
		}
		Map result = this.findFirst(nativeSQL.toString(), Map.class, params);
		return Integer.parseInt(result.get("count").toString());
	}
	
	/**
	 * 评论列表
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page<CommentEntity> pageComment(int pageNumber, int pageSize, Map<String, Object> params) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_comment t where t.flag=1 and (t.to_id = 0 or t.to_id = null) ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_comment t where t.flag=1 and (t.to_id = 0 or t.to_id = null) ");
		if(params.get("objectId") != null && params.get("objectId").toString().length() != 0){
			nativeSQL.append(" and t.object_id = :objectId ");
			nativeCountSQL.append(" and t.object_id = :objectId ");
		}
		if(params.get("type") != null && params.get("type").toString().length() != 0){
			nativeSQL.append(" and t.type = :type ");
			nativeCountSQL.append(" and t.type = :type ");
		}
		if(params.get("createTime") != null && params.get("createTime").toString().length() != 0){
			nativeSQL.append(" and t.create_time > :createTime ");
			nativeCountSQL.append(" and t.create_time > :createTime ");
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), CommentEntity.class, pageNumber, pageSize, params);
	}
	
	/**
	 * 统计评论数量
	 * @param objecId
	 * @return
	 */
	public int queryCommentCount(Long objecId, Long typeId) {
		String sql = " select count(1) as count from t_comment t where t.flag=1 and t.object_id=? and t.type=? ";
		Map map = this.findFirst(sql, Map.class, objecId, typeId);
		return Integer.parseInt(map.get("count").toString());
	}
	
	/**
	 * 评论列表
	 * @return
	 */
	public List<CommentEntity> queryCommentList(Long id) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_comment t where t.flag=1 and t.to_id=? ");
		return this.find(nativeSQL.toString(), CommentEntity.class, id);
	}
}
