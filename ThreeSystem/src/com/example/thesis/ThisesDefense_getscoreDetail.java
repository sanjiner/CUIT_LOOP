package com.example.thesis;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class ThisesDefense_getscoreDetail extends BaseActivity implements OnClickListener{

	public Button guid_personal_information_back;
	public TextView teacher_rate_name;
	public TextView teacher_rate_grade;
	public EditText teacher_rate_Quality;
	public EditText teacher_rate_process;
	public EditText teacher_rate_judge;
	public Button guide_teacher_rate_detail_submit;
	private String studentName, studentId, preScore;
	public ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_teacher_rate_detail);
		Intent intent = getIntent();
		studentName = intent.getStringExtra("studentName");
		preScore = intent.getStringExtra("score");
		studentId = intent.getStringExtra("studentID");
		init();
	}
	public void getViewObj(){
		guid_personal_information_back=(Button) findViewById(R.id.guid_personal_information_back);
		guide_teacher_rate_detail_submit=(Button) findViewById(R.id.guide_teacher_rate_detail_submit);
		teacher_rate_name=(TextView) findViewById(R.id.teacher_rate_name);
		teacher_rate_grade=(TextView) findViewById(R.id.teacher_rate_grade);
		teacher_rate_Quality=(EditText) findViewById(R.id.teacher_rate_Quality);
		teacher_rate_process=(EditText) findViewById(R.id.teacher_rate_process);
		teacher_rate_judge=(EditText) findViewById(R.id.teacher_rate_judge);
		
	}
	public void setlistener(){
		guid_personal_information_back.setOnClickListener(this);
		guide_teacher_rate_detail_submit.setOnClickListener(this);
	}
	public void init(){
		getViewObj();
		setlistener();
		teacher_rate_name.setText(studentName);
		teacher_rate_grade.setText(preScore);
		
	}
	
	public void submitScore(){
		/*获取分数输入值*/
		String qualityScore = teacher_rate_Quality.getText().toString();
		String processScore = teacher_rate_process.getText().toString();
		String evaluateScore = teacher_rate_judge.getText().toString();
		
		/* 检查分数是否为空 */
		if (TextUtils.isEmpty(qualityScore) || TextUtils.isEmpty(processScore)
				|| TextUtils.isEmpty(evaluateScore)) {
			dialog.dismiss();
			T.showShort(mActivity, "请输入分数");

		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("studentID", studentId);
			params.put("score", qualityScore+","+processScore+","+evaluateScore);

			String url = URLTools.buildURL(Constant.THESIS_BASEURL + "GuiEdit", params);
			System.out.println(url);
			StringRequest stringRequest = new StringRequest(url, onResponse(),
					errorListener());
			executeRequest(stringRequest);
		}
	}
	
	/**
	 * 处理提交后返回结果
	 * 
	 * @return
	 */
	public Response.Listener<String> onResponse() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String success = json.getString("success");
					if(success.equals("true")){
						Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show();
						teacher_rate_grade.setText(json.getString("Score"));
						dialog.dismiss();
						finish();
					}else{
						Toast.makeText(mActivity, "失败", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					dialog.dismiss();
					e.printStackTrace();
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
	
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.guid_personal_information_back:
			finish();
			break;
		case R.id.guide_teacher_rate_detail_submit:
			Dialog();
			submitScore();
			break;
		default:
			break;
		}
		
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
}
