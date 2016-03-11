package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import cuit.travelweather.R;
import cuit.travelweather.spiner.widget.AbstractSpinerAdapter;
import cuit.travelweather.spiner.widget.CustemObject;
import cuit.travelweather.spiner.widget.CustemSpinerAdapter;
import cuit.travelweather.spiner.widget.SpinerPopWindow;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.GPSInfoProvider;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.view.DropDownListView;
import cuit.travelweather.view.DropDownListView.OnDropDownListener;

public class PlaceDetailActivity extends BaseAct implements OnClickListener,
		AbstractSpinerAdapter.IOnItemSelectListener {

	private LinkedList<String> listItems = null;
	private DropDownListView eatlistview = null;
	private DropDownListView hotellistview = null;
	private DropDownListView shoplistview = null;

	private ListView lv_main_books;// listView对象
	private ListView lv_hotel;
	private ListView lv_shop;
	private JSONObject jsonbject;
	private SimpleAdapter spadapter;
	private SimpleAdapter shopadapter;
	private SimpleAdapter hoteladapter;
	private RadioButton rb_eat;
	private RadioButton rb_hotel;
	private RadioButton rb_shop;
	private RadioButton rb_road;
	private RadioGroup rb_main;
	private int page = 9;
	private String method = Constant.getFoodList;
	private String info = "foodlist";
	private String listid;
	private TextView rbar;

	private String rbcheck;
	private String placeName;
	private String placeCity;
	private static final int SHOW_PROCESS = 1;
	private static String searchkey = "0";
	private static String searchvalue = "";
	private static String searchvalue2 = "";
	private static String orderkey = "0";
	private static String orderValue = "";

	private static String latitude;
	private static String longtitude;

	private String[] names = null;
	private String[] namess = null;
	private String[] namesss = null;
	private String[] hotelnames = null;
	private String[] hotelnamess = null;
	private String[] hotelnamesss = null;
	private String[] shopnames = null;
	private String[] shopnamess = null;
	private String[] shopnamesss = null;

	private static final int level = 3;
	private static final int DISMISS_PROCESS = 0;
	public static final int MORE_DATA_MAX_COUNT = 3;
	private static ProgressDialog pg_dialog;

	private TabHost tabHost;
	private TextView mTView;
	private TextView mTView2;
	private TextView mTView3;
	private TextView mTViews;
	private TextView mTView2s;
	private TextView mTView3s;
	private TextView mTViewss;
	private TextView mTView2ss;
	private TextView mTView3ss;
	private List<CustemObject> nameList1 = new ArrayList<CustemObject>();
	private List<CustemObject> nameList2 = new ArrayList<CustemObject>();
	private List<CustemObject> nameList3 = new ArrayList<CustemObject>();
	private AbstractSpinerAdapter<CustemObject> mAdapter;
	private AbstractSpinerAdapter<CustemObject> mAdapter2;
	private AbstractSpinerAdapter<CustemObject> mAdapter3;
	// ImageView tv_groupon;
	// 位置信息

	
	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROCESS:
				pg_dialog.show();
				break;
			case DISMISS_PROCESS:
				pg_dialog.dismiss();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_detail);

		init();
		setupViews();

		LocationManager alm = (LocationManager) PlaceDetailActivity.this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			GPSInfoProvider provider = GPSInfoProvider
					.getInstance(getApplicationContext());
			latitude = provider.getLocationlatitude();
			System.out.println("latitude44444sss"+latitude);
			
			longtitude = provider.getLocationlongtitude();
			System.out.println("latitude44444sss"+longtitude);
		} else {
			/*Toast.makeText(PlaceDetailActivity.this, "请手动打开GPS",
					Toast.LENGTH_SHORT).show();*/
		}

		MyFont.setTypeface(this, new int[] { R.id.place_radio_eat,
				R.id.place_radio_hotel, R.id.place_radio_road,
				R.id.place_radio_shop });

		eatlistview.setDivider(null);
		hotellistview.setDivider(null);
		shoplistview.setDivider(null);

	}

	private void setupViews() {
		mTView = (TextView) findViewById(R.id.tv_value);
		mTView2 = (TextView) findViewById(R.id.tv_value2);
		mTView3 = (TextView) findViewById(R.id.tv_value3);
		mTView.setOnClickListener(this);
		mTView2.setOnClickListener(this);
		mTView3.setOnClickListener(this);
		mTViews = (TextView) findViewById(R.id.tv_hotel_value);
		mTView2s = (TextView) findViewById(R.id.tv_hotel_value2);
		mTView3s = (TextView) findViewById(R.id.tv_hotel_value3);
		mTViews.setOnClickListener(this);
		mTView2s.setOnClickListener(this);
		mTView3s.setOnClickListener(this);
		mTViewss = (TextView) findViewById(R.id.tv_shop_value);
		mTView2ss = (TextView) findViewById(R.id.tv_shop_value2);
		mTView3ss = (TextView) findViewById(R.id.tv_shop_value3);
		mTViewss.setOnClickListener(this);
		mTView2ss.setOnClickListener(this);
		mTView3ss.setOnClickListener(this);
		initNameArray();

	}

	private void initNameArray() {

		if (info.equals("foodlist")) {
			names = getResources().getStringArray(R.array.distance);
			namess = getResources().getStringArray(R.array.dishes);
			namesss = getResources().getStringArray(R.array.fever);
			for (int i = 0; i < names.length; i++) {
				CustemObject object = new CustemObject();
				object.data = names[i];
				nameList1.add(object);
			}

			for (int i = 0; i < namess.length; i++) {
				CustemObject object2 = new CustemObject();
				object2.data = namess[i];
				nameList2.add(object2);
			}
			for (int i = 0; i < namesss.length; i++) {
				CustemObject object3 = new CustemObject();
				object3.data = namesss[i];
				nameList3.add(object3);    
			}

		} else if (info.equals("Hotellist")) {
			hotelnames = getResources().getStringArray(R.array.distance);
			hotelnamess = getResources().getStringArray(R.array.hotel);
			hotelnamesss = getResources().getStringArray(R.array.fever);
			for (int i = 0; i < hotelnames.length; i++) {
				CustemObject object = new CustemObject();
				object.data = hotelnames[i];
				nameList1.add(object);
			}

			for (int i = 0; i < hotelnamess.length; i++) {
				CustemObject object2 = new CustemObject();
				object2.data = hotelnamess[i];
				nameList2.add(object2);
			}
			for (int i = 0; i < hotelnamesss.length; i++) {
				CustemObject object3 = new CustemObject();
				object3.data = hotelnamesss[i];
				nameList3.add(object3);
			}
			System.out.println("运行2");
		} else if (info.equals("Shoplist")) {
			shopnames = getResources().getStringArray(R.array.distance);
			shopnamess = getResources().getStringArray(R.array.shop);
			shopnamesss = getResources().getStringArray(R.array.fever);

			for (int i = 0; i < shopnames.length; i++) {
				CustemObject object = new CustemObject();
				object.data = shopnames[i];
				nameList1.add(object);
			}

			for (int i = 0; i < shopnamess.length; i++) {
				CustemObject object2 = new CustemObject();
				object2.data = shopnamess[i];
				nameList2.add(object2);
			}
			for (int i = 0; i < shopnamesss.length; i++) {
				CustemObject object3 = new CustemObject();
				object3.data = shopnamesss[i];
				nameList3.add(object3);
			}
			System.out.println("运行3");
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_value:
			mAdapter = new CustemSpinerAdapter(this);
			mAdapter.refreshData(nameList1, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow();
			break;
		case R.id.tv_value2:
			mAdapter2 = new CustemSpinerAdapter(this);
			mAdapter2.refreshData(nameList2, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter2);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow2();
			break;
		case R.id.tv_value3:
			mAdapter3 = new CustemSpinerAdapter(this);
			mAdapter3.refreshData(nameList3, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter3);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow3();
			break;
		case R.id.tv_hotel_value:
			mAdapter = new CustemSpinerAdapter(this);
			mAdapter.refreshData(nameList1, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindows();
			break;
		case R.id.tv_hotel_value2:
			mAdapter2 = new CustemSpinerAdapter(this);
			mAdapter2.refreshData(nameList2, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter2);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow2s();
			break;
		case R.id.tv_hotel_value3:
			mAdapter3 = new CustemSpinerAdapter(this);
			mAdapter3.refreshData(nameList3, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter3);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow3s();
			break;
		case R.id.tv_shop_value:
			mAdapter = new CustemSpinerAdapter(this);
			mAdapter.refreshData(nameList1, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindowss();
			break;
		case R.id.tv_shop_value2:
			mAdapter2 = new CustemSpinerAdapter(this);
			mAdapter2.refreshData(nameList2, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter2);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow2ss();
			break;
		case R.id.tv_shop_value3:
			mAdapter3 = new CustemSpinerAdapter(this);
			mAdapter3.refreshData(nameList3, 0);
			mSpinerPopWindow = new SpinerPopWindow(this);
			mSpinerPopWindow.setAdatper(mAdapter3);
			mSpinerPopWindow.setItemListener(this);
			showSpinWindow3ss();
			break;

		}
	}

	private SpinerPopWindow mSpinerPopWindow;

	private void showSpinWindow() {
		Log.e("", "showSpinWindow1");
		mSpinerPopWindow.setWidth(mTView.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView);
	}

	private void showSpinWindow2() {
		Log.e("", "showSpinWindow2");
		mSpinerPopWindow.setWidth(mTView2.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView2);
	}

	private void showSpinWindow3() {
		Log.e("", "showSpinWindow2");
		mSpinerPopWindow.setWidth(mTView3.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView3);
	}

	private void showSpinWindows() {
		Log.e("", "showSpinWindow1s");
		mSpinerPopWindow.setWidth(mTViews.getWidth());
		mSpinerPopWindow.showAsDropDown(mTViews);
	}

	private void showSpinWindow2s() {
		Log.e("", "showSpinWindow2s");
		mSpinerPopWindow.setWidth(mTView2s.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView2s);
	}

	private void showSpinWindow3s() {
		Log.e("", "showSpinWindow3s");
		mSpinerPopWindow.setWidth(mTView3s.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView3s);
	}

	private void showSpinWindowss() {
		Log.e("", "showSpinWindow1ss");
		mSpinerPopWindow.setWidth(mTViewss.getWidth());
		mSpinerPopWindow.showAsDropDown(mTViewss);
	}

	private void showSpinWindow2ss() {
		Log.e("", "showSpinWindow2ss");
		mSpinerPopWindow.setWidth(mTView2ss.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView2ss);
	}

	private void showSpinWindow3ss() {
		Log.e("", "showSpinWindow3ss");
		mSpinerPopWindow.setWidth(mTView3ss.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView3ss);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) {
		View views = (View) parent;
		if (info.equals("foodlist")) {
			if (parent.getCount() == 4) {
				mTView.setText((CharSequence) parent.getItemAtPosition(pos));
				System.out.println("searchvalue>>>>>>>>>>>>>>>>>>>>>>>"+searchvalue);
					String constant =  (String) parent.getItemAtPosition(pos);
					searchkey = "4";
					if(constant.equals("1000米")){
						searchvalue = 1000+","+longtitude+","+latitude;
						getdata();
					}else if(constant.equals("500米")){
						searchvalue = 500+","+longtitude+","+latitude;
						getdata();
						System.out.println("22222222222222"+searchvalue);
					}else if(constant.equals("200米")){
						searchvalue = 200+","+longtitude+","+latitude;
						getdata();
					}else if(constant.equals("100米")){
						searchvalue = 100+","+longtitude+","+latitude;
						getdata();
					}
			} else if (parent.getCount() == 3) {
				mTView2.setText((CharSequence) parent.getItemAtPosition(pos));
				String constant =  (String) parent.getItemAtPosition(pos);
				searchkey="5";
				searchvalue = constant+"";
				getdata();
				
			} else {
				
				mTView3.setText((CharSequence) parent.getItemAtPosition(pos));
				searchkey = "3";
				searchvalue = pos + 1 + "";
				getdata();
			}
		} else if (info.equals("Hotellist")) {
			if (parent.getCount() == 4) {
				mTViews.setText((CharSequence) parent.getItemAtPosition(pos));
				String constant =  (String) parent.getItemAtPosition(pos);
				searchkey = "4";
				if(constant.equals("1000米")){
					searchvalue = 1000+","+longtitude+","+latitude;
					getdata();
					System.out.println("1111111111111111"+searchvalue);
				}else if(constant.equals("500米")){
					searchvalue = 500+","+longtitude+","+latitude;
					getdata();
					System.out.println("22222222222222"+searchvalue);
				}else if(constant.equals("200米")){
					searchvalue = 200+","+longtitude+","+latitude;
					getdata();
					System.out.println("333333333333333333333333333"+searchvalue);
				}else if(constant.equals("100米")){
					searchvalue = 100+","+longtitude+","+latitude;
					getdata();
					System.out.println("44444444444444444444444");
				}
			} else if (parent.getCount() == 3) {
				mTView2s.setText((CharSequence) parent.getItemAtPosition(pos));
				searchkey="5";
				searchvalue = (CharSequence) parent.getItemAtPosition(pos)+"";
				getdata();
			} else {
				mTView3s.setText((CharSequence) parent.getItemAtPosition(pos));
				searchkey = "3";
				searchvalue = pos + 1 + "";
				getdata();
			}
		} else {
			if (parent.getCount() == 4) {
				mTViewss.setText((CharSequence) parent.getItemAtPosition(pos));
				String constant =  (String) parent.getItemAtPosition(pos);
				searchkey = "4";
				if(constant.equals("1000米")){
					searchvalue = 1000+","+longtitude+","+latitude;
					getdata();
					System.out.println("1111111111111111"+searchvalue);
				}else if(constant.equals("500米")){
					searchvalue = 500+","+longtitude+","+latitude;
					getdata();
					System.out.println("22222222222222"+searchvalue);
				}else if(constant.equals("200米")){
					searchvalue = 200+","+longtitude+","+latitude;
					getdata();
					System.out.println("333333333333333333333333333"+searchvalue);
				}else if(constant.equals("100米")){
					searchvalue = 100+","+longtitude+","+latitude;
					getdata();
					System.out.println("44444444444444444444444");
				}
			} else if (parent.getCount() == 3) {
				mTView2ss.setText((CharSequence) parent.getItemAtPosition(pos));
				
				searchkey="5";
				searchvalue = (CharSequence) parent.getItemAtPosition(pos)+"";
				getdata();
			} else {
				mTView3ss.setText((CharSequence) parent.getItemAtPosition(pos));
				searchkey = "3";
				searchvalue = pos + 1 + "";
				getdata();
			}

		}

	}

	private void reinitdata() {
		searchkey = "0";
		searchvalue = "";
		searchvalue2 = "";
		orderkey = "0";
		orderValue = "0";
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		// 得到上一个界面传过来的值
		Intent intent = getIntent();
		rbcheck = intent.getStringExtra("tag");
		placeName = intent.getStringExtra("placeName");
		placeCity = intent.getStringExtra("placeCity");
		if (placeName.equals("")) {
			placeName = "武侯祠";
		}
		eatlistview = (DropDownListView) this.findViewById(R.id.lv_main_books);
		hotellistview = (DropDownListView) this.findViewById(R.id.lv_hotel);
		shoplistview = (DropDownListView) this.findViewById(R.id.lv_shop);
		rb_eat = (RadioButton) findViewById(R.id.place_radio_eat);
		rb_hotel = (RadioButton) findViewById(R.id.place_radio_hotel);
		rb_shop = (RadioButton) findViewById(R.id.place_radio_shop);
		rb_main = (RadioGroup) findViewById(R.id.place_main_tabs);
		rbar = (TextView) findViewById(R.id.content_rating);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("eat").setIndicator("eat")
				.setContent(R.id.place_page_eat));
		tabHost.addTab(tabHost.newTabSpec("hotel").setIndicator("hotel")
				.setContent(R.id.place_page_hotel));
		tabHost.addTab(tabHost.newTabSpec("shop").setIndicator("shop")
				.setContent(R.id.place_page_shop));

		if (rbcheck.equals("eat")) {
			tabHost.setCurrentTabByTag("eat");
			info = "foodlist";
			getdata();
		} else if (rbcheck.equals("hotel")) {
			tabHost.setCurrentTabByTag("hotel");
			rb_hotel.setChecked(true);
			method = Constant.getHotelList;
			info = "Hotellist";
			page = 9;
			getdata();
		} else if (rbcheck.endsWith("shop")) {
			tabHost.setCurrentTabByTag("shop");
			rb_shop.setChecked(true);
			method = Constant.getShopsList;
			info = "Shoplist";
			page = 9;
			getdata();
		} else if (rbcheck.endsWith("road")) {
			Intent it = new Intent().setClass(PlaceDetailActivity.this,
					PlaceRoadActivity.class);
			it.putExtra("placeName", placeName);
			it.putExtra("placeCity", placeCity);
			startActivity(it);
		}

		rb_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.place_radio_eat:
					tabHost.setCurrentTabByTag("eat");
					page = 9;
					method = Constant.getFoodList;
					info = "foodlist";
					reinitdata();
					eatlistview.setVisibility(View.VISIBLE);
					shoplistview.setVisibility(View.INVISIBLE);
					hotellistview.setVisibility(View.INVISIBLE);
					nameList1.clear();
					nameList2.clear();
					nameList3.clear();
					initNameArray();
					getdata();
					break;
				case R.id.place_radio_hotel:
					page = 9;
					tabHost.setCurrentTabByTag("hotel");
					method = Constant.getHotelList;
					info = "Hotellist";
					nameList1.clear();
					nameList2.clear();
					nameList3.clear();
					initNameArray();
					reinitdata();
					eatlistview.setVisibility(View.INVISIBLE);
					shoplistview.setVisibility(View.INVISIBLE);
					hotellistview.setVisibility(View.VISIBLE);
					getdata();
					break;
				case R.id.place_radio_shop:
					eatlistview.setVisibility(View.INVISIBLE);
					shoplistview.setVisibility(View.VISIBLE);
					hotellistview.setVisibility(View.INVISIBLE);
					tabHost.setCurrentTabByTag("shop");
					page = 9;
					info = "Shoplist";
					nameList1.clear();
					nameList2.clear();
					nameList3.clear();
					initNameArray();
				
					reinitdata();
					method = Constant.getShopsList;

					getdata();
					break;
				case R.id.place_radio_road:
					Intent it = new Intent().setClass(PlaceDetailActivity.this,
							PlaceRoadActivity.class);
					it.putExtra("placeName", placeName);
					it.putExtra("placeCity", placeCity);
					startActivity(it);
				default:
					break;
				}
			}
		});

		eatlistview.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				page = page + 5;
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd HH:mm:ss");
				eatlistview.onDropDownComplete(getString(R.string.update_at)
						+ dateFormat.format(new Date()));
				getdata();
				eatlistview.onDropDownComplete();
			}
		});

		eatlistview.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = page + 5;
				getdata();
				eatlistview.onBottomComplete();

			}
		});

		listItems = new LinkedList<String>();
		eatlistview.setAdapter(spadapter);
		eatlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);

				listid = (String) map.get("id");
				Intent intent = new Intent();
				intent.setClass(PlaceDetailActivity.this, PlaceDetailInfo.class);
				intent.putExtra("id", listid);
				intent.putExtra("info", info);
				startActivity(intent);
			}

		});

		hotellistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				listid = (String) map.get("id");
				Intent intent = new Intent();
				intent.setClass(PlaceDetailActivity.this, PlaceDetailInfo.class);
				intent.putExtra("id", listid);
				intent.putExtra("info", info);
				startActivity(intent);

			}

		});

		hotellistview.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd HH:mm:ss");
				hotellistview.onDropDownComplete(getString(R.string.update_at)
						+ dateFormat.format(new Date()));
				page = page + 5;
				getdata();
				hotellistview.onDropDownComplete();
			}
		});
		hotellistview.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = page + 5;
				getdata();
				hotellistview.onBottomComplete();
			}
		});
		listItems = new LinkedList<String>();
		hotellistview.setAdapter(hoteladapter);

		shoplistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				listid = (String) map.get("id");
				Intent intent = new Intent();
				intent.setClass(PlaceDetailActivity.this, PlaceDetailInfo.class);
				intent.putExtra("id", listid);
				intent.putExtra("info", info);
				startActivity(intent);
			}

		});

		shoplistview.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				page = page + 5;
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd HH:mm:ss");
				shoplistview.onDropDownComplete(getString(R.string.update_at)
						+ dateFormat.format(new Date()));
				getdata();
				shoplistview.onDropDownComplete();
			}
		});

		shoplistview.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = page + 5;
				getdata();
				shoplistview.onBottomComplete();
			}
		});
		listItems = new LinkedList<String>();
		shoplistview.setAdapter(shopadapter);
	}

	private void getdata() {
		pg_dialog = new ProgressDialog(PlaceDetailActivity.this);
		pg_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pg_dialog.setMessage("正在加载");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("pageNo", URLEncoder
							.encode("1", "UTF-8")));
					params.add(new BasicNameValuePair("pageSize", URLEncoder
							.encode("" + page, "UTF-8")));
					params.add(new BasicNameValuePair("searchKey", URLEncoder
							.encode(searchkey, "UTF-8")));
					params.add(new BasicNameValuePair("searchValue", URLEncoder
							.encode(searchvalue, "UTF-8")));
					params.add(new BasicNameValuePair("searchValue2", URLEncoder
							.encode(searchvalue2, "UTF-8")));
					params.add(new BasicNameValuePair("orderKey", URLEncoder
							.encode("0", "UTF-8")));
					params.add(new BasicNameValuePair("orderValue", URLEncoder
							.encode("0", "UTF-8")));

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();

				}
				spadapter = getAdapter(data, method, params);
				System.out.println("spadapter>>>>>>>>>>" + data + method
						+ params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				switch (rb_main.getCheckedRadioButtonId()) {
				case R.id.place_radio_eat:
					eatlistview.setAdapter(spadapter);
					break;
				case R.id.place_radio_hotel:
					hotellistview.setAdapter(hoteladapter);
					break;
				case R.id.place_radio_shop:
					shoplistview.setAdapter(shopadapter);
					break;
				default:
					break;
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	public SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {
		JSONObject jsonobject = MyHttpClient.getJson(PlaceDetailActivity.this,
				method, params);
		try {
			HashMap<String, Object> map = null;
			switch (rb_main.getCheckedRadioButtonId()) {
			case R.id.place_radio_eat:
				JSONArray jsonarry = jsonobject.getJSONArray("foodlist");
				// System.out.println("8888888888888888888883333"+jsonarry.opt(level));
				for (int i = 0; i < jsonarry.length(); i++) {
					map = new HashMap<String, Object>();
					JSONObject json = jsonarry.getJSONObject(i);
					map.put("id", json.getString("foodId"));
					map.put("HolderName", json.getString("foodHolderName"));
					map.put("MeanPrice", json.getString("foodMeanPrice"));
					map.put("foodDescribe", json.getString("foodRecommend"));
					map.put("Type", json.getString("foodType"));
					map.put("foodLevel", json.getString("foodLevel"));
					data.add(i, map);
					spadapter = new SimpleAdapter(PlaceDetailActivity.this,
							data, R.layout.place_eat_detail, new String[] {
									"Type", "HolderName", "MeanPrice",
									"foodDescribe", "foodLevel" }, new int[] {
									R.id.tv_eat_type, R.id.tv_eat_name,
									R.id.tv_eat_price, R.id.tv_eat_reason,
									R.id.content_rating });

				}

				break;
			case R.id.place_radio_hotel:
				JSONArray jsonarryhotel = jsonobject.getJSONArray("Hotellist");
				for (int i = 0; i < jsonarryhotel.length(); i++) {
					map = new HashMap<String, Object>();
					JSONObject json2 = jsonarryhotel.getJSONObject(i);
					map.put("id", json2.getString("hotelId"));
					map.put("HolderName", json2.getString("hotelName"));
					map.put("MeanPrice", json2.getString("hotelLevel"));
					map.put("foodDescribe", json2.getString("hotelDescribe"));
					map.put("Type", json2.getString("hotelType"));
					map.put("hotelLevel", json2.getString("hotelLevel"));
					data.add(i, map);
					hoteladapter = new SimpleAdapter(PlaceDetailActivity.this,
							data, R.layout.place_hotel_detal, new String[] {
									"Type", "HolderName", "MeanPrice",
									"foodDescribe", "hotelLevel" }, new int[] {
									R.id.tv_hotel_type, R.id.tv_hotel_name,
									R.id.tv_hotel_price, R.id.tv_hotel_reason,
									R.id.content_rating });

				}
				break;
			case R.id.place_radio_shop:
				JSONArray jsonArrayshop = jsonobject.getJSONArray("Shopslist");
				for (int i = 0; i < jsonArrayshop.length(); i++) {
					map = new HashMap<String, Object>();
					JSONObject json = jsonArrayshop.getJSONObject(i);
					map.put("id", json.getString("shopId"));
					map.put("HolderName", json.getString("shopName"));
					map.put("MeanPrice", json.getString("shopPhoneNo"));
					map.put("foodDescribe", json.getString("shopDescribe"));
					map.put("Type", json.getString("shopType"));
					map.put("shopLevel", json.getString("shopLevel"));
					data.add(i, map);
					shopadapter = new SimpleAdapter(PlaceDetailActivity.this,
							data, R.layout.place_shop_detail, new String[] {
									"Type", "HolderName", "MeanPrice",
									"foodDescribe", "shopLevel" }, new int[] {
									R.id.tv_shop_type, R.id.tv_shop_name,
									R.id.tv_shop_price, R.id.tv_shop_reason,
									R.id.content_rating });
				}
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return spadapter;
	}
	
	

	public Filter getFilter() {

		return null;
	}
	

	/**
	 * 设置菜单
	 * 
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.place_addship, menu);
		return super.onCreateOptionsMenu(menu);
	}
    
	
  	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
  		Intent intent = new Intent(PlaceDetailActivity.this, PlaceAddShop.class);
		intent.putExtra("info", info);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}

 
	@Override
	public void onDestroy() {

		super.onDestroy();
	}
  	
  	
  	

}
