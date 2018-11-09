package com.framwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 系统管理跳转Action
 * @author YXD110
 *
 */


@Controller
public class SystemManagerAction {
	
	
	/**
	 * 进入用户页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/user")
	public String user(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/user";
	}
	
	
	/**
	 * 进入用户
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/editUser")
	public String editUser(Model model,HttpServletRequest request,HttpServletResponse response) {
		String userId = request.getParameter("userId");
		model.addAttribute("userId", userId);
		return "view/system/editUser";
	}
	
	/**
	 * 进入角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/role")
	public String role(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/role";
	}
	
	
	/**
	 * 进入角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/editRole")
	public String editRole(Model model,HttpServletRequest request,HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		model.addAttribute("roleId", roleId);
		return "view/system/editRole";
	}
	
	/**
	 * 进入用户-角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/user_role")
	public String user_role(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/user_role";
	}
	
	/**
	 * 进入菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/menu")
	public String menu(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/menu";
	}
	
	/**
	 * 进入分配菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/role_menu")
	public String role_menu(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/role_menu";
	}
	
	/**
	 * 进入机构页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/org")
	public String org(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/org";
	}
	

	/**
	 * 进入个人资料页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/userInfo")
	public String userInfo(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/userInfo";
	}
	

	/**
	 * 进入修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/password")
	public String password(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/changePwd";
	}
	
	/**
	 * 进入用户页面
	 * 
	 * @return
	 */
	@RequestMapping("/view/system/icon")
	public String icon(HttpServletRequest request,HttpServletResponse response) {
		return "view/system/icon";
	}
}
