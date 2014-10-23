/*
 * Class: Authority
 * Description:权限，映射数据库的权限表
 * Version: 1.0
 */
package com.ttd.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

/**
 * @author gao
 * 
 */
@Entity
@Table(name = "Authorities")
public class Authority {
	/**
	 * 授权ID，主键，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHORITY_KEY")
	private long authorityKey;
	
	/**
	 * 授权名称
	 */
	@Column(name = "NAME",unique=true,nullable=false,updatable=false,length=64)
	private String name;
	
	/**
	 * 授权代码
	 */
	@Column(name = "CODE",unique=true,nullable=false,updatable=false,length=64)
	private String code;
	
	/**
	 * 是否激活
	 */
	@Type(type="yes_no")
	@Column(name = "ENABLED",nullable=false)
	private Boolean enabled;
	

	/**
	 * 授权描述
	 */
	@Column(name = "DES",length=256)
	private String desc;
	
	/**
	 * 权限和资源的关系
	 */
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)//程序初始化时会用到，所以不能lazy
	@JoinTable(name = "Authority_Resource", joinColumns = { @JoinColumn(name = "AUTHORITY_KEY") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_KEY") })
	private Set<Resource> resources;

	/**
	 * @return the authorityKey
	 */
	public long getAuthorityKey() {
		return authorityKey;
	}

	/**
	 * @param authorityKey the authorityKey to set
	 */
	public void setAuthorityKey(long authorityKey) {
		this.authorityKey = authorityKey;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	/**
	 * @return the resources
	 */
	public Set<Resource> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	
	

	
}
