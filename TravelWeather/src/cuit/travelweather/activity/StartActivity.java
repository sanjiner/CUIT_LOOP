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

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
 * 软件起始页面Activity
 */
public class StartActivity extends BaseAct {
	private Context context = StartActivity.this;
	private SharedPreferences sp;
	private Editor editor;
	private JSONObject jsonObject;
	private String[] jsonStr;
	protected int status_register;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
//		gettel();//得到设备号
		if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
			RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.start);
			relativeLayout.setBackgroundResource(R.drawable.start_qhc);
			TextView textView = (TextView) findViewById(R.id.logo);
			textView.setTextColor(this.getResources().getColor(R.color.theme_bule));
		}
	
		sp = StartActivity.this.getSharedPreferences("User_SP", MODE_PRIVATE);
		editor = sp.edit();
		if (sp.getString("userName", "").equals("")) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					startActivity(new Intent(StartActivity.this,
							MainActivity.class));
					StartActivity.this.finish();
				}
			}, 2000);
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					autoLogin();
				}
			}, 1000);
		}

		MyFont.setTypeface(this, new int[] { R.id.logo });
	}

	protected void getLogin() {
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在登录……");
		new AsyncTask<Void, Void, Void>() {

			private int status_login;

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(sp.getString(Constant.tel, ""), "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(sp.getString(Constant.tel, ""), "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(StartActivity.this,
						Constant.login, params);
				try {
					status_login = jsonObject.getInt("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (status_login == 1) {
					try {
						JSONObject jsonObj = jsonObject
								.getJSONObject("message");
						JSONArray jsonArray = jsonObj.getJSONArray("userinfo");
						JSONObject jo = jsonArray.getJSONObject(0);
						jsonStr = new String[] { jo.getString("userId"),
								jo.getString("userName"),
								jo.getString("userPassword"),
								jo.getString("userEmail"),
								jo.getString("registerTime"),
								jo.getString("userCoin") };
						sp = context.getSharedPreferences("User_SP",
								MODE_PRIVATE);

						editor.putString("userId", jsonStr[0]);
						editor.putString("userName", jsonStr[1]);
						editor.putString("userPassword", Constant.tel);
						editor.putString("userEmail", jsonStr[3]);
						editor.putString("registerTime", jsonStr[4]);
						editor.putString("userCoin", jsonStr[5]);
						editor.putString("Sum", jsonObject.getString("sum"));
						editor.putString("YourNum",
								jsonObject.getString("yourNum"));
						editor.commit();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						Intent intent = new Intent();
						intent.setClass(StartActivity.this, MainActivity.class);
						StartActivity.this.startActivity(intent);
						StartActivity.this.finish();
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

//				try {
//					if (jsonObject.getInt("status") == 1) {
//						JSONObject jsonObj = jsonObject
//								.getJSONObject("message");
//						JSONArray jsonArray = jsonObj.getJSONArray("userinfo");
//						JSONObject jo = jsonArray.getJSONObject(0);
//						jsonStr = new String[] { jo.getString("userId"),
//								jo.getString("userName"),
//								jo.getString("userPassword"),
//								jo.getString("userEmail"),
//								jo.getString("registerTime"),
//								jo.getString("userCoin") };
//						sp = StartActivity.this.getSharedPreferences("User_SP",
//								MODE_PRIVATE);
//
//						editor.putString("userId", jsonStr[0]);
//						editor.putString("userName", jsonStr[1]);
//						editor.putString("userPassword",
//								sp.getString("userPassword", ""));
//						editor.putString("userEmail", jsonStr[3]);
//						editor.putString("registerTime", jsonStr[4]);
//						editor.putString("userCoin", jsonStr[5]);
//						editor.putString("Sum", jsonObject.getString("sum"));
//						editor.putString("YourNum",
//								jsonObject.getString("yourNum"));
//						editor.commit();
//						System.out.print(sp.getString("YourNum", "1111111"));
//						Toast a = Toast.makeText(StartActivity.this, "登录成功",
//								Toast.LENGTH_SHORT);
//						a.show();
//					} else {
//					} 
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					Intent intent = new Intent();
//					intent.setClass(StartActivity.this, MainActivity.class);
//					StartActivity.this.startActivity(intent);
//					StartActivity.this.finish();
//				}
			}
		}.execute();
	}

	protected void getRegiset() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(StartActivity.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在zhuce……");
		new AsyncTask<Void, Void, Void>() {

			private String message;

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(Constant.tel, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(Constant.tel, "UTF-8")));
					params.add(new BasicNameValuePair("Email", URLEncoder
							.encode("123@qq.com", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				JSONObject jsonObject = MyHttpClient.getJson(context,
						Constant.regist, params);

				try {
					status_register = jsonObject.getInt("status");
					message = jsonObject.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

				switch (status_register) {
				case 1:
					//注册成功
					getLogin();
					break;
				case -1:
					//已被注册
					getLogin();
					break;
				case 0:
					Toast a2 = Toast.makeText(StartActivity.this, "网络错误",
							Toast.LENGTH_SHORT);
					a2.show();
				}
			}
		}.execute();

	}

		

	public void autoLogin() {
		pdialog = new ProgressDialog(StartActivity.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在登录……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(sp.getString("userName", ""), "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(sp.getString("userPassword", ""), "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject = MyHttpClient.getJson(StartActivity.this,
						Constant.login, params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

				try {
					if (jsonObject.getInt("status") == 1) {
						JSONObject jsonObj = jsonObject
								.getJSONObject("message");
						JSONArray jsonArray = jsonObj.getJSONArray("userinfo");
						JSONObject jo = jsonArray.getJSONObject(0);
						jsonStr = new String[] { jo.getString("userId"),
								jo.getString("userName"),
								jo.getString("userPassword"),
								jo.getString("userEmail"),
								jo.getString("registerTime"),
								jo.getString("userCoin") };
						sp = StartActivity.this.getSharedPreferences("User_SP",
								MODE_PRIVATE);

						editor.putString("userId", jsonStr[0]);
						editor.putString("userName", jsonStr[1]);
						editor.putString("userPassword",
								sp.getString("userPassword", ""));
						editor.putString("userEmail", jsonStr[3]);
						editor.putString("registerTime", jsonStr[4]);
						editor.putString("userCoin", jsonStr[5]);
						editor.putString("Sum", jsonObject.getString("sum"));
						editor.putString("YourNum",
								jsonObject.getString("yourNum"));
						editor.commit();
						System.out.print(sp.getString("YourNum", "1111111"));
//						Toast a = Toast.makeText(StartActivity.this, "登录成功",
//								Toast.LENGTH_SHORT);
//						a.show();
					} else {
//						Toast a = Toast.makeText(StartActivity.this,
//								"登陆失败，您将以游客身份进入", Toast.LENGTH_LONG);
//						a.show();
						editor.putString("userId", "");
						editor.apply();
					} 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					Intent intent = new Intent();
					intent.setClass(StartActivity.this, MainActivity.class);
					StartActivity.this.startActivity(intent);
					StartActivity.this.finish();
				}
			}
		}.execute();
	}
	
	//得到手机号码
	public void gettel(){
				TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		         Constant.tel = tm.getDeviceId();
		         Toast a = Toast.makeText(StartActivity.this, Constant.tel,
							Toast.LENGTH_SHORT);
					a.show();
	}

	
}
