package com.example.process;

import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.adapter.ClassListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.exam.ThreeSystem.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DetailGradeActivity extends BaseActivity implements OnClickListener{

	private ProgressDialog dialog;
	private ListView GradeListView;
	private ProgressBar progressBar;
	private Button backBtn;
	private String CourseID;
	private List<Map<String, String>> GradeList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_coursegrade_detail);
		Intent intent = getIntent();
		CourseID = intent.getStringExtra("CourseID");
		
		init();		
	}
	
	private void getViewObj()
	{
		backBtn = (Button) findViewById(R.id.process_gradedetail_back);
		progressBar = (ProgressBar) findViewById(R.id.process_gradedetail_progressbar);
		GradeListView = (ListView) findViewById(R.id.process_gradedetail_ListView);
	}
	
	private void setListener()
	{
		backBtn.setOnClickListener(this);
	}

	private void init()
	{
		getViewObj();
		setListener();
		Dialog();
		getDetailGrade();
	}
	
	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
	}

	
	private void getDetailGrade(){
		dialog.show();
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		params.put("CourseID", "100");
		params.put("UserName", "2012121112");	
		action = "GetStuScore";
		String url = URLTools.buildURL(Constant.PROCESS_BASUURL + action,params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url,successListener(),new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
								.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(stringRequest);
	}
	
	private Response.Listener<String> successListener(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(response.length() != 0){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(success.equals("true")){
							GradeList = new ArrayList<Map<String, String>>();
							JSONArray jsonArray = jsonObj.getJSONArray("StuInfo");
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);						
								
								Map<String, String> map = new HashMap<String, String>();
								map.put("NumOfModule", json.getString("NumOfModule"));
								map.put("ExamScore",json.getString("ExamScore"));
								GradeList.add(map);
							}
							GradeListView.setAdapter(new ProcessGradeDetailAdapter(mActivity, GradeList));
							progressBar.setVisibility(View.INVISIBLE);
							dialog.dismiss();
						}else{
							L.d(TAG, "Success is false");
						}
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.process_gradedetail_back:
			finish();
			break;
		}
	}
}
