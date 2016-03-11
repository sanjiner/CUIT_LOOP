package com.example.PCenter.Home;

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
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.HomeModuleStudentScoreDetailAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeOtherStudentScoreDetailActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	
		private Button back, giveScore;	
		private TextView StudentId, StudentName, isAddScoreTodayTextview;
		private String TeachingClassId;		
		private String studentId, studentName;
		private LinearLayout linearlayout;
		private ProgressBar progressbar;
		private List<Map<String,String>> TimeAndScoreList;
		private ListView todayScoreListView;
		private ProgressDialog dialog;
		private HomeModuleStudentScoreDetailAdapter adapter;
		private SharedPreferences sp;
		private String FILE = Constant.USERINFO_SP;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_module_student_today_detail);
		sp = mActivity.getSharedPreferences(FILE, MODE_PRIVATE);
		
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentNumber");
		studentName = intent.getStringExtra("studentName");
		TeachingClassId = intent.getStringExtra("TeachingClassID");
		
		TimeAndScoreList = new ArrayList<Map<String,String>>();
		adapter = new HomeModuleStudentScoreDetailAdapter(mActivity, TimeAndScoreList);
		//TimeAndScoreList
		
		getObjView();
		todayScoreListView.setAdapter(adapter);
	}
	
	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在操作，请等待。。。。");  
        dialog.setTitle("提示");
	}
	
	private void getObjView() {
		back = (Button)findViewById(R.id.home_module_student_today_detail_back);
		giveScore = (Button)findViewById(R.id.home_module_student_today_detail_giveScore);
		StudentId = (TextView)findViewById(R.id.home_module_student_today_detail_studentnNum);
		StudentName = (TextView)findViewById(R.id.home_module_student_today_detail_studentName);		
		todayScoreListView = (ListView)findViewById(R.id.home_module_student_today_detail_listView);
		isAddScoreTodayTextview = (TextView)findViewById(R.id.home_module_student_today_detail_no);		
		progressbar = (ProgressBar)findViewById(R.id.home_module_student_today_detail_progressbar);
		linearlayout = (LinearLayout)findViewById(R.id.student_info);
		
		isAddScoreTodayTextview.setVisibility(View.INVISIBLE);
		StudentId.setText(studentId);	
		StudentName.setText(studentName);		
		linearlayout.setVisibility(View.INVISIBLE);
		Dialog();
		setOnClick();				
	}

	private void setOnClick() {
		back.setOnClickListener(this);
		giveScore.setOnClickListener(this);
		todayScoreListView.setOnItemClickListener(this);
		getTimeandScore();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_module_student_today_detail_back:
			finish();
			break;
		case R.id.home_module_student_today_detail_giveScore:
			Intent intent = new Intent(mActivity, HomeOtherGiveScoreActivity.class);
			intent.putExtra("TeachingClassId", TeachingClassId);
			intent.putExtra("studentNumber", studentId);
			intent.putExtra("studentName", studentName);
			startActivity(intent);	
			break;
		}
	}
	
	private void getTimeandScore() {
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		String Name = sp.getString(Constant.SPFIELD.NAME, "");		
		params.put("TeacherName", Name);
		params.put("Term", Term);
		action = "New_GetAllStudentByTeacher";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),
				new Response.ErrorListener(){
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
	
	private Response.Listener<String> successListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject json = new JSONObject(response);
						String success = json.getString("Success");
						if (success.equals("true")) {
							JSONArray jsonArray = json.getJSONArray("ClassList");
							
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObj = jsonArray.getJSONObject(i);
								if(jsonObj.getString("TeachingClassID").equals(TeachingClassId))
								{
									JSONArray jsonArray2 = jsonObj.getJSONArray("StudentList");
									for (int j=0; j<jsonArray2.length(); j++) {
										JSONObject jsonObj2 = jsonArray2.getJSONObject(j);
										if(jsonObj2.getString("StuNumber").equals(studentId)){
											if(jsonObj2.getString("isAddedOtherScoreToday").equals("NO")){
												progressbar.setVisibility(View.INVISIBLE);
												isAddScoreTodayTextview.setVisibility(View.VISIBLE);
												dialog.dismiss();
											}else{
												JSONArray jsonarray = jsonObj2.getJSONArray("AddedOtherScoreList");
												if(jsonarray.length()==0){
													progressbar.setVisibility(View.INVISIBLE);
													isAddScoreTodayTextview.setVisibility(View.VISIBLE);
													dialog.dismiss();
												}
												else{
													TimeAndScoreList.clear();
													for(int k = 0; k < jsonarray.length(); k++){
														JSONObject each = jsonarray.getJSONObject(k);
														Map<String, String> map = new HashMap<String, String>();
														map.put("addTime", each.getString("AddedOtherScoreDate"));
														map.put("score", each.getString("AddedOtherScoreGrade"));
														map.put("AddedOtherScoreID", each.getString("AddedOtherScoreID"));
														map.put("AddedOtherScoreDesc", each.getString("AddedOtherScoreDesc"));
														TimeAndScoreList.add(map);
													}									
													progressbar.setVisibility(View.INVISIBLE);
													isAddScoreTodayTextview.setVisibility(View.INVISIBLE);
													dialog.dismiss();
													adapter.notifyDataSetChanged();
												}
											}
											linearlayout.setVisibility(View.VISIBLE);											
										}
									}
								}
							}							
						}						
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dialog.show();
		getTimeandScore();
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String AddedOtherScoreID = TimeAndScoreList.get(position).get("AddedOtherScoreID");
		String AddedOtherScoreDesc = TimeAndScoreList.get(position).get("AddedOtherScoreDesc");
		String AddedOtherScoreGrade = TimeAndScoreList.get(position).get("score");
		
		Intent intent = new Intent(mActivity, HomeOtherGiveScoreActivity.class);
		intent.putExtra("modify", "modify");//只是一个标示
		intent.putExtra("AddedOtherScoreGrade", AddedOtherScoreGrade);
		intent.putExtra("AddedOtherScoreID", AddedOtherScoreID);
		intent.putExtra("AddedOtherScoreDesc", AddedOtherScoreDesc);
		intent.putExtra("studentName", studentName);
		startActivity(intent);
	}
	
}



