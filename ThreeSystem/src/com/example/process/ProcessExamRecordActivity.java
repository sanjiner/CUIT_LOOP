package com.example.process;

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
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessExamRecordActivity extends BaseActivity implements OnClickListener{

	
	private Button backButton;
	private ProgressDialog dialog;
	private ProgressBar progressBar;
	private ListView GradeListView;
	private List<Map<String, String>> RecordList;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private TextView noData;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_exam_record);
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		Init();
	}	
	
	private void Init()
	{
		getViewObj();
		Dialog();
		getExamRecord();
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
	
	private void getViewObj()
	{
		backButton = (Button) findViewById(R.id.process_exam_record_back);
		noData = (TextView) findViewById(R.id.process_exam_record_no_data);
		progressBar = (ProgressBar) findViewById(R.id.process_appoint_exam_progressbar);
		Listener();
	}
	
	private void Listener()
	{
		backButton.setOnClickListener(this);
	}

	
	private void getExamRecord(){
		dialog.show();
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		String stunumber = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("StuNumber", stunumber);	
		action = "GetAppoinedList";
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
							RecordList = new ArrayList<Map<String, String>>();
							JSONArray jsonArray = jsonObj.getJSONArray("StuInfo");
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);						
								
								Map<String, String> map = new HashMap<String, String>();
								map.put("NumOfModule", json.getString("NumOfModule"));
								map.put("ExamScore",json.getString("ExamScore"));
								RecordList.add(map);
							}
							GradeListView.setAdapter(new ProcessGradeDetailAdapter(mActivity, RecordList));
							noData.setVisibility(View.INVISIBLE);
						}else{
							L.d(TAG, "Success is false");
						}
						dialog.dismiss();
						progressBar.setVisibility(View.INVISIBLE);
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
		case R.id.process_exam_record_back:
			finish();
			break;

		default:
			break;
		}
		
	}
}
