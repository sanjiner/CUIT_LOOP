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
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlacelistActivity extends BaseAct {

	private ListView list;
	private TextView tv;
	private String city;

	private JSONObject jsonObject;
	private SimpleAdapter adapter;

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
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placelist);
		MyFont.setTypeface(PlacelistActivity.this, new int[] { R.id.city_tv });
		sp = PlacelistActivity.this.getSharedPreferences("User_SP",
				MODE_PRIVATE);
		if (sp.getString("userName", "").equals("")) {
			init();
		} else {
			init();
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.place_city_list);
		tv = (TextView) findViewById(R.id.city_tv);
		
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv.setTextSize(30);
		}

		
		tv.setText(getIntent().getStringExtra("proName") + "热门景区");
		city = getIntent().getStringExtra("proName");
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
				intent.setClass(PlacelistActivity.this,
						PrePlaceDetailActivity.class);
				intent.putExtra("addr", (String) map.get("scenicId"));
				PlacelistActivity.this.startActivity(intent);
			}
		});

	}
	
	public void add(View v) {
		Intent it = new Intent().setClass(PlacelistActivity.this,
				NewScenicActivity.class);
		startActivity(it);
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(PlacelistActivity.this);
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
					params.add(new BasicNameValuePair("pageNo", URLEncoder
							.encode("1", "UTF-8")));
					params.add(new BasicNameValuePair("pageSize", URLEncoder
							.encode("20", "UTF-8")));
					params.add(new BasicNameValuePair("provinceName",
							URLEncoder.encode(city, "UTF-8")));

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter = getAdapter(data, "/attractions/GetScenicNum", params);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub 
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				list.setAdapter(adapter);
				String temp = "0";
				try {
					temp = jsonObject.getString("scenicNum");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (temp.equals("0")) {
					Toast.makeText(PlacelistActivity.this, "此省还没得还没有热门景点",
							Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		jsonObject = MyHttpClient.getJson(this, method, params);
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("scenicList");
			HashMap<String, Object> map = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				map = new HashMap<String, Object>();
				JSONObject json = jsonArray.getJSONObject(i);
				String scenicId = json.getString("scenicId");
				String scenicName = json.getString("scenicName");
				String scenicLevel = json.getString("scenicLevel");
				String scenicLocation = json.getString("scenicLocation");
				String remark = json.getString("remark");
				map.put("scenicId", scenicId);
				map.put("scenicName", scenicName);
				map.put("scenicLevel", scenicLevel);
				map.put("scenicLocation", scenicLocation);
				map.put("remark", remark);
				data.add(i, map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new SimpleAdapter(PlacelistActivity.this, data,
				R.layout.place_list_itme, new String[] { "scenicName" },
				new int[] { R.id.place_list_tv });

		return adapter;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// if (pictureFragment.isVisable) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, 0, "新增景点");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent it = new Intent().setClass(PlacelistActivity.this,
					NewScenicActivity.class);
			startActivity(it);
			break;
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}

}
