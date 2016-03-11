package com.android.volley.plus;

import java.util.Map;

public class CookieManager {
	/* ASP.NET_SessionId PHPSESSID JSESSIONID */
	public static String SESSION_NAME = "JSESSIONID";

	/* 下一次网络请求的cookie */
	public static String REQUEST_COOKIE = "";
	
	/* 最近一次网络访问获取的cookie */
	public static String RESPONSE_COOKIE = "";
	
	/**
	 * 在有sessionid的情况下添加 cookie
	 * @param cookies
	 */
	public static void addCookie(Map<String, String> cookies){
		if(cookies != null){
			for(String name : cookies.keySet()){
				REQUEST_COOKIE = REQUEST_COOKIE + ";" + name + "=" + cookies.get(name);
			}
		}
	}
}
