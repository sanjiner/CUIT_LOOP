package cuit.travelweather.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;

/*
 * [天气-预报]页面，显示一周天气预报信息
 */
public class ForecastFragment extends Fragment {

	private View view;
	private LayoutInflater inflater;
	private TextView forecase_city;
	private TextView forecast_today_temperature;
	private TextView forecast_today_rain;
	private TextView forecast_today_wind;
	private TextView forecase_w1;
	private TextView forecase_d1;
	private TextView forecase_w2;
	private TextView forecase_d2;
	private TextView forecase_w3;
	private TextView forecase_d3;
	private TextView forecase_w4;
	private TextView forecase_d4;
	private TextView forecast_today_day;
	private JSONObject jsonObject;
	TextView forecase_h1;
	TextView forecase_l1;
	TextView forecase_h2;
	TextView forecase_l2;
	TextView forecase_h3;
	TextView forecase_l3;
	TextView forecase_h4;
	TextView forecase_l4;
	private int mDay;
	private int mMonth;
	private int mWeekday;
	
	private ImageView iv_forecast_today;
	private ImageView iv_forecase_0;
	private ImageView iv_forecase_1;
	private ImageView iv_forecase_2;
	private ImageView iv_forecase_3;

	private SharedPreferences sp;
	private Editor editor;
	
	private int length;
	public ViewPager viewPager;
	private List<View> viewList = new ArrayList<View>(); //把需要滑动的页卡添加到这个list中 
	private RadioGroup forecastdotGroupButton;
	/**  实况是否加载  **/
	private int[] positionValue;
	private ArrayList<String> listItem = new ArrayList<String>();
	private Boolean lo = true;
	public String temperature_night0 ="0";
	public String temperature_day0 = "1";
	/**  实况的加载状态  **/
	public static final int NO_LOADED = 0;
	public static final int LOADING = 1;
	public static final int LOADED = 2;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	
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

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		view = inflater.inflate(R.layout.weather_forecast, container, false);
		sp = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE);
		editor = sp.edit();
		if (Constant.city.equals("N/A")) {
			Constant.city = sp.getString("currentCityName", "成都市,双流县");
		}
		getCityName();
		initViewPager();
		return view;
	}
	
	private void initViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.forecast_viewpager);
		forecastdotGroupButton = (RadioGroup) view.findViewById(R.id.forecast_dotGroupButton);
		length = sp.getInt("length", 1);
//		GetSet getset = new GetSet();
//		length = getset.getLength();
//		if(length == 0){
//			length = 1;
//		}
		positionValue = new int[length];
		for(int i = 0; i<length;i++) {
			positionValue[i] = NO_LOADED;
			final View itemView = inflater.inflate(R.layout.forecast_item,null);
			final RadioButton forecastdotButton = new RadioButton(getActivity());
			forecastdotButton.setId(i);
			forecastdotButton.setLayoutParams(new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT));
			forecastdotButton.setButtonDrawable(R.drawable.dot_bg);
			forecastdotButton.setPadding(20, 20, 20, 0);
			forecastdotButton.setTag(i);//设置当前位置
			//为点注册checked事件，当点击对应的点时，Viewpager切换到对应的page,并将点击的点设置为高亮
			forecastdotButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						viewPager.setCurrentItem((Integer)forecastdotButton.getTag());
					}
				}
			});
			
			forecastdotGroupButton.addView(forecastdotButton);
			forecastdotGroupButton.check(0);//将第一个小白点设置为高亮
			viewList.add(itemView);
		}
		
		viewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return length;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				((ViewPager) container).removeView(viewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				((ViewPager) container).addView(viewList.get(position));
				getViews(viewList.get(position));	
				return viewList.get(position);
			}

			@Override
			public void startUpdate(ViewGroup container) {
				// TODO Auto-generated method stub
				super.startUpdate(container);
			}

			@Override
			public void setPrimaryItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				super.setPrimaryItem(container, position, object);
				if(positionValue.length == 0)
				{
					return;
				}
				else{
					if (positionValue[position] == NO_LOADED) {
						positionValue[position] = LOADED;
						init(listItem.get(position),viewList.get(position));
						if (forecase_city.getText().toString().equals("N/A")){
//							init(listItem.get(position));
							init(listItem.get(position),viewList.get(position));
						}	
					} else {
						if (position == length-2 && lo) {
							lo = false;
							init(listItem.get(position),viewList.get(position));
						}
					}
				}
				}
				
			
		});
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				((RadioButton)forecastdotGroupButton.getChildAt(position)).setChecked(true);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@SuppressLint("NewApi")
	public void getCityName() {
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		set1.add("510122");
		set = sp.getStringSet("dingYueCityId", set1);
		int i = 0;
		for (Iterator<String> it = set.iterator(); it.hasNext(); i++) {
			listItem.add(i, it.next().toString());
		}
	}
	
	private void init(String tmp,View view) {
		// TODO Auto-generated method stub
		getViews(view);
		getWeekDay();
		getData(tmp);
		forecast_today_day.setText(forecase_d1.getText().toString().trim());
	}
	public void getViews(View view) {
		forecase_city = (TextView) view.findViewById(R.id.forecase_city);
		forecast_today_temperature = (TextView) view
				.findViewById(R.id.forecast_today_temperature);
		forecast_today_rain = (TextView) view
				.findViewById(R.id.forecast_today_rain);
		forecast_today_wind = (TextView) view
				.findViewById(R.id.forecast_today_wind);
		forecase_w1 = (TextView) view.findViewById(R.id.forecase_w1);
		forecase_d1 = (TextView) view.findViewById(R.id.forecase_d1);
		forecase_w2 = (TextView) view.findViewById(R.id.forecase_w2);
		forecase_d2 = (TextView) view.findViewById(R.id.forecase_d2);
		forecase_w3 = (TextView) view.findViewById(R.id.forecase_w3);
		forecase_d3 = (TextView) view.findViewById(R.id.forecase_d3);
		forecase_w4 = (TextView) view.findViewById(R.id.forecase_w4);
		forecase_d4 = (TextView) view.findViewById(R.id.forecase_d4);
		forecase_h1 = (TextView) view.findViewById(R.id.forecase_h1);
		forecase_l1 = (TextView) view.findViewById(R.id.forecase_l1);
		forecase_h2 = (TextView) view.findViewById(R.id.forecase_h2);
		forecase_l2 = (TextView) view.findViewById(R.id.forecase_l2);
		forecase_h3 = (TextView) view.findViewById(R.id.forecase_h3);
		forecase_l3 = (TextView) view.findViewById(R.id.forecase_l3);
		forecase_h4 = (TextView) view.findViewById(R.id.forecase_h4);
		forecase_l4 = (TextView) view.findViewById(R.id.forecase_l4);
		forecast_today_day = (TextView) view.findViewById(R.id.forecast_today_day);
		iv_forecast_today = (ImageView) view.findViewById(R.id.iv_forecast_today);
		iv_forecase_0 = (ImageView) view.findViewById(R.id.iv_forecase_0);
		iv_forecase_1 = (ImageView) view.findViewById(R.id.iv_forecase_1);
		iv_forecase_2 = (ImageView) view.findViewById(R.id.iv_forecase_2);
		iv_forecase_3 = (ImageView) view.findViewById(R.id.iv_forecase_3);
		
		sp= getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			forecase_city.setTextSize(10);
			forecast_today_temperature.setTextSize(10);
			forecast_today_rain.setTextSize(10);
			forecast_today_wind.setTextSize(10);
			forecase_w1.setTextSize(10);
			forecase_d1.setTextSize(10);
			forecase_d2.setTextSize(10);
			forecase_d3.setTextSize(10);
			forecase_w3.setTextSize(10);
			forecase_w2.setTextSize(10);
			forecase_w4.setTextSize(10);
			forecase_d4.setTextSize(10);
			forecase_h1.setTextSize(10);
			forecase_h2.setTextSize(10);
			forecase_h3.setTextSize(10);
			forecase_h4.setTextSize(10);
			forecase_l1.setTextSize(10);
			forecase_l2.setTextSize(10);
			forecase_l3.setTextSize(10);
			forecase_l4.setTextSize(10);
			forecast_today_day.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			forecase_city.setTextSize(30);
			forecast_today_temperature.setTextSize(30);
			forecast_today_rain.setTextSize(30);
			forecast_today_wind.setTextSize(30);
			forecase_w1.setTextSize(30);
			forecase_d1.setTextSize(30);
			forecase_d2.setTextSize(30);
			forecase_d3.setTextSize(30);
			forecase_w3.setTextSize(30);
			forecase_w2.setTextSize(30);
			forecase_w4.setTextSize(30);
			forecase_d4.setTextSize(30);
			forecase_h1.setTextSize(30);
			forecase_h2.setTextSize(30);
			forecase_h3.setTextSize(30);
			forecase_h4.setTextSize(30);
			forecase_l1.setTextSize(30);
			forecase_l2.setTextSize(30);
			forecase_l3.setTextSize(30);
			forecase_l4.setTextSize(30);
			forecast_today_day.setTextSize(30);
		}


	}
	public void getData(final String tmp) {

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
							.encode("forecast", "UTF-8")));
					params.add(new BasicNameValuePair("cityID", URLEncoder
							.encode(tmp,"UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(getActivity(),
						Constant.getForecast, params);
				//getJson();

				return null;

			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

				try {
					JSONArray jsonArray = jsonObject.getJSONArray("nodes");
					JSONObject jo = jsonArray.getJSONObject(0);
					String str = Split.cut(jo.getString("location"));
					forecase_city.setText(str);
					//Constant.city = str;
					editor.putString("currentCityName", str);
					editor.commit();
					JSONObject j1 = jsonArray.getJSONObject(1);
					JSONArray nodes_info = j1.getJSONArray("nodes_info");
					JSONObject day0 = nodes_info.getJSONObject(0);
					JSONObject day1 = nodes_info.getJSONObject(1);
					JSONObject day2 = nodes_info.getJSONObject(2);
					//温度
					Constant.temperature_night0 = day0.getString("temperature_night");
					Constant.temperature_day0 = day0.getString("temperature_day");
					Constant.temperature_night1 = day1.getString("temperature_night");
					Constant.temperature_day1 = day1.getString("temperature_day");
					Constant.temperature_night2 = day2.getString("temperature_night");
					Constant.temperature_day2 = day2.getString("temperature_day");
					//风力
					forecast_today_temperature.setText(day0
							.getString("temperature_night")
							+ "℃ ~ "
							+ day0.getString("temperature_day") + "℃");
					forecast_today_rain.setText(day0.getString("weather_day"));
					forecast_today_wind.setText(day0.getString("wind_day"));
					forecase_l1
							.setText(day0.getString("temperature_day") + "℃");
					forecase_h1.setText(day0.getString("temperature_night")
							+ "℃");
					forecase_l2
							.setText(day1.getString("temperature_day") + "℃");
					forecase_h2.setText(day1.getString("temperature_night")
							+ "℃");
					forecase_l3
							.setText(day2.getString("temperature_day") + "℃");
					forecase_h3.setText(day2.getString("temperature_night")
							+ "℃");
					forecase_l4
							.setText(day2.getString("temperature_day") + "℃");
					forecase_h4.setText(day2.getString("temperature_night")
							+ "℃");
					setImageIcon(day0.getString("weather_day"),iv_forecast_today);
					setImageIcon(day0.getString("weather_day"),iv_forecase_0);
					setImageIcon(day1.getString("weather_day"),iv_forecase_1);
					setImageIcon(day2.getString("weather_day"),iv_forecase_2);
					setImageIcon(day2.getString("weather_day"),iv_forecase_3);
					editor.putString("weather_day",
							day0.getString("weather_day"));
					editor.putString("wind_day", day0.getString("wind_day"));
					editor.commit();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	public void getData1() {

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("forecast", "UTF-8")));
					params.add(new BasicNameValuePair("cityID", URLEncoder
							.encode("510122","UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(getActivity(),
						Constant.getForecast, params);
				return null;

			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("nodes");
					JSONObject j1 = jsonArray.getJSONObject(1);
					JSONArray nodes_info = j1.getJSONArray("nodes_info");
					JSONObject day0 = nodes_info.getJSONObject(0);
					temperature_night0 = day0.getString("temperature_night");
					temperature_day0 = day0.getString("temperature_day");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	protected void setImageIcon(String day0,ImageView iv) {
		// TODO Auto-generated method stub
		if (day0.equals("晴")) {
			iv.setImageResource(R.drawable.p02);
		} else if (day0.equals("多云")) {
			iv.setImageResource(R.drawable.p03);
		} else if (day0.equals("阴")) {
			iv.setImageResource(R.drawable.p04);
		} else if (day0.equals("小雨")) {
			iv.setImageResource(R.drawable.p09);
		} else if (day0.equals("中雨")) {
			iv.setImageResource(R.drawable.p010);
		} else if (day0.equals("大雨")) {
			iv.setImageResource(R.drawable.p011);
		} else if (day0.equals("暴雨")) {
			iv.setImageResource(R.drawable.p012);
		} else if (day0.equals("阵雨")) {
			iv.setImageResource(R.drawable.p023);
		} else if (day0.equals("多云转晴")) {
			iv.setImageResource(R.drawable.p03);
		} else if (day0.equals("小到中雨")) {
			iv.setImageResource(R.drawable.p024);
		} else if (day0.equals("中到大雨")) {
			iv.setImageResource(R.drawable.p025);
		} else if (day0.equals("大到暴雨")) {
			iv.setImageResource(R.drawable.p026);
		} else if (day0.equals("雷阵雨")) {
			iv.setImageResource(R.drawable.p06);
		}  else if (day0.equals("小雪")) {
			iv.setImageResource(R.drawable.p016);
		} else if (day0.equals("中雪")) {
			iv.setImageResource(R.drawable.p017);
		}else if (day0.equals("大雪")) {
			iv.setImageResource(R.drawable.p018);
		}else if (day0.equals("暴雪")) {
			iv.setImageResource(R.drawable.p019);
		}else if (day0.equals("雨夹雪")) {
			iv.setImageResource(R.drawable.p08);
		}else {
			iv.setImageResource(R.drawable.undefined);
		}
		
	}

	public void getWeekDay() {
		final Calendar currentDate = Calendar.getInstance();
		mDay = currentDate.get(Calendar.DAY_OF_MONTH);
		mMonth = currentDate.get(Calendar.MONTH) + 1;// 得到的月份+1，因为从0开始
		mWeekday = currentDate.get(Calendar.DAY_OF_WEEK);

		switch (mWeekday) {
		case 1:
			forecase_w1.setText("周日");
			forecase_w2.setText("周一");
			forecase_w3.setText("周二");
			forecase_w4.setText("周三");
			break;
		case 2:
			forecase_w1.setText("周一");
			forecase_w2.setText("周二");
			forecase_w3.setText("周三");
			forecase_w4.setText("周四");
			break;
		case 3:
			forecase_w1.setText("周二");
			forecase_w2.setText("周三");
			forecase_w3.setText("周四");
			forecase_w4.setText("周五");
			break;
		case 4:
			forecase_w1.setText("周三");
			forecase_w2.setText("周四");
			forecase_w3.setText("周五");
			forecase_w4.setText("周六");
			break;
		case 5:
			forecase_w1.setText("周四");
			forecase_w2.setText("周五");
			forecase_w3.setText("周六");
			forecase_w4.setText("周日");
			break;
		case 6:
			forecase_w1.setText("周五");
			forecase_w2.setText("周六");
			forecase_w3.setText("周日");
			forecase_w4.setText("周一");
			break;
		case 7:
			forecase_w1.setText("周六");
			forecase_w2.setText("周日");
			forecase_w3.setText("周一");
			forecase_w4.setText("周二");
			break;
		default:

		}

		switch (mMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
			if (mDay == 29) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 30) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 29).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 31) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 30).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 29).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 28).append("日"));
			} else {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 3).append("日"));
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (mDay == 29) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 27).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 30) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 29));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 28) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth + 1)
						.append("月").append(mDay - 27).append("日"));
			} else {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 3).append("日"));
			}
			break;
		case 2:
			if (currentDate.get(Calendar.DAY_OF_YEAR) % 4 == 0) {
				if (mDay == 27) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 2).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 26).append("日"));
				} else if (mDay == 28) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 27).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 26).append("日"));
				} else if (mDay == 29) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 28).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 27).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 26).append("日"));
				} else {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 2).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 3).append("日"));
				}
			} else {
				if (mDay == 26) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 2).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 25).append("日"));
				} else if (mDay == 27) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 26).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 25).append("日"));
				} else if (mDay == 28) {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 27).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 26).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth + 1)
							.append("月").append(mDay - 25).append("日"));
				} else {
					forecase_d1.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay).append("日"));
					forecase_d2.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 1).append("日"));
					forecase_d3.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 2).append("日"));
					forecase_d4.setText(new StringBuilder().append(mMonth)
							.append("月").append(mDay + 3).append("日"));
				}

			}
			break;
		case 12:
			if (mDay == 29) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 30) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 29).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 28).append("日"));
			} else if (mDay == 31) {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 30).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 29).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth - 11)
						.append("月").append(mDay - 28).append("日"));
			} else {
				forecase_d1.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay).append("日"));
				forecase_d2.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 1).append("日"));
				forecase_d3.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 2).append("日"));
				forecase_d4.setText(new StringBuilder().append(mMonth)
						.append("月").append(mDay + 3).append("日"));
			}
			break;
		default:
			break;
		}

	}
}
