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
import cuit.travelweather.util.MyHttpClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AreaChooseActivity extends BaseAct {

	private ListView areaList;
	private SimpleAdapter adapter;
	private TextView tv_area_choose;

	public static final int LIST_TYPE_PROVINCE = 1; // 省列表
	public static final int LIST_TYPE_CITY = 2; // 市列表
	public static final int LIST_TYPE_COUNTY = 3; // 县列表

	private int listType;// 列表类型
	private static String area = "";// 为了跳转Activity时页面能保存 才定义为静态变量（权宜之计）
	private static String area_province = "";
	private static String area_city = "";
	private static String area_county = "";
	private int backActivityCode = 0;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		areaList = (ListView) findViewById(R.id.choose_lv);
		tv_area_choose = (TextView) findViewById(R.id.choose_tv_title);
		
		 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv_area_choose.setTextSize(10);
		  
		}else if(size.equals("2")){
		}else{
			tv_area_choose.setTextSize(30);
			  
		}
		
		

		areaList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				Intent intent = getIntent();
				String areaid = "";

				if (listType == LIST_TYPE_PROVINCE) {
					areaid = (String) map.get("areaid");
					area_province = (String) map.get("name");
					intent.putExtra("code", 0);
				} else if (listType == LIST_TYPE_CITY) {
					areaid = (String) map.get("areaid");
					area_city = (String) map.get("name");
					intent.putExtra("code", 0);
				} else if (listType == LIST_TYPE_COUNTY) {
					areaid = (String) map.get("areaid");
					area_county = (String) map.get("name");
					intent.putExtra("code", backActivityCode);
				}

				area = area_province + "," + area_city + "," + area_county;
				intent.putExtra("areaid", areaid);
				intent.putExtra("area", area);

				int code = intent.getIntExtra("code", 0);
				AreaChooseActivity.this.setResult(code, intent);
				if (code == 0 && listType != LIST_TYPE_COUNTY) {
					intent.putExtra("listType", ++listType); // 循环切换
					onResume();
				} else {
					AreaChooseActivity.this.finish();
				}
			}
		});
	}

	public void choose_return(View v) {
		Intent intent = getIntent();
		int code = intent.getIntExtra("code", 0);

		if (listType == LIST_TYPE_PROVINCE || listType == LIST_TYPE_COUNTY) {
			//code = backActivityCode;
			//AreaChooseActivity.this.setResult(code, intent);
			AreaChooseActivity.this.finish();
		} else {
			intent.putExtra("listType", --listType); // 循环切换
			onResume();

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AreaChooseAsyncTask AreachooseAtask = new AreaChooseAsyncTask();
		AreachooseAtask.execute();
	}

	@SuppressWarnings("unused")
	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		try {

			JSONObject jsonObject = MyHttpClient.getJson(this, method, params);

			if (jsonObject.getInt("status") == 1) {

				if (listType == LIST_TYPE_PROVINCE) {
					JSONArray jsonArray = jsonObject
							.getJSONArray("provinceList");
					HashMap<String, Object> map = null;
					JSONObject list;

					for (int i = 0; i < jsonArray.length(); i++) {

						map = new HashMap<String, Object>();
						list = jsonArray.getJSONObject(i);

						String areaid;
						String name;

						areaid = list.getString("areaid");
						name = list.getString("name");
						map.put("name", name);
						map.put("areaid", areaid);
						data.add(i, map);
					}
					adapter = new SimpleAdapter(this, data, R.layout.area_item,
							new String[] { "name" }, new int[] { R.id.area });
				} else if (listType == LIST_TYPE_CITY) {
					JSONArray jsonArray = jsonObject.getJSONArray("cityList");
					HashMap<String, Object> map = null;
					JSONObject list;

					for (int i = 0; i < jsonArray.length(); i++) {

						map = new HashMap<String, Object>();
						list = jsonArray.getJSONObject(i);

						String areaid;
						String name;

						areaid = list.getString("areaid");
						name = list.getString("name");
						map.put("name", name);
						map.put("areaid", areaid);
						data.add(i, map);
					}
					adapter = new SimpleAdapter(this, data, R.layout.area_item,
							new String[] { "name" }, new int[] { R.id.area });
				} else if (listType == LIST_TYPE_COUNTY) {
					JSONArray jsonArray = jsonObject.getJSONArray("countyList");
					HashMap<String, Object> map = null;
					JSONObject list;

					for (int i = 0; i < jsonArray.length(); i++) {

						map = new HashMap<String, Object>();
						list = jsonArray.getJSONObject(i);

						String areaid;
						String name;

						areaid = list.getString("areaid");
						name = list.getString("name");
						map.put("name", name);
						map.put("areaid", areaid);
						data.add(i, map);
					}
					adapter = new SimpleAdapter(this, data, R.layout.area_item,
							new String[] { "name" }, new int[] { R.id.area });
				}

			} else {
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("json里面没有指定的值");
		}

		return adapter;
	}

	@SuppressLint("NewApi")
    class AreaChooseAsyncTask extends
			AsyncTask<Void, Void, List<NameValuePair>> {

		@Override
		protected List<NameValuePair> doInBackground(Void... param) {
			// TODO Auto-generated method stub
			Intent intent = getIntent();
			backActivityCode = intent.getIntExtra("backActivityCode", 0);// 获取返回页面的code
																			// 300-添加城市，500-起点和终点地址
			listType = intent.getIntExtra("listType", 1);
			ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String parentID = "";

			try {
				if (listType == LIST_TYPE_PROVINCE) {
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("province", "UTF-8")));
					params.add(new BasicNameValuePair("parentID", URLEncoder
							.encode("0", "UTF-8")));
					adapter = getAdapter(data, Constant.findAdress, params);

				} else if (listType == LIST_TYPE_CITY) {
					parentID = intent.getStringExtra("areaid");
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("city", "UTF-8")));
					params.add(new BasicNameValuePair("parentID", URLEncoder
							.encode(parentID, "UTF-8")));
					adapter = getAdapter(data, Constant.findAdress, params);
				} else if (listType == LIST_TYPE_COUNTY) {
					parentID = intent.getStringExtra("areaid");
					params.add(new BasicNameValuePair("type", URLEncoder
							.encode("county", "UTF-8")));
					params.add(new BasicNameValuePair("parentID", URLEncoder
							.encode(parentID, "UTF-8")));
					adapter = getAdapter(data, Constant.findAdress, params);
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return params;
		}

		@Override
		protected void onPostExecute(List<NameValuePair> result) {
			// TODO Auto-generated method stub
			if (listType == LIST_TYPE_PROVINCE) {
				tv_area_choose.setText("地区选择-省");
			} else if (listType == LIST_TYPE_CITY) {
				tv_area_choose.setText(area_province);

			} else if (listType == LIST_TYPE_COUNTY) {
				tv_area_choose.setText(area_city);

			}
			areaList.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}
}
