package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;

public class TravelLineMakedActivity extends BaseAct {

	private ImageButton imageButton;
	private RadioGroup radioGroup;
	private ListView make_list;
	private Context context = TravelLineMakedActivity.this;
	private String routeid;

	private SimpleAdapter adapter;

	private String[] str;
	
	private JpushApplication application;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_line_maked);
		routeid = getIntent().getStringExtra("routeid");
		application = (JpushApplication) TravelLineMakedActivity.this.getApplication();
		init();
		MyFont.setTypeface(this, new int[] { R.id.travel_rb_list,
				R.id.travel_rb_map });
	}

	private void init() {
		imageButton = (ImageButton) findViewById(R.id.travel_ibtn_return);
		radioGroup = (RadioGroup) findViewById(R.id.travel_line_maked_tabs);
		make_list = (ListView) findViewById(R.id.make_list);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TravelLineMakedActivity.this.finish();
			}
		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.travel_rb_list:

					break;
				case R.id.travel_rb_map:
					Intent intent = new Intent();
					intent.setClass(context, TravelMapActivity.class);
					intent.putExtra("node_info", str);
					context.startActivity(intent);
					break;
				}
			}
		});

		getData();

	}

	public void back(View v) {
		this.finish();
//		super.onDestroy();
	}

	public void getData() {
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在更新路线……");
		new AsyncTask<Void, Void, List<NameValuePair>>() {

			@Override
			protected List<NameValuePair> doInBackground(Void... param) {
				// TODO Auto-generated method stub
				// Looper.prepare();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("routeID", URLEncoder
							.encode(routeid, "UTF-8")));
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("forecast", "UTF-8")));
					params.add(new BasicNameValuePair("route", URLEncoder
							.encode("123123", "UTF-8")));
					adapter = getAdapter(data, Constant.getRoutieWeather,
							params);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//getWeatherCurrent();
				return params;
			}

			@Override
			protected void onPostExecute(List<NameValuePair> result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				make_list.setAdapter(adapter);
				super.onPostExecute(result);
			}

		}.execute();
	}

	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		JSONObject jsonObject = MyHttpClient.getJson(this, method, params);

		try {
			JSONArray jsonArray = jsonObject.getJSONArray("nodes_info");
			HashMap<String, Object> map = null;
			str = new String[jsonArray.length() / 2];
			int j = 0;
			for (int i = 0; i < jsonArray.length(); i++) {
				map = new HashMap<String, Object>();
				map = new HashMap<String, Object>();
				JSONObject json = jsonArray.getJSONObject(i);
				map.put("location", Split.cut(json.getString("location")));
				i++;
				JSONObject json2 = jsonArray.getJSONObject(i);
				JSONArray jsonArray2 = json2.getJSONArray("content");
				map.put("day1_weather", jsonArray2.getJSONObject(0).getString("weather_day"));
				map.put("day1_tem", jsonArray2.getJSONObject(0).getString("temperature_night")+"℃~"+jsonArray2.getJSONObject(0).getString("temperature_day")+"℃");
				map.put("day2_weather", jsonArray2.getJSONObject(1).getString("weather_day"));
				map.put("day2_tem", jsonArray2.getJSONObject(1).getString("temperature_night")+"℃~"+jsonArray2.getJSONObject(1).getString("temperature_day")+"℃");
				map.put("day3_weather", jsonArray2.getJSONObject(2).getString("weather_day"));
				map.put("day3_tem", jsonArray2.getJSONObject(2).getString("temperature_night")+"℃~"+jsonArray2.getJSONObject(2).getString("temperature_day")+"℃");
				//data.add(map);
				data.add(j, map);
				// str[j] = new String();
				str[j] = json.getString("location");
				j++;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		application.setJson4travle_forcast(jsonObject);
		
		adapter = new SimpleAdapter(context, data, R.layout.travle_txt_itme,
				new String[] { "location", "day1_weather", "day2_weather", "day3_weather", "day1_tem","day2_tem","day3_tem" },
				new int[] { R.id.travle_tv_city, R.id.day1_weather, R.id.day2_weather,R.id.day3_weather,R.id.day1_tem, R.id.day2_tem, R.id.day3_tem, });

		return adapter;

	}
	public void getWeatherCurrent() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("routeID", URLEncoder
							.encode(routeid, "UTF-8")));
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("live", "UTF-8")));
					params.add(new BasicNameValuePair("route", URLEncoder
							.encode("123123", "UTF-8")));
					adapter = getAdapter(data, Constant.getRoutieWeather,
							params);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JSONObject json = new JSONObject();
				json = MyHttpClient.getJson(context, Constant.getRoutieWeather, params);
				application.setJson4travle_live(json);
				return null;
			}


		}.execute();
	}
}
