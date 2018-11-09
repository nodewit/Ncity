package com.framwork.filter;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@WebFilter(filterName="customFilter",urlPatterns="/*")
public class CustomFilter implements Filter{
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	/**
     * 封装，不需要过滤的list列表
     */
    private static String patterns = "login,verification,loginIn,loginOut";
	
	public void destroy() {
		System.out.println("CustomFilter过滤器销毁");  
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", 0);
        
        Map<String, Cookie> cookieMap = readCookieMap(httpRequest);
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        if(url.startsWith("css/") || url.startsWith("js/") || url.startsWith("img/")
        		|| url.startsWith("spark/") || url.startsWith("files/")){
        	 chain.doFilter(httpRequest, httpResponse);
             return;
        }
        if(url.indexOf(".png") >-1 || url.indexOf(".jpg") >-1 || url.indexOf(".PNG") >-1 || url.indexOf(".JPG") >-1
        		|| url.indexOf(".JPEG") >-1 || url.indexOf(".jpeg") >-1){
        	 chain.doFilter(httpRequest, httpResponse);
             return;
        }
        
        if(url.equals("login") || url.equals("/")){
        	Cookie session = cookieMap.get("sessionId");
        	if(session != null){
        		String sess = get(session.getValue());
        		if(sess != null && !sess.equals("")){
        			httpResponse.sendRedirect(httpRequest.getContextPath()+"/index");
                    return;	
        		}
                
        	}
        }
        
        if (isInclude(url)){
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {
        	Cookie session =  cookieMap.get("sessionId");
        	if(session != null){
        		String userJosn = redisTemplate.opsForValue().get(session.getValue());
            	if (userJosn != null && !userJosn.equals("")){
                    chain.doFilter(httpRequest, httpResponse);
                    return;
                } else {
                	httpResponse.sendRedirect(httpRequest.getContextPath()+"/login");
                    return;
                }
        	}else{
        		httpResponse.sendRedirect(httpRequest.getContextPath()+"/login");
        		return;
        	}
        }
	}

	public void init(FilterConfig config) throws ServletException {
		System.out.println("CustomFilter初始化......");  
	}
    
	 /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
    	String [] uri = patterns.split(",");
    	for (int i = 0; i < uri.length; i++) {
			if(uri[i].equals(url)){
				return true;
			}
		}
        return false;
    }
    
    /**
     * 将cookie封装成map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();        //获取所有的cookie值
        if(cookies != null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
    
	/**
	 * 获取redis值
	 * @param key
	 * @return
	 */
	public String get(final String key){  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
    }  
}
