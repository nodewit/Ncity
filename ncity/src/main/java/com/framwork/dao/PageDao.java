package com.framwork.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framwork.bean.PageBean;

@Repository
public class PageDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 分页查询
	 * @param sql
	 * @param where
	 * @param orderby
	 * @return
	 */
	public PageBean queryPageByMysql(PageBean pageBean,String sql,String where,String orderby){
		int page = pageBean.getPage();
		int rows = pageBean.getRows();
		int startIndex = (page-1) * rows;
		String pageSql = sql+where+orderby+" limit "+startIndex+","+rows; 
		List list = jdbcTemplate.queryForList(pageSql);
		pageBean.setPageData(list);
		int total = this.queryTotal(sql, where);
		pageBean.setTotal(total);
		return pageBean;
	}
	
	/**
	 * 分页查询
	 * @param sql
	 * @param where
	 * @param orderby
	 * @return
	 */
	public PageBean queryPageByOracle(PageBean pageBean,String sql,String where,String orderby){
		int page = pageBean.getPage();
		int rows = pageBean.getRows();
		int startIndex = (page-1) * rows;
		int endIndex = page * rows;
		String pageSql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM ("+sql+where+orderby+") A WHERE ROWNUM <= "+endIndex+" ) WHERE RN > "+startIndex;
		List list = jdbcTemplate.queryForList(pageSql);
		pageBean.setPageData(list);
		int total = this.queryTotal(sql, where);
		pageBean.setTotal(total);
		return pageBean;
	}
	
	
	/**
	 * 查询总数量
	 * @param sql
	 * @param where
	 * @return
	 */
	public int queryTotal(String sql,String where){
		String totalSql = " select count(1) as total from ("+sql+where+") a ";
		List list = jdbcTemplate.queryForList(totalSql);
		Map map = (Map)list.get(0);
		return Integer.parseInt(map.get("total").toString());
	}

}
