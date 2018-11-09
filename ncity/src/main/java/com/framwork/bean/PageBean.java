package com.framwork.bean;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageBean {
	
	private int page = 1;
	private int rows = 10;
	private int total = 0;
	private List pageData;
	private Map queryParams;
	
	@JsonIgnore
	public Map getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map queryParams) {
		this.queryParams = queryParams;
	}
	@JsonIgnore
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	@JsonIgnore
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@JsonProperty("rows")
	public List getPageData() {
		return pageData;
	}
	public void setPageData(List pageData) {
		this.pageData = pageData;
	}
}
