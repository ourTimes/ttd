/*
 * Class: ExceptionResolver
 * Description:异常捕获类，捕获所有controller中抛出的异常，区分请求类型，返回不同的异常展示
 * Version: 1.0
 */
package com.ttd.web.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.ttd.model.SLFMessage;
import com.ttd.util.Constant;
import com.ttd.util.HttpUtils;

/**
 * 继承Spring的SimpleMappingExceptionResolver，重写doResolveException方法，加入请求类型的判断，
 * 实现普通异常和ajax请求异常的处理
 * 
 * @author gao
 * 
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		logger.error(ex.getMessage(), ex);

		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// 设置相应http错误码
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			if (!HttpUtils.isAjaxRequest(request)) {
				// JSP格式返回错误信息
				if (statusCode != null) {
					return getModelAndView(viewName, ex, request);
				}
			} else {
				// JSON格式返回错误信息
				try {
					OutputStream osOutputStream=response.getOutputStream();
					OutputStreamWriter w = new OutputStreamWriter(osOutputStream, "utf-8");
					PrintWriter writer=new PrintWriter(w);
					
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
				return new ModelAndView();
			}
			return null;
		} else {
			return null;
		}

	}
}
