package cc.util.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 保证不会与外部竖直可滑动空间冲突
 * @author wangcccong
 * @version 1.140122
 * create at：2014-03-20
 */
public class InnerGridView extends GridView {
	
	/**
	 * Instantiates a new ab inner list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public InnerGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
	}
}
