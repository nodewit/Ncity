package com.manager.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.manager.utils.JpaUtils;
import com.manager.utils.Page;

/**
 * 反馈意见-数据库操作
 * @author 艾克
 * 2018年11月7日 16点51分
 */
@Repository
public class AppSuggestDao extends JpaUtils{
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page querySuggestByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		StringBuilder nativeSQL = new StringBuilder(" select t.*,u.name from t_suggest t,t_app_user u where u.UUID=t.uuid and t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_suggest t,t_app_user u where u.UUID=t.uuid and t.flag=1 ");
		if(params.get("keyword") != null && params.get("keyword").toString().length() != 0){
			nativeSQL.append(" and (t.content like CONCAT('%',:keyword,'%') or u.name like CONCAT('%',:keyword,'%')) ");
			nativeCountSQL.append(" and (t.content like CONCAT('%',:keyword,'%') or u.name like CONCAT('%',:keyword,'%')) ");
			searchMap.put("keyword", params.get("keyword"));
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), Map.class, pageNumber, pageSize, searchMap);
	}
	
	
}
