package com.android.volley.plus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.common.tools.L;

/**
 * url 工具类
 * @author Jialong
 *
 */
public class URLTools {
	public static String DEFAULT_CONDING = "UTF-8";
	public static String TAG = "URLTools";

	/**
	 * 将请求参数和值添加到url上
	 * 
	 * @param url
	 *            源url
	 * @param params
	 *            请求参数
	 * @return 如果请求params为null或参数个数为0 返回原url，创建失败返回null
	 */
	public static String buildURL(String url, Map<String, String> params) {
		if (params == null) {
			return url;
		} else if (params.size() == 0) {
			return url;
		} else {
			StringBuilder str = new StringBuilder(url);
			str.append("?");
			for (String name : params.keySet()) {
				try {
					str.append(URLEncoder.encode(name, DEFAULT_CONDING));
					str.append("=");
					str.append(URLEncoder.encode(params.get(name),
							DEFAULT_CONDING));
				} catch (UnsupportedEncodingException e) {
					L.e(TAG, e.getMessage());
					return null;
				}
				str.append("&");
			}
			str.deleteCharAt(str.length() - 1);
			return str.toString();
		}
	}

	/**
	 * 将请求参数和值添加到url上
	 * 
	 * @param url
	 *            源url
	 * @param names
	 *            参数名
	 * @param values
	 *            参数值
	 * @return 
	 *         如果names和values同时为null或长度为0，返回原url，names和values长度同时不为0且长度相等，返回需要的url
	 */
	public static String buildURL(String url, String[] names, String[] values) {
		if ((names == null) && (values == null)) {
			return url;
		} else if ((names.length == 0) && (values.length == 0)) {
			return url;
		} else if (names.length == values.length) {
			Map<String, String> params = new HashMap<String, String>();
			for (int i = 0; i < names.length; i++) {
				params.put(names[i], values[i]);
			}
			return buildURL(url, params);
		} else {
			return null;
		}

	}

}
