package com.example.theses.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.exam.ThreeSystem.R;
import com.example.PCenter.Constant;
import com.example.PCenter.fragments.BaseFragment;
import com.example.thesis.student.activity.Thesis_StudentHome_GuideContentActivity;
import com.example.thesis.student.activity.Thesis_StudentHome_MidleCheckActivity;
import com.example.thesis.student.activity.Thesis_StudentHome_OpenReportActivity;
import com.example.thesis.student.activity.Thesis_StudentHome_PersonGradeActivity;
import com.example.thesis.student.activity.Thesis_StudentHome_SubjectInfoSelect;
import com.example.thesis.student.activity.Thesis_StudentHome_TaskBookActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Thesis_StudentHome_Fragment extends BaseFragment implements OnClickListener{
	private LinearLayout personGrade, projectInfo, taskBook, openReport, guideContent, midleCheck;
	private SharedPreferences sp;
	private String FILE;
	private TextView mStudentName;

	//借鉴教师
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

	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	//借鉴教师

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mActivity = getActivity();
		FILE = Constant.USERINFO_SP;
		sp = getActivity().getSharedPreferences(FILE, Context.MODE_PRIVATE);
		View view = inflater.inflate(R.layout.thesis_activity_studenthome, container, false);
		initView(view);

		//借鉴教师
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
		//借鉴教师

		return view;
	}

	private void initView(View view) {
		personGrade = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_personGrade);
		projectInfo = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_projectInfo);
		taskBook = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_taskBook);
		openReport = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_openReport);
		guideContent = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_guideContent);
		midleCheck = (LinearLayout) view.findViewById(R.id.thesis_StudentHome_midleCheck);
		mStudentName = (TextView) view.findViewById(R.id.thesis_StudentHome_StudentName);
		mStudentName.setText(sp.getString(Constant.SPFIELD.REALNAME, ""));

		//图片
		v_dot0=view.findViewById(R.id.stu_v_dot0);
		v_dot1=view.findViewById(R.id.stu_v_dot1);
		v_dot2=view.findViewById(R.id.stu_v_dot2);
		v_dot3=view.findViewById(R.id.stu_v_dot3);
		v_dot4=view.findViewById(R.id.stu_v_dot4);
		tv_title=(TextView) view.findViewById(R.id.stu_tv_title);

		viewPager = (ViewPager) view.findViewById(R.id.stu_vp);


		setEvent();

	}

	private void setEvent() {
		personGrade.setOnClickListener(this);
		projectInfo.setOnClickListener(this);
		taskBook.setOnClickListener(this);
		openReport.setOnClickListener(this);
		guideContent.setOnClickListener(this);
		midleCheck.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_personGrade:
			startActivity(new Intent(mActivity, Thesis_StudentHome_PersonGradeActivity.class));
			break;
		case R.id.thesis_StudentHome_projectInfo:
			startActivity(new Intent(mActivity, Thesis_StudentHome_SubjectInfoSelect.class));

			break;
		case R.id.thesis_StudentHome_taskBook:
			startActivity(new Intent(mActivity, Thesis_StudentHome_TaskBookActivity.class));
			break;
		case R.id.thesis_StudentHome_openReport:
			startActivity(new Intent(mActivity, Thesis_StudentHome_OpenReportActivity.class));
			break;
		case R.id.thesis_StudentHome_guideContent:
			startActivity(new Intent(mActivity, Thesis_StudentHome_GuideContentActivity.class));
			break;
		case R.id.thesis_StudentHome_midleCheck:
			startActivity(new Intent(mActivity, Thesis_StudentHome_MidleCheckActivity.class));
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
