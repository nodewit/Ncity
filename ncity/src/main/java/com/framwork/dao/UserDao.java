package com.framwork.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framwork.bean.PageBean;
import com.framwork.utils.MD5Utils;

/**
 * 用户dao
 * 
 * @author YXD110
 * 
 */
@Repository
public class UserDao extends PageDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login(String account, String pwd) {
		String sql = " select t.password from t_user t where t.flag=1 and t.login_id='"
				+ account + "' ";
		List list = jdbcTemplate.queryForList(sql);
		if (list.size() > 0) {
			Map passwordMap = (Map) list.get(0);
			if (passwordMap.get("password") != null
					&& !passwordMap.get("password").toString().equals("")) {
				String password = passwordMap.get("password").toString();
				if (!password.equals(MD5Utils.getMD5(pwd))) {
					return "密码错误。";
				}
			}
		} else {
			return "该账号不存在。";
		}
		return "";
	}

	/**
	 * 查询账号信息
	 */
	public Map queryUserByLoginId(String account) {
		String sql = " select t.id,t.`name`,t.sex,t.mobile,t.email,t.count,t.login_time from t_user t where t.flag=1 and t.login_id='"
				+ account + "' ";
		Map<String, Object> userMap = jdbcTemplate.queryForMap(sql);

		String roleSql = " select r.role_name,r.id from t_role r,t_user u,t_user_role ur where r.id=ur.role_id and ur.user_id=u.id and r.flag=1 and u.login_id='"
				+ account + "' ";
		List role = jdbcTemplate.queryForList(roleSql);
		String roleIds = "";
		String roleNames = "";
		for (int i = 0; i < role.size(); i++) {
			Map<String, Object> map = (Map) role.get(i);
			roleIds += map.get("id").toString();
			roleNames += map.get("role_name").toString();
			if (i < (role.size() - 1)) {
				roleIds += ",";
				roleNames += ",";
			}
		}

		userMap.put("roleIds", roleIds);
		userMap.put("roleNames", roleNames);

		String menuSql = " select m.id,m.`name`,m.icon,m.`level`,m.parent_id,m.order_,m.url from "
				+ " t_role r,t_role_menu rm,t_menu m where r.id=rm.role_id and m.id=rm.menu_id and m.flag=1 and m.id != 0 "
				+ " and r.id in ("
				+ roleIds
				+ ") GROUP BY "
				+ " m.id,m.`name`,m.icon,m.`level`,m.parent_id,m.order_  order by m.order_ ";
		List menu = jdbcTemplate.queryForList(menuSql);
		List newMenu = getMenu(menu, "0");
		userMap.put("menu", newMenu);
		return userMap;
	}

	/**
	 * 构建菜单
	 */
	public List getMenu(List menu, String parentId) {
		List newMenu = new ArrayList();
		for (int i = 0; i < menu.size(); i++) {
			Map menuMap = new HashMap();
			Map<String, Object> map = (Map) menu.get(i);
			String pId = map.get("parent_id").toString();
			if (parentId.equals(pId)) {
				menuMap.put("id", map.get("id"));
				menuMap.put("title", map.get("name"));
				menuMap.put("icon", map.get("icon"));
				menuMap.put("href", map.get("url"));
				menuMap.put("spread", false);
				List childs = getMenu(menu, map.get("id").toString());
				menuMap.put("children", childs);
				newMenu.add(menuMap);
			}
		}
		return newMenu;
	};

	/**
	 * 更新登录时间和次数
	 */
	public void updateCountAndTime(String account, int count) {
		String sql = " update t_user set count=" + count + ",login_time="
				+ new Date().getTime() + " where login_id='" + account + "'";
		jdbcTemplate.update(sql);
	}

	/**
	 * 日志
	 */
	public void addLogs(String uid, String event, HttpServletRequest request) {
		String ip = getIpAddr(request);
		String sql = " insert into t_logs(ip,event,create_time,update_time,creator,flag) values('"
				+ ip
				+ "','"
				+ event
				+ "','"
				+ new Date().getTime()
				+ "','"
				+ new Date().getTime() + "','" + uid + "','1') ";
		jdbcTemplate.execute(sql);
	}

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				return XFor.substring(0, index);
			} else {
				return XFor;
			}
		}
		XFor = Xip;
		if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			return XFor;
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		return XFor;
	}

	/**
	 * 查询用户信息
	 */
	public Map getUserInfo(int id) {
		String sql = " select * from t_user t where t.flag=1 and t.id=" + id;
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);

		String userRoleSql = " select * from t_user_role t where t.user_id="
				+ id;
		List list = jdbcTemplate.queryForList(userRoleSql);
		map.put("roles", list);

		return map;
	}

	/**
	 * 更新密码
	 */
	public String updatePassword(int id, String oldPwd, String newPwd) {
		Map map = this.getUserInfo(id);
		String password = map.get("password").toString();
		if (!password.equals(MD5Utils.getMD5(oldPwd))) {
			return "旧密码不正确。";
		}
		String sql = " update t_user set password='" + MD5Utils.getMD5(newPwd)
				+ "' where id=" + id;
		jdbcTemplate.execute(sql);
		return "";
	}

	/**
	 * 重置密码
	 */
	public void resetPassword(int uid) {
		String sql = " update t_user set password='"
				+ MD5Utils.getMD5("123456") + "' where id=" + uid;
		jdbcTemplate.execute(sql);
	}

	/**
	 * 更新个人信息
	 */
	public void updateUserInfo(Map<String, Object> userMap) {
		String email = userMap.get("email").toString();
		String mobile = userMap.get("mobile").toString();
		String sex = userMap.get("sex").toString();
		String photo = userMap.get("photo") == null ? "" : userMap.get("photo")
				.toString();
		String roleIds = userMap.get("roleIds").toString();

		String updateSql = "";
		if (email != null) {
			updateSql += " email='" + email + "', ";
		}
		if (mobile != null) {
			updateSql += " mobile='" + mobile + "', ";
		}
		if (sex != null) {
			updateSql += " sex='" + sex + "', ";
		}
		if (photo != null) {
			updateSql += " photo='" + photo + "', ";
		}
		updateSql += " update_time='" + new Date().getTime() + "' ";
		if (updateSql.length() > 2) {
			updateSql = updateSql.substring(0, updateSql.length() - 1);
			String sql = " update t_user set " + updateSql + " where id="
					+ userMap.get("userId");
			jdbcTemplate.execute(sql);
		}

		String del = " delete from t_user_role where user_id="
				+ userMap.get("userId");
		jdbcTemplate.execute(del);

		String[] temp = roleIds.split(",");
		for (int i = 0; i < temp.length; i++) {
			String sql = " insert into t_user_role(role_id,user_id,create_time,update_time,creator) values('"
					+ temp[i]
					+ "','"
					+ userMap.get("userId")
					+ "','"
					+ new Date().getTime()
					+ "','"
					+ new Date().getTime()
					+ "','" + userMap.get("userId") + "') ";
			jdbcTemplate.execute(sql);
		}

	}

	/**
	 * 分页查询用户
	 * 
	 * @return
	 */
	public PageBean queryUser(PageBean pageBean, String where, String orderby) {
		String sql = " select * from t_user t where t.flag=1 ";
		return this.queryPageByMysql(pageBean, sql, where, orderby);
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 */
	public void deleteUser(String ids) {
		String sql = " update t_user set flag=0 where id in (" + ids + ")";
		jdbcTemplate.execute(sql);
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			String del = " delete from t_user_role where user_id=" + id[i];
			jdbcTemplate.execute(del);
		}
	}

	/**
	 * 验证登录账号是否存在
	 */
	public boolean isLoginId(String loginId) {
		String sql = " select count(1) as total from t_user t where t.flag=1 and t.login_id='"
				+ loginId + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		String total = map.get("total").toString();
		if (!total.equals("0")) {
			return false;
		}
		return true;
	}

	/**
	 * 新增用户
	 */
	public void addUser(Map<String, Object> userMap, int uid) {
		String password = MD5Utils.getMD5("123456");
		String sql = " insert into t_user(login_id,password,name,sex,mobile,email,create_time,update_time,creator,count,flag) "
				+ " values('"
				+ userMap.get("loginId")
				+ "','"
				+ password
				+ "','"
				+ userMap.get("name")
				+ "','"
				+ userMap.get("sex")
				+ "','"
				+ userMap.get("mobile")
				+ "','"
				+ userMap.get("email")
				+ "',"
				+ "'"
				+ new Date().getTime()
				+ "','"
				+ new Date().getTime() + "','" + uid + "',0,'1')";
		jdbcTemplate.execute(sql);

		String userSql = " select t.id,t.`name`,t.sex,t.mobile,t.email,t.count,t.login_time from t_user t where t.flag=1 and t.login_id='"
				+ userMap.get("loginId") + "' ";
		Map<String, Object> userInfo = jdbcTemplate.queryForMap(userSql);

		String roles = userMap.get("roleIds").toString();
		String[] temp = roles.split(",");
		for (int i = 0; i < temp.length; i++) {
			String roleSql = " insert into t_user_role(role_id,user_id,create_time,update_time,creator) values('"
					+ temp[i]
					+ "','"
					+ userInfo.get("id")
					+ "','"
					+ new Date().getTime()
					+ "','"
					+ new Date().getTime()
					+ "','1') ";
			jdbcTemplate.execute(roleSql);
		}
	}

}
