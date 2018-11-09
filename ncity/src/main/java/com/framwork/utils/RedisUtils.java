package com.framwork.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

@Service("redisUtils")
public class RedisUtils {
	
	private RedisTemplate redisTemplate;

	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
	    RedisSerializer stringSerializer = new StringRedisSerializer();
	    redisTemplate.setKeySerializer(stringSerializer);
	    redisTemplate.setValueSerializer(stringSerializer);
	    redisTemplate.setHashKeySerializer(stringSerializer);
	    redisTemplate.setHashValueSerializer(stringSerializer);
	    this.redisTemplate = redisTemplate;
	}
	
	public void delete(String key){
		redisTemplate.delete(key);
	}
	
	public void deleteList(List keys){
		redisTemplate.delete(keys);
	}
	
	/**
	 * 设置值
	 * @param id
	 * @param obj
	 * @param time
	 * @param tu
	 */
	@SuppressWarnings("unchecked")
	public void set(String id, Object obj, long time, TimeUnit tu){
		redisTemplate.opsForValue().set(id, obj, time, tu);
	}


	/**
	 * 获取redis值
	 * @param key
	 * @return
	 */
	public String get(final String key){  
		String result = (String) redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(serializer.serialize(key));  
                return serializer.deserialize(value);  
            }  
        });  
        return result;  
    }  
	
	
	/**
     * 将cookie封装成map
     * @param request
     * @return
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request){
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
     * 获取redis用户ID
     */
    public int getUserId(HttpServletRequest request){
    	int id = 0;
    	Map<String, Cookie> cookieMap = this.readCookieMap(request);
		if(cookieMap.containsKey("sessionId")){
			String sessionId = cookieMap.get("sessionId").getValue();
			if(sessionId != null && !sessionId.equals("")){
				try {
					JSONObject json = new JSONObject(this.get(sessionId));
					id = json.getInt("id");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
        }
		return id;
    }
}
