package com.example.PCenter;

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
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.QuestRandomStudentListViewAdapter;
import com.example.PCenter.adapter.QuestStudentListViewAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuestionRandomStudentListActivity extends BaseActivity implements OnClickListener{
	
	private Button question_student_list_back;
	private TextView textViewBanjiName, textViewBanjiCount;//显示在页面上的班级与班级人数
	private ListView studentListView;
	private ProgressBar progressBar;
	private LinearLayout linearLayout;
	private String TeachingClassID; // 保存当前班级的ID，
	private String currentclassname;// 保存Intent的班级名称
	private String question_Score;// 保存Intent传过来的班级提问分数
	private String currentclassCount;// 保存Intent传过来的班级人数
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String Term = "", TeacherName = "";// 获取学期与老师姓名
	private List<Map<String, String>> studentList;
	private List<String> fenList;
	private String random;
	private String[] s;
	private String StuNum_and_questID = ";";  //将学生学号和对应的回答问题ID拼接起来
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_random_student_list);
		
		mActivity = this;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		Intent intent = getIntent();
		currentclassname = intent.getStringExtra("currentclassname");
		question_Score = intent.getStringExtra("Score");
		currentclassCount = intent.getStringExtra("classCount");
		random = intent.getStringExtra("randompick");
		s = random.split(";");
		
		Init();
	}

	
	private void Init()
	{
		question_student_list_back = (Button) findViewById(R.id.question_random_student_list_back);
		textViewBanjiName = (TextView) findViewById(R.id.question_random_studentlist_class);
		textViewBanjiName.setText(currentclassname + "");
		textViewBanjiCount = (TextView) findViewById(R.id.question_random_student_list_count);
		textViewBanjiCount.setText(currentclassCount + "");
		studentListView = (ListView) findViewById(R.id.question_random_student_list);
		progressBar = (ProgressBar) findViewById(R.id.question_random_student_list_progressbar);
		linearLayout = (LinearLayout) findViewById(R.id.question_random_student_list_linearLayout);
		linearLayout.setVisibility(View.INVISIBLE);
		SetListener();
		
		getQuestionFenList();
	}
	
	private void SetListener()
	{
		question_student_list_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.question_random_student_list_back:
			finish();
			break;
		}
	}
	
	
	// 得到学生列表
	private void getQuestionStudentList() {
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "GetAllStudentByTeacher";
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
								if(json.getString("TeachingClassName").equals(currentclassname))
								{
									TeachingClassID = json.getString("TeachingClassID");
									studentList = new ArrayList<Map<String, String>>();
									JSONArray jsonArray2 = json.getJSONArray("StudentList");
									for (int j = 0; j < s.length; j++) {																										
										//得到相应随机数里面postion的学生
										int a = Integer.parseInt(s[j]);
										JSONObject json2 = jsonArray2.getJSONObject(a);
										
										if(json2.getString("isAddedQuestionToday").equals("YES")){	
											//将学生学号和对应的回答问题ID拼接起来
											String stuNum = json2.getString("StuNumber");
											String questID = json2.getString("AddedQuestionID");
											StuNum_and_questID = StuNum_and_questID + stuNum + "=" + questID + ";";
										}
										
										Map<String, String> map = new HashMap<String, String>();
										map.put("stuName",
												json2.getString("StuName"));
										map.put("stuNumber",
												json2.getString("StuNumber"));
										map.put("isAddedQuestionToday",
												json2.getString("isAddedQuestionToday"));
										studentList.add(map);									
									}
								}
							}
							studentListView
									.setAdapter(new QuestRandomStudentListViewAdapter(mActivity, studentList,TeachingClassID,question_Score,fenList,currentclassname,StuNum_and_questID));
							progressBar.setVisibility(View.INVISIBLE);
							linearLayout.setVisibility(View.VISIBLE);
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
		
	//得到分数列表
	private void getQuestionFenList() {
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "GetAllStudentByTeacher";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener2(),
				errorListener());
		executeRequest(stringRequest);
	}
	
	private Response.Listener<String> successListener2() {
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
								if(json.getString("TeachingClassName").equals(currentclassname))
								{
									fenList = new ArrayList<String>();
									JSONArray jsonArray2 = json.getJSONArray("StudentList");
									for (int j = 0; j < s.length; j++) {
										int a = Integer.parseInt(s[j]);
										JSONObject json2 = jsonArray2.getJSONObject(a);
										if(json2.getString("isAddedQuestionToday").equals("YES")){
											fenList.add(json2.getString("AddedScore"));
											System.out.println(fenList);
										}else{
											fenList.add("0");
											System.out.println(fenList);
										}							
									}
								}
							}
							getQuestionStudentList();
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
