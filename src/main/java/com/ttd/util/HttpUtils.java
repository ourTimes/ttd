/*
 * Class: HttpUtils
 * Description:Http工具类
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-7-25
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vinda
 * 
 */
public class HttpUtils {
	/**
	 * 检查请求是否为ajax类型
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return ((request.getHeader("accept") != null && request.getHeader(
				"accept").indexOf("application/json") > -1) || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1));
	}

	/**
	 * 获取Request中的Header, Parameter信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestInfo(HttpServletRequest request) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(HttpUtils.getRequestHeaders(request) + "\n\r");
		sb.append(HttpUtils.getRequestParameters(request) + "\n\r");
		return sb.toString();
	}

	/**
	 * 获取Request中的Header信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestHeaders(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("Headers - ");
		Enumeration<?> enu = request.getHeaderNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			sb.append(paraName + ": " + request.getHeader(paraName) + ", ");
		}
		return sb.toString();
	}

	/**
	 * 获取Request中的Attributes信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestAttrs(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("Attributes - ");
		Enumeration<?> enu = request.getAttributeNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			sb.append(paraName + ": " + request.getAttribute(paraName) + ", ");
		}
		return sb.toString();
	}

	/**
	 * 获取Request中的Parameters信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestParameters(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("Param - ");
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			sb.append(paraName + ": " + request.getParameter(paraName) + ", ");
		}
		return sb.toString();
	}

	/**
	 * 获取Request中的InputStream信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestInputStream(HttpServletRequest request)
			throws IOException {
		BufferedReader br;
		String line = null;
		StringBuilder sb = new StringBuilder();
		/**
		 * request 编码
		 */
		request.setCharacterEncoding("utf-8");
		br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
