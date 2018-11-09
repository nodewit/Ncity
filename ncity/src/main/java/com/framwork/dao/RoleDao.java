package com.framwork.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framwork.bean.PageBean;

/**
 * 角色dao
 * @author YXD110
 *
 */
@Repository
public class RoleDao extends PageDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 分页查询角色
	 */
	public PageBean queryRole(PageBean pageBean,String where,String orderby){
		String sql = " select * from t_role t where t.flag=1 ";
		return this.queryPageByMysql(pageBean, sql, where, orderby);
	}
	
	/**
	 * 保存角色
	 */
	public void addRole(Map<String,Object> roleMap,int uid){
		String sql = " insert into t_role(role_name,description,create_time,update_time,creator,flag) values('"+roleMap.get("roleName")+"','"+roleMap.get("description")+"','"+new Date().getTime()+"','"+new Date().getTime()+"','"+uid+"','1') ";
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 更新角色
	 */
	public void updateRole(Map<String,Object> roleMap){
		String roleName = roleMap.get("roleName").toString();
		String description = roleMap.get("description").toString();
		String updateSql = "";
		if(roleName != null){
			updateSql += " role_name='"+roleName+"', ";
		}
		if(description != null){
			updateSql += " description='"+description+"', ";
		}
		updateSql += " update_time="+new Date().getTime();
		if(updateSql.length() > 2){
			String sql = " update t_role set "+updateSql+" where id="+roleMap.get("roleId");
			jdbcTemplate.execute(sql);
		}
	}
	
	/**
	 * 获取角色信息
	 */
	public Map getRoleInfo(int id){
		String sql = "  select * from t_role t where t.flag=1 and t.id="+id;
		Map roleMap = jdbcTemplate.queryForMap(sql);
		return roleMap;
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(String ids){
		String sql = "  update t_role set flag='0' where id in ("+ids+") ";
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 查询所有角色
	 */
	public List queryRoleList(){
		String sql = " select * from t_role t where t.flag=1 ";
		return jdbcTemplate.queryForList(sql);
	}
}
