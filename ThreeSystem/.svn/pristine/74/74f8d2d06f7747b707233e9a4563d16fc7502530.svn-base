package com.example.PCenter.Home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.plus.URLTools;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.QuestStudentListViewAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeQuestionStudentListActivity extends BaseActivity implements OnClickListener{
	
	private Button question_student_list_back;
	private TextView textViewBanjiName, textViewBanjiCount, noData;//显示在页面上的班级与班级人数
	private ListView studentListView;
	private ProgressBar progressBar;
	private RelativeLayout relativelayout;
	private String TeachingClassID; // 保存当前班级的ID，
	private String currentclassname;// 保存Intent的班级名称
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String Term = "", TeacherName = "";// 获取学期与老师姓名
	private List<Map<String, String>> studentList;
	//private List<String> fenList;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_student_list);
		
		mActivity = this;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		Intent intent = getIntent();
		currentclassname = intent.getStringExtra("TeachingClassName");
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		
		Init();
		getQuestionStudentList();
		//getQuestionFenList();
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
	
	private void Init()
	{
		question_student_list_back = (Button) findViewById(R.id.question_student_list_back);
		textViewBanjiName = (TextView) findViewById(R.id.question_studentlist_class);		
		textViewBanjiCount = (TextView) findViewById(R.id.question_student_list_count);
		studentListView = (ListView) findViewById(R.id.question_student_list);
		progressBar = (ProgressBar) findViewById(R.id.question_student_list_progressbar);
		relativelayout =  (RelativeLayout) findViewById(R.id.question_student_list_relativeLayout);
		noData = (TextView) findViewById(R.id.question_student_list_no_data);
		
		textViewBanjiName.setText(currentclassname + "");
		relativelayout.setVisibility(View.INVISIBLE);
		noData.setVisibility(View.INVISIBLE);
		
		SetListener();
		Dialog();
	}
	
	private void SetListener()
	{
		question_student_list_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.question_student_list_back:
			finish();
			break;
		}
	}
	
	
	// 得到学生列表
	private void getQuestionStudentList() {
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "New_GetAllStudentByTeacher";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),
				errorListener());
		executeRequest(stringRequest);
	}

	private Response.Listener<String> successListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							JSONArray jsonArray = jsonObj
									.getJSONArray("ClassList");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject json = jsonArray.getJSONObject(i);
								if(json.getString("TeachingClassID").equals(TeachingClassID))
								{
									//TeachingClassID = json.getString("TeachingClassID");
									studentList = new ArrayList<Map<String, String>>();
									JSONArray jsonArray2 = json.getJSONArray("StudentList");
									textViewBanjiCount.setText(jsonArray2.length()
											+ "");
									if(jsonArray2.length()==0){
										noData.setVisibility(View.VISIBLE);
									}else{
										for (int j = 0; j < jsonArray2.length(); j++) {
											JSONObject json2 = jsonArray2
													.getJSONObject(j);
											
											Map<String, String> map = new HashMap<String, String>();
											map.put("stuName",
													json2.getString("StuName"));
											map.put("stuNumber",
													json2.getString("StuNumber"));
											map.put("isAddedQuestionToday",
													json2.getString("isAddedQuestionToday"));
											System.out.println(json2.getString("isAddedQuestionToday"));
											if(json2.getString("isAddedQuestionToday").equals("YES")){
												map.put("AddedScoreGrade", json2.getString("AddedScoreGrade"));
											}else{
												map.put("AddedScoreGrade", "C");
											}
											map.put("AlreadyQuestionNum",
													json2.getString("AlreadyQuestionNum"));
											studentList.add(map);									
										}
									}									
								}
							}
							studentListView
									.setAdapter(new QuestStudentListViewAdapter(mActivity, studentList,TeachingClassID,currentclassname));
							progressBar.setVisibility(View.INVISIBLE);
							relativelayout.setVisibility(View.VISIBLE);
							dialog.dismiss();
						} else {
							L.d(TAG, "Success is false");
						}
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
	
	
}	
	
	//得到分数列表
//	private void getQuestionFenList() {
//		Map<String, String> params = new HashMap<String, String>();
//		String action = "";
//		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
//		TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
//		params.put("TeacherName", TeacherName);
//		params.put("Term", Term);
//		action = "GetAllStudentByTeacher";
//		String url = URLTools
//				.buildURL(Constant.INTERFACE_SITE + action, params);
//		L.d(TAG, url);
//		StringRequest stringRequest = new StringRequest(url, successListener2(),
//				errorListener());
//		executeRequest(stringRequest);
//	}
//	
//	private Response.Listener<String> successListener2() {
//		return new Response.Listener<String>() {
//			@Override
//			public void onResponse(String response) {
//				if (response.length() != 0) {
//					try {
//						JSONObject jsonObj = new JSONObject(response);
//						String success = jsonObj.getString("Success");
//						if (success.equals("true")) {							
//							JSONArray jsonArray = jsonObj
//									.getJSONArray("ClassList");
//							for (int i = 0; i < jsonArray.length(); i++) {
//								JSONObject json = jsonArray.getJSONObject(i);
//								if(json.getString("TeachingClassName").equals(currentclassname))
//								{
//									fenList = new ArrayList<String>();
//									JSONArray jsonArray2 = json.getJSONArray("StudentList");
//									for (int j = 0; j < jsonArray2.length(); j++) {
//										JSONObject json2 = jsonArray2.getJSONObject(j);
//										if(json2.getString("isAddedQuestionToday").equals("YES")){
//											fenList.add(json2.getString("AddedScore"));
//											System.out.println(fenList);
//										}else{
//											fenList.add("0");
//											System.out.println(fenList);
//										}							
//									}
//								}
//							}
//							getQuestionStudentList();
//						} else {
//							L.d(TAG, "Success is false");
//						}
//					} catch (JSONException e) {
//						L.e(TAG, e.toString());
//					}
//				}
//			}
//		};
//	}

