package com.example.PCenter;

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
import com.example.PCenter.adapter.AttenStudentListViewAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AttendenceStudentListActivity extends BaseActivity implements
		OnClickListener,OnItemClickListener{

	private Button attendance_student_list_back;
	private TextView textViewBanjiName, textViewBanjiCount, noData;
	private GridView studentListView;
	private ProgressBar progressBar;
	private RelativeLayout linearLayout;
	private String TeachingClassID; // 保存当前班级的ID，
	private String currentclassname;// 保存Intent的班级名称
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String Term = "", TeacherName = "";// 获取学期与老师姓名
	private List<Map<String, String>> studentList;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_student_list);		
		mActivity = this;
		Intent intent = getIntent();
		currentclassname = intent.getStringExtra("currentclassname");	
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		
		Init();			
		
		getStudentList();
	}

	private void Init() {
		attendance_student_list_back = (Button) findViewById(R.id.attendance_student_list_back);
		textViewBanjiName = (TextView) findViewById(R.id.attendance_studentlist_class);
		textViewBanjiName.setText(currentclassname + "");
		textViewBanjiCount = (TextView) findViewById(R.id.attendance_studentlist_count);
		progressBar = (ProgressBar) findViewById(R.id.attendance_student_list_progressbar);
		linearLayout = (RelativeLayout) findViewById(R.id.kk);		
		studentListView = (GridView) findViewById(R.id.attendance_student_list_bac);
		noData = (TextView) findViewById(R.id.attendance_student_list_no_data);
		
		linearLayout.setVisibility(View.INVISIBLE);
		noData.setVisibility(View.INVISIBLE);
		Dialog();
		setListener();
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
	
	private void setListener() {
		attendance_student_list_back.setOnClickListener(this);
	}

	// 得到学生列表
	private void getStudentList() {
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
									JSONArray jsonArray2 = json
											.getJSONArray("StudentList");
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
											map.put("stuNum",
													json2.getString("StuNumber"));
											map.put("isAddedAttendance",
													json2.getString("isAddedAttendanceToday"));
											studentList.add(map);							
										}
									}																		
								}
							}							
							studentListView
									.setAdapter(new AttenStudentListViewAdapter(mActivity, studentList,TeachingClassID,currentclassname));
							progressBar.setVisibility(View.INVISIBLE);
							linearLayout.setVisibility(View.VISIBLE);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendance_student_list_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		view = parent.getChildAt(position);
		//Button but = (Button) view.findViewById(R.id.tv_more_studentname_gridview_item);
	}
}
