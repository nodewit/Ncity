package com.manager.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.framwork.bean.PageBean;
import com.framwork.service.UserService;
import com.framwork.utils.RedisUtils;
import com.manager.bean.ChainEntity;
import com.manager.service.AppChainService;
import com.manager.utils.Constants;
import com.manager.utils.Page;


/**
 * 内容数据
 * @author 艾克
 * 2018年11月6日 15点11分
 */
@RestController
public class AppChainAction {
	
	@Autowired
	private AppChainService appChainService;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 分页查询时光机
	 */
	@RequestMapping("/view/manager/queryChainByPage")
	public Map<String, Object> queryChainByPage(HttpServletRequest request,@RequestParam Map queryParams){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page page = appChainService.queryChainByPage(Integer.parseInt(queryParams.get("page").toString()), 
					Integer.parseInt(queryParams.get("limit").toString()), queryParams);
			result.put("count", page.getTotalRow());
			result.put("data", page.getList());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			if (queryParams.get("typeId") != null && queryParams.get("typeId").toString().equals(Constants.PROMISE + "")) {
				userService.addLogs(id + "", "查询一诺千金",request);
			}else{
				userService.addLogs(id + "", "查询时光机",request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("count", 0);
			result.put("data", new ArrayList());
			result.put("msg", "");
			result.put("code", 500);
		}
		return result;
	}
	
	/**
	 * 查询实体
	 */
	@RequestMapping("/view/manager/queryChainById")
	public Map<String, Object> queryChainById(HttpServletRequest request,long id){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ChainEntity chain = appChainService.queryChainById(id);
			result.put("data", chain);
			result.put("msg", "");
			result.put("code", 0);
			int uid = redisUtils.getUserId(request);
			userService.addLogs(uid + "", "查询时光机",request);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "查询失败");
			result.put("code", 500);
		}
		return result;
	}
	
	/**
	 * 分页查询灵钻
	 */
	@RequestMapping("/view/manager/queryDiamondByPage")
	public Map<String, Object> queryDiamondByPage(HttpServletRequest request,@RequestParam Map queryParams){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page page = appChainService.queryDiamondByPage(Integer.parseInt(queryParams.get("page").toString()), 
					Integer.parseInt(queryParams.get("limit").toString()), queryParams);
			result.put("count", page.getTotalRow());
			result.put("data", page.getList());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询灵钻",request);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("count", 0);
			result.put("data", new ArrayList());
			result.put("msg", "");
			result.put("code", 500);
		}
		return result;
	}
	
	/**
	 * 分页查询灵力
	 */
	@RequestMapping("/view/manager/queryIntegralByPage")
	public Map<String, Object> queryIntegralByPage(HttpServletRequest request,@RequestParam Map queryParams){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page page = appChainService.queryIntegralByPage(Integer.parseInt(queryParams.get("page").toString()), 
					Integer.parseInt(queryParams.get("limit").toString()), queryParams);
			result.put("count", page.getTotalRow());
			result.put("data", page.getList());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询灵力",request);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("count", 0);
			result.put("data", new ArrayList());
			result.put("msg", "");
			result.put("code", 500);
		}
		return result;
	}
}
