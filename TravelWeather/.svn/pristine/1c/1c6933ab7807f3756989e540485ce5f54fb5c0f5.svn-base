package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dcs.test.Activity.CalendarGridView;
import com.dcs.test.Activity.CalendarGridViewAdapter;
import com.dcs.test.Tools.NumberHelper;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class GuessWeatherActivity extends BaseAct implements OnTouchListener {
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	GestureDetector mGesture = null;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}

	public void guess_return(View v) {
		finish();
	}

	AnimationListener animationListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			CreateGirdView();
		}
	};

	class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideLeftIn);
					viewFlipper.setOutAnimation(slideLeftOut);
					viewFlipper.showNext();
					setNextViewItem();
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					viewFlipper.setInAnimation(slideRightIn);
					viewFlipper.setOutAnimation(slideRightOut);
					viewFlipper.showPrevious();
					setPrevViewItem();
					return true;
				}
			} catch (Exception e) {
			}
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			int pos = gView2.pointToPosition((int) e.getX(), (int) e.getY());
			LinearLayout txtDay = (LinearLayout) gView2
					.findViewById(pos + 5000);
			if (txtDay != null) {
				if (txtDay.getTag() != null) {
					Date date = (Date) txtDay.getTag();
					calSelected.setTime(date);
					gAdapter.setSelectedDate(calSelected);
					gAdapter.notifyDataSetChanged();
					gAdapter1.setSelectedDate(calSelected);
					gAdapter1.notifyDataSetChanged();
					gAdapter3.setSelectedDate(calSelected);
					gAdapter3.notifyDataSetChanged();
				}
			}
			Log.i("TEST", "onSingleTapUp -  pos=" + pos);
			return false;
		}
	}

	private Context mContext = GuessWeatherActivity.this;
	private GridView title_gView;
	private GridView gView1;
	private GridView gView2;
	private GridView gView3;
	boolean bIsSelection = false;
	private Calendar calStartDate = Calendar.getInstance();
	private Calendar calSelected = Calendar.getInstance();
	private Calendar calToday = Calendar.getInstance();
	private CalendarGridViewAdapter gAdapter;
	private CalendarGridViewAdapter gAdapter1;
	private CalendarGridViewAdapter gAdapter3;
	private Button btnToday = null;
	private RelativeLayout mainLayout;
	private int count = 0;// 记录猜中的天数
	private Date[] Dates = new Date[31];
	private int iMonthViewCurrentYearF = 0;

	private TextView tv;
	private TextView guesstimes_tv;// 已猜天气次数，中奖次数。
	private Button view_rating;// 查看排名
	private Button comments;// 评论
	private TextView no_present;
	private static String text = "您今天已经猜过了!";
	private static ProgressDialog pdialog;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private JSONObject jsonObject;
	private JSONObject jsonobject;
	private String userID = null;
	List<NameValuePair> param = new ArrayList<NameValuePair>();

	private SharedPreferences sp;
	private int iMonthViewCurrentMonth = 0;
	private int iMonthViewCurrentYear = 0;
	private int iFirstDayOfWeek = Calendar.MONDAY;
	private static final int mainLayoutID = 88;
	private static final int titleLayoutID = 77;
	private static final int caltitleLayoutID = 66;
	private static final int calLayoutID = 55;
	String[] menu_toolbar_name_array;
	private String max = "1";
	private String min = "0";
	private int in = 0;
	private String str2 = null;
	//private Button present_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
		setContentView(generateContentView());
		
		guesstimes_tv = (TextView) findViewById(R.id.gw_content);
		no_present = (TextView) findViewById(R.id.no_present);
	
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			guesstimes_tv.setTextSize(10);
			no_present.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			guesstimes_tv.setTextSize(30);
			no_present.setTextSize(30);
		}

		
		
		ImageButton titleBtn = (ImageButton) findViewById(R.id.title_btn);  
	    titleBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {  
	                startActivity(new Intent(GuessWeatherActivity.this,DialogCurrentActivity.class));  
	            }  
	        });                     

		UpdateStartDateForMonth();
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		slideLeftIn.setAnimationListener(animationListener);
		slideLeftOut.setAnimationListener(animationListener);
		slideRightIn.setAnimationListener(animationListener);
		slideRightOut.setAnimationListener(animationListener);

		mGesture = new GestureDetector(this, new GestureListener());
	}

	public void simple(View source) {
		SharedPreferences sp;
		sp = mContext.getSharedPreferences("User_SP", MODE_PRIVATE);
		String sum = sp.getString("Sum", "100");
		String rating = sp.getString("YourNum", "1");
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(
				"查看排名")
				.setMessage("您当前排名第" + rating + "位\n总共有" + sum + "人参加排名");
		setPositiveButton(builder);
		setNegativeButton(builder).create().show();
	}

	private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
		return builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast tt = Toast
						.makeText(mContext, "亲，你真棒！", Toast.LENGTH_LONG);
				tt.show();

			}
		});
	}

	private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
		return builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast tt = Toast
						.makeText(mContext, "亲，加油哦！", Toast.LENGTH_LONG);
				tt.show();
			}
		});
	}

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

	public void getDate() {
		pdialog = new ProgressDialog(mContext);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载内容……");
		sp = this.getSharedPreferences("User_SP", this.MODE_PRIVATE);

		new AsyncTask<Void, Void, List<NameValuePair>>() {
			@Override
			protected List<NameValuePair> doInBackground(Void... params) {

				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				String userID = sp.getString("userName", "");
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				try {
					param.add(new BasicNameValuePair("userID", URLEncoder
							.encode(userID, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonObject = MyHttpClient.getJson(mContext,
						Constant.getWeather, param);
				return null;
			}

			@Override
			protected void onPostExecute(List<NameValuePair> result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("dateList");
					int j = jsonArray.length();
					count = j;
					Date date[] = new Date[j];
					for (int i = 0; i < j; i++) {
						date[i] = toDate(jsonArray.getString(i));
						Dates[i] = date[i];
					}
					guesstimes_tv.setText("你当前竞猜"
							+ jsonObject.getString("guessTimes") + "次，共有"
							+ jsonObject.getString("guessTrue") + "次竞猜成功");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				setTitleGirdView();
				super.onPostExecute(result);
			}

		}.execute();
	}

	void getData() {
		pdialog = new ProgressDialog(this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载内容……");

		sp = this.getSharedPreferences("User_SP", this.MODE_PRIVATE);
		new AsyncTask<Void, Void, List<NameValuePair>>() {

		
			@Override
			protected List<NameValuePair> doInBackground(Void... params) {
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				String userID = sp.getString("userName", "");
				try {
					param.add(new BasicNameValuePair("userID", URLEncoder
							.encode(userID, "UTF-8")));
					param.add(new BasicNameValuePair("highestTemperature",
							URLEncoder.encode(max, "UTF-8")));
					param.add(new BasicNameValuePair("lowestTemperature",
							URLEncoder.encode(min, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonobject = MyHttpClient.getJson(mContext,
						Constant.guessWeather, param);
				return null;
			}

			@Override
			protected void onPostExecute(List<NameValuePair> result) {

				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				try {
					in = jsonobject.getInt("status");
					str2 = jsonobject.getString("message");
					System.out.println(in);
					System.out.println(str2);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (in == -1) {
					no_present.setText(text);
					//present_btn.setClickable(false);
				
				} else {
					
					
				}
				super.onPostExecute(result);
			}

		}.execute();

	}

	/**
	 * 将时间字符串转换为Date类型
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public Date toDate(String dateStr) {
		Date date = null;
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern("yyyy-MM-dd");
		try {
			date = formater.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	void showDialogFragment(String str) {
		DialogFragment newFragment = MyDialogFragment.newInstance(str);
		newFragment.show(getFragmentManager(), str);
	}

	AlertDialog.OnKeyListener onKeyListener = new AlertDialog.OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				GuessWeatherActivity.this.finish();
			}
			return false;
		}
	};

	private View generateContentView() {
		viewFlipper = new ViewFlipper(this);
		viewFlipper.setId(calLayoutID);
		setContentView(R.layout.guess_weather);
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.gw_id);
		LinearLayout layTopControls = createLayout(LinearLayout.HORIZONTAL);
	
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//int width = dm.widthPixels;
		int height = dm.heightPixels ;//高度
		 layTopControls.setPadding(0, height/6, 0, 0);/////////////获取屏幕高度
		  
		
		
		generateTopButtons(layTopControls);
		RelativeLayout.LayoutParams params_title = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_title.topMargin = 325;
		//params_title.bottomMargin=0;//////////////////////////////////
		layTopControls.setId(titleLayoutID);
		mainLayout.addView(layTopControls, params_title);

		calStartDate = getCalendarStartDate();

		setTitleGirdView();
		RelativeLayout.LayoutParams params_cal_title = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_cal_title.addRule(RelativeLayout.BELOW, titleLayoutID);
		mainLayout.addView(title_gView, params_cal_title);
		CreateGirdView();
		RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params_cal.addRule(RelativeLayout.BELOW, caltitleLayoutID);

		mainLayout.addView(viewFlipper, params_cal);

		LinearLayout br = new LinearLayout(this);
		RelativeLayout.LayoutParams params_br = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, 1);
		params_br.addRule(RelativeLayout.BELOW, calLayoutID);
		br.setBackgroundColor(getResources().getColor(
				R.color.calendar_background));
		mainLayout.addView(br, params_br);
		return mainLayout;
	}

	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(this);
		LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.topMargin = 10;
		//params.bottomMargin=0;//////////////////////////////////////////////////////
		lay.setLayoutParams(params);
		lay.setOrientation(iOrientation);
		lay.setGravity(Gravity.LEFT);
		return lay;
	}

	private void generateTopButtons(LinearLayout layTopControls) {
		btnToday = new Button(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 20;
		//lp.bottomMargin=0;//////////////////////////////////////////////////////////////////
		btnToday.setLayoutParams(lp);
		//btnToday.setGravity(Gravity.BOTTOM);///////////////////////
		btnToday.setTextSize(25);
		btnToday.setBackgroundResource(Color.TRANSPARENT);//
		btnToday.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				setToDayViewItem();
			}
		});
		layTopControls.setGravity(Gravity.CENTER_HORIZONTAL);
		layTopControls.addView(btnToday);
	}

	private void setTitleGirdView() {
		title_gView = setGirdView();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		title_gView.setLayoutParams(params);
		title_gView.setVerticalSpacing(0);
		title_gView.setHorizontalSpacing(0);
		TitleGridAdapter titleAdapter = new TitleGridAdapter(this);
		title_gView.setAdapter(titleAdapter);
		title_gView.setId(caltitleLayoutID);
	}

	private void CreateGirdView() {
		Calendar tempSelected1 = Calendar.getInstance();
		Calendar tempSelected2 = Calendar.getInstance();
		Calendar tempSelected3 = Calendar.getInstance();
		tempSelected1.setTime(calStartDate.getTime());
		tempSelected2.setTime(calStartDate.getTime());
		tempSelected3.setTime(calStartDate.getTime());

		gView1 = new CalendarGridView(mContext);
		tempSelected1.add(Calendar.MONTH, -1);
		gAdapter1 = new CalendarGridViewAdapter(this, tempSelected1, Dates,
				count, iMonthViewCurrentYearF);
		gView1.setAdapter(gAdapter1);
		gView1.setId(calLayoutID);
		gView2 = new CalendarGridView(mContext);
		gAdapter = new CalendarGridViewAdapter(this, tempSelected2, Dates,
				count, iMonthViewCurrentYearF);
		gView2.setAdapter(gAdapter);
		gView2.setId(calLayoutID);

		gView3 = new CalendarGridView(mContext);
		tempSelected3.add(Calendar.MONTH, 1);
		gAdapter3 = new CalendarGridViewAdapter(this, tempSelected3, Dates,
				count, iMonthViewCurrentYearF);
		gView3.setAdapter(gAdapter3);
		gView3.setId(calLayoutID);

		gView2.setOnTouchListener(this);
		gView1.setOnTouchListener(this);
		gView3.setOnTouchListener(this);

		if (viewFlipper.getChildCount() != 0) {
			viewFlipper.removeAllViews();
		}

		viewFlipper.addView(gView2);
		viewFlipper.addView(gView3);
		viewFlipper.addView(gView1);

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);
		btnToday.setText(s);
	}

	private GridView setGirdView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		GridView gridView = new GridView(this);
		gridView.setLayoutParams(params);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setBackgroundColor(getResources().getColor(
				R.color.calendar_background));

		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int i = display.getWidth() / 7;
		int j = display.getWidth() - (i * 7);
		int x = j / 2;
		gridView.setPadding(x, 0, 0, 0);
		return gridView;
	}

	private void setPrevViewItem() {
		iMonthViewCurrentMonth--;
		if (iMonthViewCurrentMonth == -1) {
			iMonthViewCurrentMonth = 11;
			iMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
	}

	private void setToDayViewItem() {
		calSelected.setTimeInMillis(calToday.getTimeInMillis());
		calSelected.setFirstDayOfWeek(iFirstDayOfWeek);
		calStartDate.setTimeInMillis(calToday.getTimeInMillis());
		calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);

	}

	private void setNextViewItem() {
		iMonthViewCurrentMonth++;
		if (iMonthViewCurrentMonth == 12) {
			iMonthViewCurrentMonth = 0;
			iMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);

	}

	public void doPositiveClick(String strFragmentNumber) {
		Toast.makeText(getApplicationContext(),
				"Clicked OK! (" + strFragmentNumber + ")", Toast.LENGTH_SHORT)
				.show();
	}

	public static LayoutInflater from(Context context) {
		LayoutInflater LayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (LayoutInflater == null) {
			throw new AssertionError("LayoutInflater not found.");
		}
		return LayoutInflater;
	}

	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1);
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		iMonthViewCurrentYearF = iMonthViewCurrentYear;

		String s = calStartDate.get(Calendar.YEAR)
				+ "-"
				+ NumberHelper.LeftPad_Tow_Zero(calStartDate
						.get(Calendar.MONTH) + 1);
		btnToday.setText(s);

		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
	}

	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		return calStartDate;
	}

	// 标题适配器
	public class TitleGridAdapter extends BaseAdapter {

		int[] titles = new int[] { R.string.Sun, R.string.Mon, R.string.Tue,
				R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat };

		private Activity activity;

		// construct
		public TitleGridAdapter(Activity a) {
			activity = a;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout iv = new LinearLayout(activity);
			TextView txtDay = new TextView(activity);
			txtDay.setFocusable(false);
			txtDay.setBackgroundColor(Color.TRANSPARENT);
			iv.setOrientation(1);

			txtDay.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			int i = (Integer) getItem(position);
			txtDay.setTextColor(Color.WHITE);
			Resources res = getResources();

			if (i == R.string.Sat) {
				txtDay.setBackgroundColor(res.getColor(R.color.title_text_6));
			} else if (i == R.string.Sun) {
				txtDay.setBackgroundColor(res.getColor(R.color.title_text_7));
			} else {
			}
			txtDay.setText((Integer) getItem(position));
			iv.addView(txtDay, lp);
			return iv;
		}

	}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, 0, "查看评论");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			Intent it = new Intent().setClass(GuessWeatherActivity.this,
					CommentDetailActivity.class);
			startActivity(it);
			break;
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}
