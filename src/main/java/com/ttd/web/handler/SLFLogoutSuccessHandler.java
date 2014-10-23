/*
 * Class: SLFLogoutSuccessHandler
 * Description:用户注销成功处理类
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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.ttd.util.HttpUtils;
import com.ttd.util.StatusJSONObjectProducer;
/**
 * @author gao
 * 
 */
public class SLFLogoutSuccessHandler implements LogoutSuccessHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 登录成功后默认跳转页
	 */
	private String defaultTargetUrl = null;

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (!HttpUtils.isAjaxRequest(request)) {
				// 普通请求
				// 转到默认页面
				response.sendRedirect(request.getContextPath()
						+ defaultTargetUrl);
			} else {
				// JSON格式返回信息
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
		}
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
