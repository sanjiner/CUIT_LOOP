package com.example.thesis;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.common.tools.NetDetection;
import com.common.tools.T;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;

public class Project_OpenReport_detail extends BaseActivity implements OnClickListener {

	
	/*开题报告
	 * 
	 * */
	public Activity mActivity;
	public Button project_report_back;
	public ImageView project_report_edit_btn;
	public TextView project_openreport_name;
	public TextView project_openreport_background;
	public TextView project_openreport_idea;
	public TextView project_openreport_progress;
	private Map<String, String> infoMap = new HashMap<String, String>();
	public String studentId ;
	public ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_openreport_detail);
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		mActivity=this;
		init();
	}
	public void getViewObj()
	{
		project_report_back=(Button) findViewById(R.id.project_report_back);
		project_report_edit_btn=(ImageView) findViewById(R.id.project_report_edit_btn);
		project_openreport_name=(TextView) findViewById(R.id.project_openreport_name);
		project_openreport_background=(TextView) findViewById(R.id.project_openreport_background);
		project_openreport_idea=(TextView) findViewById(R.id.project_openreport_idea);
		project_openreport_progress=(TextView) findViewById(R.id.project_openreport_progress);
		
		
	}
	public void setlistener()
	{
		project_report_back.setOnClickListener(this);
		project_report_edit_btn.setOnClickListener(this);
	}
	public void init()
	{
		getViewObj();
		setlistener();
		Dialog();
		getCheckReport();
	}
	
	/**
	 * 网络获取信息
	 */

	private void getCheckReport() {
		
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckReport",
				new String[] { "studentID" }, new String[] { studentId });
		StringRequest stringRequest = new StringRequest(url, onResponse(),
				errorListener());
		executeRequest(stringRequest);
	}

	/**
	 * 网络相应成功是处理返回值
	 * 
	 * @param v
	 */
	private Response.Listener<String> onResponse() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if (!TextUtils.isEmpty(response)) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String isSuccess = jsonObj.getString("success");
						if (isSuccess.equals("true")) {
							dialog.dismiss();
							project_openreport_name.setText(jsonObj.getString("title"));
							project_openreport_background.setText(jsonObj.getString("backGround"));
							project_openreport_idea.setText(jsonObj.getString("idea"));
							project_openreport_progress.setText(jsonObj.getString("plan"));

							infoMap.put("preBg",
									jsonObj.getString("backGround"));
							infoMap.put("preIdea", jsonObj.getString("idea"));
							infoMap.put("prePlan", jsonObj.getString("plan"));

						} else {
							dialog.dismiss();
							T.showShort(mActivity, jsonObj.getString("ErrMsg"));
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						dialog.dismiss();
						T.showShort(mActivity, e.toString());
						L.e(TAG, e.toString());
					}
				} else {
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
		getCheckReport();
		super.onRestart();
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.project_report_back:
			finish();
			break;
		case R.id.project_report_edit_btn:
			
				Intent editIntent = new Intent(mActivity,
						Project_OpenReport_detail_edit.class);
				editIntent.putExtra("studentId", studentId);
				editIntent.putExtra("preBg", infoMap.get("preBg"));
				editIntent.putExtra("preIdea", infoMap.get("preIdea"));
				editIntent.putExtra("prePlan", infoMap.get("prePlan"));
				startActivity(editIntent);
			
			break;
		default:
			break;
		}
		
	}
}
