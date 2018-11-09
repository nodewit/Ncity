package com.ncity.app.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ncity.app.entity.DiamondLogEntity;
import com.ncity.app.uitls.Constants;
import com.ncity.app.uitls.JpaUtils;
import com.ncity.app.uitls.Page;

/**
 * 灵钻数据操作
 * @author 艾克
 * 2018年10月19日 14点54分
 */
@Repository
public class DiamondDao extends JpaUtils{
	
	
	/**
	 * 分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param params 
	 * @return
	 */
	public Page<DiamondLogEntity> page(int pageNumber, int pageSize, Map<String, Object> params) {
		StringBuilder nativeSQL = new StringBuilder(" select * from t_diamond_log t where t.flag=1 ");
		StringBuilder nativeCountSQL = new StringBuilder(" select count(1) from t_diamond_log t where t.flag=1 ");
		if(params.get("receive") != null && params.get("receive").toString().length() != 0){
			nativeSQL.append(" and t.receive = :receive ");
			nativeCountSQL.append(" and t.receive = :receive ");
		}
		if(params.get("uuid") != null && params.get("uuid").toString().length() != 0){
			nativeSQL.append(" and t.uuid = :uuid ");
			nativeCountSQL.append(" and t.uuid = :uuid ");
		}
		if(params.get("updateTime") != null && params.get("updateTime").toString().length() != 0){
			nativeSQL.append(" and t.update_time = :updateTime ");
			nativeCountSQL.append(" and t.update_time = :updateTime ");
		}
		nativeSQL.append(" order by t.update_time desc ");
		return this.paginate(false, nativeSQL.toString(), nativeCountSQL.toString(), DiamondLogEntity.class, pageNumber, pageSize, params);
	}
	
	/**
	 * 领取灵钻
	 * @param uuid
	 */
	public void updateDiamondLog(DiamondLogEntity diamondLogEntity) {
		diamondLogEntity.setReceive(Constants.RECEIVE);
		diamondLogEntity.setUpdateTime(new Date().getTime());
		this.saveOrUpdate(diamondLogEntity);
	}
	
	/**
	 * 查询实体
	 * @param diamondLogId
	 * @return
	 */
	public DiamondLogEntity queryDiamondLogById(int diamondLogId, String uuid) {
		return this.findFirst(" select * from t_diamond_log t where t.flag=1 and t.id=? and t.uuid=?", DiamondLogEntity.class, diamondLogId, uuid);
	}
}
