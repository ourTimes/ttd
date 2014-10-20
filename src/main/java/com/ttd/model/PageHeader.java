/*
 * Class: PageHeader
 * Description:分页结果中的列标题
 * Version: 1.0
 */
package com.ttd.model;

/**
 * @author gao
 *
 */
public class PageHeader {

	private String columnName;
	
	private String columnDesc;
	
	private String columnType;
	
	private boolean sortable;

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the columnDesc
	 */
	public String getColumnDesc() {
		return columnDesc;
	}

	/**
	 * @param columnDesc the columnDesc to set
	 */
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * @param sortable the sortable to set
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	
}
