package com.manager.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;
import com.manager.bean.ChainEntity;
import com.manager.dao.AppSuggestDao;
import com.manager.utils.Constants;
import com.manager.utils.Page;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

/**
 * 反馈意见-服务层
 * 
 * @author 艾克 2018年11月6日 15点11分
 */
@Service
public class AppSuggestService {

	@Autowired
	private AppSuggestDao appSuggestDao;

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page querySuggestByPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Page page = appSuggestDao.querySuggestByPage(pageNumber, pageSize, params);
		return page;
	}

}
