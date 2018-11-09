package com.ncity.app.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ncity.app.entity.ChainEntity;
import com.ncity.app.uitls.JpaUtils;
import com.ncity.app.uitls.Page;

/**
 * 区块数据操作
 * @author 艾克
 * 2018年10月23日 15点56分
 */
@Repository
public class ChainDao extends JpaUtils{

	
	/**
	 * 分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param params 
	 * @return
	 */
	public Page<ChainEntity> page(int pageNumber, int pageSize, Map<String, Object> params) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_chain t where t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_chain t where t.flag=1 ");
		if(params.get("uuid") != null && params.get("uuid").toString().length() != 0){
			nativeSQL.append(" and t.uuid = :uuid ");
			nativeCountSQL.append(" and t.uuid = :uuid ");
		}
		if(params.get("type") != null && params.get("type").toString().length() != 0){
			nativeSQL.append(" and t.type = :type ");
			nativeCountSQL.append(" and t.type = :type ");
		}
		if(params.get("typeId") != null && params.get("typeId").toString().length() != 0){
			nativeSQL.append(" and t.type_id = :typeId ");
			nativeCountSQL.append(" and t.type_id = :typeId ");
		}
		if(params.get("createTime") != null && params.get("createTime").toString().length() != 0){
			nativeSQL.append(" and t.create_time = :createTime ");
			nativeCountSQL.append(" and t.create_time = :createTime ");
		}
		nativeSQL.append(" order by t.create_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), ChainEntity.class, pageNumber, pageSize, params);
	}
	
	/**
	 * 查询实体
	 * @param id 实体ID
	 * @param uuid 用户uuid
	 * @return
	 */
	public ChainEntity queryChainById(long id,String uuid) {
		return this.findFirst(" select * from t_chain t where t.flag=1 and t.id=? and t.uuid=? ", ChainEntity.class, id,uuid);
	}
	
}
