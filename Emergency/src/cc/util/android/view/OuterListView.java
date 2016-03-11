package cc.util.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 保证不会与内部竖直可滑动空间冲突
 * @author wangcccong
 * @version 1.140122
 * create at: 2014-02-26
 * update at: 2014-03-13
 */
public class OuterListView extends ListView {
	
	private GestureDetector mGestureDetector;

	public OuterListView(Context context) {
		
		super(context);
		mGestureDetector = new GestureDetector(context, new SimpleScrollDetector());
		setFadingEdgeLength(0);
	}

	public OuterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new SimpleScrollDetector());
		setFadingEdgeLength(0);
	}

	public OuterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		super.onInterceptTouchEvent(ev);
		return mGestureDetector.onTouchEvent(ev);
	}

	class SimpleScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if (velocityY >= velocityX) {
				return true;
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}
}
