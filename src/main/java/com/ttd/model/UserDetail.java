/*
 * Class: UserDetail
 * Description:包装用户实体，提供给Security认证
 * Version: 1.0
 */
package com.ttd.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ttd.entity.User;

/**
 * 包装用户实体，提供给Security认证
 * 
 * @author gao
 * 
 */
public class UserDetail implements UserDetails, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4558660254617812298L;
	/**
	 * 用户实体
	 */
	private User userEntity;
	/**
	 * 用户所具备的权限
	 */
	@Transient
	private Collection<GrantedAuthority> auths;

	/**
	 * @return the userEntity
	 */
	public User getUserEntity() {
		return userEntity;
	}

	/**
	 * @param userEntity
	 *            the userEntity to set
	 */
	public void setUserEntity(User userEntity) {
		this.userEntity = userEntity;
	}

	public UserDetail(User userEntity) {
		this.userEntity = userEntity;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return auths;
	}

	public void setAuths(Collection<GrantedAuthority> auths) {
		this.auths = auths;
	}

	/**
	 * 
	 * @return 用户所具备的权限
	 */
	public Collection<GrantedAuthority> getAuths() {
		return auths;
	}

	/**
	 * 用于验证的登录ID，返回LOGINID
	 */
	@Override
	public String getUsername() {
		return userEntity.getLoginId();
	}

	/**
	 * 账户是否可用
	 */
	@Override
	public boolean isEnabled() {
		return userEntity.getEnabled();
	}

	/**
	 * 账户是否未过期
	 */
	@Override
	public boolean isAccountNonExpired() {
		// 目前默认返回true，待扩展
		return true;
	}

	/**
	 * 账户是否未锁定
	 */
	@Override
	public boolean isAccountNonLocked() {
		// 目前默认返回true，待扩展
		return true;
	}

	/**
	 * 证书是否未过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// 目前默认返回true，待扩展
		return true;
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}
}
