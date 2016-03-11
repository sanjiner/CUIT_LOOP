package cuit.emergency.app;

import cuit.emergency.util.Constants;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.java.core.ConcreteObservable;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;

public class EmergencyReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)) {   //开机启动(默认开启)
			if (SharedPreferenceUtil.getPrefBoolean(context, Constants.AUTO_START, true)) {
				Intent atIntent = new Intent(context, EmergencyService.class);
				atIntent.setAction(Constants.AUTO_START_BROADCAST);
				context.startService(atIntent);
			}
		} else if (!TextUtils.isEmpty(action) && TextUtils.equals(action, Constants.ACCOUNT_CONFLICT)) { //帐号在异地登录
			ConcreteObservable.newInstance().notifyObserver(EmergencyService.class, Constants.ACCOUNT_CONFLICT);
		} else if (!TextUtils.isEmpty(action) && TextUtils.equals(action, Constants.AUTO_RECONNECT)) {   //自动重连
			ConcreteObservable.newInstance().notifyObserver(EmergencyService.class, Constants.AUTO_RECONNECT);
		} else if (TextUtils.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {  //网络状态改变
			ConcreteObservable.newInstance().notifyObserver(EmergencyService.class, ConnectivityManager.CONNECTIVITY_ACTION);
		}
	}

}
