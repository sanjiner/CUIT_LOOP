package com.example.thesis;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.common.tools.T;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.Login;

public class MyThises_Personal_information extends BaseActivity implements OnClickListener{
	public Button personal_information_back;
	public TextView personal_information_name;
	public TextView personal_information_School;
	public TextView personal_information_sex;
	public TextView personal_information_age;
	public TextView personal_information_telephone;
	public TextView personal_information_Email;
	public SharedPreferences sharePref;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_information_teacher);
		init();
	}
	public void init()
	{
		sharePref = getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		getViewObj();
		setListener();
		Dialog();
		getdetail();
		
	}
	public void getViewObj()
	{
		personal_information_back=(Button) findViewById(R.id.personal_information_back);
		personal_information_name=(TextView) findViewById(R.id.personal_information_name);
		personal_information_School=(TextView) findViewById(R.id.personal_information_School);
		personal_information_sex=(TextView) findViewById(R.id.personal_information_sex);
		personal_information_age=(TextView) findViewById(R.id.personal_information_age);
		personal_information_telephone=(TextView) findViewById(R.id.personal_information_telephone);
		personal_information_Email=(TextView) findViewById(R.id.personal_information_Email);
		
	}
	public void setListener()
	{
		personal_information_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.personal_information_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	
	private void Dialog()
	{
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	public void getdetail()
	{
		String teacherId = sharePref.getString(Constant.SPFIELD.TEACHERID, "");
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "TeaInfo", new String[]{"teacherID"}, new String[]{teacherId});
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
		executeRequest(stringRequest);
	}
	/**
	 * 网络相应成功是处理返回值
	 * @param v
	 */
	private Response.Listener<String> onResponse(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!TextUtils.isEmpty(response)){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String isSuccess = jsonObj.getString("success");
						if(isSuccess.equals("true")){
							dialog.dismiss();
							personal_information_name.setText(jsonObj.getString("TeacherName"));
							personal_information_School.setText(jsonObj.getString("Department"));
							personal_information_sex.setText(jsonObj.getString("Sex"));
							personal_information_age.setText(jsonObj.getString("Age"));
							personal_information_telephone.setText(jsonObj.getString("Tel"));
							personal_information_Email.setText(jsonObj.getString("Email"));
							
						}else{
							T.showShort(mActivity, jsonObj.getString("ErrMsg"));
							dialog.dismiss();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						L.e(TAG, e.toString());
						dialog.dismiss();
					}
				}else{
					T.showShort(mActivity, "获取失败");
					dialog.dismiss();
				}
				
			}
		};
				
	}
	
	/**
	 * 网络请求失败
	 */
	@Override
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				L.d(TAG, error.getMessage());
				dialog.dismiss();
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
}
