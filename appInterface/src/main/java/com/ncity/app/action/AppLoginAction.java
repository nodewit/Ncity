package com.ncity.app.action;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncity.app.bean.ResponseBean;
import com.ncity.app.entity.AppUserEntity;
import com.ncity.app.service.UserService;
import com.ncity.app.uitls.Constants;
import com.ncity.app.uitls.RedisUtils;
import com.ncity.app.yunpian.YunpianClientApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * APP登录服务
 * @author 艾克 
 * 2018年10月15日 11点47分
 */
@Api(value = "APP登录服务接口", tags = {"APP登录服务接口"})
@Controller
@RequestMapping("/")
public class AppLoginAction {

	private Logger log = Logger.getLogger(AppLoginAction.class);
	@Autowired
	private YunpianClientApi api;
	@Value("${min}")
	private int min;
	@Autowired
	private UserService userService;


	/**
	 * 手机登录
	 * @param mobile
	 * @param code
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "手机登录接口", notes = "手机登录接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="手机号", required=true, dataType="String", paramType="query", name="mobile"),
		@ApiImplicitParam(value="验证码", required=true, dataType="String", paramType="query", name="code")
	})
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@ResponseBody
	public ResponseBean login(String mobile, String code, HttpServletRequest reuqset) {
		String ua = reuqset.getHeader("UA");
		ResponseBean result = new ResponseBean();
		try {
			Map<String, Object> map = userService.login(mobile, code, ua);
			result.setCode(Integer.parseInt(map.get("code").toString()));
			result.setMessage(map.get("message").toString());
			result.setData(map.get("data"));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}

	/**
	 * 微信登录
	 * @param openid
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "微信登录接口", notes = "微信登录接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="微信openid", required=true, dataType="String", paramType="body", name="openid")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/wxLogin", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean wxLogin(@RequestBody String openid, HttpServletRequest reuqset) {
		String ua = reuqset.getHeader("UA");
		ResponseBean result = new ResponseBean();
		try {
			Map<String, Object> map = userService.wxLogin(openid, ua);
			result.setCode(Integer.parseInt(map.get("code").toString()));
			result.setMessage(map.get("message").toString());
			result.setData(map.get("data"));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}

	/**
	 * 发送验证码
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "发送验证码接口", notes = "发送验证码接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="手机号", required=true, dataType="String", paramType="body", name="mobile")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/send", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean send(@RequestBody String mobile) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("发送成功");

		try {
			String code = YunpianClientApi.random();
			RedisUtils.set(mobile, code, min, TimeUnit.MINUTES);
			log.info("验证码："+code);
			/*int code = api.send(mobile);
			if(code != 0){
				result.setCode(Constants.SYS_ERROR);
				result.setMessage("发送失败");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}

	/**
	 * 注册
	 * @param appUserEntity
	 * @param code
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "注册接口", notes = "注册接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="用户实体", required=true, dataType="AppUserEntity", paramType="body", name="appUserEntity"),
		@ApiImplicitParam(value="验证码", required=true, dataType="String", paramType="query", name="code")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean register(@RequestBody AppUserEntity appUserEntity, String code, HttpServletRequest reuqset) {
		String ua = reuqset.getHeader("UA");
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("注册成功");
		try {
			Map<String, Object> map = userService.register(appUserEntity, code, ua);
			result.setCode(Integer.parseInt(map.get("code").toString()));
			result.setMessage(map.get("message").toString());
			result.setData(map.get("data"));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}

	/**
	 * 绑定
	 * @param appUserEntity
	 * @param code
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "绑定接口", notes = "绑定接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value="用户实体", required=true, dataType="AppUserEntity", paramType="body", name="appUserEntity"),
			@ApiImplicitParam(value="验证码", required=true, dataType="String", paramType="query", name="code")
	})
	@ApiResponses({
			@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/bind", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean bind(@RequestBody AppUserEntity appUserEntity, String code, HttpServletRequest reuqset) {
		String ua = reuqset.getHeader("UA");
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("绑定成功");
		try {
			Map<String, Object> map = userService.bind(appUserEntity, code, ua);
			result.setCode(Integer.parseInt(map.get("code").toString()));
			result.setMessage(map.get("message").toString());
			result.setData(map.get("data"));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}


	/**
	 * 用户信息查询接口
	 *
	 * @param reuqset
	 * @return
	 */
	@ApiOperation(value = "用户信息查询接口", notes = "用户信息查询接口")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "用户UUID", required = true, dataType = "String", paramType = "query", name = "uuid") })
	@RequestMapping(value = "/queryUserEntityByUuid", method = { RequestMethod.POST })
	@ApiResponses({ @ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"), })
	@ResponseBody
	public ResponseBean queryUserEntityByUuid(String uuid) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("访问成功");
		try {
			AppUserEntity user = userService.queryUserEntityByUuid(uuid);
			result.setData(user);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
}
