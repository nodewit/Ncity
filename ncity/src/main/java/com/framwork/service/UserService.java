package com.framwork.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framwork.bean.PageBean;
import com.framwork.dao.UserDao;

/**
 * 用户service
 * @author YXD110
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 登录
	 * @return
	 */
	public String login(String account,String pwd){
		return userDao.login(account, pwd);
	}
	
	/**
	 * 查询账号信息
	 */
	public Map queryUserByLoginId(String account){
		return userDao.queryUserByLoginId(account);
	}
	

	/**
	 * 更新登录时间和次数
	 */
	public void updateCountAndTime(String account,int count){
		userDao.updateCountAndTime(account, count);
	}
	
	/**
	 * 日志
	 */
	public void addLogs(String uid,String event, HttpServletRequest request){
		userDao.addLogs(uid, event, request);
	}
	
	/**
	 * 更新个人信息
	 */
	public void updateUserInfo(Map<String,Object> userMap){
		userDao.updateUserInfo(userMap);
	}
	
	/**
	 * 更新密码
	 */
	public String updatePassword(int id,String oldPwd,String newPwd){
		return userDao.updatePassword(id, oldPwd, newPwd);
	}
	
	/**
	 * 查询用户信息
	 */
	public Map getUserInfo(int id){
		return userDao.getUserInfo(id);
	}
	
	/**
	 * 分页查询用户
	 * @return
	 */
	public PageBean queryUser(PageBean pageBean){
		StringBuffer whereSql = new StringBuffer();
		Map queryMap = pageBean.getQueryParams();
		if(queryMap != null && queryMap.get("keyword") != null && !queryMap.get("keyword").toString().equals("")){
			whereSql.append(" and (t.login_id like '%").append(queryMap.get("keyword"))
			.append("%' or t.name like '%").append(queryMap.get("keyword"))
			.append("%' or t.mobile like '%").append(queryMap.get("keyword"))
			.append("%' or t.email like '%").append(queryMap.get("keyword")).append("%')");;
		}
		String orderby = " ORDER BY t.create_time desc ";
		pageBean = userDao.queryUser(pageBean, whereSql.toString(), orderby);
		return pageBean;
	}
	
	/**
	 * 删除用户
	 * @param ids
	 */
	public void deleteUser(String ids){
		userDao.deleteUser(ids);
	}
	
	/**
	 * 验证登录账号是否存在
	 */
	public boolean isLoginId(String loginId){
		return userDao.isLoginId(loginId);
	}
	
	/**
	 * 新增用户
	 */
	public void addUser(Map<String,Object> userMap,int uid){
		userDao.addUser(userMap, uid);
	}
	
	/**
	 * 重置密码
	 */
	public void resetPassword(int uid){
		userDao.resetPassword(uid);
	}
}
