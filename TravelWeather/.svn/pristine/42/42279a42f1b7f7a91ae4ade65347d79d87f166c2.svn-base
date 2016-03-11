package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class Keyword extends BaseAct{
	private static ProgressDialog pdialog;
	private TextView scenic_details_title;
	private TextView scenic_point_name;
	private TextView scenic_point_describe;
	private TextView scenic_point_address;
	private JSONObject jsonObject;
	private String key;
	private Context context = Keyword.this;
	private RadioGroup radioGroup;
	private SharedPreferences sp;
	
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.scenic_details);
		super.onCreate(savedInstanceState);
		radioGroup = (RadioGroup) findViewById(R.id.place_main_tabs);
		scenic_details_title = (TextView) findViewById(R.id.scenic_details_title);
		scenic_point_name = (TextView) findViewById(R.id.scenic_point_name);
		scenic_point_describe = (TextView) findViewById(R.id.scenic_point_describe);
		scenic_point_address = (TextView) findViewById(R.id.scenic_point_address);
		Bundle extras = this.getIntent().getExtras();
		key = extras.getString("key");
		getDate();
		
		
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			scenic_details_title.setTextSize(10);
			scenic_point_name.setTextSize(10);
			scenic_point_describe.setTextSize(10);
			scenic_point_address.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			scenic_details_title.setTextSize(30);
			scenic_point_name.setTextSize(30);
			scenic_point_describe.setTextSize(30);
			scenic_point_address.setTextSize(30);
		}

//	if(Constant.flag==1){
		
	
	radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			switch (checkedId) {
			case R.id.scenic_point_food_text:
				Intent intent1 = new Intent();
				intent1.setClass(context, PlaceDetailActivity.class);
				intent1.putExtra("tag", "eat");
				intent1.putExtra("placeName", scenic_point_name.getText()
						.toString().trim());
				intent1.putExtra("placeCity", scenic_point_address
						.getText().toString().trim());
				startActivity(intent1);
				break;
			case R.id.scenic_point_hotel_text:
				Intent intent = new Intent();
				intent.setClass(context, PlaceDetailActivity.class);
				intent.putExtra("placeName", scenic_point_name.getText()
						.toString().trim());
				intent.putExtra("tag", "hotel");
				intent.putExtra("placeCity", scenic_point_address.getText()
						.toString().trim());
				startActivity(intent);
				break;
			case R.id.scenic_point_traffic_text:
				Intent intent2 = new Intent();
				intent2.setClass(context, PlaceDetailActivity.class);
				intent2.putExtra("placeName", scenic_point_name.getText()
						.toString().trim());
				intent2.putExtra("tag", "road");
				intent2.putExtra("placeCity", scenic_point_address
						.getText().toString().trim());
				startActivity(intent2);
				break;
			case R.id.scenic_point_shopping_text:
				Intent intent3 = new Intent();
				intent3.setClass(context, PlaceDetailActivity.class);
				intent3.putExtra("placeName", scenic_point_name.getText()
						.toString().trim());
				intent3.putExtra("placeCity", scenic_point_address
						.getText().toString().trim());
				intent3.putExtra("tag", "shop");
				startActivity(intent3);
				break;

			}
		}
	});
	}
//	}
	public void getDate(){
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> param1 = new ArrayList<NameValuePair>();
				try {
					param1.add(new BasicNameValuePair("searchValue", URLEncoder
							.encode(key, "UTF-8")));
					param1.add(new BasicNameValuePair("searchKey", URLEncoder
							.encode("4", "UTF-8")));
					param1.add(new BasicNameValuePair("pageSize", URLEncoder
							.encode("1", "UTF-8")));
					param1.add(new BasicNameValuePair("pageNo", URLEncoder
							.encode("1", "UTF-8")));
					param1.add(new BasicNameValuePair("orderKey", URLEncoder
							.encode("0", "UTF-8")));
					param1.add(new BasicNameValuePair("orderValue", URLEncoder
							.encode("0", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonObject = MyHttpClient.getJson(Keyword.this,Constant.getsceniclist, param1);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				String abc ="0";
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("sceniclist");
					for (int i = 0; i < 1; i++) {
						JSONObject json = jsonArray.getJSONObject(i);
						abc = json.getString("scenicName");
						if(abc.equals("")){
							Constant.flag=2;
						}
						scenic_details_title.setText(json.getString("scenicName"));
						scenic_details_title.setText(abc);
						scenic_point_describe.setText(json.getString("scenicDescribe"));
						scenic_point_address.setText(json.getString("scenicLocation"));
						if (abc.equals("")) {
							Toast a = Toast.makeText(Keyword.this, "暂时还没有数据哦",
									Toast.LENGTH_LONG);
							a.show();
							Constant.flag=2;
							
						}
						else
						{
							Constant.flag=1;
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					System.out.println("sssssssssssssssss"+e.getMessage());
				}
				super.onPostExecute(result);
			}

		}.execute();
		
	}
}
