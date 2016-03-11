package cc.util.android.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 实现类似spinner的功能
 * @author wangcccong
 * @version 1.140122
 * create at：2014-02-26
 */
public class SpinnerCompat extends Button {
	public interface onItemSelectedCompatListener {
		//void on
	}

	//private final static String TAG = "CCSpinnerCompat";
	private final Context mContext;
	/** 基本adapter*/
	private BaseAdapter mBaseAdapter;
	/** 使用内部adapter时提供的数据 */
	private List<String> mAdapterItems;
	
	/** 弹出框 */
	private PopupWindow mPopupWindow;
	
	/** 通过ListView关联数据 */
	private ListView mListView;
	
	public SpinnerCompat(Context context) {
		this(context, null);
	}

	public SpinnerCompat(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SpinnerCompat(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.init(context);
	}

	private void init(Context context) {
		mPopupWindow = new PopupWindow(context);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(Drawable.createFromPath("image/spinner_popup_bg.9.png"));
		
		mListView = new ListView(context);
		mListView.setCacheColorHint(Color.argb(0, 0, 0, 0));
		mListView.setFooterDividersEnabled(false);
		mListView.setHeaderDividersEnabled(false);
		mPopupWindow.setContentView(mListView);
	}
	
	public void setAdapter(BaseAdapter baseAdapter) {
		mBaseAdapter = baseAdapter;
		mListView.setAdapter(mBaseAdapter);
	}
	
	/** 采用默认方式显示内容,默认显示一个TextView(singleLine)  */
	public void setItems(List<String> data) {
		mAdapterItems = data;
		mBaseAdapter = new CCSimpleAdapter(mContext, mAdapterItems);
		mListView.setAdapter(mBaseAdapter);
	}
	
	public void setItems(String[] data) {
		List<String> mTempList = new ArrayList<String>();
		Collections.addAll(mTempList, data);
		this.setItems(mTempList);
	}
	
	private class CCSimpleAdapter extends BaseAdapter {
		private final Context mContext;
		private final List<String> mData;
		
		private CCSimpleAdapter(Context context, List<String> list) {
			this.mContext = context;
			this.mData = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = null;
			if (convertView == null) {
				textView = new TextView(mContext);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				textView.setLayoutParams(lp);
				textView.setMinimumHeight(ViewUtil.dip2px(mContext, 40.0f));
				textView.setTextSize(16);
				textView.setTextColor(Color.BLACK);
				textView.setSingleLine(true);
				convertView = textView;
			} else {
				textView = (TextView)convertView;
			}
			textView.setText(mData.get(position));
			return convertView;
		}
		
	}
	
}
