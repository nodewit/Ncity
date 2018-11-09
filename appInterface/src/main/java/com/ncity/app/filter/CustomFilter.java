package com.ncity.app.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.ncity.app.action.AppLoginAction;

@WebFilter(filterName = "customFilter", urlPatterns = "/*")
public class CustomFilter implements Filter {

	private Logger log = Logger.getLogger(CustomFilter.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	/**
	 * 封装，不需要过滤的list列表
	 */
	private static String patterns = "";

	public void destroy() {
		log.info("CustomFilter过滤器销毁");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, Token, UA");

        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        
        if(url.indexOf("swagger-ui.html") > -1 || url.indexOf("webjars") > -1
        		|| url.indexOf("v2") > -1 || url.indexOf("swagger-resources") > -1) {
        	chain.doFilter(httpRequest, httpResponse);
        	return;
        }
        
        if(!isInclude(url)) {
        	String token = httpRequest.getHeader("Token");
            String ua = httpRequest.getHeader("User-Agent");
            
            String oldToken = this.get(token);
            //判断token是否存在
            if(oldToken != null){
            	
            }
            chain.doFilter(httpRequest, httpResponse);
        } else {
        	chain.doFilter(httpRequest, httpResponse);
        }
	}

	public void init(FilterConfig config) throws ServletException {
		log.info("CustomFilter初始化......");
	}

	/**
	 * 是否需要过滤
	 * 
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		String[] uri = patterns.split(",");
		for (int i = 0; i < uri.length; i++) {
			if (uri[i].equals(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取redis值
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		try {
			String result = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] value = connection.get(serializer.serialize(key));
					return serializer.deserialize(value);
				}
			});
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
