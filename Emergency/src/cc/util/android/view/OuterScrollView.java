package cc.util.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 保证不会与内部竖直可滑动空间冲突
 * @author wangcccong
 * @version 1.140122
 * create at: 2014-02-26
 * update at: 2014-03-13
 */
public class OuterScrollView extends ScrollView {

    /** The m gesture detector. */
    private GestureDetector mGestureDetector;
    
    /**
     * Instantiates a new ab pager scroll view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public OuterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new SimpleScrollDetector());
        setFadingEdgeLength(0);
    }

    /**
     *
     * @param ev the ev
     * @return true, if successful
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	super.onInterceptTouchEvent(ev);
        return mGestureDetector.onTouchEvent(ev);
    }
    
    @Override
    public void requestChildFocus(View child, View focused) {
    	if (focused instanceof OuterScrollView) 
    		super.requestChildFocus(child, focused);	
    	else
    		return;
    }
    
    /**
     * The Class SimpleScrollDetector.
     */
    class SimpleScrollDetector extends SimpleOnGestureListener {
        
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//        		float velocityY) {
//        	// TODO Auto-generated method stub
//        	if (velocityY >= velocityX) {
//        		return true;
//        	}
//        	return super.onFling(e1, e2, velocityX, velocityY);
//        }
    }
    
    
}
