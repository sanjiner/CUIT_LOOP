package cuit.emergency;

import java.util.List;
import java.util.Map;

import cuit.emergency.app.EmergencyService;
import cuit.emergency.fragment.ChatFragment;
import cuit.emergency.fragment.MultiChatFragment;
import cuit.emergency.fragment.RosterFragment;
import cuit.emergency.fragment.MessageFragment;
import cuit.emergency.fragment.SettingFragment;
import cuit.emergency.tool.ChatOffline;
import cuit.emergency.tool.RoomOffline;
import cuit.emergency.tool.RosterOffline;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import cc.util.android.core.AppManager;
import cc.util.android.core.BaseActivity;
import cc.util.android.core.FragmentInfoActivity;
import cc.util.android.core.LogUtil;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.core.ToastUtil;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

public class MainActivity extends BaseActivity implements Observer {
	
	@ResInject(R.id.main_radiogroup)
	RadioGroup mRadioGroup;
	@ResInject(R.id.main_radio_message)
	RadioButton radioMessage;
	@ResInject(R.id.main_radio_contact)
	RadioButton radioContact;
	@ResInject(R.id.main_radio_setting)
	RadioButton radioSetting;
	
	RosterOffline mRosterOffline;
	ChatOffline mChatOffline;
	RoomOffline mRoomOffline;
	
	private long firstTime = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ConcreteObservable.newInstance().registerObserver(this);
		//启动服务
		Intent intent = new Intent(this, EmergencyService.class);
		startService(intent);
		setContentView(R.layout.activity_main);
		SmackerHelper.newInstance(this).fetchRoomItem();
		initActionBarCompat();
		ViewInjectUtil.inject(this);
		getSupportFragmentManager().beginTransaction().add(R.id.main_content, new MessageFragment(), 
				MessageFragment.class.getName()).commit();
		mRosterOffline = new RosterOffline(this);
		mChatOffline = new ChatOffline(this);
		mRoomOffline = new RoomOffline(this);
		SharedPreferenceUtil.setPrefBoolean(this, Constants.VOICE, false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onCheckedChanged(mRadioGroup, mRadioGroup.getCheckedRadioButtonId());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (firstTime != 0 && System.currentTimeMillis() - firstTime < 3000) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
				return true;
			} else {
				firstTime = System.currentTimeMillis();
				ToastUtil.show(MainActivity.this, "再按一次返回桌面", Toast.LENGTH_SHORT);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@ViewListenerInject(value = {R.id.main_radiogroup}, type = ViewListenerType.OnCheckedChange)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		hideAllFragment();
		String fragment_tag = "";
		switch (checkedId) {
		case R.id.main_radio_message:
			fragment_tag = MessageFragment.class.getName();
			break;
		case R.id.main_radio_contact:
			fragment_tag = RosterFragment.class.getName();
			break;
		default:
			fragment_tag = SettingFragment.class.getName();
			break;
		}
		Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragment_tag);
		if (fragment == null) {
			fragment = Fragment.instantiate(this, fragment_tag, null);
			getSupportFragmentManager().beginTransaction().add(R.id.main_content, fragment, fragment_tag).commit();
		} else {
			getSupportFragmentManager().beginTransaction().show(fragment).commit();
		}	
	}
	
	//隐藏所有已加载的Fragment
	private void hideAllFragment() {
		List<Fragment> fragments_list = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments_list) {
			getSupportFragmentManager().beginTransaction().hide(fragment).commit();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		if (objs[0].equals(Constants.FRIEND_STATUS)) {
			mRosterOffline.updateMode(objs[1].toString(), objs[2].toString());
			ConcreteObservable.newInstance().notifyObserver(RosterFragment.class, Constants.FRIEND_STATUS);
		} else if (objs[0].equals(Constants.NEW_MESSAGE)) {
			LogUtil.e("main", Constants.NEW_MESSAGE);
			FragmentActivity lastActivity = (FragmentActivity) AppManager.newInstance().lastActivity();
			if (AppManager.newInstance().lastActivity() instanceof FragmentInfoActivity && lastActivity != null &&
					lastActivity.getSupportFragmentManager().findFragmentByTag(ChatFragment.class.getName()) != null) {
				ConcreteObservable.newInstance().notifyObserver(ChatFragment.class, objs);
				LogUtil.e("main", Constants.NEW_MESSAGE+"---if");
			} else {
				mChatOffline.insert((Map<String, String>)objs[1]);
				LogUtil.e("main", Constants.NEW_MESSAGE+"---else");
			}
			ConcreteObservable.newInstance().notifyObserver(MessageFragment.class, Constants.NEW_MESSAGE);
		} else if (objs[0].equals(Constants.NEW_MESSAGE_MULTI)) {
			LogUtil.e("main", Constants.NEW_MESSAGE_MULTI);
			FragmentActivity lastActivity = (FragmentActivity) AppManager.newInstance().lastActivity();
			if (AppManager.newInstance().lastActivity() instanceof FragmentInfoActivity && lastActivity != null &&
					lastActivity.getSupportFragmentManager().findFragmentByTag(MultiChatFragment.class.getName()) != null) {
				ConcreteObservable.newInstance().notifyObserver(MultiChatFragment.class, objs);
				LogUtil.e("main", Constants.NEW_MESSAGE_MULTI+"---if");
			} else {
				mChatOffline.insert((Map<String, String>)objs[1]);
				LogUtil.e("main", Constants.NEW_MESSAGE_MULTI+"---else");
			}
			ConcreteObservable.newInstance().notifyObserver(MessageFragment.class, Constants.NEW_MESSAGE_MULTI);
		} else if (objs[0].equals(Constants.FETCH_ROOM_ENDED)) {
			List<Map<String, String>> tmpList = (List<Map<String, String>>)objs[1];
			if (tmpList.size() != 0) mRoomOffline.insert(tmpList);
			ConcreteObservable.newInstance().notifyObserver(RosterFragment.class, objs);
		}
	}
}
