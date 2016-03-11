package cc.util.android.core;

import android.content.Context;
import android.os.Environment;

/**
 * 检测SDCard是否存在
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 */
public class SdcardUtil {
	
	public static final boolean isAccessExternal(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
			&& context.getExternalCacheDir() != null)
			return true;
			
		return false;
	}
}
