package cc.util.android.core;

import cuit.emergency.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment 之间切换
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 * <br>update at: Mon, 14, Sep. 2014
 */
public class FragmentInfoActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.fragment_info_activity);
		initActionBarCompat();
		FragmentInfo info = getIntent().getParcelableExtra(FragmentInfo.INFO);
		Fragment fragment = Fragment.instantiate(getApplicationContext(),
				info.getClazz().getName(), info.getBundle()); 
		getSupportFragmentManager().beginTransaction()
			.add(R.id.content, fragment, info.getClazz().getName()).commit();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 在Fragment中跳转
	 * @param currentFragment
	 * @param info
	 */
	public static void startFragment(BaseFragment currentFragment, FragmentInfo info) {
		if (currentFragment.getActivity() instanceof FragmentInfoActivity) {
			Fragment fragment = Fragment.instantiate(currentFragment.getActivity(), info.getClazz().getName(), info.getBundle());
			currentFragment.getFragmentManager().beginTransaction().hide(currentFragment)
					.add(R.id.content, fragment, info.getClazz().getName())
					.addToBackStack(null).commit();
		} else {
			Intent intent = new Intent(currentFragment.getActivity(), FragmentInfoActivity.class);
			intent.putExtra(FragmentInfo.INFO, info);
			currentFragment.getActivity().startActivity(intent);
		}
	}
	
	/**
	 * 在Activity中的跳转
	 * @param context
	 * @param info
	 */
	public static void startActivity(Context context, FragmentInfo info) {
		Intent intent = new Intent(context, FragmentInfoActivity.class);
		intent.putExtra(FragmentInfo.INFO, info);
		context.startActivity(intent);
	}
	
}
