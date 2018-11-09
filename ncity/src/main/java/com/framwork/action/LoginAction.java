package com.framwork.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framwork.service.UserService;
import com.framwork.utils.RedisUtils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;

/**
 * 登录
 * @author YXD110
 *
 */
@Controller
public class LoginAction {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtils redisUtils;
	
	private static int sessionExpires = 1;
	private static int captchaExpires = 3 * 60; // 超时时间3min
	private static int captchaW = 200;
	private static int captchaH = 60;

	
	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String login1(HttpServletRequest request,HttpServletResponse response) {
		return "login";
	}
	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		return "login";
	}

	/**
	 * 进入欢迎页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/welcome")
	public String welcome(HttpServletRequest request,HttpServletResponse response) {
		return "view/welcome/welcome";
	}
	
	/**
	 * 登陆操作
	 * 
	 * @param account 账户
	 * @param pwd 密码
	 * @param code 验证码
	 */
	@RequestMapping("/loginIn")
	@ResponseBody
	public Map index(HttpServletRequest request,HttpServletResponse response,
			String account,String pwd,String code) {
		Map result = new HashMap();
		Cookie verfivication = null;
		Map<String, Cookie> cookieMap = redisUtils.readCookieMap(request);
		if(cookieMap.containsKey("CaptchaCode")){  //判断cookieMap是否含有该key
			verfivication =  cookieMap.get("CaptchaCode");
			String temp = redisUtils.get(verfivication.getValue()).toString();
			if(temp == null || !temp.equals(code)){
				result.put("code", -1);
				result.put("msg", "验证码错误");
				return result;
			}
        }else{
        	result.put("code", -1);
			result.put("msg", "验证码错误");
			return result;
        }
		String msg = userService.login(account, pwd);
		if(!msg.equals("")){
			result.put("code", -1);
			result.put("msg", msg);
			return result;
		}else{
			Map user = userService.queryUserByLoginId(account);
			int count = Integer.parseInt(user.get("count").toString())+1;
			userService.updateCountAndTime(account, count);
			String uuid = UUID.randomUUID().toString();
			JSONObject json = new JSONObject(user);
			redisUtils.set(uuid, json.toString(),
						sessionExpires, TimeUnit.DAYS);
			Cookie cookie = new Cookie("sessionId", uuid);
			//设置过期时间
			cookie.setMaxAge(sessionExpires*60*60*24);
			response.addCookie(cookie);
			userService.addLogs(user.get("id").toString(), "登录成功",request);
		}
		if(verfivication != null){
			redisUtils.delete(verfivication.getValue());
		}
		
		result.put("code", 0);
		result.put("msg", "登录成功");
		return result;
	}
	
	/**
	 * 进入首页
	 * 
	 * @param account 账户
	 * @param pwd 密码
	 * @param code 验证码
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		return "view/index";
	}
	

	/**
	 * 退出操作
	 * 
	 * @param args
	 */
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Cookie> cookieMap = redisUtils.readCookieMap(request);
		if(cookieMap.containsKey("sessionId")){
			String sessionId = cookieMap.get("sessionId").getValue();
			if(sessionId != null && !sessionId.equals("")){
				try {
					JSONObject json = new JSONObject(redisUtils.get(sessionId));
					userService.addLogs(json.get("id").toString(), "退出成功",request);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			redisUtils.delete(cookieMap.get("sessionId").getValue());
			Cookie cookie = new Cookie("sessionId", "");
			response.addCookie(cookie);
        }
		return "login";
	}

	/**
	 * 验证码
	 * 
	 * @param args
	 */
	@RequestMapping("/verification")
	@ResponseBody
	public String verification(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Cookie> cookieMap = redisUtils.readCookieMap(request);
		if(cookieMap.containsKey("CaptchaCode")){  //判断cookieMap是否含有该key
			Cookie verfivication =  cookieMap.get("CaptchaCode");
			redisUtils.delete(verfivication.getValue());
        }
		// 生成验证码
		String uuid = UUID.randomUUID().toString();
		Captcha captcha = new Captcha.Builder(captchaW, captchaH).addText()
				.addBackground(new GradiatedBackgroundProducer())
				.gimp(new FishEyeGimpyRenderer()).build();

		// 将验证码以<key,value>形式缓存到redis
		redisUtils.set(uuid, captcha.getAnswer(),
				captchaExpires, TimeUnit.SECONDS);
		// 将验证码key，及验证码的返回
		Cookie cookie = new Cookie("CaptchaCode", uuid);
		response.addCookie(cookie);
		return captcha.getAnswer();
	}
	
	
}
