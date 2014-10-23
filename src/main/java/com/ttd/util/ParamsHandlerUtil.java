package com.ttd.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author yi.yuy
 *
 */
public class ParamsHandlerUtil {

	/**
	 * 参数值为""或者null，不做传值
	 * @param params
	 * @param key
	 * @param param
	 */
	public static void addNotBlank(Map<String, Object> params, String key, String param) {
		if (StringUtils.isNotBlank(param)){
			params.put(key, param);		
		}
	}
	/**
	 * 参数值为null，不做传值
	 * @param params
	 * @param key
	 * @param param
	 */
	public static void addNotNull(Map<String, Object> params, String key, Object param) {
		if (null != param){
			params.put(key, param);		
		}
	}
}
