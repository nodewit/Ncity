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

import com.framwork.service.MenuService;
import com.framwork.service.UserService;
import com.framwork.utils.RedisUtils;

/**
 * 菜单action
 * @author YXD110
 *
 */
@Controller
public class MenuAction {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	
	/**
	 * 获取菜单
	 */
	@RequestMapping("/view/system/queryMenu")
	@ResponseBody
	public List queryMenu(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询菜单信息",request);
			return menuService.queryMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据ID查询菜单
	 */
	@RequestMapping("/view/system/queryMenuById")
	@ResponseBody
	public Map queryMenuById(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int mid) {
		try {
			Map map = menuService.queryMenuById(mid);
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询菜单信息",request);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 新增菜单
	 */
	@RequestMapping("/view/system/addMenu")
	@ResponseBody
	public int addMenu(HttpServletRequest request,
			HttpServletResponse response,@RequestParam Map<String,Object> menuMap) {
		try {
			int id = redisUtils.getUserId(request);
			menuService.addMenu(menuMap, id);
			userService.addLogs(id + "", "新增菜单",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping("/view/system/delMenu")
	@ResponseBody
	public int delMenu(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int mid) {
		try {
			int id = redisUtils.getUserId(request);
			menuService.delMenu(mid);
			userService.addLogs(id + "", "删除菜单",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 更新菜单
	 */
	@RequestMapping("/view/system/updateMenu")
	@ResponseBody
	public int updateMenu(HttpServletRequest request,
			HttpServletResponse response,@RequestParam Map<String,Object> menuMap) {
		try {
			int id = redisUtils.getUserId(request);
			menuService.updateMenu(menuMap);
			userService.addLogs(id + "", "更新菜单",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 获取菜单
	 */
	@RequestMapping("/view/system/queryMenuRole")
	@ResponseBody
	public List queryMenuRole(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int rid) {
		try {
			int id = redisUtils.getUserId(request);
			userService.addLogs(id + "", "查询菜单角色信息",request);
			return menuService.queryMenuRole(rid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存角色菜单
	 */
	@RequestMapping("/view/system/saveMenuRole")
	@ResponseBody
	public int saveRoleMenu(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String menuids,@RequestParam int rid) {
		try {
			int id = redisUtils.getUserId(request);
			menuService.saveRoleMenu(menuids, rid, id);
			userService.addLogs(id + "", "保存角色菜单",request);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
