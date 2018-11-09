package com.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.bean.AppUserEntity;
import com.manager.bean.DiamondLogEntity;
import com.manager.dao.AppUserDao;

/**
 * 用户服务层
 * @author 艾克
 * 2018年10月18日 15点23分
 */
@Service
public class AppUserService {

	@Autowired
	private AppUserDao appUserDao;
	
	/**
	 * 查询所有有效用户
	 * @return 
	 */
	public List<AppUserEntity> queryUserList() {
		return appUserDao.queryUserList();
	}
	
	
	/**
	 * 查询所有有效用户灵力
	 * @return 
	 */
	public long queryUserIntegralTotal() {
		return appUserDao.queryUserIntegralTotal();
	}

	/**
	 * 查询获取的灵钻
	 * @param uuid 
	 * @param receive 0-未领取  1-领取
	 * @param type 1-收入  2-支出
	 * @return
	 */
	public double queryDiamond(String uuid, Integer receive, Integer type) {
		return appUserDao.queryDiamond(uuid, receive, type);
	}
	
	/**
	 * 保存灵钻日志
	 * @param diamondLog
	 */
	public void saveDiamondLog(DiamondLogEntity diamondLog) {
		appUserDao.saveOrUpdate(diamondLog);
	}
}
