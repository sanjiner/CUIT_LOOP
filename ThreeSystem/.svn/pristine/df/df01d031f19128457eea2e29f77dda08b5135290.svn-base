package com.example.process.fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.exam.ThreeSystem.R;
import com.example.PCenter.fragments.BaseFragment;
import com.trinea.connect.AdManager;
import com.trinea.connect.br.AdSize;
import com.trinea.connect.br.AdView;
import com.trinea.connect.br.AdViewListener;

public class YuekaoFragmentProcess extends BaseFragment implements OnClickListener{

//	private RelativeLayout tadayExam,historyExam;
	private ArrayList<Fragment> mFragmentlist ;
	private ViewPager mViewpager;
	private MyFragmentPagerAdapter mAdapter;
	public TextView tab_01;
	public TextView tab_02;
	private ImageView cusor;
	
	protected int bmpW;
	protected int offset;
	protected int distance;
	protected int one;
	protected int zero;//页卡两个
	protected Animation animation;
	int start;
	int end = 0;
	int two;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_process_yuekao, container, false);
		mActivity = getActivity();
		AdManager.getInstance(mActivity).init("4927c476dbf90a2a", "4927c476dbf90a2a", true);
		init(view);
		
		mFragmentlist = new ArrayList<Fragment>();
		YuekaoDetailFragmentProcess yuekaodetailfragmentprocess = new YuekaoDetailFragmentProcess();
		YuekaoHistoryFragmentProcess yuekaohistoryfragmentprocess = new YuekaoHistoryFragmentProcess();
		mFragmentlist.add(yuekaodetailfragmentprocess);
		mFragmentlist.add(yuekaohistoryfragmentprocess);
		
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		bmpW = 68 * 4;
		offset = (screenW / 2 - bmpW) /2;
		distance = screenW /2;
		zero = offset + bmpW / 4;
		one = zero + distance;
		
		mAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentlist);
		mViewpager.setAdapter(mAdapter);
		mViewpager.setOnPageChangeListener(new YouOnPageChangeListener());
		mViewpager.setCurrentItem(0);
		
		start = zero;
		animation = new TranslateAnimation(0, start, 0 , 0);
		animation.setFillAfter(true);
		animation.setDuration(0);
		cusor.startAnimation(animation);
		return view;
	}
	
	private void init(View view)
	{
		Youmi(view);
		getViewObj(view);
	}

	private void Youmi(View view)
	{
		// 实例化广告条
		AdView adView = new AdView(mActivity, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.process_yuekao_adLayout);
		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		adView.setAdListener(new AdViewListener() {
		    @Override
		    public void onSwitchedAd(AdView adView) {
		        //Toast.makeText(mActivity, "切换广告并展示", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onReceivedAd(AdView adView) {
		        // 请求广告成功
		    	//Toast.makeText(mActivity, "请求广告成功", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // 请求广告失败
		    	//Toast.makeText(mActivity, "请求广告失败", Toast.LENGTH_SHORT).show();
		    }
		});
	}
	
	private void getViewObj(View view)
	{
//		tadayExam = (RelativeLayout) view.findViewById(R.id.process_today_exam);
//		historyExam = (RelativeLayout) view.findViewById(R.id.process_history_exam);
		mViewpager = (ViewPager) view.findViewById(R.id.yuekao_viewpager);
		tab_01 = (TextView) view.findViewById(R.id.yuekao_tab01);
		tab_02 = (TextView) view.findViewById(R.id.yuekao_tab02);
		cusor = (ImageView) view.findViewById(R.id.yuekao_cusor);
		setListener();
	}
	

	private void setListener()
	{
//		tadayExam.setOnClickListener(this);
//		historyExam.setOnClickListener(this);
		tab_01.setOnClickListener(this);
		tab_02.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
//		case R.id.process_today_exam:
//			intent = new Intent(mActivity, ProcessAppointExamActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.process_history_exam:
//			intent = new Intent(mActivity, ProcessExamRecordActivity.class);
//			startActivity(intent);
//			break;
		case R.id.yuekao_tab01:
			mViewpager.setCurrentItem(0);;
			break;
		case R.id.yuekao_tab02:
			mViewpager.setCurrentItem(1);
			break;
		}
	}
	
	public class YouOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if(arg0 == 0){
				end = zero;
			}else if(arg0 == 1){
				end = one;
			}else if(arg0 == 2){
				end = two;
			}
			end = end + (int)((offset * 2 + bmpW) * arg1);
			
			animation = new TranslateAnimation(start, end, 0 , 0);
			animation.setFillAfter(true);
			animation.setDuration(0);//
			cusor.startAnimation(animation);
			start = end;
		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				tab_01.setTextColor(Color.rgb(0,0,0));
				tab_02.setTextColor(Color.rgb(201, 201, 201));
				break;
			case 1:
				tab_01.setTextColor(Color.rgb(201, 201, 201));
				tab_02.setTextColor(Color.rgb(0,0,0));
			default:
				break;
			}
		}
		
	}
	
	class MyFragmentPagerAdapter extends FragmentPagerAdapter
	{
		private ArrayList<Fragment> fragmentlist;
		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentlist) {
			super(fm);
			this.fragmentlist = fragmentlist;
		}
		@Override
		public Fragment getItem(int position) {
			return fragmentlist.get(position);
		}

		@Override
		public int getCount() {
			return fragmentlist.size();
		}
		
		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}
	}
}


