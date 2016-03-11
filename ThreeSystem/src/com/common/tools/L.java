package com.common.tools;

import com.common.config.GlobalConfig;

import android.util.Log;

/**
 * android 日志管理类
 * 
 * @author Jialong
 *
 */
public class L {
	public static int LEVEL = GlobalConfig.LOGLEVEL;

	public static void v(String tag, String msg) {
		if (L.LEVEL <= Log.VERBOSE)
			Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (L.LEVEL <= Log.DEBUG)
			Log.d(tag, msg);
	}
	
	public static void i(String tag, String msg) {
		if (L.LEVEL <= Log.INFO)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (L.LEVEL <= Log.WARN)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (L.LEVEL <= Log.ERROR)
			Log.e(tag, msg);
	}

	public static void wtf(String tag, String msg) {
		if (L.LEVEL <= Log.ASSERT)
			Log.wtf(tag, msg);
	}

}