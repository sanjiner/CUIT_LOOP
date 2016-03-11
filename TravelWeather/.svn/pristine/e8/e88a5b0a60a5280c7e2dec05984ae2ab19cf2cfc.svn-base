package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.activity.PictureCommentActivity.SpinnerSelectedListener;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.GPSInfoProvider;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PlaceAddShop extends BaseAct implements OnClickListener {
	protected static final int SHOW_PROCESS = 0;
	protected static final int DISMISS_PROCESS = 1;
	protected static int DEFAULE = 0;

	private EditText ed_shop_name;
	private EditText ed_shop_price;
	private EditText ed_shop_tuijian;
	private EditText ed_shop_comment;
	private EditText et_address;
	private TextView tv_position;
	private String province = null;
	private Spinner sp;
	private String name;
	private String price;
	private String tuijian;
	private String comment;
	private String TYPE = "";
	private static String latitude;
	private static String longtitude;
	private static ProgressDialog pdialog;

	JSONObject jsonObject;
	private SharedPreferences sps;
	private SharedPreferences sp1;

	public void tv_position(View v) {

		Intent intent = new Intent(getApplicationContext(),
				AreaChooseActivity.class);
		intent.putExtra("listType", AreaChooseActivity.LIST_TYPE_PROVINCE);
		intent.putExtra("backActivityCode", 999);
		this.startActivityForResult(intent, 999);

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_add_shop);
		inintdata();
		String[] m = { "吃", "住", "购" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new SpinnerSelectedListener());
		sp.setVisibility(View.VISIBLE);
		getlocationinfo();
		sps = this.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		TextView tv1 = (TextView)findViewById(R.id.tv_topbar_middle_detail);
		TextView tv2 = (TextView)findViewById(R.id.tv_topbar_right_map);
		TextView tv3 = (TextView)findViewById(R.id.tv_poidetail_itemtitle);
		TextView tv4 = (TextView)findViewById(R.id.tv_poidetail_price);
		TextView tv5 = (TextView)findViewById(R.id.tv_shop_name);
		TextView tv6 = (TextView)findViewById(R.id.tv_shop_tuijian);
		TextView tv7 = (TextView)findViewById(R.id.tv_shop_comment);
		TextView tv8 = (TextView)findViewById(R.id.tv_shop_comment1);
		
		sp1= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp1.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv2.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
			tv5.setTextSize(10);
			tv6.setTextSize(10);
			tv7.setTextSize(10);
			tv8.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
			tv5.setTextSize(30);
			tv6.setTextSize(30);
			tv7.setTextSize(30);
			tv8.setTextSize(30);
		}

	}

	private void getlocationinfo() {

		LocationManager alm = (LocationManager) PlaceAddShop.this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();// Criteria，可根据当前设备情况自动选择哪种location
											// provider
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 设置为最大精度
		// criteria.setAccuracy(Criteria.ACCURACY_COARSE); //获取大体的位置
		criteria.setAltitudeRequired(false);// 不要求海拔信息
		criteria.setBearingRequired(false);// 不要求方位信息
		criteria.setCostAllowed(true);// 是否允许付费 = = ！ 这个没搞懂为啥子要付费
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 对电量的要求
		alm.getBestProvider(criteria, true);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			GPSInfoProvider provider = GPSInfoProvider
					.getInstance(getApplicationContext());
			latitude = provider.getLocationlatitude();

			longtitude = provider.getLocationlongtitude();
		} else {
			/*
			 * GPSInfoProvider providers = GPSInfoProvider
			 * .getInstance(getApplicationContext());
			 */
			latitude = "";
			longtitude = "";

		}
		System.out.println("latitude                " + latitude);
		System.out.println("longtitude              " + longtitude);
	}

	private void inintdata() {
		ed_shop_comment = (EditText) findViewById(R.id.ed_shop_comment);
		ed_shop_name = (EditText) findViewById(R.id.ed_shop_name);
		ed_shop_price = (EditText) findViewById(R.id.ed_shop_price);
		ed_shop_tuijian = (EditText) findViewById(R.id.ed_shop_tuijian);
		sp = (Spinner) findViewById(R.id.spinner);
		tv_position = (TextView) findViewById(R.id.tv_position);
		et_address = (EditText) findViewById(R.id.et_address);
		
		



	}

	private static final int FOOD = 0;
	private static final int HOTEL = 1;
	private static final int SHOP = 2;

	public class SpinnerSelectedListener implements OnItemSelectedListener {

		String[] menuEnglish = { "FOOD", "HOTEL", "SHOP" };

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TYPE = menuEnglish[arg2];
			System.out.println("type =====" + TYPE);
			if (DEFAULE != 0) {
				switch (arg2) {
				case FOOD:
					chooselevel();

					break;
				case HOTEL:
					hotelchooselevel();
					break;
				case SHOP:
					shopchooselevel();
					break;
				}

			} else {
				DEFAULE = 1;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			TYPE = menuEnglish[0];
			System.out.println("type =====" + TYPE);
		}

	}

	// 上传店铺
	private void setComment() {

		comment = ed_shop_comment.getText().toString();
		name = ed_shop_name.getText().toString();
		price = ed_shop_price.getText().toString();
		tuijian = ed_shop_tuijian.getText().toString();
		if (TextUtils.isEmpty(name)) {
			Toast.makeText(getApplicationContext(), "店铺名称不能为空",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			pdialog = new ProgressDialog(PlaceAddShop.this);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setCancelable(false);
			pdialog.setMessage("正在添加……");

			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... param) {
					handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					if (TYPE.equals("FOOD")) {
						System.out.println("food");
						try {
							params.add(new BasicNameValuePair("foodHolderName",
									URLEncoder.encode(name, "UTF-8")));
							params.add(new BasicNameValuePair("foodPhoneNo",
									URLEncoder.encode("", "UTF-8")));
							// 后面再更改
							params.add(new BasicNameValuePair("foodType",
									URLEncoder.encode(TYPE, "UTF-8")));
							params.add(new BasicNameValuePair("foodDescribe",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("foodLevel",
									URLEncoder.encode("4", "UTF-8")));
							params.add(new BasicNameValuePair("lat", URLEncoder
									.encode(latitude, "UTF-8")));
							params.add(new BasicNameValuePair("lon", URLEncoder
									.encode(longtitude, "UTF-8")));
							params.add(new BasicNameValuePair("foodLocation",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("foodAddress",
									URLEncoder.encode(tv_position.getText()
											.toString().trim()
											+ et_address.getText().toString()
													.trim(), "UTF-8")));
							params.add(new BasicNameValuePair("foodMeanPrice",
									URLEncoder.encode(price, "UTF-8")));
							params.add(new BasicNameValuePair("foodRecommend",
									URLEncoder.encode(comment, "UTF-8")));
							params.add(new BasicNameValuePair("scenicID",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("isShow",
									URLEncoder.encode("yes", "UTF-8")));
							params.add(new BasicNameValuePair("remark",
									URLEncoder.encode(tuijian, "UTF-8")));

							jsonObject = MyHttpClient
									.getJson(PlaceAddShop.this,
											Constant.addFood, params);

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

					} else if (TYPE.equals("HOTEL")) {
						System.out.println("Hotel");
						try {
							params.add(new BasicNameValuePair("hotelName",
									URLEncoder.encode(name, "UTF-8")));
							params.add(new BasicNameValuePair("hotelType",
									URLEncoder.encode(TYPE, "UTF-8")));
							params.add(new BasicNameValuePair("hotelLocation",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("lat", URLEncoder
									.encode(latitude, "UTF-8")));
							params.add(new BasicNameValuePair("lon", URLEncoder
									.encode(longtitude, "UTF-8")));
							params.add(new BasicNameValuePair("hotelAddress",
									URLEncoder.encode(province, "UTF-8")));
							params.add(new BasicNameValuePair("hotelCommandNo",
									URLEncoder.encode(comment, "UTF-8")));
							params.add(new BasicNameValuePair("hotelDescribe",
									URLEncoder.encode(price, "UTF-8")));
							params.add(new BasicNameValuePair("hotelPhoneNo",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("hotelLevel",
									URLEncoder.encode("2", "UTF-8")));
							params.add(new BasicNameValuePair("scenicID",
									URLEncoder.encode("", "UTF-8")));

							jsonObject = MyHttpClient.getJson(
									PlaceAddShop.this, Constant.addHotel,
									params);

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

					} else if (TYPE.equals("SHOP")) {
						try {
							params.add(new BasicNameValuePair("shopName",
									URLEncoder.encode(name, "UTF-8")));
							params.add(new BasicNameValuePair("shopType",
									URLEncoder.encode(TYPE, "UTF-8")));
							params.add(new BasicNameValuePair("lat", URLEncoder
									.encode(latitude, "UTF-8")));
							params.add(new BasicNameValuePair("lon", URLEncoder
									.encode(longtitude, "UTF-8")));
							params.add(new BasicNameValuePair("shopLocation",
									URLEncoder.encode(province, "UTF-8")));
							params.add(new BasicNameValuePair("shopAddress",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("shopDescribe",
									URLEncoder.encode(tuijian, "UTF-8")));
							params.add(new BasicNameValuePair("shopPhoneNo",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("shopLevel",
									URLEncoder.encode("", "UTF-8")));
							params.add(new BasicNameValuePair("scenicID",
									URLEncoder.encode("", "UTF-8")));
							jsonObject = MyHttpClient
									.getJson(PlaceAddShop.this,
											Constant.addShop, params);

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
					int status;
					try {
						status = jsonObject.getInt("status");
						if (status == 1) {
							Toast.makeText(getApplicationContext(), "添加成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(getApplicationContext(), "添加失败",
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					super.onPostExecute(result);
				}

			}.execute();
		}
	}

	@Override
	public void onBackPressed() {
		DEFAULE = 0;
		finish();
		super.onBackPressed();
	}

	public void commit(View v) {

		setComment();

	}

	public void addbeakonclick(View v) {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 999) {
			if (data.getStringExtra("area") != null) {
				tv_position
						.setText(Split.cut_r_all(data.getStringExtra("area")));
				province = Split.cut_r1(data.getStringExtra("area"));
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 选择类型
	 */
	public void chooselevel() {
		final String[] level1 = new String[] { "川菜", "湘菜", "粤菜", "其他" };
		new AlertDialog.Builder(this).setTitle("选择类型")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(level1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {
						

					}
				}).show();
	}
	public void hotelchooselevel() {
		final String[] level1 = new String[] { "舒适型", "商务型", "其他" };
		new AlertDialog.Builder(this).setTitle("选择类型")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(level1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {

					}
				}).show();
	}
	public void shopchooselevel() {
		final String[] level1 = new String[] { "超级大卖场", "其他" };
		new AlertDialog.Builder(this).setTitle("选择类型")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(level1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {

					}
				}).show();
	}

}
