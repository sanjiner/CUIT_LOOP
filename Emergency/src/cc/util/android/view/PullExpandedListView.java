package cc.util.android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Scroller;

public class PullExpandedListView extends ExpandableListView implements OnScrollListener {

	public interface OnPullExpandedleListViewListener {
		
		public void onPullExpandedLoadMore(PullExpandedListView pullExpandedListView);

		public void onPullExpandedRefresh(PullExpandedListView pullExpandedListView);
	}

	/** The m last y. */
	private float mLastY = -1;
	
	/** The m scroller. */
	private Scroller mScroller; 

	/** The m list view listener. */
	private OnPullExpandedleListViewListener mListViewListener;

	/** The m header view. */
	private ScrollViewHeader mHeaderView;
	
	/** The m footer view. */
	private ScrollViewFooter mFooterView;
	
	/** The m header view height. */
	private int mHeaderViewHeight; 
	
	/** The m footer view height. */
	//private int mFooterViewHeight; 
	
	/** The m enable pull refresh. */
	private boolean mEnablePullRefresh = true;
	
	/** The m enable pull load. */
	private boolean mEnablePullLoad = true;
	
	/** The m pull refreshing. */
	private boolean mPullRefreshing = false;
	
	/** The m pull loading. */
	private boolean mPullLoading;

	/** The m is footer ready. */
	private boolean mIsFooterReady = false;

	/** 总条数. */
	private int mTotalItemCount;

	/** The m scroll back. */
	private int mScrollBack;
	
	/** The Constant SCROLLBACK_HEADER. */
	private final static int SCROLLBACK_HEADER = 0;
	
	/** The Constant SCROLLBACK_FOOTER. */
	//private final static int SCROLLBACK_FOOTER = 1;

	/** The Constant SCROLL_DURATION. */
	private final static int SCROLL_DURATION = 200;
	
	/** The Constant OFFSET_RADIO. */
	private final static float OFFSET_RADIO = 1.8f; 
												
	/** 数据相关. */
	private ListAdapter mAdapter = null;
	
	/**上一次的数量*/
	private int count = 0;
	
	//改写部分
	private OnScrollListener mOnScrollListener;
	
	/**
	 * 构造.
	 * @param context the context
	 */
	public PullExpandedListView(Context context) {
		super(context);
		initView(context, null);
	}

	/**
	 * 构造.
	 * @param context the context
	 * @param attrs the attrs
	 */
	public PullExpandedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	/**
	 * 初始化View.
	 * @param context the context
	 */
	private void initView(Context context, AttributeSet attrs) {
		
		mScroller = new Scroller(context, new DecelerateInterpolator());
		
		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new ScrollViewHeader(context);
		
		// init header height
		mHeaderViewHeight = mHeaderView.getHeaderHeight();
		mHeaderView.setGravity(Gravity.BOTTOM);
		addHeaderView(mHeaderView);
		
		// init footer view
		mFooterView = new ScrollViewFooter(context);
		//mFooterViewHeight= mFooterView.getFooterHeight();
		
		//默认是打开刷新与更多
		setPullRefreshEnable(true);
		setPullLoadEnable(true);
		
		//先隐藏
		mFooterView.hide();
	}

	/**
	 * 描述：设置适配器
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		mAdapter = adapter;
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			mFooterView.setGravity(Gravity.TOP);
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * 打开或者关闭下拉刷新功能.
	 * @param enable 开关标记
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) {
			mHeaderView.setVisibility(View.INVISIBLE);
		} else {
			mHeaderView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 打开或者关闭加载更多功能.
	 * @param enable 开关标记
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.setState(ScrollViewFooter.STATE_READY);
			//load more点击事件.
//			mFooterView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					startLoadMore();
//				}
//			});
		}
	}

	/**
	 * 停止刷新并重置header的状态.
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
		
		count = mAdapter.getCount();
		//判断有没有数据
		if (count > 0) {
			mFooterView.setState(ScrollViewFooter.STATE_READY);
		} else {
			mFooterView.setState(ScrollViewFooter.STATE_EMPTY);
		}
	}

	/**
	 * 更新header的高度.
	 *
	 * @param delta 差的距离
	 */
	private void updateHeaderHeight(float delta) {
		int newHeight = (int) delta + mHeaderView.getVisiableHeight();
		mHeaderView.setVisiableHeight(newHeight);
		if (mEnablePullRefresh && !mPullRefreshing) {
			if (mHeaderView.getVisiableHeight() >= mHeaderViewHeight) {
				mHeaderView.setState(ScrollViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(ScrollViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); 
	}

	/**
	 * 根据状态设置Header的位置.
	 */
	private void resetHeaderHeight() {
		//当前下拉到的高度
		int height = mHeaderView.getVisiableHeight();
		if (height < mHeaderViewHeight || !mPullRefreshing) {
			//距离短  隐藏
			mScrollBack = SCROLLBACK_HEADER;
			mScroller.startScroll(0, height, 0, -1*height, SCROLL_DURATION);
		}else if(height > mHeaderViewHeight || !mPullRefreshing){
			//距离多的  弹回到mHeaderViewHeight
			mScrollBack = SCROLLBACK_HEADER;
			mScroller.startScroll(0, height, 0, -(height-mHeaderViewHeight), SCROLL_DURATION);
		}
		
		invalidate();
	}


	/**
	 * 开始加载更多.
	 */
	private void startLoadMore() {
		Log.d("TAG", "startLoadMore");
		mFooterView.show();
		mPullLoading = true;
		mFooterView.setState(ScrollViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			//开始下载数据
			mListViewListener.onPullExpandedLoadMore(this);
		}
	}
	
	/**
	 * 停止加载更多并重置footer的状态.
	 *
	 */
	public void stopLoadMore() {
		mFooterView.hide();
		mPullLoading = false;
		int countNew = mAdapter.getCount();
		//判断有没有更多数据了
		if(countNew > count){
			mFooterView.setState(ScrollViewFooter.STATE_READY);
		}else{
			mFooterView.setState(ScrollViewFooter.STATE_NO);
		}
	}

	/**
	 * 描述：onTouchEvent
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (mEnablePullRefresh && getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				updateHeaderHeight(deltaY / OFFSET_RADIO);
			} else if (mEnablePullLoad && !mPullLoading && getLastVisiblePosition() == mTotalItemCount - 1 && deltaY<0) {
				startLoadMore();
			}
			break;
		case MotionEvent.ACTION_UP:
			mLastY = -1; 
			if (getFirstVisiblePosition() == 0) {
				//需要刷新的条件
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() >= mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(ScrollViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						//刷新
						mListViewListener.onPullExpandedRefresh(this);
					}
				}
				
				if(mEnablePullRefresh){
					//弹回
					resetHeaderHeight();
				}
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 描述：TODO
	 * @see android.view.View#computeScroll()
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			}
			postInvalidate();
		}
		super.computeScroll();
	}

	/**
	 * 描述：设置ListView的监听器.
	 * 默认进入就开始刷新最新数据
	 * @param listViewListener 
	 */
	public void setOnPullExpandedListViewListener(OnPullExpandedleListViewListener listViewListener) {
		mListViewListener = listViewListener;
		mPullRefreshing = true;
		mHeaderView.setState(ScrollViewHeader.STATE_REFRESHING);
		if (mListViewListener != null) mListViewListener.onPullExpandedRefresh(this);
		mScroller.startScroll(0, mHeaderViewHeight, 0, 0, SCROLL_DURATION);
	}
	
	//改写部分(重载此方法，避免覆盖了ListView内所实现的OnScrollListener,外部重设的OnScrollListener只是多余的工作，但不应该覆盖了本类实现的工作)
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		// TODO Auto-generated method stub
//		super.setOnScrollListener(l);
		this.mOnScrollListener = l;
	}

	/**
	 * 描述：TODO
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//改写部分
		if (mOnScrollListener != null) mOnScrollListener.onScrollStateChanged(view, scrollState);
	}

	/**
	 * 描述：TODO
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mTotalItemCount = totalItemCount;
		//改写部分
		if(mOnScrollListener != null) mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	}
	
	public void setTotalItemCount(int totalItemCount) {
		this.mTotalItemCount = totalItemCount;
	}

	/**
	 * 
	 * 描述：获取Header View
	 * @return
	 * @throws 
	 */
	public ScrollViewHeader getHeaderView() {
		return mHeaderView;
	}

	/**
	 * 
	 * 描述：获取Footer View
	 * @return
	 * @throws 
	 */
	public ScrollViewFooter getFooterView() {
		return mFooterView;
	}
	
	/**
	 * 
	 * 描述：获取Header ProgressBar，用于设置自定义样式
	 * @return
	 * @throws 
	 */
	public ProgressBar getHeaderProgressBar() {
		return mHeaderView.getHeaderProgressBar();
	}
	
	
	/**
	 * 
	 * 描述：获取Footer ProgressBar，用于设置自定义样式
	 * @return
	 * @throws 
	 */
	public ProgressBar getFooterProgressBar() {
		return mFooterView.getFooterProgressBar();
	}
}