/*
 * Class: SLFAccessDeniedHandler
 * Description:请求拒绝捕获类，处理在AccessDecideManager中抛出的权限异常
 * Version: 1.0

 */
package com.ttd.web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ttd.model.SLFMessage;
import com.ttd.util.Constant;
import com.ttd.util.HttpUtils;

/**
 * 处理在AccessDecideManager中抛出的权限异常
 * 
 * @author gao
 * 
 */
public class SLFAccessDeniedHandler implements AccessDeniedHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 出现错误后要跳转的页面
	 */
	private String errorPage;
	/**
	 * 将错误放入request中的属性名
	 */
	private String exceptionAttribute;

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {
		if (!response.isCommitted()) {
			// 设置响应状态码为无权限
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			if (!HttpUtils.isAjaxRequest(request)) {
				// 普通请求直接转到错误页面
				if (errorPage != null) {
					if (exceptionAttribute != null) {
						request.setAttribute(exceptionAttribute, ex);
					} else {
						// 以默认值作为key放入request中
						request.setAttribute(WebAttributes.ACCESS_DENIED_403,
								ex);
					}
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(errorPage);
					dispatcher.forward(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN,
							ex.getMessage());
				}

			} else {
				// JSON格式返回错误信息
				try {
					PrintWriter writer = response.getWriter();
					// 构造消息
					ObjectNode json = JsonNodeFactory.instance.objectNode();
					json.put(Constant.PARAM_RESPO_MESSAGE,
							SLFMessage.ERROR(ex.getLocalizedMessage(), ex)
									.toJSON());
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
	 * @return the errorPage
	 */
	public String getErrorPage() {
		return errorPage;
	}

	/**
	 * @param errorPage
	 *            the errorPage to set
	 */
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	/**
	 * @return the exceptionAttribute
	 */
	public String getExceptionAttribute() {
		return exceptionAttribute;
	}

	/**
	 * @param exceptionAttribute
	 *            the exceptionAttribute to set
	 */
	public void setExceptionAttribute(String exceptionAttribute) {
		this.exceptionAttribute = exceptionAttribute;
	}
}
