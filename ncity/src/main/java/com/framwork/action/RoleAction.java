package com.framwork.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framwork.bean.PageBean;
import com.framwork.service.RoleService;
import com.framwork.service.UserService;
import com.framwork.utils.RedisUtils;

/**
 * 角色action
 * @author YXD110
 *
 */
@Controller
public class RoleAction {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	/**
	 * 分页查询角色
	 */
	@RequestMapping("/view/system/queryRole")
	@ResponseBody
	public Map queryRole(HttpServletRequest request,@RequestParam Map queryParams){
		Map result = new HashMap();
		try {
			PageBean pageBean = new PageBean();
			pageBean.setPage(Integer.parseInt(queryParams.get("page").toString()));
			pageBean.setRows(Integer.parseInt(queryParams.get("limit").toString()));
			pageBean.setQueryParams(queryParams);
			pageBean = roleService.queryRole(pageBean);
			result.put("count", pageBean.getTotal());
			result.put("data", pageBean.getPageData());
			result.put("msg", "");
			result.put("code", 0);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询角色信息",request);
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
	 * 新增角色
	 */
	@RequestMapping("/view/system/addRole")
	@ResponseBody
	public int addRole(HttpServletRequest request,@RequestParam Map roleMap){
		try {
			int id = redisUtils.getUserId(request);
			roleService.addRole(roleMap, id);
			userService.addLogs(id + "", "新增角色",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 更新角色
	 */
	@RequestMapping("/view/system/updateRole")
	@ResponseBody
	public int updateRole(HttpServletRequest request,@RequestParam Map roleMap){
		try {
			int id = redisUtils.getUserId(request);
			roleService.updateRole(roleMap);
			userService.addLogs(id + "", "更新角色",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	

	/**
	 * 查询通过ID角色信息
	 */
	@RequestMapping("/view/system/queryRoleById")
	@ResponseBody
	public Map queryRoleById(HttpServletRequest request,@RequestParam int rid){
		Map map = null;
		try {
			map = roleService.getRoleInfo(rid);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询通过ID角色信息",request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

	/**
	 * 删除角色信息
	 */
	@RequestMapping("/view/system/deleteRole")
	@ResponseBody
	public int deleteRole(HttpServletRequest request,@RequestParam String rid){
		try {
			roleService.deleteRole(rid);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "通过ID删除角色信息",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 查询所有角色
	 */
	@RequestMapping("/view/system/queryRoleList")
	@ResponseBody
	public List queryRoleList(HttpServletRequest request){
		try {
			List list = roleService.queryRoleList();
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询所有角色",request);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询所有角色-树
	 */
	@RequestMapping("/view/system/queryRoleTree")
	@ResponseBody
	public List queryRoleTree(HttpServletRequest request){
		try {
			List list = roleService.queryRoleList();
			List children = new ArrayList();
			List result = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				Map role = (Map)list.get(i);
				Map tree = new HashMap();
				tree.put("id", role.get("id"));
				tree.put("spread", "true");
				tree.put("name", role.get("role_name"));
				tree.put("level",1);
				children.add(tree);
			}
			Map treeMap = new HashMap();
			treeMap.put("id", "0");
			treeMap.put("spread", "true");
			treeMap.put("name", "角色");
			treeMap.put("level", 0);
			treeMap.put("children", children);
			result.add(treeMap);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询所有角色",request);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
