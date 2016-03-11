package cc.util.android.core;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * Activity 与 Fragment的封装类，将其加入栈中，方便获取Activity与Fragment和彻底退出程序
 * @author wangcccong
 * @version 1.1406
 * <br>create at: Tue, 26 Aug. 2014
 */
public class AppManager {
	private  static AppManager util = null;
	private Stack<FragmentActivity>  activityStack;
	//此对象用来保存隶属于某个Activity的Fragment，方便获取到该Activit中最后加入栈中的fragment
	private ConcurrentHashMap<FragmentActivity, Stack<AbsFragment>> fragmentMap = new ConcurrentHashMap<FragmentActivity, Stack<AbsFragment>>();;

	private AppManager() { }
	
	// 单例模式
	public synchronized static AppManager newInstance() {
		if (util == null) {
			util = new AppManager();
		}
		return util;
	}
	
	/**
	 * 新增Fragment，此处必须传入Fragment所在的Activity
	 * @param activity
	 * @param fragment
	 */
	public void addFragment(FragmentActivity activity, BaseFragment fragment) {
//		if (fragmentMap == null) fragmentMap = new ConcurrentHashMap<FragmentActivity, Stack<BaseFragment>>();
		Stack<AbsFragment> stack = fragmentMap.get(activity);
		if (stack == null) {
			stack = new Stack<AbsFragment>();
			fragmentMap.put(activity, stack);
		}
		stack.push(fragment);
	}
	
	/**
	 * 获取最后加入指定activity中的Fragment
	 * @param activity
	 * @return
	 */
	public AbsFragment lastFragment(FragmentActivity activity) {
		Stack<AbsFragment> stack = fragmentMap.get(activity);
		return stack == null ? null : stack.lastElement();
	}
	
	/**
	 * 移除指定Fragment
	 * @param activity
	 * @param fragment
	 */
	public void finishFragment(FragmentActivity activity, BaseFragment fragment) {
		Stack<AbsFragment> stack = fragmentMap.get(activity);
		if (stack != null) stack.removeElement(fragment);
	}
	
	/**
	 * 移除所有Fragment
	 * @param activity
	 */
	public void finishAllFragment(FragmentActivity activity) {
		fragmentMap.remove(activity);
	}
	
	/**
	 *  添加activity
	 */
	public void addActivity(FragmentActivity activity){
		if(activityStack == null){
			activityStack=new Stack<FragmentActivity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 获取到当前activit栈的大小
	 * @return
	 */
	public int getActivityCount() {
		return activityStack.size();
	}
	
	/**
	 * 获取栈顶的activity
	 */
	public Activity lastActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}
	
	public Activity firstActivity() {
		Activity activity = activityStack.firstElement();
		return activity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		FragmentActivity activity = activityStack.lastElement();
		finishActivity(activity);
	}
	
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(FragmentActivity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (FragmentActivity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	if(activityStack.get(i).getSupportFragmentManager().getFragments() != null)
            		activityStack.get(i).getSupportFragmentManager().getFragments().clear();
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}
	
}
