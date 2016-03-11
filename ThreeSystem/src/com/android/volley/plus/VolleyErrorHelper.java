package com.android.volley.plus;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;

public class VolleyErrorHelper {

	public static String getErrorType(Exception error) {
		if (error instanceof TimeoutError) {
			return "网络请求超时";
		} else if (error instanceof ServerError) {
			return "服务异常";
		} else if (error instanceof AuthFailureError) {
			return "认证失败";
		} else if (error instanceof NetworkError) {
			return "没有网络，去设置~";
		} else if (error instanceof NoConnectionError) {
			return "网络异常，请检查你的网络";
		} else if (error instanceof ParseError) {
			return "解析异常";
		} else {
			return "未知异常";
		}

	}
}
