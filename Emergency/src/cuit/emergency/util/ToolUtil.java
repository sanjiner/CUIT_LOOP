package cuit.emergency.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONStringer;

import cc.util.android.core.SdcardUtil;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.util.Base64;

public class ToolUtil {

	static ProgressDialog dialog;
	
	
	/**
	 * 显示提示dialog
	 * @param context
	 * @param message
	 */
	public static void showDialog(Context context, String message) {
		dialog = ProgressDialog.show(context, null, message, true, false);
		dialog.show();
	}
	
	/**
	 * 隐藏dialog
	 */
	public static void dismissDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	
	/**
	 * 格式化指定日期，指定格式的时间字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(final Date date, final String format) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format,
				Locale.CHINA);
		return dateFormat.format(date);
	}

	/**
	 * 获取到数据库缓存路径
	 * @param context
	 * @return
	 */
	public static String getDBCachePath(Context context) {
		String dbPath = SdcardUtil.isAccessExternal(context) ? context
				.getExternalCacheDir().toString()
				+ File.separator
				+ "db"
				+ File.separator : context.getCacheDir().toString()
				+ File.separator + "db" + File.separator;
		File file = new File(dbPath);
		if (!file.exists())
			file.mkdirs();
		return dbPath;
	}

	/**
	 * 获取图片缓存路径
	 * @param context
	 * @return
	 */
	public static String getIMGCachePath(Context context) {
		String imgPath = SdcardUtil.isAccessExternal(context) ? context
				.getExternalCacheDir().toString()
				+ File.separator
				+ "image"
				+ File.separator : context.getCacheDir().toString()
				+ File.separator + "image" + File.separator;
		File file = new File(imgPath);
		if (!file.exists())
			file.mkdirs();
		return imgPath;
	}

	/**
	 * 获取音频缓存路径
	 * @param context
	 * @return
	 */
	public static String getVOICECachePath(Context context) {
		String voicePath = SdcardUtil.isAccessExternal(context) ? context
				.getExternalCacheDir().toString()
				+ File.separator
				+ "voice"
				+ File.separator : context.getCacheDir().toString()
				+ File.separator + "voice" + File.separator;
		File file = new File(voicePath);
		if (!file.exists())
			file.mkdirs();
		return voicePath;
	}

	/* 将文件转换为byte[] */
	public static byte[] getFileBytes(File file) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int bytes = (int) file.length();
			byte[] buffer = new byte[bytes];
			int readBytes = bis.read(buffer);
			if (readBytes != buffer.length) {
				bis.close();
				throw new IOException("Entire file not read");
			}
			bis.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将图片转换为2进制字符串
	 * @param bitmap
	 * @return
	 */
	public static String bmpToBinary(Bitmap bitmap) {
		String bmpStr = "";
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
			byte[] bts = outputStream.toByteArray();
			bmpStr = Base64.encodeToString(bts, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bmpStr;
	}
	
	/* 将文件转换为String */
	public static String bytesToBinary(File file) {
		String fileStr = "";
		try {
			byte[] bts = getFileBytes(file);
			fileStr = Base64.encodeToString(bts, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fileStr;
	}

	/**
	 * 将二进制转换为string
	 * @param bts
	 * @return
	 */
	public static String bytesToString(byte[] bts) {
		String bmpStr = "";
		try {
			bmpStr = Base64.encodeToString(bts, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bmpStr;
	}

	/**
	 * 将base64 字符串转换为byte[]
	 * @param base64Str
	 * @return
	 */
	public static byte[] base64ToByte(String base64Str) {
		try {
			return Base64.decode(base64Str, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new byte[0];
	}
	
	public static enum Json {JsonObj, JsonArray};

	public static void verifyJabberID(String jid)
			throws Exception {
		if (jid != null) {
			Pattern p = Pattern
					.compile("(?i)[a-z0-9\\-_\\.]++@[a-z0-9\\-_]++(\\.[a-z0-9\\-_]++)++");
			Matcher m = p.matcher(jid);

			if (!m.matches()) {
				throw new Exception(
						"Configured Jabber-ID is incorrect!");
			}
		} else {
			throw new Exception("Jabber-ID wasn't set!");
		}
	}

	public static void verifyJabberID(Editable jid)
			throws Exception {
		verifyJabberID(jid.toString());
	}

	public static int tryToParseInt(String value, int defVal) {
		int ret;
		try {
			ret = Integer.parseInt(value);
		} catch (NumberFormatException ne) {
			ret = defVal;
		}
		return ret;
	}

	public static String splitJidAndServer(String account) {
		if (!account.contains("@"))
			return account;
		String[] res = account.split("@");
		String userName = res[0];
		return userName;
	}

	public static final String strToStr(String str) {
		if(str == null) return "";
		return str.trim();
	}
	
	public static final String objToStr(Object obj) {
		if(obj == null) return "";
		return obj.toString().trim();
	}
	
	/**
	 * trans map to jsonObject
	 * @param map {@link java.util.Map}
	 * @return jsonObject string
	 */
	public static final String toJsonStr(Map<String, String> map, Json json) {
		if(map == null) throw new NullPointerException("map should not be null");
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		JSONStringer jsonStringer = new JSONStringer();
		try {
			if (json == Json.JsonObj) jsonStringer.object();
			else jsonStringer.array();
			while(iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				jsonStringer.key(entry.getKey()).value(entry.getValue());
			}
			if (json == Json.JsonObj) return jsonStringer.endObject().toString();
			else jsonStringer.endArray().toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * trans two {@link java.lang.String} String[] to jsonObject
	 * @param keys the json's key
	 * @param values  the json's value
	 * @return jsonObject string
	 */
	public static final String toJsonStr(String[] keys, String[] values, Json json) {
		if(keys == null | values == null) throw new NullPointerException("keys and values should not be null");
		int length = keys.length <= values.length ? keys.length : values.length;
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < length; i++) {
			map.put(keys[i], values[i]);
		}
		return toJsonStr(map, json);
	}
	
	public static String strToFile(String filePath) {
		return "file:"+filePath;
	}
	
	public static String strFromFile(String filePath) {
		return filePath.substring(5);
	}
	
	public static boolean strIsFile(String filePath) {
		return filePath.startsWith("file:");
	}
	
	public static String strToVoice(String voicePath) {
		return "voice:"+voicePath;
	}
	
	public static String strFromVoice(String voicePath) {
		return voicePath.substring(6);
	}
	
	public static boolean strIsVoice(String voicePath) {
		return voicePath.startsWith("voice:");
	}
}
