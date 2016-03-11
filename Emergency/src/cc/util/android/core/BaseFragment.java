package cc.util.android.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import cc.util.android.view.ActionBarCompat;

/**
 * Fragment基类,处理获取Actionbar、软键盘弹出等
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-04-14
 * <br>update at: Mon, 14, Sep. 2014
 */
public class BaseFragment extends AbsFragment {

	protected InputMethodManager imm;
	protected ActionBarCompat mActionBar;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		AppManager.newInstance().addFragment((FragmentActivity)activity, this);
		if (activity instanceof BaseActivity) {
			mActionBar = ((BaseActivity)getActivity()).getActionBarCompat();
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		hideSoftInputIfNeed();
	}
	
	protected void hideSoftInputIfNeed() {
		if (imm.isActive()) {
			IBinder iBinder = getActivity().getCurrentFocus() == null ? null : getActivity().getCurrentFocus().getWindowToken();
			if (iBinder != null)
				imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		AppManager.newInstance().finishFragment(getActivity(), this);
	}
	
}
