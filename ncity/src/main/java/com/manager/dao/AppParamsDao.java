package com.manager.dao;

import org.springframework.stereotype.Repository;

import com.manager.bean.AppParameterEntity;
import com.manager.utils.JpaUtils;

/**
 * app参数配置数据库操作
 * @author 艾克
 * 2018年10月18日 16点00分
 */
@Repository
public class AppParamsDao extends JpaUtils{

	/**
	 * 根据字段名查询值
	 * @param field 字段名
	 * @return
	 */
	public AppParameterEntity queryParameterByField(String field) {
		StringBuilder sql = new StringBuilder(" select * from t_app_parameter t where t.flag=1 ");
		sql.append(" and t.field = ? ");
		return this.findFirst(sql.toString(), AppParameterEntity.class, field);
	}
	
}
