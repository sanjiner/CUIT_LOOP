package cc.util.android.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

/**
 * 网络检测 
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 */
public class NetUtil {
	public static final int NET_NONE = 0x100001;
	public static final int NET_MOBILE = 0x100002;
	public static final int NET_WIFI = 0x100003;
	
	public static final int state(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		State state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if (state == State.CONNECTING || state == State.CONNECTED)
			return NET_MOBILE;
		
		state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (state == State.CONNECTING || state == State.CONNECTED)
			return NET_WIFI;
		
		return NET_NONE;
	}
}
