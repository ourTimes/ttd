/*
 * Class: SLFAuthenticationFailureHandler
 * Description:登录失败处理类，根据不同的请求类型返回不同的响应格式
 * Version: 1.0
 */
package com.ttd.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.POJONode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ttd.util.HttpUtils;
import com.ttd.util.StatusJSONObjectProducer;

/**
 * 登录失败处理类，根据不同的请求类型返回不同的响应格式
 * @author gao
 *
 */
public class SLFAuthenticationFailureHandler implements AuthenticationFailureHandler{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 登录失败后默认跳转页
	 */
	private String defaultTargetUrl = null;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (!HttpUtils.isAjaxRequest(request)) {
					// 转到默认页面
					response.sendRedirect(request.getContextPath()+defaultTargetUrl);
				
			} else {
				// JSON格式返回登录成功的信息
				try {
					PrintWriter writer = response.getWriter();
					// 构造消息
					StatusJSONObjectProducer status = new StatusJSONObjectProducer();
					status.setFail("登录失败");
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
		}
	}

	/**
	 * @return the defaultTargetUrl
	 */
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	/**
	 * @param defaultTargetUrl the defaultTargetUrl to set
	 */
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
}
