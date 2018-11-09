package com.manager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.manager.bean.AppUserEntity;
import com.manager.utils.JpaUtils;

/**
 * 用户数据库操作
 * @author 艾克
 * 2018年10月18日 15点21分
 */
@Repository
public class AppUserDao extends JpaUtils{

	
	/**
	 * 查询所有有效用户
	 * @return 
	 */
	public List<AppUserEntity> queryUserList() {
		StringBuilder sql = new StringBuilder(" select * from t_app_user t where t.flag = 1 ");
		return this.find(sql.toString(), AppUserEntity.class);
	}
	
	/**
	 * 查询所有有效用户灵力
	 * @return 
	 */
	public long queryUserIntegralTotal() {
		StringBuilder sql = new StringBuilder(" select sum(t.integral) as total from t_app_user t where t.flag = 1 ");
		Map<String, Object> map = this.findFirst(sql.toString(), HashMap.class);
		String total = map.get("total").toString();
		return Long.parseLong(total);
	}
	
	/**
	 * 查询获取的灵钻
	 * @param uuid 
	 * @param receive 0-未领取  1-领取
	 * @param type 1-收入  2-支出
	 * @return
	 */
	public double queryDiamond(String uuid, Integer receive, Integer type) {
		StringBuilder sql = new StringBuilder(" select ifnull(sum(t.diamond),0) as total from t_diamond_log t where t.flag=1 ");
		List<String> params = new ArrayList<String>();
		if(uuid != null && uuid.length() > 0){
			sql.append(" and t.uuid = ? ");
			params.add(uuid);
		}
		if(receive != null){
			sql.append(" and t.receive = ? ");
			params.add(receive+"");
		}
		if(type != null){
			sql.append(" and t.type = ? ");
			params.add(type+"");
		}
		Map<String, Object> map = this.findFirst(sql.toString(), HashMap.class, params.toArray());
		String total = map.get("total").toString();
		return Double.parseDouble(total);
	}
	
}
