/*
 * Class: SLFInvocationSecurityMetadataSourceService
 * Description:资源权限映射器，按照请求的资源查找所需的权限
 * Version: 1.0
 */
package com.ttd.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.ttd.entity.Authority;
import com.ttd.entity.Resource;
import com.ttd.service.AuthorityService;
import com.ttd.util.StringUtils;

/**
 * 资源权限映射器，按照请求的资源查找所需的权限
 * 
 * @author gao
 * 
 */
public class SLFInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Set<String> HTTP_METHODS = new HashSet<String>(
			Arrays.asList("DELETE", "GET", "POST", "PUT"));

	/** 根据http方法存储对应的资源URL-权限映射 */
	private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMap;

	/**
	 * 忽略请求参数
	 */
	private boolean stripQueryStringFromUrls = true;

	private PathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 根据Http方法返回相应的资源URL-权限映射，若不存在则创建
	 * 
	 * @param GET
	 *            , POST etc
	 * @return 资源URL-权限映射.
	 */
	private Map<Object, Collection<ConfigAttribute>> getRequestMapForHttpMethod(
			String method) {
		if (!StringUtils.isBlank(method) && !HTTP_METHODS.contains(method)) {
			throw new IllegalArgumentException("Unrecognised HTTP method: '"
					+ method + "'");
		}
		if(StringUtils.isBlank(method)){
			//将空格等转为null
			method = null;
		}
		Map<Object, Collection<ConfigAttribute>> methodRequestMap = httpMethodMap
				.get(method);

		if (methodRequestMap == null) {
			methodRequestMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
			httpMethodMap.put(method, methodRequestMap);
		}

		return methodRequestMap;
	}

	/**
	 * 加载所有资源与权限的关系
	 */
	private void loadResourceDefine() {
		// init httpMethodMap
		httpMethodMap = new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();

		// 获取所有权限
		List<Authority> authorities = authorityService.listAll(false);

		for (Authority authority : authorities) {
			// 以权限code封装为Spring的security Object
			ConfigAttribute ca = new SecurityConfig(
					authority.getCode());
			// 权限对应的资源
			Set<Resource> resources = authority.getResources();

			for (Resource resource : resources) {
				// 根据资源的http请求方法使用不同的map
				Map<Object, Collection<ConfigAttribute>> mapToUse = getRequestMapForHttpMethod(resource
						.getMethod());

				String url = resource.getUrl();
				// 将资源URL-权限放入map中
				if (mapToUse.containsKey(url)) {
					Collection<ConfigAttribute> value = mapToUse.get(url);
					value.add(ca);
					mapToUse.put(url, value);
				} else {
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					mapToUse.put(url, atts);
				}
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		if (null == httpMethodMap) {
			loadResourceDefine();
		}
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<String, Map<Object, Collection<ConfigAttribute>>> entry : httpMethodMap
				.entrySet()) {
			for (Collection<ConfigAttribute> attrs : entry.getValue().values()) {
				allAttributes.addAll(attrs);
			}
		}

		return allAttributes;
	}

	/**
	 * 根据请求资源获取所需权限
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) {
		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException(
					"Object must be a FilterInvocation");
		}
		if (null == httpMethodMap) {
			loadResourceDefine();
		}
		String url = ((FilterInvocation) object).getRequestUrl();
		String method = ((FilterInvocation) object).getHttpRequest()
				.getMethod();

		return lookupAttributes(url, method);
	}

	/**
	 * 
	 * @param url
	 *            请求url
	 * @param method
	 *            GET, POST, DELETE..., or null for any method.
	 * @return 对应的ConfigAttribute，未找到则返回null
	 */
	public final Collection<ConfigAttribute> lookupAttributes(String url,
			String method) {
		if (stripQueryStringFromUrls) {
			// 忽略请求参数
			int firstQuestionMarkIndex = url.indexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}

		// 根据url在对应的map中取出权限
		Collection<ConfigAttribute> attributes = extractMatchingAttributes(url,
				httpMethodMap.get(method));

		// 若未找到，则获取未指定方法的，即存在null下的map
		if (attributes == null) {
			attributes = extractMatchingAttributes(url, httpMethodMap.get(null));
		}

		return attributes;
	}

	private Collection<ConfigAttribute> extractMatchingAttributes(String url,
			Map<Object, Collection<ConfigAttribute>> map) {
		if (map == null) {
			return null;
		}

		for (Map.Entry<Object, Collection<ConfigAttribute>> entry : map
				.entrySet()) {
			// System.out.println("match:"+entry.getKey()+" >>> "+url);
			boolean matched = pathMatcher.match((String) entry.getKey(), url);
			logger.debug("MATCH: "+entry.getKey()+" && "+url+" >>>>> "+matched);
			
			if (matched) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
		this.stripQueryStringFromUrls = stripQueryStringFromUrls;
	}
}
