package com.ncity.app.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ncity.app.entity.NoticeEntity;
import com.ncity.app.uitls.JpaUtils;
import com.ncity.app.uitls.Page;

/**
 * 通知数据操作
 * @author 艾克
 * 2018年10月11日 16点04分
 */
@Repository
public class NoticeDao extends JpaUtils{
	
	/**
	 * 分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param params 
	 * @return
	 */
	public Page<NoticeEntity> page(int pageNumber, int pageSize, Map<String, Object> params) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_notice t where t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_notice t where t.flag=1 ");
		if(params.get("title") != null && params.get("title").toString().length() != 0){
			nativeSQL.append(" and t.title like '%'||:title||'%' ");
			nativeCountSQL.append(" and t.title like '%'||:title||'%' ");
		}
		if(params.get("type") != null && params.get("type").toString().length() != 0){
			nativeSQL.append(" and t.type = :type ");
			nativeCountSQL.append(" and t.type = :type ");
		}
		if(params.get("createTime") != null && params.get("createTime").toString().length() != 0){
			nativeSQL.append(" and t.create_time = :createTime ");
			nativeCountSQL.append(" and t.create_time = :createTime ");
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), NoticeEntity.class, pageNumber, pageSize, params);
	}

	/**
	 * 返回最新消息标题通知
	 * @return
	 */
	public NoticeEntity queryListTitle(){
		String sql="SELECT * FROM t_notice t ORDER BY id DESC LIMIT 1";
		return this.findFirst(sql,NoticeEntity.class);
	}
	
	/**
	 * 返回通知实体
	 * @param id
	 * @return
	 */
	public NoticeEntity queryNoticeEntityById(Long id){
		String sql = " select * from t_notice t where t.flag=1 and t.id = ? ";
		return this.findFirst(sql, NoticeEntity.class, id);
	}
	
}
