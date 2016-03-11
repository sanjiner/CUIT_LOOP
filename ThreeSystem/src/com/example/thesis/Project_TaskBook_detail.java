package com.example.thesis;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.ImageView;
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

public class Project_TaskBook_detail extends BaseActivity implements OnClickListener{
//任务书
	public Activity mActivity;
	public Button project_taskbook_back;
	public ImageView project_taskbook_edit_btn;
	public TextView project_taskbook_name;
	public TextView project_taskbook_comefrom;
	public TextView project_taskbook_type;
	public TextView project_taskbook_grade;
	public TextView project_ataskbook_Requirement;
	public SharedPreferences  spPreferences;
	public String studentId;
	public ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_taskbook_detail);
		mActivity=this;
		spPreferences=getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		init();
	}
	public void getViewObj()
	{
		project_taskbook_back=(Button) findViewById(R.id.project_taskbook_back);
		project_taskbook_edit_btn=(ImageView) findViewById(R.id.project_taskbook_edit_btn);
		project_taskbook_name=(TextView) findViewById(R.id.project_taskbook_name);
		project_taskbook_comefrom=(TextView) findViewById(R.id.project_taskbook_comefrom);
		project_taskbook_type=(TextView) findViewById(R.id.project_taskbook_type);
		project_taskbook_grade=(TextView) findViewById(R.id.project_taskbook_grade);
		project_ataskbook_Requirement=(TextView) findViewById(R.id.project_ataskbook_Requirement);
		
		
	}
	public void setlistener()
	{
		project_taskbook_back.setOnClickListener(this);
		project_taskbook_edit_btn.setOnClickListener(this);
	}
	public void init()
	{
		getViewObj();
		setlistener();
		Dialog();
		String identity = spPreferences.getString(Constant.SPFIELD.USERIDENTITY, "");
		if(identity.equals(Constant.USERIDENTITY[1])){
			project_taskbook_edit_btn.setVisibility(View.VISIBLE);
		}
		getGradeInfo();
	}
	
	/**
	 * 通过网络获取成绩信息
	 */
	private void getGradeInfo(){
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckTask", new String[]{"studentID"}, new String[]{studentId});
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
							project_taskbook_name.setText(jsonObj.getString("TaskName"));
							project_taskbook_comefrom.setText(jsonObj.getString("TaskSource"));
							project_taskbook_type.setText(jsonObj.getString("TaskType"));
							project_taskbook_grade.setText(jsonObj.getString("BackGroundAndAim"));
							project_ataskbook_Requirement.setText(jsonObj.getString("TaskAndRequire"));
							
						}else{
							dialog.dismiss();
							T.showShort(mActivity, "暂无该学生数据");
						}
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						L.e(TAG, e.toString());
						dialog.dismiss();
					}
				}else{
					dialog.dismiss();
					T.showShort(mActivity, "获取失败");
					
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
				L.d(TAG, error.getMessage());
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	private void Dialog()
	{
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	@Override
	public void onRestart(){
		getGradeInfo();
		super.onRestart();
	}
	
	@Override
	public void onClick(View view ) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.project_taskbook_back:
			finish();
			break;
		case R.id.project_taskbook_edit_btn:
			Intent taskbookEditIntent = new Intent(mActivity, TeacherProjectTaskBookEditActivity.class);
			taskbookEditIntent.putExtra("studentId", studentId);
			taskbookEditIntent.putExtra("TaskSource",project_taskbook_comefrom.getText().toString());
			taskbookEditIntent.putExtra("TaskType",project_taskbook_type.getText().toString());
			taskbookEditIntent.putExtra("BackGroundAndAim",project_taskbook_grade.getText().toString());
			taskbookEditIntent.putExtra("TaskAndRequire",project_ataskbook_Requirement.getText().toString());
			startActivity(taskbookEditIntent);
		default:
			break;
		}
	}
}
