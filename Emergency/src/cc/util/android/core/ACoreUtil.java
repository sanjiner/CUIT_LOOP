package cc.util.android.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

/**
 * Android的一个工具类
 * @author wangcccong
 * @version 1.1406
 * <br>create at: Tue, 26 Aug. 2014
 */
public class ACoreUtil {
	private static Context mContext = null;
	
	public static void init(Context context) {
		mContext = context;
	}
	
	public static void hasPermission() {
		if (mContext == null) throw new NullPointerException("you must initialize in appliction to use: CCCoreUtil.init");
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pInfo = null;
		try {
			pInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
			String[] permissions = pInfo.requestedPermissions;
			for (String permission : permissions) {
				if (TextUtils.equals(mContext.getPackageName()+".permission."+"WANG_CC_CONG", permission)) return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Authentication failed, you must register WANG_CC_CONG permission like:" +
					" <uses-permission android:name=\"PACKNAME.permission.WANG_CC_CONG\" />");
			throw new RuntimeException("you must register WANG_CC_CONG permission like:" +
					" <uses-permission android:name=\"PACKNAME.permission.WANG_CC_CONG\" />");
		}
		System.out.print("Authentication failed, you must register WANG_CC_CONG permission like:" +
				" <uses-permission android:name=\"PACKAGENAME.permission.WANG_CC_CONG\" />");
		throw new RuntimeException("you must register WANG_CC_CONG permission like:" +
				" <uses-permission android:name=\"PACKNAME.permission.WANG_CC_CONG\" />");
	}
	
	/** 判断程序是否在后台运行 */
	public static boolean isBackGround() {
		ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : processInfos) {
			if (TextUtils.equals(info.processName, mContext.getPackageName())
					&& info.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
				return true;
			}
		}
		return false;
	}

	/* 将文件转换为byte[] */
	public static byte[] getFileBytes(File file) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int bytes = (int) file.length();
			byte[] buffer = new byte[bytes];
			int readBytes = bis.read(buffer);
			if (readBytes != buffer.length) {
				throw new IOException("Entire file not read");
			}
			return buffer;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	// 压缩图片
	public static byte[] compressBmpToPath(Context context, String path, int width,
			int height, boolean isAdjust) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		Log.e("压缩--->", bitmap+"");
		if (bitmap == null) return new byte[0];
		if (bitmap.getWidth() > width || bitmap.getHeight() > height) {
			// 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal
			// divisor, int scale, int roundingMode);
			// scale表示要保留的小数位,
			float scaleX = new BigDecimal(width)
					.divide(new BigDecimal(bitmap.getWidth()), 4, RoundingMode.DOWN).floatValue();
			float scaleY = new BigDecimal(height).divide(
					new BigDecimal(bitmap.getHeight()), 4, RoundingMode.DOWN).floatValue();
			if (isAdjust) {
				scaleX = (scaleX < scaleY ? scaleX : scaleY);
				scaleY = scaleX;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scaleX, scaleY);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
			byte[] bts = outputStream.toByteArray();
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bts;
		}
		return new byte[0];
	}
	
	// 压缩图片
	public static Bitmap compressBitmap(Bitmap bitmap, int width,
			int height, boolean isAdjust) {
		if (bitmap.getWidth() > width || bitmap.getHeight() > height) {
			float scaleX = new BigDecimal(width)
					.divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN).floatValue();
			float scaleY = new BigDecimal(height).divide(
					new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN).floatValue();
			if (isAdjust) {
				scaleX = (scaleX < scaleY ? scaleX : scaleY);
				scaleY = scaleX;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scaleX, scaleY);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		}
		return bitmap;
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
	
	/* 将图片转换为2进制字符串 */
	public static String bmpToString(Bitmap bitmap) {
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
	
	/* 将二进制转换为string */
	public static String bytesToString(byte[] bts) {
		String bmpStr = "";
		try {
			bmpStr = Base64.encodeToString(bts, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bmpStr;
	}
	
	/* 将base64 字符串转换为byte[] */
	public static byte[] base64ToByte(String base64Str) {
		try {
			return Base64.decode(base64Str, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new byte[0];
	}
}
