package cc.util.android.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 所有Fragment的抽象类， 支持SDK2.2以上
 * @author wangcccong
 * @version 1.1406
 * <br>create at: Tue, 26 Aug. 2014
 * <br>update at: Thur, 17 Sep. 2014
 * <br>新增Fragment初始化和被其他fragment遮挡时onHiddenChanged方法的调用
 */
public abstract class AbsFragment extends Fragment {
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		LogUtil.d(getClass().getSimpleName(), "-----onAttach");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtil.d(getClass().getSimpleName(), "-----onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d(getClass().getSimpleName(), "-----onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogUtil.d(getClass().getSimpleName(), "-----onViewCreated");
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		LogUtil.d(getClass().getSimpleName(), "-----onViewStateRestored");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(getClass().getSimpleName(), "-----onActivityCreated");
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.d(getClass().getSimpleName(), "-----onStart");
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.d(getClass().getSimpleName(), "-----onResume");
		onHiddenChanged(false);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.d(getClass().getSimpleName(), "-----onPause");
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		onHiddenChanged(true);
		LogUtil.d(getClass().getSimpleName(), "-----onStop");
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		LogUtil.d(getClass().getSimpleName(), "-----onDestoryView");
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.d(getClass().getSimpleName(), "-----onDestory");
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		LogUtil.d(getClass().getSimpleName(), "-----onDetach");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		LogUtil.d(getClass().getSimpleName(), "-----onSaveInstanceState");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		LogUtil.d(getClass().getSimpleName(), "-----onHiddenChanged-----" + (hidden ? "true" : "false"));
	}

	/**
	 * 如果需要响应按键请重写此方法 (返回false表示不会响应按键操作，true需要响应按键操作)
	 * @param keyCode
	 * @return
	 */
	protected boolean onKeyDown(int keyCode) {
		return false;
	}
}
