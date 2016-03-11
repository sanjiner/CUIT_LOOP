package cuit.emergency;

import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import cc.util.android.core.AbsActivity;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.core.ToastUtil;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

public class LoginActivity extends AbsActivity implements Observer {

	public final static String LOGIN_SUCCESS = "登录成功";
	public static String mServer;
	
	@ResInject(R.id.login_edt_account)
	EditText medtAccount;
	@ResInject(R.id.login_edt_passwd)
	EditText medtPasswd;
	@ResInject(R.id.login_edt_server)
	EditText medtServer;
	@ResInject(R.id.login_hide_check)
	CheckBox checkHide;
	@ResInject(R.id.login_savepasswd_check)
	CheckBox checkSave;
	
	ProgressDialog mDialog;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		ViewInjectUtil.inject(this);
		ConcreteObservable.newInstance().registerObserver(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		medtAccount.setText(SharedPreferenceUtil.getPrefString(this, Constants.UNAME, ""));
		medtPasswd.setText(SharedPreferenceUtil.getPrefBoolean(this, Constants.SAVE_PASSWORD, true) ?
				SharedPreferenceUtil.getPrefString(this, Constants.UPASSWD, "") : "");
		medtServer.setText(SharedPreferenceUtil.getPrefBoolean(this, Constants.SAVE_SERVER, true)?
				SharedPreferenceUtil.getPrefString(this, Constants.USERVER, "") : "");
		checkSave.setChecked(SharedPreferenceUtil.getPrefBoolean(this, Constants.SAVE_PASSWORD, true));
		checkHide.setChecked(SharedPreferenceUtil.getPrefString(this, Constants.STATUS_MODE, Constants.AVAILABLE)
				.equals(Constants.AVAILABLE) ? false : true);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}
	
	public void onClick(View view) {
		if (medtAccount.getText().toString().trim().equals("") || medtPasswd.getText().toString().equals("") || medtServer.getText().toString().equals("")) {
			ToastUtil.showShort(this, "请填写完整");
			return;
		}else if(!medtServer.getText().toString().contains("@")){
			ToastUtil.showShort(this, "请按照规范格式填写服务器地址：服务器IP@服务器名");
			return;
		}
		showDialog();
		mServer = medtServer.getText().toString();
		SmackerHelper.newInstance(getApplicationContext()).
			login(medtAccount.getText().toString().trim(), medtPasswd.getText().toString(),mServer);
	}
	
	@ViewListenerInject(value = {R.id.login_savepasswd_check, R.id.login_hide_check}, 
			type = ViewListenerType.OnCheckedChange)
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.login_hide_check:
			SharedPreferenceUtil.setPrefString(getApplicationContext(), Constants.STATUS_MODE, isChecked ? Constants.XA : Constants.AVAILABLE);
			break;
		default:
			SharedPreferenceUtil.setPrefBoolean(this, Constants.SAVE_PASSWORD, isChecked);
			break;
		}
	}

	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		mDialog.dismiss();
		if (objs[0].equals("登录成功")) {
			if (checkSave.isChecked()) {
				SharedPreferenceUtil.setPrefBoolean(this, Constants.SAVE_PASSWORD, true);
				SharedPreferenceUtil.setPrefString(getApplicationContext(), Constants.UNAME, objs[1]+"");
			}
			SharedPreferenceUtil.setPrefString(getApplicationContext(), Constants.UPASSWD, objs[2]+"");
			SharedPreferenceUtil.setPrefString(getApplicationContext(), Constants.USERVER, medtServer.getText().toString());
			SharedPreferenceUtil.setPrefBoolean(getApplicationContext(), Constants.IS_LOGOUT, false);
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();	
		} else {
			ToastUtil.showShort(this, objs[0]+"");
		}
	}
	
	//显示提示
	private void showDialog() {
		if (mDialog == null) {
			mDialog = ProgressDialog.show(this, null, "正在登录...", false);
			mDialog.setCancelable(false);
		}
		mDialog.show();

	}
}
