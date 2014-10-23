/*
 * Class: User
 * Description:用户，映射到数据库的用户表
 * Version: 1.0
 */
package com.ttd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;


/**
 * 
 * @author gao
 * 
 */

@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = -3073240485110237190L;
	
	/**
	 * 用户ID，主键，自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_KEY", nullable = false)
	private Long userKey;

	/**
	 * 用户登录名称
	 */
	@Column(name = "LOGIN_ID", unique = true, nullable = false, updatable = false, length = 64)
	private String loginId;

	/**
	 * 密码密文
	 */
	@JsonIgnore
	@Column(name = "password", nullable = false, length = 64)
	private String password;

	/**
	 * 用户名称
	 */
	@Column(name = "USER_NAME", nullable = false, length = 64)
	private String userName;




	/**
	 * 上次密码修改时间
	 */
	@Column(name = "PASSWORD_LAST_CHANGED_ON", nullable = false)
	private Date passwordLastChangedOn;

	/**
	 * 是否激活
	 */
	@Type(type = "yes_no")
	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled;



	/**
	 * 单位
	 */

	@Column(name = "ORGANIZATION_KEY", nullable = false)
	private Long organization;



	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TS", nullable = false)
	private Date createTime;

	/**
	 * 修改时间
	 */
	@Column(name = "MODIFY_TS")
	private Date modifyTime;


	/**
	 * 用户和角色的关系
	 */

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role", joinColumns = { @JoinColumn(name = "USER_KEY") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_KEY") })
	private Set<Role> roles;

	

	/**
	 * @return the userKey
	 */
	public Long getUserKey() {
		return userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	/**
	 * @return the passwordLastChangedOn
	 */
	public Date getPasswordLastChangedOn() {
		return passwordLastChangedOn;
	}

	/**
	 * @param passwordLastChangedOn
	 *            the passwordLastChangedOn to set
	 */
	public void setPasswordLastChangedOn(Date passwordLastChangedOn) {
		this.passwordLastChangedOn = passwordLastChangedOn;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getOrganization() {
		return organization;
	}

	public void setOrganization(Long organization) {
		this.organization = organization;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
