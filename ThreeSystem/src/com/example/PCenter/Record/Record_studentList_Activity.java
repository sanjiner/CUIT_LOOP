package com.example.PCenter.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.RecordStudentListAdapter;

public class Record_studentList_Activity extends BaseActivity implements OnClickListener, OnItemClickListener{
	private Button back, charList;
	private String number, teachingClassId;
	private ListView StudentList;
	private List<Map<String, String>> studentList;
	private ProgressBar progressbar;
	private String [] name;
	private int [] score;
	private TextView className, count, no; //moduleName
	private String s_className, s_moduleName;
	private LinearLayout linearlayout;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_students_score);
		Dialog();
		dialog.show();
		Intent intent = getIntent();
		number = intent.getStringExtra("number");
		teachingClassId = intent.getStringExtra("TeachingClassID");
		s_moduleName = intent.getStringExtra("modulename");
		s_className = intent.getStringExtra("TeachingClassName");
		init();
		getStudentList();
	}
	
	private void getStudentList() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TeachingClassID", teachingClassId);
		params.put("RecordType", number);
		String action = "New_GetStuScoreRecordByTeachingClass";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action, params);
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
				// L.d(TAG, response);
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							JSONArray list = null ; 
							if(number.equals("0")||number.equals("1")||number.equals("2")||number.equals("3")){
								switch(Integer.parseInt(number)){
								case 0:list = jsonObj.getJSONArray("AttendanceRecordList");break;
								case 1:list = jsonObj.getJSONArray("QuestionRecordList");break;
								case 2:list = jsonObj.getJSONArray("StudentsWorkRecordList");break;
								case 3:list = jsonObj.getJSONArray("OtherRecordList");break;
//								case 4:list = jsonObj.getJSONArray("TotalScoreList");break;
								}
								linearlayout.setVisibility(View.VISIBLE);
							}else{
								list = jsonObj.getJSONArray("ModuleItemRecordList");
							}
							count.setText(""+list.length());
							name = new String [list.length()];
							score = new int [list.length()];
							if(list.length() == 0){//暂无数据
								no.setVisibility(View.VISIBLE);
							}
							for (int i = 0; i < list.length(); i++){
								JSONObject one = list.getJSONObject(i);
								Map<String, String> map = new HashMap<String, String>();
								map.put("StuNumber", one.getString("StuNumber"));
								name[i] = one.getString("StuName");
								map.put("StuName", one.getString("StuName"));
								if(number.equals("4")){
									map.put("AllScore", one.getString("TotalScore"));
								}else{
									map.put("AllScore", one.getString("AllScore"));
								}
								score[i] = Integer.parseInt(map.get("AllScore"));
								studentList.add(map);
							}
							StudentList.setAdapter(new RecordStudentListAdapter(mActivity, studentList));
							StudentList.setVisibility(View.VISIBLE);
							dialog.dismiss();
							progressbar.setVisibility(View.INVISIBLE);
//							charList.setVisibility(View.VISIBLE);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	private void init() {
		getObjView();
		setOnclick();
	}
	private void setOnclick() {
		back.setOnClickListener(this);
		charList.setOnClickListener(this);
		StudentList.setOnItemClickListener(this);
	}
	private void getObjView() {
		back = (Button)findViewById(R.id.student_score_back);
		charList = (Button)findViewById(R.id.student_score_charLine);
		StudentList = (ListView)findViewById(R.id.Record_StudentList);
		StudentList.setVisibility(View.INVISIBLE);
		studentList = new ArrayList<Map<String, String>>();
		progressbar = (ProgressBar)findViewById(R.id.record_student_progressbar);
		className = (TextView)findViewById(R.id.record_studentList_className);
		className.setText(s_className);
//		moduleName = (TextView)findViewById(R.id.record_studentList_moduleName);
//		moduleName.setText(s_moduleName);
		count = (TextView)findViewById(R.id.record_studentList_count);
		linearlayout = (LinearLayout)findViewById(R.id.record_studentlist_info);
		no = (TextView)findViewById(R.id.record_students_score_no);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.student_score_back:
			finish();
			break;
//		case R.id.student_score_charLine:
//			Bundle b=new Bundle();
//			Intent intent = new Intent(mActivity, Record_charActivity.class);
//			b.putStringArray("name", name);
//			b.putIntArray("score", score);
//			b.putString("number", number);
//			intent.putExtras(b);
//			startActivity(intent);
//			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id){
		if(!number.equals("4")){
			Intent intent = new Intent(mActivity, Record_StudentDetail_Activity.class);
			intent.putExtra("number", number);
			intent.putExtra("TeachingClassID", teachingClassId);
			intent.putExtra("StuNumber", studentList.get(position).get("StuNumber"));
			intent.putExtra("className", s_className);
			intent.putExtra("moduleName", s_moduleName);
			startActivity(intent);
		}
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
}

