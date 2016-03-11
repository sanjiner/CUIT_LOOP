package com.common.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

/**
 * A static Class help detect the network state 
 */

public class NetDetection {
	public static final int NET_NONE = 0x100001;
	public static final int NET_MOBILE = 0x100002;
	public static final int NET_WIFI = 0x100003;
	
	public static final int state(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		State state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		/*if (state == State.CONNECTING || state == State.CONNECTED)
			return NET_MOBILE;*/
		if (state == State.CONNECTED)
			return NET_MOBILE;
		state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		/*if (state == State.CONNECTING || state == State.CONNECTED)
			return NET_WIFI;*/
		if (state == State.CONNECTED)
			return NET_WIFI;
		
		return NET_NONE;
	}
	
	/**
	 * 判断是否有网络
	 * @param context
	 * @return
	 */
	public boolean isConnect(Context context){
		if(state(context) == NET_NONE){
			return false;
		}else{
			return true;
		}
	}
}
