package cuit.travelweather.fragment;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.activity.NewScenicActivity;
import cuit.travelweather.activity.PrePlaceDetailActivity;
import cuit.travelweather.util.MyHttpClient;

public class PlaceList extends Activity{

	private SimpleAdapter adapter;

	private JSONObject jsonObject;

	private ListView list;

	private TextView tv;

	private String cityname;

	private String cityid;
	private static ProgressDialog pdialog;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placelist);
		if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
		    ListView placelistRelative = (ListView) findViewById(R.id.palce_list);
			placelistRelative.setBackgroundResource(R.drawable.login_bg_qhc);
			RelativeLayout placeDetailTitleRelative = (RelativeLayout) findViewById(R.id.rl_place_detail_title);
			placeDetailTitleRelative.setBackgroundResource(R.drawable.weather_top_bg_qhc);
			TextView textView = (TextView) findViewById(R.id.city_tv);
			textView.setTextColor(this.getResources().getColor(R.color.theme_bule));
		}
		getData();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.place_city_list);//////////////////////////////////////////////////////////////////////////////////////////
	    cityid = getIntent().getStringExtra("cityId");//city->provinceName
		getData();
		list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				Intent intent = new Intent();
				intent.setClass(PlaceList.this,
						PrePlaceDetailActivity.class);//PrePlaceDetailActivity->PlaceList
				intent.putExtra("addr", (String) map.get("sid"));  ///scenicId->sid->cityID///////传值到下一个页面/////////////////////////////////////////////////////////////////////////////////////////////////////
				PlaceList.this.startActivity(intent);
			}
		});

	}
	
	
	private void getData() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(PlaceList.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载数据中");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("seller",
							URLEncoder.encode("", "UTF-8"))); 
					params.add(new BasicNameValuePair("title",
							URLEncoder.encode("", "UTF-8"))); 
					params.add(new BasicNameValuePair("cityId",
							URLEncoder.encode(cityid, "UTF-8"))); /////////////////////////////////////////////、、、、、、
					params.add(new BasicNameValuePair("price",
							URLEncoder.encode("", "UTF-8"))); 
					params.add(new BasicNameValuePair("grade",
							URLEncoder.encode("", "UTF-8"))); 
					params.add(new BasicNameValuePair("sort",
							URLEncoder.encode("comm_asc", "UTF-8"))); 
					params.add(new BasicNameValuePair("skip",
							URLEncoder.encode("50", "UTF-8"))); 
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter = getAdapter(data, "/attractions/GetScenicListFromJH", params);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				list.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute();
	}

	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		jsonObject = MyHttpClient.getJson(this, method, params);
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("sceneryList");/////scenicList////////////////////////////////////////////////////////////////////
	
			HashMap<String, Object> map = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				map = new HashMap<String, Object>();
				JSONObject json = jsonArray.getJSONObject(i);
		
				//从服务器得到返回值
				String error_code = json.getString("error_code");
				String total = json.getString("total");
				String limit = json.getString("limit");
				String skip = json.getString("skip");
				String seller = json.getString("seller");		
				String title = json.getString("title");
				String grade = json.getString("grade");
				String price_min = json.getString("price_min");
				String comm_cnt = json.getString("comm_cnt");
				String cityId = json.getString("cityId");		
				String address = json.getString("address");		
				String sid = json.getString("sid");
				String url = json.getString("url");
				String imgurl = json.getString("imgurl");
				
				map.put("error_code", error_code);
				map.put("total", total);
				map.put("limit", limit);
				map.put("skip", skip);
				map.put("seller", seller);
				map.put("title", title);
				map.put("grade", grade);
				map.put("price_min", price_min);
				map.put("comm_cnt", comm_cnt);
				map.put("cityId", cityId);
				map.put("address", address);
				map.put("sid", sid);
				map.put("url", url);
				map.put("imgurl", imgurl);
				
						
				data.add(i, map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new SimpleAdapter(PlaceList.this, data,
				R.layout.place_list_itme, new String[] { "title" },
				new int[] { R.id.place_list_tv });///////////////////////scenicName->title////////////////////////////////////////////////////////////////////////////

		return adapter;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, 0, "新增景点");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent it = new Intent().setClass(PlaceList.this,
					NewScenicActivity.class);
			startActivity(it);
			break;
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}

}
