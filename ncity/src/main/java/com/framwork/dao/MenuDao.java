package com.framwork.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 菜单dao
 * @author YXD110
 *
 */
@Repository
public class MenuDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询菜单
	 */
	public List queryMenu(){
		String sql = " select m.id,m.`name`,m.icon,m.`level`,m.parent_id,m.order_,m.url from "+
					 " t_menu m where m.flag=1 and m.id != 0 order by m.order_ ";
		List list = jdbcTemplate.queryForList(sql);
		List newMenu = this.getMenu(list, "0");
		List reuslt = new ArrayList();
		Map menuMap = new HashMap();
		menuMap.put("id", "0");
		menuMap.put("spread", false);
		menuMap.put("name", "菜单");
		menuMap.put("level", 0);
		menuMap.put("children", newMenu);
		reuslt.add(menuMap);
		return reuslt;
	}
	
	/**
	 * 构建菜单
	 */
	public List getMenu(List menu,String parentId){
		List newMenu = new ArrayList();
		for (int i = 0; i < menu.size(); i++) {
			Map menuMap = new HashMap();
			Map<String,Object> map = (Map)menu.get(i);
			String pId = map.get("parent_id").toString();
			if(parentId.equals(pId)){
				menuMap.put("id", map.get("id"));
				menuMap.put("spread", false);
				menuMap.put("level", map.get("level"));
				menuMap.put("name", map.get("name"));
				List childs = getMenu(menu,  map.get("id").toString());
				menuMap.put("children", childs);
				newMenu.add(menuMap);
			}
		}
		return newMenu;
	};
	
	
	/**
	 * 查询菜单
	 */
	public List queryMenuRole(int rid){
		String sql = " select  m.id,m.`name`,m.icon,m.`level`,m.parent_id,m.order_,m.url,"+
					" (select if(count(1)=1,'true','false') from t_role_menu t where t.menu_id=m.id and t.role_id="+rid+") as checked "+
					" from t_menu m where m.flag=1 and m.id != 0 order by m.order_";
		List list = jdbcTemplate.queryForList(sql);
		List newMenu = this.getMenuRole(list, "0");
		List reuslt = new ArrayList();
		Map menuMap = new HashMap();
		menuMap.put("value", "0");
		menuMap.put("checked", "true");
		menuMap.put("title", "菜单");
		menuMap.put("data", newMenu);
		reuslt.add(menuMap);
		return reuslt;
	}
	
	/**
	 * 构建菜单
	 */
	public List getMenuRole(List menu,String parentId){
		List newMenu = new ArrayList();
		for (int i = 0; i < menu.size(); i++) {
			Map menuMap = new HashMap();
			Map<String,Object> map = (Map)menu.get(i);
			String pId = map.get("parent_id").toString();
			if(parentId.equals(pId)){
				menuMap.put("value", map.get("id"));
				if(map.get("checked").toString().equals("true")){
					menuMap.put("checked", map.get("checked"));
				}
				menuMap.put("title", map.get("name"));
				List childs = getMenuRole(menu,  map.get("id").toString());
				menuMap.put("data", childs);
				newMenu.add(menuMap);
			}
		}
		return newMenu;
	};
	
	/**
	 * 根据ID查询菜单
	 */
	public Map queryMenuById(int mid){
		String sql = " select t.*,m.`name` as parentName from t_menu t,t_menu m where t.parent_id=m.id and t.flag=1 and t.id="+mid;
		Map menu = jdbcTemplate.queryForMap(sql);
		return menu;
	}
	
	/**
	 * 保存菜单
	 */
	public void addMenu(Map<String,Object> menuMap,int uid){
		
		String sqlMenu = " select t.* from t_menu t where  t.id="+menuMap.get("parentId");
		Map map = jdbcTemplate.queryForMap(sqlMenu);
		int level = Integer.parseInt(map.get("level").toString());
		
		level += 1;
		
		String sql = " insert into t_menu(name,parent_id,order_,level,url,icon,create_time,update_time,creator,flag)"
				+ " values('"+menuMap.get("name")+"','"+menuMap.get("parentId")+"','"+menuMap.get("order_")+"','"+level+"','"+menuMap.get("url")+"',"
						+ "'"+menuMap.get("icon")+"','"+new Date().getTime()+"','"+new Date().getTime()+"','"+uid+"','1')";
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 删除菜单
	 */
	public void delMenu(int mid){
		String sql = " update t_menu set flag=0,update_time='"+new Date().getTime()+"' where id="+mid;
		String sqlParent = " update t_menu set flag=0,update_time='"+new Date().getTime()+"' where parent_id="+mid;
		jdbcTemplate.execute(sql);
		jdbcTemplate.execute(sqlParent);
	}
	
	/**
	 * 更新菜单
	 */
	public void updateMenu(Map<String,Object> menuMap){
		String sql = " update t_menu set update_time='"+new Date().getTime()+"'";
		String whereSql = "";
		if(menuMap.get("name") != null){
			whereSql += ",name='"+menuMap.get("name")+"'";
		}
		if(menuMap.get("icon") != null){
			whereSql += ",icon='"+menuMap.get("icon")+"'";
		}
		if(menuMap.get("order_") != null){
			whereSql += ",order_='"+menuMap.get("order_")+"'";
		}
		if(menuMap.get("url") != null){
			whereSql += ",url='"+menuMap.get("url")+"'";
		}
		
		sql = sql + whereSql + " where id="+menuMap.get("id");
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 保存角色菜单
	 */
	public void saveRoleMenu(String menuids,int roleid,int uid){
		String sql = " delete from t_role_menu where role_id="+roleid;
		jdbcTemplate.execute(sql);
		
		String [] menuid = menuids.split(",");
		for (int i = 0; i < menuid.length; i++) {
			sql = " insert into t_role_menu(menu_id,role_id,create_time,update_time,creator) values('"+menuid[i]+"','"+roleid+"','"+new Date().getTime()+"','"+new Date().getTime()+"','"+uid+"')";
			jdbcTemplate.execute(sql);
		}
	}
}
