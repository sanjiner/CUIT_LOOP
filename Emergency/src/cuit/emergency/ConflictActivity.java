package cuit.emergency;

import cuit.emergency.app.EmergencyService;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import cuit.emergency.util.ToolUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import cc.util.android.core.AbsActivity;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

public class ConflictActivity extends AbsActivity implements Observer {
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ConcreteObservable.newInstance().registerObserver(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conflict);
		ViewInjectUtil.inject(this);
	}
	
	@ViewListenerInject(value = {R.id.conflict_logout, R.id.conflict_re_login}, type = ViewListenerType.OnClick)
	void onClick(View v) {
		switch (v.getId()) {
		case R.id.conflict_logout:
			Intent serviceIntent = new Intent(this, EmergencyService.class);
			stopService(serviceIntent);
			Intent intent = new Intent(this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		default:
			ToolUtil.showDialog(this, "正在重连...");
			SmackerHelper.newInstance(this).reconnect(Constants.RECONNECT_BYHAND);
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) return true;
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void update(Object... arg0) {
		// TODO Auto-generated method stub
		if (arg0[0].equals(Constants.IS_RECONNECT)) {
			ToolUtil.dismissDialog();
			finish();
		}
	}

}
