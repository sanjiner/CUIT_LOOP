package com.example.thesis;

import java.util.Timer;
import java.util.TimerTask;

import com.exam.ThreeSystem.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.theses.fragments.teacher.MyThisesFragment;
import com.example.theses.fragments.teacher.ProjectManagementFragment;
import com.example.theses.fragments.teacher.ThesisDefenseFragment;
import com.example.theses.fragments.teacher.TopicOfManagementFragment;

public class MainActivityThesis extends BaseActivity implements OnCheckedChangeListener{

	/* menu */
	private RadioGroup mMenuRadioGroup;
	private Fragment[] mFragments;
	public RadioButton rb_Thesis_defense_thesis;
	public RadioButton rb_Topics_of_management_thesis;
	public RadioButton rb_Project_management_thesis;
	public RadioButton rb_my_thesis;
	
	/* menu items的id */
	private final int[] mRadioIds = { 
			R.id.rb_Thesis_defense_thesis,
			R.id.rb_Topics_of_management_thesis,
			R.id.rb_Project_management_thesis,
			R.id.rb_my_thesis
			};
	/* fragment的管理类 */
	private FragmentManager fragmentManager;
	/* 所有要显示的fragment */
	private Fragment[] fragments;
	/* 当前显示的fragment的索引号，和下一个将要显示的fragment的索引号, 默认索引号 */
	private int currentMenuIndex, nextMenuIndex, initMenuIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_thesis_tea);
		init();
	}
	
	private void getViewObj()
	{
		mMenuRadioGroup = (RadioGroup) findViewById(R.id.rg_menu_thesis);
		rb_Thesis_defense_thesis=(RadioButton) findViewById(R.id.rb_Thesis_defense_thesis);
		rb_Topics_of_management_thesis=(RadioButton) findViewById(R.id.rb_Topics_of_management_thesis);
		rb_Project_management_thesis=(RadioButton) findViewById(R.id.rb_Project_management_thesis);
		rb_my_thesis=(RadioButton) findViewById(R.id.rb_my_thesis);
		
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
		fragments = new Fragment[]{
				new ThesisDefenseFragment(),
				new TopicOfManagementFragment(),
				new ProjectManagementFragment(),
				new MyThisesFragment()
				};
		
		/* fragment 添加默认页面 */
		FragmentTransaction trx = fragmentManager.beginTransaction();

		initMenuIndex = 0;
		trx.add(R.id.rl_fragment_container_thesis, fragments[initMenuIndex]);
		trx.show(fragments[initMenuIndex]).commit();

		/* 设置menu的第一个被选中 */
		mMenuRadioGroup.check(mRadioIds[initMenuIndex]);
		/* 当前页面 */
		currentMenuIndex = initMenuIndex;
	}
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.rb_Thesis_defense_thesis:
			nextMenuIndex = 0;
			break;
		case R.id.rb_Topics_of_management_thesis:
			nextMenuIndex = 1;
			break;
		case R.id.rb_Project_management_thesis:
			nextMenuIndex=2;
			break;
		case R.id.rb_my_thesis:
			nextMenuIndex=3;
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
			trx.hide(fragments[currentMenuIndex]);
			if (!fragments[nextMenuIndex].isAdded()) {
				trx.add(R.id.rl_fragment_container_thesis, fragments[nextMenuIndex]);
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
