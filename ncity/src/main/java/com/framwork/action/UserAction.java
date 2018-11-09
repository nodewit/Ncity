package com.framwork.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framwork.bean.PageBean;
import com.framwork.service.UserService;
import com.framwork.utils.MD5Utils;
import com.framwork.utils.RedisUtils;

/**
 * 用户action
 * 
 * @author YXD110
 * 
 */
@Controller
public class UserAction {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private UserService userService;

	/**
	 * 获取缓存中用户菜单信息
	 */
	@RequestMapping("/view/system/getUserInfo")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Cookie> cookieMap = redisUtils.readCookieMap(request);
		if (cookieMap.containsKey("sessionId")) {
			String sessionId = cookieMap.get("sessionId").getValue();
			if (sessionId != null && !sessionId.equals("")) {
				try {
					JSONObject json = new JSONObject(redisUtils.get(sessionId));
					return json.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	/**
	 * 更新个人信息
	 */
	@RequestMapping("/view/system/updateUserInfo")
	@ResponseBody
	public int updateUserInfo(HttpServletRequest request,
			@RequestParam Map<String, Object> userMap) {
		try {
			int id = redisUtils.getUserId(request);
			userService.updateUserInfo(userMap);
			userService.addLogs(id + "", "更新个人信息",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 更新密码
	 */
	@RequestMapping("/view/system/updatePassword")
	@ResponseBody
	public String updatePassword(HttpServletRequest request,
			@RequestParam Map<String, Object> userMap) {
		String msg = "";
		int id = redisUtils.getUserId(request);
		msg = userService.updatePassword(id, userMap.get("oldPwd").toString(),
				userMap.get("newPwd").toString());
		if (msg.equals("")) {
			userService.addLogs(id + "", "更新密码",request);
		}
		return msg;
	}

	/**
	 * 重置密码
	 */
	@RequestMapping("/view/system/resetPassword")
	@ResponseBody
	public int resetPassword(HttpServletRequest request,
			@RequestParam int uid) {
		try {
			userService.resetPassword(uid);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "重置用户密码",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping("/view/system/queryUserById")
	@ResponseBody
	public Map queryUserById(HttpServletRequest request,@RequestParam int uid) {
		Map uesrMap = userService.getUserInfo(uid);
		int id = redisUtils.getUserId(request);
		userService.addLogs(id + "", "查询用户信息",request);
		return uesrMap;
	}
	
	/**
	 * 查询用户信息
	 */
	@RequestMapping("/view/system/getUserInfo2")
	@ResponseBody
	public Map getUserInfo2(HttpServletRequest request) {
		int id = redisUtils.getUserId(request);
		Map uesrMap = userService.getUserInfo(id);
		userService.addLogs(id + "", "查询用户信息",request);
		return uesrMap;
	}
	
	/**
	 * 分页查询用户
	 */
	@RequestMapping("/view/system/queryUser")
	@ResponseBody
	public Map queryUser(HttpServletRequest request,@RequestParam Map queryParams){
		Map result = new HashMap();
		try {
			PageBean pageBean = new PageBean();
			pageBean.setPage(Integer.parseInt(queryParams.get("page").toString()));
			pageBean.setRows(Integer.parseInt(queryParams.get("limit").toString()));
			pageBean.setQueryParams(queryParams);
			pageBean = userService.queryUser(pageBean);
			result.put("count", pageBean.getTotal());
			result.put("data", pageBean.getPageData());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询用户信息",request);
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
	 * 删除用户
	 */
	@RequestMapping("/view/system/deleteUser")
	@ResponseBody
	public int deleteUser(HttpServletRequest request,
			@RequestParam String ids) {
		try {
			userService.deleteUser(ids);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "通过ID删除角色信息",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 判断是否存在
	 */
	@RequestMapping("/view/system/isLoginId")
	@ResponseBody
	public boolean isLoginId(HttpServletRequest request,
			@RequestParam String loginId) {
		return userService.isLoginId(loginId);
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping("/view/system/addUser")
	@ResponseBody
	public int addUser(HttpServletRequest request,
			@RequestParam Map userMap) {
		try {
			int id = redisUtils.getUserId(request);
			userService.addUser(userMap,id);
			userService.addLogs(id + "", "新增用户",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 解锁
	 */
	@RequestMapping("/view/system/checkPassword")
	@ResponseBody
	public Map checkPassword(HttpServletRequest request,@RequestParam int uid,@RequestParam String password) {
		Map result = new HashMap();
		Map uesrMap = userService.getUserInfo(uid);
		String pwd = uesrMap.get("password").toString();
		if(pwd.equals(MD5Utils.getMD5(password))){
			result.put("code",0);
		}else{
			result.put("code",-2);
		}
		int id = redisUtils.getUserId(request);
		userService.addLogs(id + "", "解锁",request);
		return result;
	}
	
}
