package com.manager.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author
 * @see cn.zytx.common.db.QueryHelper
 * @see Record
 *      SQL语句用{@see QueryHelper}封装。结果集用JavaBean或者{@see Record}来封装。JavaBean需要保证别名就是属性。
 *      可以使用{@see QueryHelper}完全处理参数，也可以不处理，支持?和:的方式
 */
@Transactional
@Repository
public class JpaUtils {

	@Autowired
	private EntityManager entityManager;

	/**
	 * 返回查询的一个实体，没有则为null
	 */
	public <T> T findFirst(String sql, Class<T> clazz, Object... params) {
		List<T> ts = find(sql, clazz, params);
		return (ts == null || ts.size() == 0) ? null : ts.get(0);
	}

	public <T> T findFirst(String sql, Class<T> clazz, Map<String, Object> searchMap) {
		List<T> ts = find(sql, clazz, searchMap);
		return (ts == null || ts.size() == 0) ? null : ts.get(0);
	}
	
	/**
	 * 新增或修改
	 * @param clazz
	 */
	public void saveOrUpdate(Object object){
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(object);
	}

	/**
	 * 查询列表
	 * 
	 * @param sql
	 *            native sql语句，可以包含？
	 * @param clazz
	 *            返回的类型，可以是JavaBean
	 * @param params
	 *            参数列表
	 * @param <T>
	 *            泛型
	 * @return 查询列表结果
	 */
	public <T> List<T> find(String sql, Class<T> clazz, Object... params) {
		Session session = entityManager.unwrap(Session.class);
		org.hibernate.Query query = session.createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		List list = getList(query, clazz);
		return list;
	}

	/**
	 * 查询列表
	 * 
	 * @param sql
	 *            native sql语句，可以包含 :具名参数
	 * @param clazz
	 *            返回的类型，可以是JavaBean，可以是Record
	 * @param searchMap
	 *            具名参数列表
	 * @param <T>
	 *            泛型
	 * @return 查询列表结果
	 */
	public <T> List<T> find(String sql, Class<T> clazz, Map<String, Object> searchMap) {
		Session session = entityManager.unwrap(Session.class);
		org.hibernate.Query query = session.createSQLQuery(sql);
		if (null != searchMap) {
			searchMap.forEach(query::setParameter);
		}
		List list = getList(query, clazz);
		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 *            pageNumber
	 * @param pageSize
	 *            pageSize
	 * @param isGroupBySql
	 *            是否包含Group by语句，影响总行数
	 * @param nativeSQL
	 *            原生SQL语句 {@see QueryHelper}
	 * @param nativeCountSQL
	 *            原生求总行数的SQL语句 {@see QueryHelper}
	 * @param clazz
	 *            JavaBean风格的DTO或者Record，需要用别名跟JavaBean对应
	 * @param <T>
	 *            返回JavaBean风格的DTO或者Record
	 * @param params
	 *            按照顺序给条件
	 */
	public <T> Page<T> paginate(Boolean isGroupBySql, String nativeSQL, String nativeCountSQL, Class<T> clazz,
			int pageNumber, int pageSize, Object... params) {
		if (pageNumber < 1 || pageSize < 1) {
			throw new IllegalArgumentException("pageNumber and pageSize must more than 0");
		}
		Query countQuery = entityManager.createNativeQuery(nativeCountSQL);
		for (int i = 1; i <= params.length; i++) {
			countQuery.setParameter(i, params[i - 1]);
		}
		List countQueryResultList = countQuery.getResultList();
		int size = countQueryResultList.size();
		if (isGroupBySql == null) {
			isGroupBySql = size > 1;
		}
		long totalRow;
		if (isGroupBySql) {
			totalRow = size;
		} else {
			totalRow = (size > 0) ? ((Number) countQueryResultList.get(0)).longValue() : 0;
		}
		if (totalRow == 0) {
			return new Page<>(new ArrayList<>(0), pageNumber, pageSize, 0, 0);
		}
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		if (pageNumber > totalPage) {
			return new Page<>(new ArrayList<>(0), pageNumber, pageSize, totalPage, (int) totalRow);
		}
		Session session = entityManager.unwrap(Session.class);
		int offset = pageSize * (pageNumber - 1);
		org.hibernate.Query query = session.createSQLQuery(nativeSQL).setFirstResult(offset).setMaxResults(pageSize);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		final List list = getList(query, clazz);
		return new Page<T>(list, pageNumber, pageSize, totalPage, (int) totalRow);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 *            pageNumber
	 * @param pageSize
	 *            pageSize
	 * @param isGroupBySql
	 *            是否包含Group by语句，影响总行数
	 * @param nativeSQL
	 *            原生SQL语句 {@see QueryHelper}
	 * @param nativeCountSQL
	 *            原生求总行数的SQL语句 {@see QueryHelper}
	 * @param clazz
	 *            JavaBean风格的DTO或者Record，需要用别名跟JavaBean对应
	 * @param <T>
	 *            返回JavaBean风格的DTO或者Record
	 * @param searchMap
	 *            k-v条件
	 */
	public <T> Page<T> paginate(Boolean isGroupBySql, String nativeSQL, String nativeCountSQL, Class<T> clazz,
			int pageNumber, int pageSize, Map<String, Object> searchMap) {
		if (pageNumber < 1 || pageSize < 1) {
			throw new IllegalArgumentException("pageNumber and pageSize must more than 0");
		}
		Query countQuery = entityManager.createNativeQuery(nativeCountSQL);
		if (null != searchMap) {
			searchMap.forEach(countQuery::setParameter);
			/*searchMap.forEach((k,v)->{
				if(v != null){
					countQuery.setParameter(k, v);
				}
			});*/
		}
		List countQueryResultList = countQuery.getResultList();
		int size = countQueryResultList.size();
		if (isGroupBySql == null) {
			isGroupBySql = size > 1;
		}
		long totalRow;
		if (isGroupBySql) {
			totalRow = size;
		} else {
			totalRow = (size > 0) ? ((Number) countQueryResultList.get(0)).longValue() : 0;
		}
		if (totalRow == 0) {
			return new Page<>(new ArrayList<>(0), pageNumber, pageSize, 0, 0);
		}
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		if (pageNumber > totalPage) {
			return new Page<>(new ArrayList<>(0), pageNumber, pageSize, totalPage, (int) totalRow);
		}
		Session session = entityManager.unwrap(Session.class);
		int offset = pageSize * (pageNumber - 1);
		org.hibernate.Query query = session.createSQLQuery(nativeSQL).setFirstResult(offset).setMaxResults(pageSize);
		if (null != searchMap) {
			searchMap.forEach(query::setParameter);
			/*searchMap.forEach((k,v)->{
				if(v != null){
					query.setParameter(k, v);
				}
			});*/
		}
		final List list = getList(query, clazz);
		return new Page<T>(list, pageNumber, pageSize, totalPage, (int) totalRow);
	}

	@SuppressWarnings("unchecked")
	private <T> List getList(org.hibernate.Query query, Class<T> clazz) {
		final List list;

		// Object[].class
		if (Object[].class == clazz) {
			return query.list();
		}

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List mapList = query.list();
		list = new ArrayList(mapList.size());
		mapList.forEach(map -> {
			Map<String, Object> tmp = (Map<String, Object>) map;
			// Map及子类
			if (Map.class.isAssignableFrom(clazz)) {
				list.add(tmp);
			} else {
				// JavaBean风格
				list.add(Map2JavaBean(tmp, clazz));
			}
		});
		return list;
	}

	/***
	 * 下划线命名转为驼峰命名
	 * 
	 * @param para
	 *            下划线命名的字符串
	 */
	private String UnderlineToHump(String para) {
		StringBuilder result = new StringBuilder();
		String a[] = para.split("_");
		for (String s : a) {
			if (result.length() == 0) {
				result.append(s.toLowerCase());
			} else {
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	private <T> T Map2JavaBean(Map<String, Object> tmp, Class<T> clazz) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : tmp.keySet()) {
			map.put(UnderlineToHump(key), tmp.get(key));
		}
		String text = JSONObject.toJSONString(tmp);
		T obj = JSON.parseObject(text, clazz);
		return obj;
	}

	private String getCountSQL(String sql) {
		String countSQL = "SELECT COUNT(*) AS totalRow " + sql.substring(sql.toUpperCase().indexOf("FROM"));
		return replaceOrderBy(countSQL);
	}

	protected static class Holder {
		private static final Pattern ORDER_BY_PATTERN = Pattern.compile(
				"order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	}

	public String replaceOrderBy(String sql) {
		return Holder.ORDER_BY_PATTERN.matcher(sql).replaceAll("");
	}

	/**
	 * Map --> Bean
	 * 
	 * @param map
	 * @param obj
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(value != null){
						map.put(HumpToUnderline(key), value);
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	/**
	 * 下划线转驼峰
	 * 
	 * @param args
	 */
	private static String HumpToUnderline(String str) {
		str = str.toLowerCase();
		final StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("_(\\w)");
		Matcher m = p.matcher(str);
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}
	
}
