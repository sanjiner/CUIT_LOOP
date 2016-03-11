package cc.util.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 保证不会与外部竖直可滑动空间冲突
 * @author wangcccong
 * @version 1.140122
 * create at：2014-02-26
 */
public class InnerListView extends ListView {
	
	/**
	 * Instantiates a new ab inner list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public InnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
	}

}