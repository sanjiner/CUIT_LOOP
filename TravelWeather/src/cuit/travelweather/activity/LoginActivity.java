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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;

/*
 * 登录页面Activity
 */
public class LoginActivity extends BaseAct {
	private ListPreference list_affairinfo_fontsize;
	
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private static ProgressDialog pdialog;
	private Context context = LoginActivity.this;
	private String etUserName;
	private String etPassword;
	private String etUsername_register;
	private String etPassword_regitster;
	private String etPassword_regitster2;
	private String editText_Email;
	private String message;
//	private String etEmail;
	private View pageLogin;
	private View pageRegist;
	private int status_register;
	private int status_login;
	private String[] jsonStr;
	private SharedPreferences sp;
	private Editor editor;

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

	public boolean isExit;

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.login);
		TextView tv = (TextView)findViewById(R.id.login_label_pass);
		TextView tv1 = (TextView)findViewById(R.id.login_label_ID);
		
		TextView tv2 = (TextView)findViewById(R.id.login_tabs_login);
		TextView tv3 = (TextView)findViewById(R.id.login_tabs_regist);
		TextView tv4 = (TextView)findViewById(R.id.login_btn_login);
		TextView tv5 = (TextView)findViewById(R.id.login_find_back);
		TextView tv6 = (TextView)findViewById(R.id.login_title);
		
		//字体设置
		 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv.setTextSize(10);
			tv1.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
			tv5.setTextSize(10);
			tv6.setTextSize(10);
			tv2.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv.setTextSize(30);
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
			tv5.setTextSize(30);
			tv6.setTextSize(30);
		}
		
		init();
		MyFont.setTypeface(this, new int[] { R.id.login_title,
				R.id.login_label_ID, R.id.login_label_pass,
				R.id.regist_label_ID, R.id.regist_label_pass,
				R.id.regist_label_pass_confirm, R.id.login_btn_login,
				R.id.login_btn_regist });
	}

	private void init() {
		pageLogin = this.findViewById(R.id.login_page_login);
		//pageLogin = this.findViewById(SkinManager.getIdentifier(context, "login_page_login", "id"));
		pageRegist = this.findViewById(R.id.login_page_regist);
		RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.login_tabs);

		sp = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		editor = sp.edit();

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.login_tabs_login:
					pageRegist.setVisibility(View.INVISIBLE);
					pageLogin.setVisibility(View.VISIBLE);
					break;
				case R.id.login_tabs_regist:
					pageLogin.setVisibility(View.INVISIBLE);
					pageRegist.setVisibility(View.VISIBLE);
					break;
				}
			}

		});
	}

	/**
	 * 登录方法，在xml中绑定控件
	 */
	public void login(View v) {

		etUserName = ((EditText) findViewById(R.id.editText_uname)).getText()
				.toString();
		etPassword = ((EditText) findViewById(R.id.editText_upass)).getText()
				.toString();
		if (etUserName.trim().equals("") || etPassword.trim().equals("")) {
			Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
			return;
		}

		pdialog = new ProgressDialog(context);
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
							.encode(etUserName, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(etPassword, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				JSONObject jsonObject = MyHttpClient.getJson(context,
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
						editor.putString("userPassword", etPassword);
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
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

				switch (status_login) {
				case 1:
					Toast a = Toast.makeText(LoginActivity.this, "登录成功",
							Toast.LENGTH_SHORT);
					a.show();
					Intent intent = new Intent();
					intent.setClass(context, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					LoginActivity.this.startActivity(intent);
					LoginActivity.this.finish();
					break;
				case -1:
					Toast a1 = Toast.makeText(LoginActivity.this, "用户名或密码错误",
							Toast.LENGTH_SHORT);
					a1.show();
					break;
				case -2:
					Toast a2 = Toast.makeText(LoginActivity.this, "空错误",
							Toast.LENGTH_SHORT);
					a2.show();
					break;
				case -3:
					Toast a3 = Toast.makeText(LoginActivity.this, "系统错误",
							Toast.LENGTH_SHORT);
					a3.show();
					break;
				case 0:
					Toast a0 = Toast.makeText(LoginActivity.this, "网络连接错误",
							Toast.LENGTH_SHORT);
					a0.show();
					break;
				}
			}
		}.execute();
	}

	
	//注册    。。。
	public void register(View v) {
		String format = "\\w{2,15}[@][a-z0-9]{2,}[.]\\p{Lower}{2,}";
		etUsername_register = ((EditText) findViewById(R.id.editText_registname))
				.getText().toString();
		etPassword_regitster = ((EditText) findViewById(R.id.editText_registpass))
				.getText().toString();
		etPassword_regitster2 = ((EditText) findViewById(R.id.editText_registpass2))
				.getText().toString();
		editText_Email=((EditText)findViewById(R.id.editText_Email))
				.getText().toString();
		if (etUsername_register.trim().equals("")
				|| etPassword_regitster.trim().equals("")
				|| etPassword_regitster2.trim().equals("")){
			Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!etPassword_regitster.trim().equals(etPassword_regitster2)) {
			Toast.makeText(this, "两次输入密码不同！请重新输入", Toast.LENGTH_SHORT).show();
			return;
		}
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在注册……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(etUsername_register, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(etPassword_regitster, "UTF-8")));
					params.add(new BasicNameValuePair("Email", URLEncoder
							.encode(editText_Email, "UTF-8")));
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
					Toast a = Toast.makeText(LoginActivity.this, "注册成功,请登录！",
							Toast.LENGTH_SHORT);
					a.show();
					pageRegist.setVisibility(View.INVISIBLE);
					pageLogin.setVisibility(View.VISIBLE);
					break;
				case -1:
					Toast a1 = Toast.makeText(LoginActivity.this, "该用户已被注册",
							Toast.LENGTH_SHORT);
					a1.show();
					break;
				case 0:
					Toast a2 = Toast.makeText(LoginActivity.this, "网络错误",
							Toast.LENGTH_SHORT);
					a2.show();
				}
			}
		}.execute();

	}

}
