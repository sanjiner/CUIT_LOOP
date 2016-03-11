package com.example.PCenter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

import com.exam.ThreeSystem.R;

/** What's new 的导航界面 */
public class WhatsnewPagesA extends Activity {
	/** Viewpager对象 */
	private ViewPager viewPager;
	private ImageView imageView;
	/** 创建一个数组，用来存放每个页面要显示的View */
	private ArrayList<View> pageViews;
	/** 创建一个imageview类型的数组，用来表示导航小圆点 */
	private ImageView[] imageViews;
	/** 装显示图片的viewgroup */
	private ViewGroup viewPictures;
	/** 导航小圆点的viewgroup */
	private ViewGroup viewPoints;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		
		LayoutInflater inflater = getLayoutInflater();
		pageViews = new ArrayList<View>();
		pageViews.add(inflater.inflate(R.layout.viewpager01, null));
		pageViews.add(inflater.inflate(R.layout.viewpager02, null));
		pageViews.add(inflater.inflate(R.layout.viewpager03, null));
		pageViews.add(inflater.inflate(R.layout.viewpager04, null));
		pageViews.add(inflater.inflate(R.layout.viewpager05, null));
		pageViews.add(inflater.inflate(R.layout.viewpager06, null));
		pageViews.add(inflater.inflate(R.layout.viewpager10, null));
		pageViews.add(inflater.inflate(R.layout.viewpager07, null));
		pageViews.add(inflater.inflate(R.layout.viewpager08, null));
		pageViews.add(inflater.inflate(R.layout.viewpager09, null));
		pageViews.add(inflater.inflate(R.layout.viewpager11, null));
	

		// 小圆点数组，大小是图片的个数
		imageViews = new ImageView[pageViews.size()];
		// 从指定的XML文件中加载视图
		viewPictures = (ViewGroup) inflater.inflate(R.layout.viewpagers, null);

		viewPager = (ViewPager) viewPictures.findViewById(R.id.guidePagers);
		viewPoints = (ViewGroup) viewPictures.findViewById(R.id.viewPoints);

		// 添加小圆点导航的图片
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(WhatsnewPagesA.this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 0, 5, 0);
			// 吧小圆点放进数组中
			imageViews[i] = imageView;
			// 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
			if (i == 0)
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
			else
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_unfocused));
			// 将imageviews添加到小圆点视图组
			viewPoints.addView(imageViews[i]);
		}

		setContentView(viewPictures);

		viewPager.setAdapter(new NavigationPageAdapter());
		// 为viewpager添加监听，当view发生变化时的响应
		viewPager.setOnPageChangeListener(new NavigationPageChangeListener());
	}

	// 导航图片view的适配器，必须要实现的是下面四个方法
	class NavigationPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// 初始化每个Item
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(pageViews.get(position));
			return pageViews.get(position);
		}

		// 销毁每个Item
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(pageViews.get(position));
		}

	}

	// viewpager的监听器，主要是onPageSelected要实现
	class NavigationPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// 循环主要是控制导航中每个小圆点的状态
			for (int i = 0; i < imageViews.length; i++) {
				// 当前view下设置小圆点为选中状态
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
				// 其余设置为飞选中状态
				if (position != i)
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_indicator_unfocused));
			}
		}

	}


	public void startbutton(View v) {
		String istomore = sp.getString(Constant.SPFIELD.ISTOMORE, "");
		if(istomore.equals("yes"))
		{
			Intent intentToMore = new Intent(WhatsnewPagesA.this, MainActivity.class);
			intentToMore.putExtra("againtomore", "yes");
			intentToMore.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentToMore);
			WhatsnewPagesA.this.finish();
		}
		else{
			Intent intentToLogin = new Intent(WhatsnewPagesA.this, Login.class);
			startActivity(intentToLogin);
			WhatsnewPagesA.this.finish();
		}
	}

}
