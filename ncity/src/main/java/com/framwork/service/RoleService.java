package com.framwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framwork.bean.PageBean;
import com.framwork.dao.RoleDao;

/**
 * 角色service
 * @author YXD110
 *
 */
@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 分页查询角色
	 */
	public PageBean queryRole(PageBean pageBean){
		StringBuffer whereSql = new StringBuffer();
		Map queryMap = pageBean.getQueryParams();
		if(queryMap != null && queryMap.get("keyword") != null && !queryMap.get("keyword").toString().equals("")){
			whereSql.append(" and (t.role_name like '%").append(queryMap.get("keyword"))
			.append("%' or t.description like '%").append(queryMap.get("keyword")).append("%')");
		}
		String orderby = " ORDER BY t.create_time desc ";
		pageBean = roleDao.queryRole(pageBean, whereSql.toString(), orderby);
		return pageBean;
	}
	
	/**
	 * 保存角色
	 */
	public void addRole(Map<String,Object> roleMap,int uid){
		roleDao.addRole(roleMap, uid);
	}
	
	/**
	 * 更新角色
	 */
	public void updateRole(Map<String,Object> roleMap){
		roleDao.updateRole(roleMap);
	}
	
	/**
	 * 获取角色信息
	 */
	public Map getRoleInfo(int id){
		return roleDao.getRoleInfo(id);
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(String id){
		roleDao.deleteRole(id);
	}
	
	/**
	 * 查询所有角色
	 */
	public List queryRoleList(){
		return roleDao.queryRoleList();
	}
}
