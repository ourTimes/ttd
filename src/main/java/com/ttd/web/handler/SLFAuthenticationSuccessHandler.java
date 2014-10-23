/*
 * Class: SLFAuthenticationSuccessHandler
 * Description:登录成功处理类，根据不同的请求类型返回不同的响应格式
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-7-25
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.POJONode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ttd.entity.User;

import com.ttd.model.UserDetail;

import com.ttd.util.HttpUtils;
import com.ttd.util.StatusJSONObjectProducer;

/**
 * 登录成功处理类，根据不同的请求类型返回不同的响应格式
 * 
 * @author Vinda
 * 
 */
public class SLFAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {



	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 登录成功后默认跳转页
	 */
	private String defaultTargetUrl = null;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (!HttpUtils.isAjaxRequest(request)) {
				// 普通请求
				// // 尝试取出原本访问的URL
				// Object savedRequestObject =
				// request.getSession().getAttribute(
				// "SPRING_SECURITY_SAVED_REQUEST");
				// String redirectUrl = null;
				// if (savedRequestObject != null) {
				// redirectUrl = ((SavedRequest) savedRequestObject)
				// .getRedirectUrl();
				// request.getSession().removeAttribute(
				// "SPRING_SECURITY_SAVED_REQUEST");
				// }
				// if (!StringUtils.isBlank(redirectUrl)) {
				// response.sendRedirect(redirectUrl);
				// } else {
				// // 转到默认页面
				// response.sendRedirect(request.getContextPath()+defaultTargetUrl);
				// }
				// 转到默认页面
				response.sendRedirect(request.getContextPath()
						+ defaultTargetUrl);
			} else {
				// JSON格式返回登录成功的信息
				try {
					PrintWriter writer = response.getWriter();
					// 构造消息
					StatusJSONObjectProducer status = new StatusJSONObjectProducer();
					POJONode json = JsonNodeFactory.instance.POJONode(status
							.produce());
					ObjectMapper objectMapper = new ObjectMapper();
					// 将消息写入response流中
					objectMapper.writeValue(writer, json);
					writer.flush();
					writer.close();

				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			/**
			 * 记录用户登录时间等信息
			 */
			saveUserInfo(request);
		}

	}

	/**

	 * 
	 * 添加用户登录时间记录到log表
	 * 
	 * @param request
	 */
	public void saveUserInfo(HttpServletRequest request) {
		UserDetail ud = (UserDetail) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		User currentUser = ud.getUserEntity();
		
	}

	/**
	 * @return the defaultTargetUrl
	 */
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	/**
	 * @param defaultTargetUrl
	 *            the defaultTargetUrl to set
	 */
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

}
