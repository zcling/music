package com.qmx.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpServletRequest帮助类
 * 
 */
public class RequestUtils {
	public static final String PARAM_SEP = ",";

	public static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		int start = uri.indexOf(contextPath);
		return uri.substring(start+contextPath.length());
	}
	
	public static String getString(HttpServletRequest request, String name, String defaultValue) {
		String[] values = request.getParameterValues(name);
		if(values == null || values.length == 0) {
			return defaultValue;
		}
		return StringUtils.join(values, PARAM_SEP);
	}
	
	public static Integer getInt(HttpServletRequest request, String name,
                                 Integer defaultValue) {
		String temp = request.getParameter(name);
		if (StringUtils.isEmpty(temp)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(temp);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	public static Long getLong(HttpServletRequest request, String name,
                               long defaultValue) {
		String temp = request.getParameter(name);
		if (StringUtils.isEmpty(temp)) {
			return defaultValue;
		}
		try {
			return Long.valueOf(temp);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static Integer[] getIntAll(HttpServletRequest request, String name) {
		String[] temps = request.getParameterValues(name);
		if(temps == null) {
			return new Integer[0];
		}
		List<Integer> result = new ArrayList<Integer>();
		for(String temp : temps) {
			try {
				result.add(Integer.valueOf(temp));
			} catch (Exception e) {
			}
		}
		return result.toArray(new Integer[]{});
	}
	
	public static Double getDouble(HttpServletRequest request, String name,
                                   Double defaultValue) {
		String temp = request.getParameter(name);
		if (StringUtils.isEmpty(temp)) {
			return defaultValue;
		}
		try {
			return Double.valueOf(temp);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static Double[] getDoubleAll(HttpServletRequest request, String name) {
		String[] temps = request.getParameterValues(name);
		if(temps == null) {
			return new Double[0];
		}
		List<Double> result = new ArrayList<Double>();
		for(String temp : temps) {
			try {
				result.add(Double.valueOf(temp));
			} catch (Exception e) {
			}
		}
		return result.toArray(new Double[]{});
	}
	
	public static BigDecimal getBigDecimal(HttpServletRequest request, String name,
                                           BigDecimal defaultValue) {
		String temp = request.getParameter(name);
		if (StringUtils.isEmpty(temp)) {
			return defaultValue;
		}
		try {
			return new BigDecimal(temp);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static BigDecimal[] getBigDecimalAll(HttpServletRequest request, String name) {
		String[] temps = request.getParameterValues(name);
		if(temps == null) {
			return new BigDecimal[0];
		}
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		for(String temp : temps) {
			try {
				result.add(new BigDecimal(temp));
			} catch (Exception e) {
			}
		}
		return result.toArray(new BigDecimal[]{});
	}
	
	/**
	 * 获取QueryString的参数
	 * 
	 * @param request
	 *            web请求
	 * @param name
	 *            参数名称
	 * @return
	 */
	public static String getQueryParam(HttpServletRequest request, String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return StringUtils.join(request.getParameterValues(name), PARAM_SEP);
	}

	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	public static Map<String, String> parseQueryString(String s) {
		if (s == null) {
			return Collections.EMPTY_MAP;
		}
		Map<String, String> ht = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals = (String) ht.get(key);
				ht.put(key, oldVals+PARAM_SEP+val);
			} else {
				ht.put(key, val);
			}
		}
		return ht;
	}

	public static Map<String, Object> getRequestMap(HttpServletRequest request,
                                                    String suffix) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Map<String, String[]> map = request.getParameterMap();
			for(Map.Entry<String, String[]> entry : map.entrySet()) {
				String key = entry.getKey();
				if(key.endsWith(suffix)) {
					String tmpkey = StringUtils.substringBeforeLast(key, suffix);
					String[] value = entry.getValue();
					if(tmpkey.indexOf(".") > -1) {
						String[] keyArray = tmpkey.split("\\.");
						for (int vi = 0; vi < value.length; vi++) {
							Map[] maps = new Map[keyArray.length];
							for(int i = 1; i < keyArray.length; i++) {
								maps[i] = new HashMap();
								if(i == keyArray.length - 1) {
									maps[i].put(keyArray[i], StringUtils.join(value, PARAM_SEP));
								}
							}
							for(int i = 1; i < keyArray.length - 1; i++) {
								maps[i].put(keyArray[i], maps[i+1]);
							}
							Matcher matcher = Pattern.compile("([a-zA-Z]+)\\[([\\d]+)\\]").matcher(keyArray[0]);
							if(matcher.find()) {
								String resultKey = matcher.group(1);
								int index = Integer.parseInt(matcher.group(2));
								Vector list = (Vector)data.get(resultKey);
								if(list == null) {
									list = new Vector();
									list.setSize(index+1);
									list.set(index, maps[1]);
									data.put(resultKey, list);
								} else {
									if(list.size() < index+1) {
										list.setSize(index+1);
										list.set(index, maps[1]);
									} else {
										Map tmp = (Map)list.get(index);
										if(tmp == null) {
											list.set(index, maps[1]);
										} else {
											tmp.putAll(maps[1]);
										}
									}
								}
							} else {
								Map tmp = (Map)data.get(keyArray[0]);
								if(tmp == null) {
									data.put(keyArray[0], maps[1]);
								} else {
									tmp.putAll(maps[1]);
								}
							}
						}
					} else {
						data.put(tmpkey, StringUtils.join(value, PARAM_SEP));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		Map<String, String> result = new HashMap<String, String>();
		for(Map.Entry<String, Object> entry : data.entrySet()) {
			Object value = entry.getValue();
			if(value instanceof Map) {
				result.put(entry.getKey(), JSONObject.fromObject(value).toString());
			} else if (value instanceof Collection) {
				result.put(entry.getKey(), JSONArray.fromObject(value).toString());
			} else {
				result.put(entry.getKey(), value.toString());
			}
		}
		*/
		return data;
	}
	
	public static Map<String, Object> getRequestMap(HttpServletRequest request, ParameterWrapper wrapper) {
		return wrapper.wrap(request);
	}
	
	public static Map<String, String> getRequestMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			map.put(name, StringUtils.join(
					request.getParameterValues(name), PARAM_SEP));
		}
		return map;
	}
	
	public static void setAttributes(HttpServletRequest request, Map<String, String> map) {
		for(Map.Entry<String, String> entry : map.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request.getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	public static String getQueryString(HttpServletRequest request, String... exclude) {
		StringBuffer sb = new StringBuffer();
		List<String> excludeList = Arrays.asList(exclude);
		Enumeration<String> names = request.getParameterNames();
		String name;
		String value;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			value = StringUtils.join(request.getParameterValues(name));
			if (!excludeList.contains(name)) {
				sb.append("&").append(name).append("=")
							.append(value);
			}
		}
		if(sb.length() > 0)
			sb.deleteCharAt(0);
		return sb.toString();
	}
	
	public static interface ParameterWrapper {
		public Map<String, Object> wrap(HttpServletRequest request);
	}
}
