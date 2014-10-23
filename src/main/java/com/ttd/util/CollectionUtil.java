/**
 * 
 */
package com.ttd.util;

import java.util.Collection;

/**
 * collection相关工具方法
 * 
 * 
 *
 */
public class CollectionUtil {

	/**
	 * 判断Collection是否null或者size为0
	 * @param c
	 * @return
	 */
	public static final boolean isEmpty(Collection<?> c) {
		return null == c || 0 == c.size()? true : false;
	}
	
	/**
	 * 判断Collection是否不为null或者size不为0
	 * @param c
	 * @return
	 */
	public static final boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
	
	/**
	 * 判断指定的一个或者多个集合，是否存在空集合
	 * @param colls 参数集合， 一个或者多个
	 * @return
	 */
	public static final boolean isEmpty(Collection<?>... colls) {
		boolean result = false;
		for (Collection<?> coll : colls) {
			if (null == coll || coll.isEmpty()) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 判断指定的一个或者多个集合，是否都是非空集合
	 * @param colls 参数集合， 一个或者多个
	 * @return
	 */
	public static final boolean isNotEmpty(Collection<?>... colls) {
		return !isEmpty(colls);
	}
}
