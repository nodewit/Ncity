package com.framwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framwork.dao.MenuDao;

/**
 * 菜单service
 * @author YXD110
 *
 */
@Service
public class MenuService {
	
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 查询菜单
	 */
	public List queryMenu(){
		return menuDao.queryMenu();
	}
	
	/**
	 * 根据ID查询菜单
	 */
	public Map queryMenuById(int mid){
		return menuDao.queryMenuById(mid);
	}
	
	/**
	 * 保存菜单
	 */
	public void addMenu(Map<String,Object> menuMap,int uid){
		menuDao.addMenu(menuMap, uid);
	}
	
	/**
	 * 删除菜单
	 */
	public void delMenu(int mid){
		menuDao.delMenu(mid);
	}
	
	/**
	 * 更新菜单
	 */
	public void updateMenu(Map<String,Object> menuMap){
		menuDao.updateMenu(menuMap);
	}
	
	/**
	 * 查询菜单
	 */
	public List queryMenuRole(int rid){
		return menuDao.queryMenuRole(rid);
	}
	
	/**
	 * 保存角色菜单
	 */
	public void saveRoleMenu(String menuids,int roleid,int uid){
		menuDao.saveRoleMenu(menuids, roleid, uid);
	}
}
