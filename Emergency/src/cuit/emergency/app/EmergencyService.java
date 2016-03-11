package cuit.emergency.app;

import java.util.List;
import java.util.Map;

import cuit.emergency.ConflictActivity;
import cuit.emergency.LoadingActivity;
import cuit.emergency.MainActivity;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import cuit.emergency.util.ToolUtil;
import cc.util.android.core.AppManager;
import cc.util.android.core.LogUtil;
import cc.util.android.core.NetUtil;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;

public class EmergencyService extends Service implements Observer {

	private ActivityManager mActivityManager;  
	private NotificationManager nManager;  //消息通知
	private AlarmManager alarmManager;   //重连闹钟
	private PendingIntent pendIntent;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ConcreteObservable.newInstance().registerObserver(this);
		
		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent != null && intent.getAction() != null && TextUtils.equals(intent.getAction(), 
				Constants.AUTO_START_BROADCAST)) { //如果收到开机启动信号
			final String account = SharedPreferenceUtil.getPrefString(this,
					Constants.UNAME, "");
			final String password = SharedPreferenceUtil.getPrefString(this,
					Constants.UPASSWD, "");
			final String server = SharedPreferenceUtil.getPrefString(this, Constants.USERVER, "");
			if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
				SmackerHelper.newInstance(this).login(account, password,server);
			} else {
				Intent intent2 = new Intent(this, LoadingActivity.class);
				startActivity(intent2);
			}
		}

		//注册一个重连闹钟
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(getApplicationContext(), EmergencyReceiver.class);
		alarmIntent.setAction(Constants.AUTO_RECONNECT);
		pendIntent = PendingIntent.getBroadcast(getApplicationContext(),  
		        0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);  
		// 5秒后发送广播，然后每个15秒重复发广播。广播都是直接发到AlarmReceiver的  
		long triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000; 
		int interval = 15 * 1000;  
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, interval, pendIntent);

		return START_STICKY_COMPATIBILITY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.e("EmergencyService--Destory", "执行destory");
		ConcreteObservable.newInstance().unRegisterObserver(this);
		alarmManager.cancel(pendIntent);
		cancelNotify();
	}
	
	//判断程序运行状态
	public boolean isAppOnForeground() {
		List<RunningTaskInfo> taskInfos = mActivityManager.getRunningTasks(1);
		if (taskInfos.size() > 0
				&& TextUtils.equals(getPackageName(),
						taskInfos.get(0).topActivity.getPackageName())) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void showNotification(final Map<String, String> map) {
		if (!SharedPreferenceUtil.getPrefBoolean(this, Constants.MESSAGE_NOTIFY, true)) return;
		String title = map.get(DBHelper.C_CHAT_NICKNAME);
		final Notification n = new Notification(cuit.emergency.R.drawable.ic_launcher,
				title, System.currentTimeMillis());
		if (SharedPreferenceUtil.getPrefBoolean(this, Constants.VIBRATOR, true))
			n.defaults = Notification.DEFAULT_VIBRATE;
		if (SharedPreferenceUtil.getPrefBoolean(this, Constants.VOICE, true))
			n.defaults = Notification.DEFAULT_SOUND;
		n.flags = Notification.FLAG_AUTO_CANCEL;

		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra("notify", true);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		n.contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		String message = ToolUtil.strIsFile(map.get(DBHelper.C_MSG)) ? 
				"图片消息" : (ToolUtil.strIsVoice(map.get(DBHelper.C_MSG)) ? 
				"语音消息" : map.get(DBHelper.C_MSG));
		n.setLatestEventInfo(this, title, message, n.contentIntent);
		nManager.notify(1, n);
	}
	
	public void cancelNotify() {
		nManager.cancel(1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		if (objs[0].equals(ConnectivityManager.CONNECTIVITY_ACTION)) {  //网络状态改变
			if (NetUtil.state(this) != NetUtil.NET_NONE) {
				SmackerHelper.newInstance(this).reconnect(Constants.RECONNECT_AUTO);
			}
		} else if (objs[0].equals(Constants.ACCOUNT_CONFLICT)) {  //帐号在异地登录
			stopSelf();
			if (AppManager.newInstance().lastActivity() instanceof ConflictActivity) return;
			Intent intent = new Intent(this, ConflictActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else if (objs[0].equals(Constants.AUTO_RECONNECT)) {  //自动重连
			SmackerHelper.newInstance(this).reconnect(Constants.RECONNECT_AUTO);
		} else if (objs[0].equals(Constants.NEW_MESSAGE) || objs[0].equals(Constants.NEW_MESSAGE_MULTI)) {
			if (!isAppOnForeground()) {
				showNotification((Map<String, String>)objs[1]);
			} 
		}
	}
}
