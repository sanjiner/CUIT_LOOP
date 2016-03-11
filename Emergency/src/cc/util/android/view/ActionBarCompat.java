package cc.util.android.view;

import cc.util.android.core.LogUtil;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义ActionBar
 * @author wangcccong
 * @version 1.1406
 * create at： Mon, 28 Apr. 2014
 * update at: Thur, 28 Aug. 2014
 */
public class ActionBarCompat extends LinearLayout {
	
	private final static String TAG = "ActionBarCompat";
	
	private Context mContext;
	private RelativeLayout mTotalLayout;
    //左方布局界面
    private LinearLayout mLeftView;
    
    //中间布局
    private LinearLayout mTitleLinear;
    private TextView mTitle;
    private ProgressBar mProgressBar;
    
    //右方布局
    private LinearLayout mRightView;
    
    public ActionBarCompat(Context context) {
    	super(context);
    	init(context, null);
    }
	
	public ActionBarCompat(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		LogUtil.i(TAG, "init--ActionBarCompat");
		this.mContext = context;
		setOrientation(LinearLayout.HORIZONTAL);
		mTotalLayout = new RelativeLayout(context);
		addView(mTotalLayout, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		//中间布局(ProgressBar + TextView)
		mTitleLinear = new LinearLayout(context);
		android.widget.RelativeLayout.LayoutParams titlePP = 
				new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, 
						android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
		mTitleLinear.setLayoutParams(titlePP);
		mTitleLinear.setOrientation(HORIZONTAL);
		mTitleLinear.setGravity(Gravity.CENTER);
		
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmallTitle);
        LayoutParams lpp = new LayoutParams(ViewUtil.dip2px(context, 16), ViewUtil.dip2px(context, 16));
        lpp.setMargins(0, 0, 5, 0);
        mProgressBar.setLayoutParams(lpp);
        mProgressBar.setVisibility(View.GONE);
        mTitleLinear.addView(mProgressBar);
        
        mTitle = new TextView(context);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(20);
        mTitle.setFocusableInTouchMode(true);
        mTitle.setMarqueeRepeatLimit(-1);
        mTitle.setEllipsize(TruncateAt.MARQUEE);
        mTitle.setSingleLine(true);
        mTitle.setHorizontallyScrolling(true);
        mTitleLinear.addView(mTitle);
		mTotalLayout.addView(mTitleLinear);
		
		//左方按钮
		mLeftView = new LinearLayout(context);
		mLeftView.setGravity(Gravity.CENTER);
		android.widget.RelativeLayout.LayoutParams leftPP = 
				new android.widget.RelativeLayout.LayoutParams(ViewUtil.dip2px(context, 30), ViewUtil.dip2px(context, 20));
		leftPP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		leftPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		leftPP.setMargins(20, 0, 0, 0);;
		mLeftView.setLayoutParams(leftPP);
		mTotalLayout.addView(mLeftView);
		
		//右方按钮
		mRightView = new LinearLayout(context);
		mRightView.setGravity(Gravity.CENTER);
		android.widget.RelativeLayout.LayoutParams rightPP = 
				new android.widget.RelativeLayout.LayoutParams(ViewUtil.dip2px(context, 42), ViewUtil.dip2px(context, 20));
		rightPP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		rightPP.setMargins(0, 0, 20, 0);
		mRightView.setLayoutParams(rightPP);
		mRightView.setVisibility(View.INVISIBLE);
		mTotalLayout.addView(mRightView);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
	
	/**
	 * 设置资源id(可以是布局文件，也可以是drawable资源文件)
	 * @param id
	 */
	public void setLeftViewResourceId(int id) {
		mLeftView.setVisibility(View.VISIBLE);
		try {
			View view = (View)View.inflate(mContext, id, null);
			mLeftView.addView(view);
		} catch (Exception e) {
			// TODO: handle exception
			mLeftView.setBackgroundResource(id);
		}
	}
	
	/**
	 * 设置左方监听
	 * @param resId
	 * @param listener {@link android.view.View.OnClickListener}
	 */
	public void setLeftViewAction(OnClickListener listener) {
		mLeftView.setOnClickListener(listener);
	}
	
	/**
	 * 设置左方资源和监听
	 * @param drawable {@link android.graphics.drawable.Drawable}
	 * @param listener {@link android.view.View.OnClickListener}
	 */
	public void setLeftViewResAction(int id, OnClickListener listener) {
		setLeftViewResourceId(id);
		setLeftViewAction(listener);
	}
	
	/**
	 * 隐藏左方控件
	 */
	public void hideLeftView() {
		mLeftView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 显示左方控件，默认显示
	 */
	public void showLeftView() {
		mLeftView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置progressbar的样式
	 * @param drawable
	 */
	public void setProgressDrawable(Drawable drawable) {
		mProgressBar.setIndeterminateDrawable(drawable);
	}
	
	/**
	 * 显示加载的progressbar
	 */
	public void showProgressBar() {
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏progressBar
	 */
	public void hideProgressBar() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}
	
	
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		mTitle.setText(title);
	}
	
	/**
	 * @param size
	 */
	public void setTitleSize(float size) {
		mTitle.setTextSize(size);
	}
	
	/**
	 * @param color
	 */
	public void setTitleColor(int color) {
		mTitle.setTextColor(color);
	}
	
	/**
	 * @param colors {@link android.content.res.ColorStateList}
	 */
	public void setTitleColor(ColorStateList colors) {
		mTitle.setTextColor(colors);
	}
	
	/**
	 * 添加右方自定义View
	 * @param view
	 */
	public void addRightView(View view) {
		android.widget.RelativeLayout.LayoutParams rightPP = 
				new android.widget.RelativeLayout.LayoutParams(ViewUtil.dip2px(mContext, 30), ViewUtil.dip2px(mContext, 25));
		rightPP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightPP.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		rightPP.setMargins(0, 0, 5, 0);
		view.setLayoutParams(rightPP);
		mTotalLayout.addView(view);
	}
	
	/**
	 * 添加右方控件,并设置监听（此方法只能添加Drawable ID，适用于只在右方添加一个控件）
	 * @param id
	 * @param listener
	 */
	public void addRightResAction(int id, OnClickListener listener) {
		mRightView.setVisibility(View.VISIBLE);
		mRightView.setBackgroundResource(id);
		mRightView.setOnClickListener(listener);
	}
	
	/**
	 * 隐藏右方控件
	 */
	public void hideRightView() {
		if (mRightView != null) mRightView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 显示右方控件
	 */
	public void showRightView() {
		if (mRightView != null) mRightView.setVisibility(View.VISIBLE);
	}
}
