package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.ExampleUtil;
import cuit.travelweather.util.HanziToPinyin;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MoreInfocenterActivity extends BaseAct {

	private int layout;
	private int mDay;
	private int mMonth;
	private int mYear;
	private int mDay1;
	private int mMonth1;
	private int mYear1;
	private int status;
	private int flag;
	private TextView tv_addcity;
	private TextView tv_push_start_time;
	private TextView tv_end;
	private String cityName;
	private SharedPreferences sp;
	private Editor editor;
	private String cityID1;// 暂时方案
	private RelativeLayout rl_push_list;
	private RelativeLayout push_add;
	private ListView city_list;
	private ListView push_list;
	private ListView coin_list;
	private SimpleAdapter adapter;
	private JSONObject jsonObject;
	private Context context = MoreInfocenterActivity.this;
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

	// 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID：
	static final int DATE_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID1 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = getIntent().getIntExtra("LAYOUT", R.layout.start);
		setContentView(layout);
		if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
			RelativeLayout city_r_1 = (RelativeLayout) findViewById(R.id.city_r_1);
			city_r_1.setBackgroundResource(R.drawable.weather_top_bg_qhc);
			
			ImageButton infocenter_backimagebtn = (ImageButton) findViewById(R.id.infocenter_backimagebtn);
			infocenter_backimagebtn.setBackgroundResource(R.drawable.back_map_normal_qhc);
			
			TextView infocenter_city_tv = (TextView) findViewById(R.id.infocenter_city_tv);
			infocenter_city_tv.setTextColor(this.getResources().getColor(R.color.theme_bule));
			
			ImageButton infocenter_refreshimagebtn = (ImageButton) findViewById(R.id.infocenter_refreshimagebtn);
			infocenter_refreshimagebtn.setBackgroundResource(R.drawable.refresh_map_top_normal_qhc);
		}
		init();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (flag == 123) {
				finish();
			} else {
				Intent intent = new Intent(context, MoreActivity.class);
				intent.putExtra("LAYOUT", R.layout.more_infocenter);
				startActivity(intent);
				MoreInfocenterActivity.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void init() {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		editor = sp.edit();
		tv_push_start_time = (TextView) findViewById(R.id.tv_start);
		tv_end = (TextView) findViewById(R.id.tv_end);
		tv_addcity = (TextView) findViewById(R.id.tv_addcity);
		city_list = (ListView) findViewById(R.id.city_list);
		push_list = (ListView) findViewById(R.id.push_list);
		rl_push_list = (RelativeLayout) findViewById(R.id.rl_push_list);
		push_add = (RelativeLayout) findViewById(R.id.push_add);

		final Calendar currentDate = Calendar.getInstance();

		switch (layout) {
		case R.layout.infocenter_push:
			mDay = currentDate.get(Calendar.DAY_OF_MONTH);
			mMonth = currentDate.get(Calendar.MONTH);
			mYear = currentDate.get(Calendar.YEAR);
			tv_push_start_time.setText(new StringBuilder().append(mYear)
					.append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日"));
			tv_end.setText(new StringBuilder().append(mYear).append("年")
					.append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日"));
			getPushListDate();
			push_list.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					cityName = (String) map.get("tags");

					return false;
				}
			});

			push_list
					.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

						@Override
						public void onCreateContextMenu(ContextMenu menu,
								View v, ContextMenuInfo menuInfo) {
							// TODO Auto-generated method stub
							menu.setHeaderTitle("是否要删除？");
							menu.add(0, 2, 0, "是");
							menu.add(0, 3, 0, "否");
						}
					});
			break;
		case R.layout.infocenter_city_management:
			getCityListDate();
			city_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					String cityID = (String) map.get("cityID");
					Constant.city = (String)map.get("city");
					editor.putString("currentCity", cityID);
					editor.commit();
					Intent intent = new Intent();
					intent.setClass(context, MainActivity.class);
					startActivity(intent);
					MoreInfocenterActivity.this.finish();
				}
			});

			city_list.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					cityID1 = (String) map.get("cityID");

					return false;
				}
			});
			// 添加长按点击
			city_list
					.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

						@Override
						public void onCreateContextMenu(ContextMenu menu,
								View v, ContextMenuInfo menuInfo) {
							// TODO Auto-generated method stub
							menu.setHeaderTitle("是否要删除？");
							menu.add(0, 0, 0, "是");
							menu.add(0, 1, 0, "否");

						}
					});
			flag = getIntent().getIntExtra("flag", 0);

			break;
		case R.layout.infocenter_coin_rank :
			coin_list = (ListView) findViewById(R.id.coin_list);
			getCoinList();
			break;
		default:
			break;
		}

	}

	
//积分排行
	private void getCoinList() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(MoreInfocenterActivity.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(true);
		pdialog.setMessage("正在获取路况信息……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				
				try {
					params.add(new BasicNameValuePair(" ", URLEncoder
							.encode(" ", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonObject = MyHttpClient.getJson(MoreInfocenterActivity.this, Constant.coinList, params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				SimpleAdapter adapter;
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("coinList");
					int j;
					for (int i =0;i<10;i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						JSONObject jo = jsonArray.getJSONObject(i);
						j= i+1;
						map.put("rank", ""+j+".");
						map.put("rank_name", jo.get("userName"));
						map.put("rank_coin", jo.get("userCoin"));
						data.add(map);
					}
					adapter = new SimpleAdapter(MoreInfocenterActivity.this, data, R.layout.infocenter_coin_item, new String[] {"rank","rank_name","rank_coin"}, new int[]{R.id.rank,R.id.rank_name,R.id.rank_coin});
					coin_list.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}.execute();
	}

	// 返回按钮onclick
	public void return_infocenter(View v) {

		if (flag == 123) {
			finish();
		} else {
			Intent intent = new Intent(context, MoreActivity.class);
			intent.putExtra("LAYOUT", R.layout.more_infocenter);
			startActivity(intent);
			MoreInfocenterActivity.this.finish();
		}
	}

	// 城市管理---添加城市
	public void add_city(View v) {
		Intent intent = new Intent(context, AreaChooseActivity.class);
		intent.putExtra("listType", AreaChooseActivity.LIST_TYPE_PROVINCE);
		intent.putExtra("backActivityCode", 300);
		MoreInfocenterActivity.this.startActivityForResult(intent, 300);
	}

	// 获取开始时间
	@SuppressWarnings("deprecation")
	public void start_time(View v) {
		// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
		// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
		showDialog(DATE_DIALOG_ID);
		/*
		 * tv_push_start_time.setText(new StringBuilder().append(mYear)
		 * .append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
		 * .append(mDay).append("日"));
		 */

	}

	// 获取结束时间
	@SuppressWarnings("deprecation")
	public void end_time(View v) {
		// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
		// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
		showDialog(DATE_DIALOG_ID1);
		/*
		 * tv_end.setText(new StringBuilder().append(mYear).append("年")
		 * .append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
		 * .append(mDay).append("日"));
		 */

	}

	// 需要定义弹出的DatePicker对话框的事件监听器：
	private DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 设置文本的内容：
			tv_push_start_time.setText(new StringBuilder().append(mYear)
					.append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日"));
		}
	};

	// 需要定义弹出的DatePicker对话框的事件监听器：
	private DatePickerDialog.OnDateSetListener mDateSetListener1 = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// 设置文本的内容：
			tv_end.setText(new StringBuilder().append(mYear).append("年")
					.append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(mDay).append("日"));
		}
	};

	// 当Activity调用showDialog函数时会触发该函数的调用：
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case DATE_DIALOG_ID1:
			return new DatePickerDialog(this, mDateSetListener1, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// 订阅消息管理 -出发地点
	public void from(View v) {
		Intent intent = new Intent(context, AreaChooseActivity.class);
		intent.putExtra("listType", AreaChooseActivity.LIST_TYPE_PROVINCE);
		intent.putExtra("backActivityCode", 500);
		// startActivity(intent);
		MoreInfocenterActivity.this.startActivityForResult(intent, 500);
	}

	

	public void push_choose(View v) {
		rl_push_list.setVisibility(View.INVISIBLE);
		push_add.setVisibility(View.VISIBLE);
	}

	// 城市管理-刷新按钮
	public void city_refresh(View v) {
		getCityListDate();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 500) {
			if (data.getStringExtra("area") != null) {
				tv_addcity.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 300) {
			final String id = data.getStringExtra("areaid");
			pdialog = new ProgressDialog(context);
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setCancelable(false);
			pdialog.setMessage("正在添加……");
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... param) {
					// TODO Auto-generated method stub
					// handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					try {
						params.add(new BasicNameValuePair("userID", URLEncoder
								.encode(sp.getString("userId", ""), "UTF-8")));
						params.add(new BasicNameValuePair("cityID", URLEncoder
								.encode(id, "UTF-8")));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JSONObject jsonObject = MyHttpClient.getJson(context,
							Constant.addCity, params);
					try {
						status = jsonObject.getInt("status");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					// handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

					if (status == 1) {
						Toast a = Toast.makeText(context, "添加成功",
								Toast.LENGTH_SHORT);
						a.show();
						getCityListDate();
					} else {
						Toast a = Toast.makeText(context, "添加失败",
								Toast.LENGTH_SHORT);
						a.show();
					}
					super.onPostExecute(result);
				}

			}.execute();
		}

	}

	// 获取列表数据
	protected void getCityListDate() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在更新列表……");
		new AsyncTask<Void, Void, List<NameValuePair>>() {

			@Override
			protected List<NameValuePair> doInBackground(Void... param) {
				// TODO Auto-generated method stub
				// Looper.prepare();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("userID", URLEncoder
							.encode(sp.getString("userId", ""), "UTF-8")));
					System.out.println(params);
					adapter = getAdapter(data, Constant.getCity, params);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return params;
			}

			@Override
			protected void onPostExecute(List<NameValuePair> result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				if (status == 1) {
					city_list.setAdapter(adapter);
					city_list.getCount();
					
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	// 添加城市列表适配器
	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		JSONObject jsonObject = MyHttpClient.getJson(this, method, params);
		System.out.println(jsonObject);
		try {
			status = jsonObject.getInt("status");
			if (status == 1) {
				JSONArray jsonArray = jsonObject.getJSONArray("result");
				HashMap<String, Object> map = null;
				JSONObject list;
				Set<String> set = new HashSet<String>();
				Set<String> setId = new HashSet<String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					map = new HashMap<String, Object>();
					list = jsonArray.getJSONObject(i);
					String cityID = null;
					String city = null;
					cityID = list.getString("cityID");
					city = list.getString("city");
					String str = Split.cut(city);
					map.put("cityID", cityID);
					map.put("city", str);
					data.add(i, map);
					set.add(city);
					setId.add(cityID);
					
				}
				editor.putInt("length", jsonArray.length());
				editor.putStringSet("dingYueCity", set);
				editor.putStringSet("dingYueCityId", setId);
				editor.commit();
				
				adapter = new SimpleAdapter(this, data,
						R.layout.infocenter_city_list_item,
						new String[] { "city" },
						new int[] { R.id.tv_wacth_city });
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adapter;
	}

	// 删除城市
	private void deleteCity(final String delete) {
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在删除……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("userID", URLEncoder
							.encode(sp.getString("userId", ""), "UTF-8")));
					params.add(new BasicNameValuePair("cityID", URLEncoder
							.encode(delete, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JSONObject jsonObject = MyHttpClient.getJson(context,
						Constant.delCity, params);

				try {
					status = jsonObject.getInt("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (status == 1) {
					if (sp.getString("currentCity", "").equals(delete)) {
						editor.remove("currentCity");
						status = 3;
					}
					// getCityListDate();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				switch (status) {
				case 1:
					Toast t = Toast.makeText(context, "删除城市成功，请点击刷新查看",
							Toast.LENGTH_SHORT);
					t.show();
					break;
				case 0:
					Toast t0 = Toast.makeText(context, "网络错误",
							Toast.LENGTH_SHORT);
					t0.show();
					break;
				case 3:
					Toast t3 = Toast.makeText(context, "删除城市成功，但你已删除默认城市",
							Toast.LENGTH_SHORT);
					t3.show();
					break;
				default:
					Toast t11 = Toast.makeText(context, "系统错误",
							Toast.LENGTH_SHORT);
					t11.show();
					break;
				}
				super.onPostExecute(result);
			}

		}.execute();
	}

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
		if (item.getItemId() == 0) {
			deleteCity(cityID1);
		} else if (item.getItemId() == 2) {
			deleteTags(cityName);
		}
		return super.onContextItemSelected(item);
	}
	// 极光推送 添加标签
	public void push_do(View v) {
		String tags;
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		Set<String> setHan = new HashSet<String>();
		Set<String> setHan1 = new HashSet<String>();
		set1.add("chengdoushi");
		setHan1.add("成都市");

		if (!tv_addcity.getText().toString().trim().equals("")) {
			tags = Split.cut_r2(tv_addcity.getText().toString().trim());

			set = sp.getStringSet("citySet", set1);
			setHan = sp.getStringSet("citySetHan", setHan1);
			set.add(HanziToPinyin.HanyuToPinyin(tags));
			setHan.add(tags);
			setTags(set);
			push_add.setVisibility(View.INVISIBLE);
			rl_push_list.setVisibility(View.VISIBLE);
			
			editor.putStringSet("citySet", set);
			editor.putStringSet("citySetHan", setHan);
			editor.commit();
			
			getPushListDate();
		} else {
			Toast.makeText(context, "请选择订阅城市", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void getPushListDate() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Set<String> set = new HashSet<String>();
		Set<String> setHan1 = new HashSet<String>();
		setHan1.add("成都市");
		set = sp.getStringSet("citySetHan", setHan1);
		
		int i = 0;
		for (Iterator it = set.iterator(); it.hasNext(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tags", it.next().toString());
			listItem.add(i, map);
		}

		SimpleAdapter listAdapter = new SimpleAdapter(context, listItem,
				R.layout.infocenter_city_list_item, new String[] { "tags" },
				new int[] { R.id.tv_wacth_city });
		push_list.setAdapter(listAdapter);
	}
	
	// 极光推送 删除标签
	private void deleteTags(String cityName2) {
		// TODO Auto-generated method stub
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		Set<String> setHan = new HashSet<String>();
		Set<String> setHan1 = new HashSet<String>();
		set1.add("chengdoushi");
		setHan1.add("成都市");
		set = sp.getStringSet("citySet", set1);
		setHan = sp.getStringSet("citySetHan", setHan1);

		set.remove(HanziToPinyin.HanyuToPinyin(cityName2));
		setHan.remove(cityName2);
		setTags(set);
		editor.putStringSet("citySet", set);
		editor.putStringSet("citySetHan", setHan);
		editor.commit();
		getPushListDate();

	}

	private void setTags(Set<String> set) {
		jpushHandler.sendMessage(jpushHandler.obtainMessage(MSG_SET_TAGS, set));
	}

	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private static final String TAG = "JPush";
	private final Handler jpushHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d(TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;
			case MSG_SET_TAGS:
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;
			default:
				Log.i(TAG, "Unhandled msg - " + msg.what);
				break;
			}
		}

	};

	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					jpushHandler.sendMessageDelayed(
							jpushHandler.obtainMessage(MSG_SET_TAGS, tags),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				Log.i(TAG, "No network");
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					jpushHandler.sendMessageDelayed(
							jpushHandler.obtainMessage(MSG_SET_ALIAS, alias),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				Log.i(TAG, "No network");
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
}
