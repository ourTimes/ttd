/*
 * Class: StatusJSONObjectProducer
 * Description:状态消息生成类，生成前台需要的状态消息
 */
package com.ttd.util;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 产生JSONObject形式的状态信息，以Ajax形式返回到前台
 * 
 * @author gao
 * 
 */
public class StatusJSONObjectProducer {
	private ObjectNode json;
	private static final String SQL_EXCEPTION = "SQL EXCEPTION";

	public StatusJSONObjectProducer() {
		this.json = JsonNodeFactory.instance.objectNode();
		this.setSuccess();
	}

	public ObjectNode produce() {
		if (json.get("description") != null) {
			String s = json.get("description").asText();
			s = doFilter(s);
			json.put("description", s);
		}
		return json;
	}

	public String doFilter(String description) {
		if (description != null && description.contains(SQL_EXCEPTION)) {
			return "error.009.sql.exception";
		} else
			return description;
	}
	/**
	 * 设置状态为成功
	 * @param message
	 */
	public void setSuccess() {
		json.put("success", "true");
	}
	
	/**
	 * 设置状态为成功
	 * @param message
	 */
	public void setSuccess(String message ) {
		json.put("success", "true");
		json.put("description", message);
	}

	/**
	 * 设置状态为错误，并设置错误消息
	 * @param message
	 */
	public void setFail(String message) {
		json.put("success", "false");
		json.put("description", message);
	}

	/**
	 * 设置状态为错误，并设置错误消息和错误代码
	 * @param message
	 * @param errorCode
	 */
	public void setFail(String message, int errorCode) {
		json.put("success", "false");
		json.put("description", message);
		json.put("errorCode", errorCode);
	}

	/**
	 * 设置错误代码
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode) {
		json.put("errorCode", errorCode);
	}

	public void setReturnData(Object data) {
		json.putPOJO("data", data);
	}
}
