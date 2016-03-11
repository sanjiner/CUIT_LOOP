package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class FindBackPwdActivity extends BaseAct {

	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private int status;

	private static ProgressDialog pdialog;

	private EditText et_find_username;
	private EditText et_find_random;
	private EditText et_find_pwd;
	private EditText et_find_pwd2;
	private Context context = FindBackPwdActivity.this;

	private String username;
	private String random;
	private String pwd;
	private String pwd2;
	private SharedPreferences sp;

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
		setContentView(R.layout.find_back_pwd);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		et_find_username = (EditText) findViewById(R.id.et_find_username);
		et_find_random = (EditText) findViewById(R.id.et_find_random);
		et_find_pwd = (EditText) findViewById(R.id.et_find_pwd);
		et_find_pwd2 = (EditText) findViewById(R.id.et_find_pwd2);
		
		TextView tv1 = (TextView)findViewById(R.id.tv_find_username);
		TextView tv2 = (TextView)findViewById(R.id.tv_find_pwd);
		TextView tv3 = (TextView)findViewById(R.id.tv_find_pwd2);
		TextView tv4 = (TextView)findViewById(R.id.tv_find_random);
		
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		 if(size.equals("1")){
			   tv1.setTextSize(10);
			   tv2.setTextSize(10);
			   tv3.setTextSize(10);
			   tv4.setTextSize(10);
			}else if(size.equals("2")){
			}else{
				tv1.setTextSize(30);
				   tv2.setTextSize(30);
				   tv3.setTextSize(30);
				   tv4.setTextSize(30);
			}
			
		
	}

	public void find_back(View v) {
		getMessage();
	}

	public void return_login(View v) {
		Intent intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		FindBackPwdActivity.this.startActivity(intent);
		FindBackPwdActivity.this.finish();
	}

	private void getMessage() {

		username = et_find_username.getText().toString();
		random = et_find_random.getText().toString();
		pwd = et_find_pwd.getText().toString();
		pwd2 = et_find_pwd2.getText().toString();

		if (username.trim().equals("") || random.trim().equals("")
				|| pwd.trim().equals("") || pwd2.trim().equals("")) {
			Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!pwd.equals(pwd2)) {
			Toast.makeText(this, "两次输入密码不同！请重新输入", Toast.LENGTH_SHORT).show();
			return;
		}

		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在重置……");

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("username", URLEncoder
							.encode(username, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(pwd, "UTF-8")));
					params.add(new BasicNameValuePair("random", URLEncoder
							.encode(random, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jsonObject = MyHttpClient.getJson(context,
						Constant.reSetPwd, params);
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
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				switch (status) {
				case 1:
					Intent intent = new Intent();
					intent.setClass(context, LoginActivity.class);
					context.startActivity(intent);
					FindBackPwdActivity.this.finish();
					Toast a = Toast.makeText(context, "密码重置成功！！请登录",
							Toast.LENGTH_SHORT);
					a.show();
					break;
				case 0:
					Toast a0 = Toast.makeText(context, "网络连接错误",
							Toast.LENGTH_SHORT);
					a0.show();
					break;
				default:
					Toast a1 = Toast.makeText(context, "用户名或验证码错误",
							Toast.LENGTH_SHORT);
					a1.show();
					break;
				}
				super.onPostExecute(result);
			}

		}.execute();
	}
}
