package com.ncity.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncity.app.dao.NoticeDao;
import com.ncity.app.entity.NoticeEntity;
import com.ncity.app.uitls.Page;

/**
 * 通知服务层
 * @author 艾克
 * 2018年10月11日 16点04分
 */
@Service
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;
	
	
	/**
	 * 分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param noticeEntity 
	 * @return
	 */
	public List<NoticeEntity> queryListByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Page<NoticeEntity> page = noticeDao.page(pageNumber, pageSize, params);
		return page.getList();
	}

	/**
	 * 返回最新消息标题通知
	 * @return
	 */
	public NoticeEntity queryListTitle(){
		return noticeDao.queryListTitle();
	}

	
	/**
	 * 返回通知实体
	 * @param id
	 * @return
	 */
	public NoticeEntity queryNoticeEntityById(Long id) {
		return noticeDao.queryNoticeEntityById(id);
	}
	
}
