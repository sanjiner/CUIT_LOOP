package com.example.process;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.common.tools.L;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;
import com.example.process.fragments.GradeFragmentProcess;
import com.example.process.fragments.HomeFragmentProcess;
import com.example.process.fragments.MyFragmentProcess;
import com.example.process.fragments.YuekaoFragmentProcess;

public class MainActivityProcess extends BaseActivity implements OnCheckedChangeListener{

	/* fragment容器 */
	//private RelativeLayout mContainer;
	/* menu */
	private RadioGroup mMenuRadioGroup;
	/* menu 中的各个radiobutton */
	//private RadioButton menuMe, menuHome;
	/* menu items的id */
	private final int[] mRadioIds = { R.id.rb_home_process,
			R.id.rb_grade_process,R.id.rb_yuekao_process,
			R.id.rb_my_process };
	/* fragment的管理类 */
	private FragmentManager fragmentManager;
	/* 所有要显示的fragment */
	private Fragment[] fragments;
	/* 当前显示的fragment的索引号，和下一个将要显示的fragment的索引号, 默认索引号 */
	private int currentMenuIndex, nextMenuIndex, initMenuIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitvity_main_process);
		init();
	}
	
	private void getViewObj()
	{
		mMenuRadioGroup = (RadioGroup) findViewById(R.id.rg_menu_process);
	}
	
	private void setListener()
	{
		mMenuRadioGroup.setOnCheckedChangeListener(this);
	}

	private void init()
	{
		getViewObj();
		setListener();
		initFragment();
	}
	
	/**
	 * 初始化fragment
	 */
	private void initFragment()
	{
		fragmentManager = getSupportFragmentManager();
		fragments = new Fragment[]{new HomeFragmentProcess(), 
				new GradeFragmentProcess(), new YuekaoFragmentProcess(),
				new MyFragmentProcess()};
		
		/* fragment 添加默认页面 */
		FragmentTransaction trx = fragmentManager.beginTransaction();

		initMenuIndex = 0;
		trx.add(R.id.rl_fragment_container_process, fragments[initMenuIndex]);
		trx.show(fragments[initMenuIndex]).commit();

		/* 设置menu的第一个被选中 */
		mMenuRadioGroup.check(mRadioIds[initMenuIndex]);
		/* 当前页面 */
		currentMenuIndex = initMenuIndex;
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.rb_home_process:
			nextMenuIndex = 0;
			break;
		case R.id.rb_grade_process:
			nextMenuIndex = 1;
			break;
		case R.id.rb_yuekao_process:
			nextMenuIndex = 2;
			break;
		case R.id.rb_my_process:
			nextMenuIndex = 3;
			break;
		default:
			nextMenuIndex = 0;
			break;
		}
		L.d(TAG, "currentMenuIndex=" + currentMenuIndex + " nextMenuIndex="
				+ nextMenuIndex);
		if (currentMenuIndex != nextMenuIndex) {

			FragmentTransaction trx = fragmentManager.beginTransaction();
			/* 隐藏当前的fragment */
			//trx.remove(fragments[currentMenuIndex]);
			trx.hide(fragments[currentMenuIndex]);
			//trx.replace(R.id.rl_fragment_container_process, fragments[currentMenuIndex]);
			if (!fragments[nextMenuIndex].isAdded()) {
				trx.add(R.id.rl_fragment_container_process, fragments[nextMenuIndex]);
			}

			trx.show(fragments[nextMenuIndex]).commit();

			currentMenuIndex = nextMenuIndex;
		}	
	}

	//点击退出弹出确认提示框
	private static Boolean isQuit = false;
	Timer timer = new Timer();
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            if (isQuit == false) {
	                isQuit = true;
	                Toast.makeText(getBaseContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
	                TimerTask task = null;
	                task = new TimerTask() {
	                    @Override
	                    public void run() {
	                        isQuit = false;
	                    }
	                };
	                timer.schedule(task, 2000);
	            } else {
	                finish();
	                System.exit(0);
	            }
	        }
	        return false;
	}
}
