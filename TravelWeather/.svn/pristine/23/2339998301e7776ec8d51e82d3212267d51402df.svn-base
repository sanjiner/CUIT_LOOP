package cuit.travelweather.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.activity.JpushApplication;
import cuit.travelweather.activity.MainActivity;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import cuit.travelweather.view.TrendView;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * [天气-趋势]页面，显示温度、风力、气压、湿度四种走势图
 */
public class TrendFragment extends Fragment {
	private View view;
	private ViewPager viewPager;
	private LayoutInflater inflater;// 视图填充器，用于ViewPager添加页面	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;
	private TextView trend_c_city;
	private JSONObject jsonObject;
	private int mWeekday;
	private SharedPreferences sp;
	/**  实况的加载状态  **/
	public static final int NO_LOADED = 0;
	public static final int LOADING = 1;
	public static final int LOADED = 2;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	/**  实况是否加载  **/
	private int[] positionValue;
	private static ProgressDialog pdialog;
	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROCESS:
				pdialog.show();
				break;
			case DISMISS_PROCESS:
				pdialog.dismiss();
				break;
			}
		}

	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;// 初始化inflater
		view = inflater.inflate(R.layout.weather_trend, container, false);
		
		sp = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE);
		trend_c_city = (TextView)view.findViewById(R.id.trend_c_city);
		trend_c_city.setText(Constant.city);
		initViewPager();
		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		
		tv1 = (TextView)view.findViewById(R.id.tv_week_1);
		tv2 = (TextView)view.findViewById(R.id.tv_week_2);
		tv3 = (TextView)view.findViewById(R.id.tv_week_3);
		tv4 = (TextView)view.findViewById(R.id.tv_week_4);
		tv5 = (TextView)view.findViewById(R.id.tv_week_5);
		tv6 = (TextView)view.findViewById(R.id.tv_week_6);
		
		getWeekDay();
		getdata();
	}
	
	public void getdata() {
		
		pdialog = new ProgressDialog(getActivity());
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("获取数据。。。");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				
				try {
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode(sp.getString("forecast", "forecast"),
									"UTF-8")));
					params.add(new BasicNameValuePair("cityID", URLEncoder
							.encode(sp.getString("currentCity", "510122"),
									"UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonObject = MyHttpClient.getJson(getActivity(),
						Constant.getWeathertrend, params);		
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				super.onPostExecute(result);
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("nodes");
					int[] top = new int[6];
					int[] low = new int[6];
					ArrayList<String> map;
					for (int i = 0; i < 6; i++) {
						 map = new ArrayList<String>();
						JSONObject json = jsonArray.getJSONObject(i);
						top[i] = Integer.parseInt(Split.cutTemp_rTop(json.getString("wind_night")));
						low[i] = Integer.parseInt(Split.cutTemp_rLow(json.getString("")));
						
					}
					TrendView trendView = new TrendView(getActivity());
					trendView.setTopT(top);
					trendView.setLowT(low);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.execute();
	}
	
	public void getWeekDay() {
		
		final Calendar currentDate = Calendar.getInstance();
		mWeekday = currentDate.get(Calendar.DAY_OF_WEEK);
		switch (mWeekday) {
		case 1:
			tv1.setText("今天");
			tv2.setText("周一");
			tv3.setText("周二");
			tv4.setText("周三");
			tv5.setText("周四");
			tv6.setText("周五");
			break;
		case 2:
			tv1.setText("今天");
			tv2.setText("周二");
			tv3.setText("周三");
			tv4.setText("周四");
			tv5.setText("周五");
			tv6.setText("周六");
			break;
		case 3:
			tv1.setText("今天");
			tv2.setText("周三");
			tv3.setText("周四");
			tv4.setText("周五");
			tv5.setText("周六");
			tv6.setText("周日");
			break;
		case 4:
			tv1.setText("今天");
			tv2.setText("周四");
			tv3.setText("周五");
			tv4.setText("周六");
			tv5.setText("周日");
			tv6.setText("周一");
			break;
		case 5:
			tv1.setText("今天");
			tv2.setText("周五");
			tv3.setText("周六");
			tv4.setText("周日");
			tv5.setText("周一");
			tv6.setText("周二");
			break;
		case 6:
			tv1.setText("今天");
			tv2.setText("周六");
			tv3.setText("周日");
			tv4.setText("周一");
			tv5.setText("周二");
			tv6.setText("周三");
			break;
		case 7:
			tv1.setText("今天");
			tv2.setText("周日");
			tv3.setText("周一");
			tv4.setText("周二");
			tv5.setText("周三");
			tv6.setText("周四");
			break;
		}
	}
	
	/*
	 * 初始化ViewPager，添加页面，设置适配器和监听器
	 */
	private void initViewPager() {
		
		viewPager = (ViewPager) view.findViewById(R.id.trend_viewpager);
		ArrayList<View> pages = new ArrayList<View>();
		pages.add(inflater.inflate(R.layout.trend_temperature, null));
		pages.add(inflater.inflate(R.layout.trend_wind, null));
		pages.add(inflater.inflate(R.layout.trend_pressure, null));
		pages.add(inflater.inflate(R.layout.trend_humidity, null));
		positionValue = new int[4];
		for (int i=0;i<4;i++) {
			positionValue[i] = NO_LOADED;
		}
		viewPager.setAdapter(new MyViewPagerAdapter(pages));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	
	}

	/*
	 * 自定义ViewPager适配器
	 */
	class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}
		/**
	     * 跳转到每个页面都要执行的方法
	     */
		@Override
		public void setPrimaryItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			super.setPrimaryItem(container, position, object);
			if (position == 0) {
				if (positionValue[0] == NO_LOADED) {
					positionValue[0] = LOADED;
			
					init();
					
//					getWeekDay();
				}
				
			}
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	/*
	 * 自定义页面切换监听器
	 */
	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int position) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int arg0) {

		}

	}

}
