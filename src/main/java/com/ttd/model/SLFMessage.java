/*
 * Class: SLFMessage
 * Description:消息类，用来向前台页面或ajax传递提示或错误信息
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-6-26
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.model;

import java.io.Serializable;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 消息类。用来向前台页面或ajax传递提示或错误信息
 * 
 * @author Vinda
 * 
 */
public class SLFMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1185647777120707264L;
	// 提示信息
	public static final int LEVEL_INFO = 1;

	// 警告信息
	public static final int LEVEL_WARN = 3;

	// 错误信息
	public static final int LEVEL_ERROR = 5;

	// 消息的级别
	private int level = LEVEL_INFO;

	// 消息的内容
	private String text;

	// 关联的异常堆栈信息
	private Throwable stack;

	public SLFMessage() {
	}

	public SLFMessage(int level, String text) {
		this.level = level;
		this.text = text;
	}

	public static SLFMessage INFO(String text) {
		return new SLFMessage(LEVEL_INFO, text);
	}

	public static SLFMessage WARN(String text) {
		return new SLFMessage(LEVEL_WARN, text);
	}

	public static SLFMessage ERROR(String text, Throwable stack) {
		SLFMessage msg = new SLFMessage(LEVEL_ERROR, text);
		msg.setStack(stack);
		return msg;
	}

	/**
	 * 获取详细错误信息
	 * @return
	 */
	public JsonNode toJSON() {
		ObjectNode json = JsonNodeFactory.instance.objectNode();
		json.put("level", getLevel());
		json.put("text", getText());
		if (getStack() != null) {
			StringBuffer str = new StringBuffer();

			str.append(getStack().toString());
			for (StackTraceElement trace : getStack().getStackTrace()) {
				str.append("\n\t " + trace);
			}
			json.put("stack", str.toString());
		}
		return json;
	}

	/** get,set */
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Throwable getStack() {
		return stack;
	}

	public void setStack(Throwable stack) {
		this.stack = stack;
	}
}
