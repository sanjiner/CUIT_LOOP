package cuit.emergency.fragment;

import cuit.emergency.LoginActivity;
import cuit.emergency.R;
import cuit.emergency.app.EmergencyService;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

public class SettingFragment extends BaseFragment implements Observer {

	@ResInject(R.id.setting_vibrator_switch)
	private CheckBox mVibrator;
	@ResInject(R.id.setting_msg_voice)
	private CheckBox mVoice;
	@ResInject(R.id.setting_my_security)
	private CheckBox mSecurity;
	@ResInject(R.id.setting_auto_start)
	private CheckBox mAutoStart;
	@ResInject(R.id.setting_message_notify)
	private CheckBox mNotify;
	@ResInject(R.id.setting_status_text)
	private TextView mStatusText;
	@ResInject(R.id.statusIcon)
	private ImageView statusImage;
	
	private ProgressDialog mDialog;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ConcreteObservable.newInstance().registerObserver(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = (View) inflater.inflate(R.layout.fragment_setting, container, false);
		ViewInjectUtil.inject(this, view);
		SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.VOICE, false);
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String statusMode = SharedPreferenceUtil.getPrefString(getActivity(),
				Constants.STATUS_MODE, Constants.AVAILABLE);
		if (statusMode.equals(Constants.AVAILABLE)) {
			statusImage.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.status_online));
			mStatusText.setText("在线");
		}else {
			statusImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.status_invisible));
			mStatusText.setText("隐身");
		}
		mVoice.setChecked(SharedPreferenceUtil.getPrefBoolean(getActivity(), Constants.VOICE, true));
		mVibrator.setChecked(SharedPreferenceUtil.getPrefBoolean(getActivity(), Constants.VIBRATOR, true));
		mNotify.setChecked(SharedPreferenceUtil.getPrefBoolean(getActivity(), Constants.MESSAGE_NOTIFY, true));
		mSecurity.setChecked(SharedPreferenceUtil.getPrefBoolean(getActivity(), Constants.SECURITY, true));
		mAutoStart.setChecked(SharedPreferenceUtil.getPrefBoolean(getActivity(), Constants.AUTO_START, true));
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			mActionBar.setTitle("设置");
			mActionBar.hideRightView();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}

	@ViewListenerInject(value = {R.id.setting_msg_voice, R.id.setting_vibrator_switch, R.id.setting_message_notify,
			R.id.setting_my_security, R.id.setting_auto_start}, type = ViewListenerType.OnCheckedChange)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.setting_msg_voice:
			SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.VOICE, isChecked);
			break;
		case R.id.setting_vibrator_switch:
			SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.VIBRATOR, isChecked);
			break;
		case R.id.setting_message_notify:
			SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.MESSAGE_NOTIFY, isChecked);
			break;
		case R.id.setting_auto_start:
			SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.AUTO_START, isChecked);
			break;
		case R.id.setting_my_security:
			SharedPreferenceUtil.setPrefBoolean(getActivity(), Constants.SECURITY, isChecked);
			break;
		default:
			break;
		}
	}

	@ViewListenerInject(value = {R.id.setting_accountSetting_rela, R.id.setting_exit_app, 
			R.id.setting_about_rela}, type = ViewListenerType.OnClick)
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setting_accountSetting_rela:
			String statusMode = SharedPreferenceUtil.getPrefString(getActivity(),
					Constants.STATUS_MODE, Constants.AVAILABLE);
			SharedPreferenceUtil.setPrefString(getActivity(), Constants.STATUS_MODE, 
					statusMode.equals(Constants.AVAILABLE) ? Constants.UNAVAILABLE : Constants.AVAILABLE);
			statusMode = SharedPreferenceUtil.getPrefString(getActivity(),
					Constants.STATUS_MODE, Constants.AVAILABLE);
			if (statusMode.equals(Constants.AVAILABLE)) {
				statusImage.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.status_online));
				mStatusText.setText("在线");
			}else {
				statusImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.status_invisible));
				mStatusText.setText("隐身");
			}
			SmackerHelper.newInstance(getActivity()).setStatusFromConfig();
			break;
		case R.id.setting_exit_app:
			showDialog();
			SmackerHelper.newInstance(getActivity()).logout(Constants.LOGOUT_BYHAND);
			break;
		case R.id.setting_about_rela:
			final Dialog dialog = new Dialog(getActivity(), R.style.PopDialogStyle);
			View view = (View)View.inflate(getActivity(), R.layout.setting_about, null);
			dialog.setContentView(view);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			break;
		default:
			break;
		}
	}
	
	//显示提示
	private void showDialog() {
		if (mDialog == null) {
			mDialog = ProgressDialog.show(getActivity(), null, "正在注销...", false);
			mDialog.setCancelable(false);
		}
		mDialog.show();

	}

	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		mDialog.dismiss();
		if (objs[0].equals(Constants.IS_LOGOUT)) {
			//关闭后台服务
			Intent serviceIntent = new Intent(getActivity(), EmergencyService.class);
			getActivity().stopService(serviceIntent);
			//跳转到登录界面
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			getActivity().finish();
		}
	}
}
