package com.example.thesis;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class Project_GuideReport_detail_edit extends BaseActivity implements OnClickListener{

	public Button project_guidereport_detail_back;
	public EditText et_guidereport_edit_content;
	public Button  b_guidereport_edit_submitBtn;
	public Activity mActivity;
	public ProgressDialog dialog;
	public SharedPreferences shared_Pref;
	public String studentId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_guidereport_detail_edit);
		shared_Pref=getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		Intent intent=getIntent();
		studentId=intent.getStringExtra("studentId");
		mActivity=this;
		init();
	}

	public void getViewObj()
	{
		project_guidereport_detail_back=(Button) findViewById(R.id.project_guidereport_detail_back);
		b_guidereport_edit_submitBtn=(Button) findViewById(R.id.b_guidereport_edit_submitBtn);
		et_guidereport_edit_content=(EditText) findViewById(R.id.et_guidereport_edit_content);
		
	}
	public void setlistener()
	{
		project_guidereport_detail_back.setOnClickListener(this);
		b_guidereport_edit_submitBtn.setOnClickListener(this);
	}
	public void init()
	{
		getViewObj();
		setlistener();
		
	}
	
	/**
	 * 提交记录
	 * @param v
	 */
	private void submitRecord(){
		String content = et_guidereport_edit_content.getText().toString();
		if(!TextUtils.isEmpty(content)){
			Map<String, String> params = new HashMap<String, String>();
			params.put("studentID", studentId);
			
			params.put("Content", content);
			
			String url = URLTools.buildURL(Constant.THESIS_BASEURL+"EditRecord", params);
			
			StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
			executeRequest(stringRequest);
		}else{
			T.showShort(mActivity, "内容不能为空");
		}
		
	}
	private Response.Listener<String> onResponse(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String result = json.getString("success");
					if(result.equals("true")){
						dialog.dismiss();
						T.showShort(mActivity, "成功");
						finish();
					}
				} catch (JSONException e) {
					dialog.dismiss();
					T.showShort(mActivity, "失败");
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
				dialog.dismiss();
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	private void Dialog()
	{
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在提交，请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.project_guidereport_detail_back:
			finish();
			break;
		case R.id.b_guidereport_edit_submitBtn:
			Dialog();
			submitRecord();
			break;
		default:
			break;
		}
	}

	
}
