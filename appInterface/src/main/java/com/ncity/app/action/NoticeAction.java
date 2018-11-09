package com.ncity.app.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncity.app.bean.ResponseBean;
import com.ncity.app.entity.NoticeEntity;
import com.ncity.app.service.NoticeService;
import com.ncity.app.uitls.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 通知服务
 * @author 艾克
 * 2018年10月11日 16点04分
 */
@Api(value = "通知服务接口", tags = {"通知服务接口"})
@Controller
@RequestMapping("/")
public class NoticeAction {
	
	private Logger log = Logger.getLogger(NoticeAction.class);
	
	@Autowired
	private NoticeService noticeService;

	/**
	 * 通知列表
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @param type
	 * @param createTime
	 * @return
	 */
	@ApiOperation(value = "通知分页接口", notes = "通知分页接口", response = NoticeEntity.class)	
	@ApiImplicitParams({
		@ApiImplicitParam(value="页数", required=true, dataType="int", paramType="query", name="pageNumber"),
		@ApiImplicitParam(value="每页数量", required=true, dataType="int", paramType="query", name="pageSize"),
		@ApiImplicitParam(value="类型", required=false, dataType="int", paramType="query", name="type"),
		@ApiImplicitParam(value="创建时间", required=false, dataType="long", paramType="query", name="createTime"),
		@ApiImplicitParam(value="标题", required=false, dataType="Sting", paramType="query", name="title")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseBean queryListByPage(int pageNumber, int pageSize, 
			String title, Integer type, Long createTime) {
		log.info("pageNumber:"+pageNumber+"pageSize:"+pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			if(title != null){
				params.put("title", title);
			}
			if(type != null){
				params.put("type", type);
			}
			if(createTime != null){
				params.put("createTime", createTime);
			}
			List<NoticeEntity> list = noticeService.queryListByPage(pageNumber, pageSize, params);
			if(list == null){
				list = new ArrayList<NoticeEntity>();
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
	 *返回最新消息标题通知
	 * @return
	 */
	@ApiOperation(value="通知滚动的接口",notes="通知滚动的接口",response = NoticeEntity.class)
	@ApiImplicitParam(value="标题", required=false, dataType="Sting", paramType="query", name="title")
	@ApiResponses({
			@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryListTitle", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean queryListTitle(){
		log.info("进入查询通知");
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			NoticeEntity noticeEntity = noticeService.queryListTitle();
			log.info("Title:"+noticeEntity.getTitle());
			if(noticeEntity == null){
				noticeEntity = new NoticeEntity();
			}
			result.setData(noticeEntity);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 通知实体
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通知实体接口", notes = "通知实体接口",  response = NoticeEntity.class)	
	@ApiImplicitParam(value="通知ID", name="id", paramType="path", required=true)
	@ApiResponses({
		@ApiResponse(code = 200, message = "{\"code\":0,\"message\":\"返回信息\",\"data\":\"返回值\"}"),
	})
	@RequestMapping(value = "/queryNoticeEntityById/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseBean queryNoticeEntityById(@PathVariable Long id) {
		ResponseBean result = new ResponseBean();
		result.setCode(Constants.SYS_NORMAL);
		result.setMessage("查询成功");
		try {
			NoticeEntity noticeEntity = noticeService.queryNoticeEntityById(id);
			if(noticeEntity == null){
				noticeEntity = new NoticeEntity();
			}
			result.setData(noticeEntity);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Constants.SYS_ERROR);
			result.setMessage("操作过快，请稍后在试");
			log.error(e.getMessage());
		} 
		return result;
	}
	
}
