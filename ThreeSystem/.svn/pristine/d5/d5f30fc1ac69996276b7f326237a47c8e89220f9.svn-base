package com.example.PCenter.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private ViewGroup mMenu;
	private ViewGroup mContent;
	//dp
	private int mMenuLeftPadding = 50;
	//屏幕宽度
	private int mScreenWidth;
	// 菜单的宽度
	private int mMenuWidth;
	private int mHalfMenuWidth;
	
	private boolean once=false;
	private boolean isOpen;
	
	
	public SlidingMenu(Context context, AttributeSet attrs){
		super(context, attrs);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);		
		mScreenWidth = outMetrics.widthPixels;
		
		// 默认100，把dp转化为px
		mMenuLeftPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 50,
				getResources().getDisplayMetrics());
	}

	
	//决定子View的宽和高，以及自己的宽和高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		if(!once){
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mContent = (ViewGroup) wrapper.getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(1);			
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuLeftPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
				
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	//决定子View的放置的位置,将Menu隐藏
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {						
		super.onLayout(changed, l, t, r, b);
		
		if(changed){
			// 将菜单隐藏
			//this.scrollTo(mMenuWidth, 0);
			once = true;
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) 
	{
		int action = ev.getAction();
		switch (action)
		{
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX >= mHalfMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}		
		
		return super.onTouchEvent(ev);
	}
	
	
	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
//		if (isOpen){
//			this.smoothScrollTo(mMenuWidth, 0);
//			isOpen = false;
//		}
		if (!isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle()
	{
		if (isOpen){
			closeMenu();
		} else{
			openMenu();
		}
	}
}
