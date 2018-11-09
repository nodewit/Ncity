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
import com.manager.service.AppSuggestService;
import com.manager.utils.Page;


/**
 * 反馈意见
 * @author 艾克
 * 2018年11月6日 15点11分
 */
@RestController
public class AppSuggestAction {
	
	@Autowired
	private AppSuggestService appSuggestService;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 分页查询反馈意见
	 */
	@RequestMapping("/view/manager/querySuggestByPage")
	public Map<String, Object> queryTimeMachineByPage(HttpServletRequest request,@RequestParam Map queryParams){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page page = appSuggestService.querySuggestByPage(Integer.parseInt(queryParams.get("page").toString()), 
					Integer.parseInt(queryParams.get("limit").toString()), queryParams);
			result.put("count", page.getTotalRow());
			result.put("data", page.getList());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询反馈意见",request);
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
