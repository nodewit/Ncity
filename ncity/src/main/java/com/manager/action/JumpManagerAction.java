package com.manager.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转Action
 * @author 艾克
 * 2018年11月6日 14点48分
 */
@Controller
public class JumpManagerAction {
	
	
	/**
	 * 进入用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/user")
	public String user(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/user";
	}
	
	/**
	 * 进入灵钻日志
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/diamond")
	public String diamond(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/diamond";
	}
	
	/**
	 * 进入灵力日志
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/integral")
	public String integral(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/integral";
	}
	
	/**
	 * 进入时光机
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/timeMachine")
	public String timeMachine(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/timeMachine";
	}
	
	/**
	 * 进入时光机
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/timeMachineInfo")
	public String timeMachineInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "view/manager/timeMachineInfo";
	}
	
	/**
	 * 进入一诺千金
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/promise")
	public String promise(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/promise";
	}
	
	/**
	 * 进入反馈意见
	 * 
	 * @return
	 */
	@RequestMapping("/view/manager/suggest")
	public String suggest(HttpServletRequest request, HttpServletResponse response) {
		return "view/manager/suggest";
	}
	
}
