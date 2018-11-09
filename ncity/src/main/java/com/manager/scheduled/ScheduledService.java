package com.manager.scheduled;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.manager.bean.AppParameterEntity;
import com.manager.bean.AppUserEntity;
import com.manager.bean.DiamondLogEntity;
import com.manager.service.AppParamsService;
import com.manager.service.AppUserService;

/**
 * 定时生成灵钻
 * 
 * @author 艾克 2018年10月18日 10点02分
 */
@Component
public class ScheduledService {
	private Logger log = Logger.getLogger(ScheduledService.class);
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private AppParamsService appParamsService;
	// 初始公钥
	private String initPublicKey = "7thwLyeUGZVWULqjSCE6eXMHeSuxy9Kg1chwbw7sFfr3";
	// 访问地址
	@Value("${url}")
	private String path;

	/*@Scheduled(cron = "0 0 0/1 * * *")
	public void scheduled() {
		log.info("《--------灵钻生成开始--------》");
		// 剩余灵钻
		double diamond = ScheduledService.sum(path, initPublicKey);
		if(diamond > 0){
			//获取app配置参数
			AppParameterEntity params = appParamsService.queryParameterByField("produced_power");
			AppParameterEntity daySum = appParamsService.queryParameterByField("day_sum");
			AppParameterEntity noReceive = appParamsService.queryParameterByField("no_receive");
			int value = Integer.parseInt(params.getValue());
			double sum = Double.parseDouble(daySum.getValue());
			double nr = Double.parseDouble(noReceive.getValue());
			//有效用户灵力
			long total = appUserService.queryUserIntegralTotal();
			//总未领取的灵钻 
			double diamondSum =  appUserService.queryDiamond(null, 0, 1);
			//用户未领取灵钻是否大于剩余灵钻
			if(diamondSum < diamond) {
				List<AppUserEntity> userList = appUserService.queryUserList();
				for (int k = 0; k < userList.size(); k++) {
					//用户灵力是否大于生成灵钻条件
					if(userList.get(k).getIntegral() >= value){
						//用户未领取的灵钻 
						double userSum =  appUserService.queryDiamond(userList.get(k).getUuid(), 0, 1);
						//用户未领取灵钻达到一定量，将不再生成
						if(userSum < nr){
							double diamondCount = sum*userList.size()*(userList.get(k).getIntegral()/Double.parseDouble(total+""))/(double)24;
							if(diamondCount < (diamond-diamondSum)){
								DiamondLogEntity diamondLog = new DiamondLogEntity();
								diamondLog.setCreateTime(new Date().getTime());
								diamondLog.setFlag(1);
								diamondLog.setDiamond(diamondCount);
								diamondLog.setUuid(userList.get(k).getUuid());
								diamondLog.setType(1);
								diamondLog.setReceive(0);
								diamondLog.setUpdateTime(new Date().getTime());
								appUserService.saveDiamondLog(diamondLog);
							}
						}
					}
				}
			}
		}
		log.info("《--------灵钻生成结束--------》");
	}*/


	/**
	 * 获取用户灵钻总量
	 * @param path
	 * @param publicKey
	 * @return
	 */
	public static double sum(String path, String publicKey) {
		// 用户灵钻总量
		double num = 0;
		// 未花费
		String noSpent = HttpRequest.get(path + "/api/v1/outputs?public_key=" + publicKey + "&spent=false").body();
		List<Map<String, Object>> noSpentList = (List<Map<String, Object>>) JSONArray.parse(noSpent);
		if(noSpentList != null){
			String transaction_id = noSpentList.get(0).get("transaction_id").toString();
			//查询总量
			String metaData = HttpRequest.get(path + "/api/v1/metadata?search=" + transaction_id).body();
			JSONArray list = JSONArray.parseArray(metaData);
			JSONObject map = (JSONObject) list.get(0);
			JSONObject json = JSONObject.parseObject(map.get("metadata").toString());
			String total = json.getString("value");
			num = Double.parseDouble(total);
		}
		return num;
	}
}
