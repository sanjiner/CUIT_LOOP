package com.example.PCenter.Record;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

public class Record_StudentDetail_ListView extends ListView{
	private boolean isShown;

	private View mPreItemView;

	private View mCurrentItemView;

	private float mFirstX;

	private float mFirstY;

	private boolean mIsHorizontal;
	
	private boolean two = false;
	
	
	public Record_StudentDetail_ListView(Context context) {
		super(context);
	}
	public Record_StudentDetail_ListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public Record_StudentDetail_ListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float lastX = ev.getX();
		float lastY = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			mIsHorizontal = false;
			
			mFirstX = lastX;
			mFirstY = lastY;
			int motionPosition = pointToPosition((int) mFirstX, (int) mFirstY);

			
			if (motionPosition >= 0) {
				View currentItemView = getChildAt(motionPosition - getFirstVisiblePosition());
				mPreItemView = mCurrentItemView;
				mCurrentItemView = currentItemView;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			float dx = lastX - mFirstX;
			float dy = lastY - mFirstY;

			if (Math.abs(dx) >= 5 && Math.abs(dy) >= 5) {
				return true;
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			if (isShown && mPreItemView != mCurrentItemView) {
				/**
                 * 情况一：
                 * <p>
                 * 一个Item的右边布局已经显示，
                 * <p>
                 * 这时候点击任意一个item, 那么那个右边布局显示的item隐藏其右边布局
                 */
				hiddenRight(mPreItemView);
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		float lastX = ev.getX();
		float lastY = ev.getY();

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:
			float dx = lastX - mFirstX;
			float dy = lastY - mFirstY;

			mIsHorizontal = isHorizontalDirectionScroll(dx, dy);
			
			if (!mIsHorizontal) {
				break;
			}

			
			if (mIsHorizontal) {
                if (isShown && mPreItemView != mCurrentItemView) {
                    /**
                     * 情况二：
                     * <p>
                     * 一个Item的右边布局已经显示，
                     * <p>
                     * 这时候左右滑动另外一个item,那个右边布局显示的item隐藏其右边布局
                     * <p>
                     * 向左滑动只触发该情况，向右滑动还会触发情况五
                     */
                    hiddenRight(mPreItemView);
                }
			}else {
                if (isShown) {
                    /**
                     * 情况三：
                     * <p>
                     * 一个Item的右边布局已经显示，
                     * <p>
                     * 这时候上下滚动ListView,那么那个右边布局显示的item隐藏其右边布局
                     */
                    hiddenRight(mPreItemView);
                }
            }
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (isShown) {
                /**
                 * 情况四：
                 * <p>
                 * 一个Item的右边布局已经显示，
                 * <p>
                 * 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
                 */
                hiddenRight(mPreItemView);
            }

            if (mIsHorizontal) {
                if (mFirstX - lastX > 30) {
                    showRight(mCurrentItemView);
                } else {
                    /**
                     * 情况五：
                     * <p>
                     * 向右滑动一个item,且滑动的距离超过了右边View的宽度的一半，隐藏之。
                     */
                    hiddenRight(mCurrentItemView);
                }
                return true;
            }
			break;
		}

		return super.onTouchEvent(ev);
	}
	private void hiddenRight(View rightView) {
		
		final Button rl_right=(Button)rightView.findViewById(R.id.record_detail_del);
		
		Animation animation=AnimationUtils.loadAnimation(getContext(), R.anim.delete_button_out);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationStart(Animation animation) {
				rl_right.setVisibility(View.INVISIBLE);
				isShown = false;
				two = false;
			}
		});
		if(two&&rl_right.getVisibility()==View.VISIBLE){
		rl_right.startAnimation(animation);
		}
		rl_right.setVisibility(View.INVISIBLE);
		isShown = false;
	}
	private void showRight(View rightView) {
		final Button rl_right=(Button)rightView.findViewById(R.id.record_detail_del);
		Animation animation=AnimationUtils.loadAnimation(getContext(), R.anim.delete_button_in);
//		rl_right.setVisibility(View.VISIBLE);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				rl_right.setVisibility(View.VISIBLE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				rl_right.setVisibility(View.VISIBLE);
				isShown = true;
				two = true;
			}
		});
		if(!two&&rl_right.getVisibility()==View.INVISIBLE){
			rightView.startAnimation(animation);
		}
		rl_right.setVisibility(View.VISIBLE);
		isShown = true;
	}
	
	private boolean isHorizontalDirectionScroll(float dx, float dy) {
		boolean mIsHorizontal = true;

		if (Math.abs(dx) > 30 && Math.abs(dx) > 2 * Math.abs(dy)) {
			mIsHorizontal = true;
		} else if (Math.abs(dy) > 30 && Math.abs(dy) > 2 * Math.abs(dx)) {
			mIsHorizontal = false;
		}
		return mIsHorizontal;
	}
}
