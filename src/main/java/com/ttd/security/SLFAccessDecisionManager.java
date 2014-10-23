/*
 * Class: SLFAccessDecisionManager
 * Description:用户请求的决策器，决定用户是否可以访问该资源
 * Version: 1.0
 */
package com.ttd.security;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用户请求的决策器，决定用户是否可以访问该资源
 * 
 * @author goa
 * 
 */
public class SLFAccessDecisionManager implements AccessDecisionManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (null == configAttributes) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			// 访问所请求资源所需要的权限
			String needPermission = ((SecurityConfig) ca).getAttribute();
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				logger.debug("Need: "+needPermission.trim()+"    Granted: "+ga.getAuthority().trim());
				if (needPermission.trim().equals(ga.getAuthority().trim())) {
					return;
				}
			}
		}
		// 没有权限
		throw new AccessDeniedException("Not authorized to access!");
	}

	@Override
	public boolean supports(ConfigAttribute paramConfigAttribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return true;
	}

}
