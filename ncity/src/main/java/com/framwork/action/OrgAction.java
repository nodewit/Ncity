package com.framwork.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framwork.service.OrgService;
import com.framwork.service.UserService;
import com.framwork.utils.RedisUtils;

/**
 * 机构
 * @author YXD110
 *
 */
@Controller
public class OrgAction {
	
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;
	
	/**
	 * 查询机构树
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/system/queryOrg")
	@ResponseBody
	public List queryOrg(HttpServletRequest request,
			HttpServletResponse response){
		try {
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询机构树",request);
			return orgService.queryOrg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据code查询机构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/system/queryOrgByCode")
	@ResponseBody
	public Map queryOrgByCode(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String orgCode){
		try {
			Map map = orgService.queryOrgByCode(orgCode);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "根据code查询机构",request);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 更新机构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/system/updateOrg")
	@ResponseBody
	public int updateOrg(HttpServletRequest request,
			HttpServletResponse response,@RequestParam Map<String,Object> orgMap){
		try {
			orgService.updateOrg(orgMap);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "更新机构",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 删除机构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/system/delOrg")
	@ResponseBody
	public int delOrg(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String orgCode){
		try {
			orgService.delOrg(orgCode);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "删除机构",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 新增机构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/system/addOrg")
	@ResponseBody
	public int addOrg(HttpServletRequest request,
			HttpServletResponse response,@RequestParam Map<String,Object> orgMap){
		try {
			int id = redisUtils.getUserId(request);
			orgService.addOrg(orgMap,id);
			userService.addLogs(id + "", "新增机构",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
