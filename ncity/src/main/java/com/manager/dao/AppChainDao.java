package com.manager.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.manager.bean.ChainEntity;
import com.manager.utils.JpaUtils;
import com.manager.utils.Page;

/**
 * 内容数据-数据库操作
 * @author 艾克
 * 2018年11月6日 15点11分
 */
@Repository
public class AppChainDao extends JpaUtils{

	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryChainByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select t.*,u.`name` from t_chain t,t_app_user u where u.uuid=t.uuid and t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_chain t,t_app_user u where u.uuid=t.uuid and t.flag=1 ");
		if(params.get("keyword") != null && params.get("keyword").toString().length() != 0){
			nativeSQL.append(" and (t.address like CONCAT('%',:keyword,'%') or u.name like CONCAT('%',:keyword,'%')) ");
			nativeCountSQL.append(" and (t.address like CONCAT('%',:keyword,'%') or u.name like CONCAT('%',:keyword,'%')) ");
			searchMap.put("keyword", params.get("keyword"));
		}
		if(params.get("type") != null && params.get("type").toString().length() != 0){
			nativeSQL.append(" and t.type = :type ");
			nativeCountSQL.append(" and t.type = :type ");
			searchMap.put("type", params.get("type"));
		}
		if(params.get("typeId") != null && params.get("typeId").toString().length() != 0){
			nativeSQL.append(" and t.type_id = :typeId ");
			nativeCountSQL.append(" and t.type_id = :typeId ");
			searchMap.put("typeId", params.get("typeId"));
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), Map.class, pageNumber, pageSize, searchMap);
	}
	
	
	/**
	 * 查询实体
	 * @param id 实体ID
	 * @param uuid 用户uuid
	 * @return
	 */
	public ChainEntity queryChainById(long id) {
		return this.findFirst(" select * from t_chain t where t.flag=1 and t.id=? ", ChainEntity.class, id);
	}
	
	
	/**
	 * 灵钻分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryDiamondByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select t.*,u.name from t_diamond_log t,t_app_user u where u.uuid=t.uuid and t.flag=1 and t.receive=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_diamond_log t,t_app_user u where u.uuid=t.uuid and t.flag=1 and t.receive=1 ");
		if(params.get("keyword") != null && params.get("keyword").toString().length() != 0){
			nativeSQL.append(" and u.name like CONCAT('%',:keyword,'%') ");
			nativeCountSQL.append(" and u.name like CONCAT('%',:keyword,'%') ");
			searchMap.put("keyword", params.get("keyword"));
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), Map.class, pageNumber, pageSize, searchMap);
	}
	
	
	/**
	 * 灵力分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page queryIntegralByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select t.*,u.name from t_activity_integral t,t_app_user u where u.uuid=t.uuid and t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_activity_integral t,t_app_user u where u.uuid=t.uuid and t.flag=1 ");
		if(params.get("keyword") != null && params.get("keyword").toString().length() != 0){
			nativeSQL.append(" and u.name like CONCAT('%',:keyword,'%') ");
			nativeCountSQL.append(" and u.name like CONCAT('%',:keyword,'%') ");
			searchMap.put("keyword", params.get("keyword"));
		}
		if(params.get("type") != null && params.get("type").toString().length() != 0){
			nativeSQL.append(" and t.type = :type ");
			nativeCountSQL.append(" and t.type = :type ");
			searchMap.put("type", params.get("type"));
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), Map.class, pageNumber, pageSize, searchMap);
	}
	
	/**
	 * 统计评论数量
	 * @param typeId 1-时光机 2-一诺千金
	 * @param objectId 对象ID
	 * @return
	 */
	public int queryCommentCount(Long objecId, Long typeId) {
		String sql = " select count(1) as count from t_comment t where t.flag=1 and t.object_id=? and t.type=? ";
		Map map = this.findFirst(sql, Map.class, objecId, typeId);
		return Integer.parseInt(map.get("count").toString());
	}
	
	/**
	 * 统计行为数量
	 * @param uuid
	 * @param type 类型
	 * @param typeId 1-时光机 2-一诺千金
	 * @param objectId 对象ID
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
}
