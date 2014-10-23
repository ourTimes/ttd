/*
 * Class: SLFUsernamePasswordAuthenticationFilter
 * Description:扩展用户认证功能
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-6-24
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 继承SpringSecurity3的UsernamePasswordAuthenticationFilter，重写attemptAuthentication方法，加入登录时验证码支持
 * @author gao
 * 
 */
public class SLFUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	// 验证码字段
	private String validateCodeParameter = "validateCode";
	// 是否开启验证码功能
	private boolean openValidateCode = false;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		// 只接受POST方式传递的数据
		if (!"POST".equals(request.getMethod())) {
			// TODO throw MethodNotSupportException
		}

		// 开启验证码功能的情况
		if (isOpenValidateCode())
			checkValidateCode(request);

		// 获取Username和Password
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		// UsernamePasswordAuthenticationToken实现Authentication校验
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	// 匹对验证码的正确性
	public void checkValidateCode(HttpServletRequest request) {
		// TODO check the validatecode
	}

	public String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(getValidateCodeParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	public String getValidateCodeParameter() {
		return validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public boolean isOpenValidateCode() {
		return openValidateCode;
	}

	public void setOpenValidateCode(boolean openValidateCode) {
		this.openValidateCode = openValidateCode;
	}
}
