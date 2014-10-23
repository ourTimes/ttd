/*
 * Class: SLFLoginUrlAuthenticationEntryPoint
 * Description:在跳转到登录页面之前的入口点，根据请求返回相应格式的响应
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-7-25
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.ttd.model.SLFMessage;
import com.ttd.util.Constant;
import com.ttd.util.HttpUtils;

/**
 * 
 * @author gao
 * 
 */
public class SLFLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	public SLFLoginUrlAuthenticationEntryPoint(String loginUrl) {
		super(loginUrl);
	}

	/**
	 * 重写commence方法，判断是否为ajax请求，若为ajax请求则返回401错误，不进行跳转
	 */
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if (HttpUtils.isAjaxRequest(request)) {
			//ajax请求则写入json格式信息
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter writer = response.getWriter();
			// 构造消息
			ObjectNode json = JsonNodeFactory.instance.objectNode();
			json.put(Constant.PARAM_RESPO_MESSAGE,
					SLFMessage.ERROR("会话超时，请重新登录", authException)
							.toJSON());
			ObjectMapper objectMapper = new ObjectMapper();
			// 将消息写入response流中
			objectMapper.writeValue(writer, json);
			writer.flush();
			writer.close();
		} else {
			super.commence(request, response, authException);
		}
	}
}
