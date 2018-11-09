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
 * 机构
 * @author YXD110
 *
 */
@Repository
public class OrgDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 查询菜单
	 */
	public List queryOrg(){
		String parentSql = " select t.org_code,t.org_name,t.parent_org_code,t.order_,t.short_name,t.org_path,t.org_level "+
						   " from t_org t where t.flag=1 and t.org_code = '001' ";
		Map parent = jdbcTemplate.queryForMap(parentSql);
		String sql = " select t.org_code,t.org_name,t.parent_org_code,t.order_,t.short_name,t.org_path,t.org_level  "+
					 " from t_org t where t.flag=1 and t.org_code != '001' order by order_ ";
		List list = jdbcTemplate.queryForList(sql);
		List newOrg = this.getOrg(list,parent.get("org_code").toString());
		List reuslt = new ArrayList();
		Map orgMap = new HashMap();
		orgMap.put("id", parent.get("org_code"));
		orgMap.put("spread", "true");
		orgMap.put("name", parent.get("org_name"));
		orgMap.put("orgShortName", parent.get("short_name"));
		orgMap.put("orgPath", parent.get("org_path"));
		orgMap.put("level", parent.get("org_level"));
		orgMap.put("children", newOrg);
		reuslt.add(orgMap);
		return reuslt;
	}
	
	/**
	 * 构建菜单
	 */
	public List getOrg(List org,String parentId){
		List newOrg = new ArrayList();
		for (int i = 0; i < org.size(); i++) {
			Map orgMap = new HashMap();
			Map<String,Object> map = (Map)org.get(i);
			String pId = map.get("parent_org_code").toString();
			if(parentId.equals(pId)){
				orgMap.put("id", map.get("org_code"));
				orgMap.put("spread", "true");
				orgMap.put("orgShortName", map.get("short_name"));
				orgMap.put("orgPath", map.get("org_path"));
				orgMap.put("level", map.get("org_level"));
				orgMap.put("name", map.get("org_name"));
				List childs = getOrg(org,  map.get("org_code").toString());
				orgMap.put("children", childs);
				newOrg.add(orgMap);
			}
		}
		return newOrg;
	};
	
	/**
	 * 根据ID查询机构
	 */
	public Map queryOrgByCode(String orgCode){
		String sql = " select t.org_code,t.org_name,t.parent_org_code,t.order_,t.short_name,t.org_path,t.org_level,g.org_name as parentName "+
				     " from t_org t,t_org g where t.parent_org_code=g.org_code and t.flag=1 and t.org_code='"+orgCode+"'";
		Map map = jdbcTemplate.queryForMap(sql);
		return map;
	}
	
	/**
	 * 更新机构
	 */
	public void updateOrg(Map<String,Object> orgMap){
		String sqlOrg = " select t.* from t_org t where t.org_code='"+orgMap.get("parentOrgId")+"'";
		List list = jdbcTemplate.queryForList(sqlOrg);
		String path = "";
		if(list.size()>0){
			Map map = (Map)list.get(0);
			path = map.get("org_path").toString();
		}
		
		String sql = " update t_org set update_time="+new Date().getTime();
		if(orgMap.get("orgName") != null){
			sql += ",org_name='"+orgMap.get("orgName")+"'";
			sql += ",org_path='"+path+"/"+orgMap.get("orgName")+"'";
		}
		if(orgMap.get("orgShortName") != null){
			sql += ",short_name='"+orgMap.get("orgName")+"'";	
		}
		if(orgMap.get("order_") != null){
			sql += ",order_='"+orgMap.get("order_")+"'";
		}
		sql += " where org_code='"+orgMap.get("orgId")+"'";
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 删除机构
	 */
	public void delOrg(String orgCode){
		String sql = " update t_org set flag=0,update_time='"+new Date().getTime()+"' where org_code='"+orgCode+"'";
		String sqlParent = " update t_org set flag=0,update_time='"+new Date().getTime()+"' where parent_org_code='"+orgCode+"'";
		jdbcTemplate.execute(sql);
		jdbcTemplate.execute(sqlParent);
	}
	
	/**
	 * 新增机构
	 */
	public void addOrg(Map<String,Object> orgMap,int uid){
		String sqlOrg = " select t.* from t_org t where t.org_code='"+orgMap.get("parentOrgId")+"'";
		List list = jdbcTemplate.queryForList(sqlOrg);
		int level = 1;
		String path = "";
		if(list.size()>0){
			Map map = (Map)list.get(0);
			level = Integer.parseInt(map.get("org_level").toString());
			level += 1;
			path = map.get("org_path").toString();
			path = path +"/"+ orgMap.get("orgName").toString();
		}
		String sql1 = " select max(org_code) as code from t_org t where t.parent_org_code='"+orgMap.get("parentOrgId")+"'";
		List org = jdbcTemplate.queryForList(sql1);
		String code = "";
		Map map = (Map)org.get(0);
		if(map.get("code") != null){
			code = map.get("code").toString();
			long num = Long.parseLong(code);
			num += 1;
			code = "00"+num;
		}else{
			code = orgMap.get("parentOrgId")+"001";
		}
		
		String sql = " insert into t_org(org_code,org_name,parent_org_code,order_,short_name,org_path,org_level,create_time,update_time,creator,flag) "
				+ "values('"+code+"','"+orgMap.get("orgName")+"','"+orgMap.get("parentOrgId")+"','"+orgMap.get("order_")+"',"
						+ "'"+orgMap.get("orgShortName")+"','"+path+"','"+level+"','"+new Date().getTime()+"','"+new Date().getTime()+"','"+uid+"','1')";
		jdbcTemplate.execute(sql);
	}
}
