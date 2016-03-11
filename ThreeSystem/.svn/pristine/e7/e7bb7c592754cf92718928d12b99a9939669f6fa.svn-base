package com.example.theses.fragments.teacher;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.view.View.OnClickListener;

import com.exam.ThreeSystem.R;
import com.example.PCenter.fragments.BaseFragment;
import com.example.thesis.TeacherThesisDefenseRevscoreActivity_studentlist;
import com.example.thesis.TeacherThesisDefenseTeamscoreActivity_studentlist;
import com.example.thesis.ThesisDefense_studentlist;
public class ThesisDefenseFragment extends BaseFragment implements OnClickListener{
	
	public RelativeLayout 
			thesisdefense_guidescore,
			thesisdefense_revscore,
			thesisdefense_teamscore;
	private Activity mActivity;
	
	//图片显示
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	public View v_dot0;
	public View v_dot1;
	public View v_dot2;
	public View v_dot3;
	public View v_dot4;
	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_thesis_home, container, false);
		mActivity = getActivity();
		init(view);
		
		
		//图片
		imageResId = new int[]{
				R.drawable.a,R.drawable.ic_home_slide1,R.drawable.ic_home_slide2,R.drawable.ic_home_slide3,R.drawable.e};
		titles = new String[imageResId.length];
		titles[0] = "广告待招";
		titles[1] = "成信之美";
		titles[2] = "成信之美";
		titles[3] = "成信之美";
		titles[4] = "广告待招";

		imageViews = new ArrayList<ImageView>();
		// 初始化图片资源
				for (int i = 0; i < imageResId.length; i++)
				{
					ImageView imageView=new ImageView(mActivity);
					imageView.setImageResource(imageResId[i]);
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageViews.add(imageView);
				}
				dots = new ArrayList<View>();
				dots.add(v_dot0);
				dots.add(v_dot1);
				dots.add(v_dot2);
				dots.add(v_dot3);
				dots.add(v_dot4);
				
				tv_title.setText(titles[0]);//
				viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
				// 设置一个监听器，当ViewPager中的页面改变时调用
				viewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		
		return view;
	}
	public void init(View view)
	{
		getViewObj(view);
		setListener();
	}
	public void getViewObj(View view )
	{
		thesisdefense_guidescore=(RelativeLayout) view.findViewById(R.id.thesisdefense_guidescore);
		thesisdefense_revscore=(RelativeLayout) view.findViewById(R.id.thesisdefense_revscore);
		thesisdefense_teamscore=(RelativeLayout) view.findViewById(R.id.thesisdefense_teamscore);
		
		//图片
		v_dot0=view.findViewById(R.id.v_dot0);
		v_dot1=view.findViewById(R.id.v_dot1);
		v_dot2=view.findViewById(R.id.v_dot2);
		v_dot3=view.findViewById(R.id.v_dot3);
		v_dot4=view.findViewById(R.id.v_dot4);
		tv_title=(TextView) view.findViewById(R.id.tv_title);
		
		viewPager = (ViewPager) view.findViewById(R.id.vp);
	}
	public void setListener()
	{
		thesisdefense_guidescore.setOnClickListener(this);
		thesisdefense_revscore.setOnClickListener(this);
		thesisdefense_teamscore.setOnClickListener(this);
		
		
		
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent=null;
		switch (view.getId()) {
		case R.id.thesisdefense_guidescore:
			intent=new Intent(getActivity(),ThesisDefense_studentlist.class);
			startActivity(intent);
			break;
		case R.id.thesisdefense_revscore:
			intent=new Intent(getActivity(),TeacherThesisDefenseRevscoreActivity_studentlist.class);
			startActivity(intent);
			break;
		case R.id.thesisdefense_teamscore:
			intent=new Intent(getActivity(),TeacherThesisDefenseTeamscoreActivity_studentlist.class);
			startActivity(intent);
			break;
			
		default:
			
			break;
		}
		
	}

//图片
	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
	
	
	
	
}
