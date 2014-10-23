/*
 * Class: Page
 * Description:分页查询的结果
 * Version: 1.0
 */
package com.ttd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 分页参数及查询结果封装.
 * 
 * 所有序号从1开始.
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 * @author gao
 */
public class Page<T> {
	
	// -- 默认页大小 15行 --//
	public static final int DEFAULT_PAGESIZE = 15;
	public static final String DEFAULT_PAGESIZE_STR = "15";
	
	//通过findPage查询获得所有结果，之后从page中取出list
	public static final int QUERY_ALL_PAGESIZE = -1;
	public static final int QUERY_ALL_PAGENO = -1;
	
	// -- 分页参数 --//
	protected int pageNo = 1;
	protected int pageSize = 15;
	
	protected boolean autoCount = true;

	private boolean paging = true;
	/**
	 * 汇总，key为列名，value为汇总的值
	 */
	private Map<String,?> summary;
	
	// -- 返回结果 --//
	protected List<T> result = new ArrayList<T>();
	
	private List<PageHeader> header = new ArrayList<PageHeader>();
	
	protected long totalCount = -1;

	
	
	public Page() {
	}


	public boolean isPaging(){
		//若页码和页大小均为-1，则表示不分页
		if(pageNo==-1&&pageSize==-1){
			return false;
		}
		return paging;
	}
	
	public void setPaging(boolean p){
		paging=p;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}
	

	/**
	 * modified by chenchenxing at 20140103 remove condition
	 * 
	 * 设置当前页的页号
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

//		if (pageNo < 1) {
//			this.pageNo = 1;
//		}
	}


	/**
	 * 获得每页的记录数量, 默认为-1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * modified by chenchenxing at 20140103 remove condition
	 * 
	 * 设置每页的记录数量.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
//		if(pageSize<1){
//			this.pageSize=DEFAULT_PAGESIZE;
//		}
	}



	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		if(!isPaging()){
			//不分页的话返回值始终为1
			return 1;
		}else{
			return ((pageNo - 1) * pageSize) + 1;
		}
	}



	/**
	 * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为true.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 设置查询对象时是否自动先执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}


	/**
	 * 获得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * @return the header
	 */
	public List<PageHeader> getHeader() {
		return header;
	}


	/**
	 * @param header the header to set
	 */
	public void setHeader(List<PageHeader> header) {
		this.header = header;
	}


	/**
	 * 获得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the summary
	 */
	public Map<String,?> getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(Map<String,?> summary) {
		this.summary = summary;
	}
	
	/**
	 * 将页中的列表类型转为其他类型
	 * @param newList 新的列表
	 * @return
	 */
	public <NT> Page<NT> convert(List<NT> newList){
		Page<NT> newPage = new Page<NT>();
		newPage.setPageNo(this.pageNo);
		newPage.setPageSize(this.pageSize);
		newPage.setAutoCount(this.autoCount);
		newPage.setPaging(this.paging);
		newPage.setResult(newList);
		newPage.setTotalCount(this.totalCount);
		newPage.setSummary(this.summary);
		newPage.setHeader(this.header);
		return newPage;
	}

}