package cc.util.android.core;

import cuit.emergency.R;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import cc.util.android.view.ActionBarCompat;

/**
 * Activity基类，处理ActionBar、返回键等操作
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 * <br>update at: Mon, 14, Sep. 2014
 */
public class BaseActivity extends AbsActivity {
	
	protected ActionBarCompat mActionBar;

	//此方法必须在调用了setContentView(R.id...)之后才能调用
	public void initActionBarCompat() {
		if (mActionBar == null) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
			//初始化自定义的actionbar
			mActionBar = (ActionBarCompat) findViewById(R.id.action_bar);
//			mActionBar.setBackgroundResource(R.drawable.home);
//			mActionBar.setLeftViewResourceId(R.drawable.back_btn);
			//如果左方只是返回键，则在其他地方不需要管点击后做什么，也可以根据需要重新设置该监听，这里设置的功能是
			//如果Activity中含有多个Fragment则Pop操作，否则finish Activity
			mActionBar.setLeftViewAction(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!(BaseActivity.this instanceof FragmentInfoActivity))
						return;
					AbsFragment fragment = AppManager.newInstance().lastFragment(BaseActivity.this);
					if (fragment != null && fragment.onKeyDown(KeyEvent.KEYCODE_BACK)) return;
					if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
						getSupportFragmentManager().popBackStack();
						AbsFragment fragment2 = AppManager.newInstance().lastFragment(BaseActivity.this);
						getSupportFragmentManager().beginTransaction().show(fragment2).commit();
					} else {
						finish();
					}
				}
			});
			
		}
	}
	
	//在fragment中需要用到此方法
	public ActionBarCompat getActionBarCompat() {
		return mActionBar;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(arg0);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AbsFragment fragment = AppManager.newInstance().lastFragment(this);
		if (keyCode != KeyEvent.KEYCODE_BACK && fragment != null
				&& fragment.onKeyDown(keyCode)) return true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (fragment != null && fragment.onKeyDown(keyCode)) return true;
			if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
				getSupportFragmentManager().popBackStack();
				AbsFragment fragment2 = AppManager.newInstance().lastFragment(this);
				getSupportFragmentManager().beginTransaction().show(fragment2).commit();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
