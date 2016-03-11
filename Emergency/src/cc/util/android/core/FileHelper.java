package cc.util.android.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 获取文件缓存路径等
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 */
public class FileHelper {

	public final static String getCacheDir(Context context) {
		String cacheDir = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheDir = context.getExternalCacheDir() + File.separator;
		} else {
			cacheDir = context.getCacheDir() + File.separator;
		}
		File file = new File(cacheDir);
		if (!file.exists()) file.mkdirs();
		return cacheDir;
	}
	
	/**
	 * 
	 * @param srcPath
	 * @return
	 */
	public final static byte[] readByte(String srcPath) {
		File file = new File(srcPath);
		if (file.exists()) return readByte(file);
		return null;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public final static byte[] readByte(File file) {
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
		} catch (IOException e) {
			Log.e("FileHelper-->readByte", e.getMessage()+"");
		}
		return null;
	}
	
	public final static String readString(String srcPath) {
		return null;
	}
	
	public final static boolean write(Context context, String srcPath, String desFileName) {
		return false;
	}
	
	public final static boolean write(Context context, String srcPath, String desFileName, boolean isDelSrc) {
		return false;
	}
}