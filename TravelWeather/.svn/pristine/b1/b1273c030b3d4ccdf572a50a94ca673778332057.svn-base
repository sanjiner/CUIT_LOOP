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
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PrePlaceDetailActivity extends BaseAct {

	private Context context = PrePlaceDetailActivity.this;

	private RadioGroup radioGroup;
	private static ProgressDialog pdialog;
	private TextView scenic_details_title;
	private TextView scenic_point_name;
	private TextView scenic_point_describe;
	private TextView scenic_point_address;
	private SharedPreferences sp;
	private String addr;
	private JSONObject jsonObject;

	private TextView scenic_details_say;

	private TextView scenic_jieshao;

	private TextView dizhi;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenic_details);
		sp = PrePlaceDetailActivity.this.getSharedPreferences("User_SP",
				MODE_PRIVATE);
			init();
	}

	private void init() {
		// TODO Auto-generated method stub
		addr = getIntent().getStringExtra("addr");
		radioGroup = (RadioGroup) findViewById(R.id.place_main_tabs);
		scenic_details_title = (TextView) findViewById(R.id.scenic_details_title);
		scenic_point_name = (TextView) findViewById(R.id.scenic_point_name);
		scenic_point_describe = (TextView) findViewById(R.id.scenic_point_describe);
		scenic_point_address = (TextView) findViewById(R.id.scenic_point_address);
		scenic_jieshao = (TextView) findViewById(R.id.scenic_jieshao);
		dizhi = (TextView) findViewById(R.id.dizhi);
		
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			scenic_details_title.setTextSize(10);
			scenic_point_name.setTextSize(10);
			scenic_point_describe.setTextSize(10);
			scenic_point_address.setTextSize(10);
			scenic_jieshao.setTextSize(10);
			dizhi.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			scenic_details_title.setTextSize(30);
			scenic_point_name.setTextSize(30);
			scenic_point_describe.setTextSize(30);
			scenic_point_address.setTextSize(30);
			scenic_jieshao.setTextSize(30);
			dizhi.setTextSize(30);
		}
		
		
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
		getData();
	}

	protected void getData() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载内容……");
		new AsyncTask<Void, Void, List<NameValuePair>>() {

			// doInBackground(Params…) 后台执行，比较耗时的操作都可以放在这里。
			// 注意这里不能直接操作UI。此方法在后台线程执行，完成任务的主要工作，
			// 通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected List<NameValuePair> doInBackground(Void... params) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				List<NameValuePair> param = new ArrayList<NameValuePair>();

				try {
					param.add(new BasicNameValuePair("scenicID", URLEncoder
							.encode(addr, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(PrePlaceDetailActivity.this,
						Constant.getscenicSingle, param);
				return null;
			}

			// onPostExecute(Result) 相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
			// 得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(List<NameValuePair> result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				try {
					scenic_details_title.setText(jsonObject
							.getString("scenicName"));
					scenic_point_name.setText(jsonObject
							.getString("scenicName"));
					scenic_point_describe.setText(jsonObject
							.getString("scenicDescribe"));
					scenic_point_address.setText(jsonObject
							.getString("scenicLocation"));
					if (jsonObject.getString("scenicName").equals("")) {
						Toast a = Toast.makeText(context, "数据不见了~.~!!数据去哪儿？",
								Toast.LENGTH_LONG);
						a.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}

		}.execute();
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
			Intent it = new Intent().setClass(PrePlaceDetailActivity.this,
					NewScenicActivity.class);
			startActivity(it);
			break;
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}

}
