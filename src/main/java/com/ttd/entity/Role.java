/*
 * Class: Role
 * Description:角色，映射到数据库的角色表
 * Version: 1.0.
 */
package com.ttd.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

/**
 * 
 * @author gao
 * 
 */
@Entity
@Table(name = "ROLES")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691649711216592344L;

	/**
	 * 角色ID，主键，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_KEY")
	private long roleKey;

	/**
	 * 角色名称
	 */
	@Column(name = "NAME", unique = true, nullable = false, updatable = false, length = 64)
	private String name;

	/**
	 * 是否激活
	 */
	@Type(type="yes_no")
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	/**
	 * 角色描述
	 */
	@Column(name = "DES", length = 256)
	private String desc;

	/**
	 * 权限和角色的关系
	 */
	@ManyToMany
	@JoinTable(name = "Role_Authority", joinColumns = { @JoinColumn(name = "ROLE_KEY") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_KEY") })
	private Set<Authority> auth;

	/**
	 * @return the roleKey
	 */
	public long getRoleKey() {
		return roleKey;
	}

	/**
	 * @param roleKey the roleKey to set
	 */
	public void setRoleKey(long roleKey) {
		this.roleKey = roleKey;
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
	 * @return the auth
	 */
	public Set<Authority> getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(Set<Authority> auth) {
		this.auth = auth;
	}

	

}
