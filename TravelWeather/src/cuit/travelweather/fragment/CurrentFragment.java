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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import cuit.travelweather.util.Lunar;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;

public class CurrentFragment extends Fragment{

	private View view;
	private LayoutInflater infater;
	
	private TextView gregorian_calendar;
	private TextView lunar_calendar;
	private TextView weekday;
	private TextView tem;
	private TextView weather;
	private TextView weather_1;
	private TextView weather_2;
	private TextView weather_3;
	private TextView fengli;
	private TextView tv_city;
	private TextView tv_time;
	private ImageView current_img;
	private int length;
	private JSONObject jsonObject;
	
	private ViewPager viewPager;
	private List<View> viewList = new ArrayList<View>(); //把需要滑动的页卡添加到这个list中 
	private RadioGroup dotGroupButton;
	/**  实况是否加载  **/
	private int[] positionValue;
	private ArrayList<String> listItem = new ArrayList<String>();
//	private String tempCity;
	private Boolean lo = true;
	/**  实况的加载状态  **/
	public static final int NO_LOADED = 0;
	public static final int LOADING = 1;
	public static final int LOADED = 2;

	private SharedPreferences sp;
//	private Editor editor;
	
	
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
		this.infater = inflater;
		view = inflater.inflate(R.layout.weather_current, container, false);
		//init();
		sp = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE);
		getCityName();
		initViewPager();
		return view;
	}
	private void initViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.current_viewpager);
		dotGroupButton = (RadioGroup) view.findViewById(R.id.dotGroupButton);
		length = sp.getInt("length", 1);
		positionValue = new int[length];
		for(int i = 0; i<length;i++) {
			positionValue[i] = NO_LOADED;
			final View itemView = infater.inflate(R.layout.current_item,null);
			final RadioButton dotButton = new RadioButton(getActivity());
			dotButton.setId(i);
			dotButton.setLayoutParams(new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT));
			dotButton.setButtonDrawable(R.drawable.dot_bg);
			dotButton.setPadding(20, 20, 20, 0);
			dotButton.setTag(i);//设置当前位置
			//为点注册checked事件，当点击对应的点时，Viewpager切换到对应的page,并将点击的点设置为高亮
			dotButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						viewPager.setCurrentItem((Integer)dotButton.getTag());
					}
				}
			});
			
			dotGroupButton.addView(dotButton);
			dotGroupButton.check(0);//将第一个小白点设置为高亮
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
				
				if (positionValue[position] == NO_LOADED) {
					positionValue[position] = LOADED;
					init(listItem.get(position),viewList.get(position));
				}else {
					if (position == length-2 && lo) {
						lo = false;
						init(listItem.get(position),viewList.get(position));
					}
				}
			}
			
		});
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				((RadioButton)dotGroupButton.getChildAt(position)).setChecked(true);
				
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
	
	private void init(String tmp,View view) {
		// TODO Auto-generated method stub
		getViews(view);
		getWeekDay();
		getData(tmp);
	}
	
	private void getData(final String tmp) {
		pdialog = new ProgressDialog(getActivity());
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("获取数据。。。");
		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("cityName", URLEncoder
							.encode(Split.cut_r2(tmp), "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(getActivity(),
						Constant.weatherSK, params);
				
				 
				return null;

			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				try {
					JSONObject json = jsonObject.getJSONObject("weatherinfo");
					tv_city.setText(Split.cut(tmp));
					tem.setText(json.getString("temp")+"℃");
					tv_time.setText(json.getString("time"));
					weather.setText(sp.getString("weather_day", "多云"));
					setImageIcon(weather.getText().toString(),current_img);
					weather_1.setText("湿度："+json.getString("SD"));
					weather_2.setText("能见度：无");
					fengli.setText(json.getString("WD"));
					weather_3.setText("风级："+json.getString("WS"));
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
		} else if (day0.equals("小雪")) {
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
	private void getWeekDay() {
		final Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH)+1;
		int day = currentDate.get(Calendar.DATE);
		int weekDay = currentDate.get(Calendar.DAY_OF_WEEK);
		gregorian_calendar.setText(new StringBuilder().append(year).append("年")
				.append(month).append("月").append(day).append("日"));
		lunar_calendar.setText(Lunar.getLunar(Integer.toString(year),
				Integer.toString(month), Integer.toString(day)));

		switch (weekDay) {
		case 1:
			weekday.setText("星期日");
			break;
		case 2:
			weekday.setText("星期一");
			break;
		case 3:
			weekday.setText("星期二");
			break;
		case 4:
			weekday.setText("星期三");
			break;
		case 5:
			weekday.setText("星期四");
			break;
		case 6:
			weekday.setText("星期五");
			break;
		case 7:
			weekday.setText("星期六");
			break;
		default:
			break;
		}
	}
	private void getViews(View view) {
		gregorian_calendar = (TextView) view
				.findViewById(R.id.gregorian_calendar);
		lunar_calendar = (TextView) view.findViewById(R.id.lunar_calendar);
		weekday = (TextView) view.findViewById(R.id.weekday);
		tem = (TextView) view.findViewById(R.id.tem);
		weather = (TextView) view.findViewById(R.id.weather);
		weather_1 = (TextView) view.findViewById(R.id.weather_1);
		weather_2 = (TextView) view.findViewById(R.id.weather_2);
		weather_3 = (TextView) view.findViewById(R.id.weather_3);
		fengli = (TextView) view.findViewById(R.id.fengli);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_city = (TextView) view.findViewById(R.id.tv_city);
		current_img = (ImageView) view.findViewById(R.id.current_img);
		
		sp= getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			gregorian_calendar.setTextSize(10);
			lunar_calendar.setTextSize(10);
			weekday.setTextSize(10);
			tem.setTextSize(10);
			weather.setTextSize(10);
			weather_1.setTextSize(10);
			weather_2.setTextSize(10);
			weather_3.setTextSize(10);
			fengli.setTextSize(10);
			tv_time.setTextSize(10);
			tv_city.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			gregorian_calendar.setTextSize(30);
			lunar_calendar.setTextSize(30);
			weekday.setTextSize(30);
			tem.setTextSize(30);
			weather.setTextSize(30);
			weather_1.setTextSize(30);
			weather_2.setTextSize(30);
			weather_3.setTextSize(30);
			fengli.setTextSize(30);
			tv_time.setTextSize(30);
			tv_city.setTextSize(30);
		}
	}
	@SuppressLint("NewApi")
	private void getCityName() {
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		set1.add("四川省,成都市,双流县");
		set = sp.getStringSet("dingYueCity", set1);
		int i = 0;
		for (Iterator<String> it = set.iterator(); it.hasNext(); i++) {
			listItem.add(i, it.next().toString());
		}
	}
}
