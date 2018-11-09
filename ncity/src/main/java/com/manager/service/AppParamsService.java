package com.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.bean.AppParameterEntity;
import com.manager.dao.AppParamsDao;

/**
 * app参数配置服务层
 * @author 艾克
 * 2018年10月18日 15点23分
 */
@Service
public class AppParamsService {

	@Autowired
	private AppParamsDao appParamsDao;
	
	/**
	 * 根据字段名查询值
	 * @param field 字段名
	 * @return
	 */
	public AppParameterEntity queryParameterByField(String field) {
		return appParamsDao.queryParameterByField(field);
	}
	
}
