package com.ncity.app.uitls;

import java.io.Serializable;
import java.util.List;
 
public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = -5395997221963176643L;
	
	private List<T> list;				// list result of this page
	private int pageNumber;				// page number
	private int pageSize=10;				// result amount of this page
	private int totalPage;				// total page
	private int totalRow;				// total row
 
 
	public Page(int pageNumber) {
		this.pageNumber = pageNumber;
	}
 
	/**
	 * Constructor.
	 * @param list the list of paginate result
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param totalPage the total page of paginate
	 * @param totalRow the total row of paginate
	 */
	public Page(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
		this.list = list;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
	}
 
	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	/**
	 * Return list of this page.
	 */
	public List<T> getList() {
		return list;
	}
	
	/**
	 * Return page number.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	
	/**
	 * Return page size.
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Return total page.
	 */
	public int getTotalPage() {
 
		totalPage = totalRow / pageSize;
		if (totalRow % pageSize > 0) {
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * Return total row.
	 */
	public int getTotalRow() {
		return totalRow;
	}
	
	public boolean isFirstPage() {
		return pageNumber == 1;
	}
	
	public boolean isLastPage() {
		return pageNumber == totalPage;
	}
 
	public void setList(List<T> list) {
		this.list = list;
	}
 
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
 
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
 
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
 
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
 
	@Override
	public String toString() {
		return "Page{" +
				"list=" + list +
				", pageNumber=" + pageNumber +
				", pageSize=" + pageSize +
				", totalPage=" + totalPage +
				", totalRow=" + totalRow +
				'}';
	}
}
 

