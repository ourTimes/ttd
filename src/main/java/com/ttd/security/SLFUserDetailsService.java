/*
 * Class: SLFUserDetailsService
 * Description:用户信息提供器
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-6-21
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.ttd.entity.User;
import com.ttd.model.UserDetail;
import com.ttd.service.AuthorityService;
import com.ttd.service.UserService;



/**
 * 用于提供用户信息
 * @author gao
 *
 */
@SuppressWarnings("deprecation")
public class SLFUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@Override
	public UserDetails loadUserByUsername(String userLoginId)
			throws UsernameNotFoundException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<String> authorities = authorityService.getEnabledAuthorityCodesByUserLoginId(userLoginId);
		
		for (String authorityCode : authorities) {
			//以权限code包装成GA
			GrantedAuthority ga = new GrantedAuthorityImpl(
					authorityCode);
			auths.add(ga);
		}
		
		User user = userService.getByUserLoginId(userLoginId);
		if(user==null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		UserDetail ud = new UserDetail(user);
		ud.setAuths(auths);
		return ud;
	}

}
