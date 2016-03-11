package com.example.thesis;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class Project_OpenReport_detail_edit extends BaseActivity implements OnClickListener {
	public String studentId;
	public String preBg;
	public String preIdea;
	public String prePlan;
	public Button project_report_edit_back;
	public Button edit_openreport_edit_submit;
	
	public EditText project_openreport_background_edit;
	public EditText project_openreport_idea_edit;
	public EditText project_openreport_progress_edit;
	public ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_openreport_detail_edit);
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		preBg = intent.getStringExtra("preBg");
		preIdea = intent.getStringExtra("preIdea");
		prePlan = intent.getStringExtra("prePlan");
		init();
	}
	public void getViewObj()
	{
		project_report_edit_back=(Button) findViewById(R.id.project_report_edit_back);
		edit_openreport_edit_submit=(Button) findViewById(R.id.edit_openreport_edit_submit);
		project_openreport_background_edit=(EditText) findViewById(R.id.project_openreport_background_edit);
		project_openreport_idea_edit=(EditText) findViewById(R.id.project_openreport_idea_edit);
		project_openreport_progress_edit=(EditText) findViewById(R.id.project_openreport_progress_edit);
		
	}
	public void setlistener()
	{
		project_report_edit_back.setOnClickListener(this);
		edit_openreport_edit_submit.setOnClickListener(this);
	}
	public void init()
	{
		getViewObj();
		setlistener();
		project_openreport_background_edit.setText(preBg);
		project_openreport_idea_edit.setText(preIdea);
		project_openreport_progress_edit.setText(prePlan);
		
	}
	
	private void submitInfo(){
		String bg = project_openreport_background_edit.getText().toString();
		String idea = project_openreport_idea_edit.getText().toString();
		String plan = project_openreport_progress_edit.getText().toString();
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("studentID", studentId);
		params.put("backGround", bg);
		params.put("idea", idea);
		params.put("plan", plan);
		
		String url = URLTools.buildURL(Constant.THESIS_BASEURL+"EditReport", params);
		StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
		executeRequest(stringRequest);
	}
	
	
	private Response.Listener<String> onResponse(){
		
		return new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					if(json.getString("success").equals("true")){
						dialog.dismiss();
						finish();
					}
					else {
						dialog.dismiss();
						T.showShort(mActivity, json.getString("ErrMsg"));
					}
				} catch (JSONException e) {
					dialog.dismiss();
					T.showShort(mActivity, e.toString());
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
		case R.id.project_report_edit_back:
			finish();
			break;
		case R.id.edit_openreport_edit_submit:
			Dialog();
			submitInfo();
			break;
		default:
			break;
		}
		
	}
}
