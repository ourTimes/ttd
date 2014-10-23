/*
 * Class: Resource
 * Description:资源，映射到数据库的资源表
 * Version: 1.0
 */
package com.ttd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 
 * @author gao
 *
 */
@Entity
@Table(name = "Resources")
public class Resource {
	/**
	 * 资源ID，主键，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESOURCE_KEY")
	private long resourceKey;
	
	/**
	 * 资源名称
	 */
	@Column(name = "NAME",nullable=false,unique=true,updatable=false,length=64)
	private String name;
	
	/**
	 * 是否激活
	 */
	@Type(type="yes_no")
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	/**
	 * 资源路径
	 */
	@Column(name = "URL",nullable=false,length=512)
	private String url;
	
	/**
	 * 资源类型,Service/API
	 */
	@Column(name = "TYPE",nullable=false,length=32)
	private String type;
	
	/**
	 * 调用方式,GET/POST/PUT/DELETE
	 */
	@Column(name = "METHOD",nullable=false,length=32)
	private String method;

	/**
	 * 资源描述
	 */
	@Column(name = "DES",length=256)
	private String desc;

	/**
	 * @return the resourceKey
	 */
	public long getResourceKey() {
		return resourceKey;
	}

	/**
	 * @param resourceKey the resourceKey to set
	 */
	public void setResourceKey(long resourceKey) {
		this.resourceKey = resourceKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


}
