package cc.util.android.core;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 所有activity的抽象类， 支持SDK2.2以上
 * @author wangcccong
 * @version 1.1406
 * <br>create at: Tue, 26 Aug. 2014
 */
public abstract class AbsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		LogUtil.d(getClass().getSimpleName(), "-----onCreate");
		AppManager.newInstance().addActivity(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		LogUtil.d(getClass().getSimpleName(), "-----onStart");
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		LogUtil.d(getClass().getSimpleName(), "-----onRestart");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.d(getClass().getSimpleName(), "-----onResume");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.d(getClass().getSimpleName(), "-----onPause");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtil.d(getClass().getSimpleName(), "-----onStop");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.d(getClass().getSimpleName(), "-----onDestory");
		AppManager.newInstance().finishActivity(this);
	}
}
