package com.framwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framwork.dao.OrgDao;

/**
 * 机构
 * @author YXD110
 *
 */
@Service
public class OrgService {
	
	@Autowired
	private OrgDao orgDao;
	
	
	/**
	 * 查询菜单
	 */
	public List queryOrg(){
		return orgDao.queryOrg();
	}
	
	/**
	 * 更新机构
	 */
	public void updateOrg(Map<String,Object> orgMap){
		orgDao.updateOrg(orgMap);
	}
	
	/**
	 * 删除机构
	 */
	public void delOrg(String orgCode){
		orgDao.delOrg(orgCode);
	}
	
	/**
	 * 新增机构
	 */
	public void addOrg(Map<String,Object> orgMap,int uid){
		orgDao.addOrg(orgMap, uid);
	}
	
	/**
	 * 根据ID查询机构
	 */
	public Map queryOrgByCode(String orgCode){
		return orgDao.queryOrgByCode(orgCode);
	}
}
