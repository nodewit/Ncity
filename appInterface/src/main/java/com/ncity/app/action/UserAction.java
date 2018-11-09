package com.ncity.app.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncity.app.bean.ResponseBean;
import com.ncity.app.entity.ActivityIntegralEntity;
import com.ncity.app.entity.CommentEntity;
import com.ncity.app.service.UserService;
import com.ncity.app.uitls.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 用户操作服务
 * @author 艾克
 * 2018年10月11日 16点04分
 */
@Api(value = "用户操作服务接口", tags = {"用户操作服务接口"})
@Controller
@RequestMapping("/user")
public class UserAction {
	
	private Logger log = Logger.getLogger(UserAction.class);
	@Autowired
	private UserService userService;
	/**
	 * 灵力日志分页列表
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @param type
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "灵力日志分页接口", notes = "灵力日志分页接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="页数", required=true, dataType="int", paramType="query", name="pageNumber"),
		@ApiImplicitParam(value="每页数量", required=true, dataType="int", paramType="query", name="pageSize"),
		@ApiImplicitParam(value="uuid", required=true, dataType="long", paramType="query", name="uuid"),
		@ApiImplicitParam(value="创建时间", required=false, dataType="long", paramType="query", name="createTime")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryListByPage(int pageNumber, int pageSize, String uuid, Long createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if(uuid != null){
				params.put("uuid", uuid);
			}
			if(createTime != null){
				params.put("createTime", createTime);
			}
			List<ActivityIntegralEntity> list = userService.queryActivityIntegralByPage(pageNumber, pageSize, params);
			if(list == null){
				list = new ArrayList<ActivityIntegralEntity>();
			}
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}
	
	/**
	 * 是否今日签到接口
	 * @return
	 */
	@ApiOperation(value="是否今日签到接口",notes="是否今日签到接口")
	@ApiImplicitParam(value="uuid", required=false, dataType="Sting", paramType="query", name="uuid")
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/isSignIn", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean isSignIn(String uuid){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			result.setData(userService.isSignIn(uuid));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 签到接口
	 * @return
	 */
	@ApiOperation(value="签到接口",notes="签到接口")
	@ApiImplicitParam(value="uuid", required=false, dataType="Sting", paramType="query", name="uuid")
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/signIn", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean signIn(String uuid){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			userService.saveActivityIntegral(uuid, Constants.SIGN_IN, "sign_in", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 意见反馈接口
	 * @return
	 */
	@ApiOperation(value="意见反馈接口",notes="意见反馈接口")
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/suggest", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean suggest(@RequestBody Map<String, Object> params){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			userService.saveSuggest(params.get("uuid").toString(), params.get("content").toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 检测版本接口
	 * @return
	 */
	@ApiOperation(value="检测版本接口",notes="检测版本接口")
	@ApiImplicitParam(value="android/ios", required=true, dataType="Sting", paramType="query", name="type")
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryVersion", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryVersion(String type){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			result.setData(userService.queryVersion(type));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 评论分页列表
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @param type
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "评论分页接口", notes = "评论分页接口")	
	@ApiImplicitParams({
		@ApiImplicitParam(value="页数", required=true, dataType="int", paramType="query", name="pageNumber"),
		@ApiImplicitParam(value="每页数量", required=true, dataType="int", paramType="query", name="pageSize"),
		@ApiImplicitParam(value="类型（1-时光机 2-一诺千金）", required=true, dataType="int", paramType="query", name="type"),
		@ApiImplicitParam(value="对象ID", required=true, dataType="long", paramType="query", name="objectId"),
		@ApiImplicitParam(value="创建时间", required=false, dataType="long", paramType="query", name="createTime")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryCommentListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryCommentListByPage(int pageNumber, int pageSize, Integer type, Long objectId, Long createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if(type != null){
				params.put("type", type);
			}
			if(objectId != null){
				params.put("objectId", objectId);
			}
			if(createTime != null){
				params.put("createTime", createTime);
			}
			List<CommentEntity> list = userService.queryCommentListByPage(pageNumber, pageSize, params);
			if(list == null){
				list = new ArrayList<CommentEntity>();
			}
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}
	
	/**
	 * 点赞接口
	 * @return
	 */
	@ApiOperation(value="点赞接口",notes="点赞接口")
	@ApiImplicitParams({
		@ApiImplicitParam(value="uuid", required=true, dataType="Sting", paramType="query", name="uuid"),
		@ApiImplicitParam(value="点赞（1-点赞 0-取消）", required=true, dataType="int", paramType="query", name="like"),
		@ApiImplicitParam(value="类型（1-时光机 2-一诺千金）", required=true, dataType="long", paramType="query", name="typeId"),
		@ApiImplicitParam(value="对象ID", required=true, dataType="long", paramType="query", name="objectId")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/like", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean like(String uuid, Integer like, Long typeId, Long objectId){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			userService.saveLike(uuid, like, typeId, objectId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 评论接口
	 * @return
	 */
	@ApiOperation(value="评论接口",notes="评论接口")
	/*@ApiImplicitParams({
		@ApiImplicitParam(value="uuid", required=true, dataType="Sting", paramType="body", name="uuid"),
		@ApiImplicitParam(value="被评论的ID", required=false, dataType="long", paramType="body", name="toId"),
		@ApiImplicitParam(value="类型（1-时光机 2-一诺千金）", required=true, dataType="long", paramType="body", name="typeId"),
		@ApiImplicitParam(value="对象ID", required=true, dataType="long", paramType="body", name="objectId"),
		@ApiImplicitParam(value="评论", required=true, dataType="long", paramType="body", name="comment")
	})*/
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/comment", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean comment(@RequestBody Map<String, Object> params){
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			userService.saveComment(params.get("uuid").toString(), Long.parseLong(params.get("typeId").toString()), 
					Long.parseLong(params.get("objectId").toString()), Long.parseLong(params.get("toId")==null?"0":params.get("toId").toString()),  params.get("comment").toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}
}
