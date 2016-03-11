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
import com.example.PCenter.adapter.HomeModuleStudentListViewAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeOtherStudentListActivity extends BaseActivity implements OnClickListener,OnItemClickListener  {
	private Button StudentList_back; 
	private TextView className, classcount, noData;
	private ListView studentListView;
	
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	
	private ProgressBar progressBar;
	private RelativeLayout relativelayout;
	private String  currentclassname; //接受当前班级的名字和模块名字
	private List<Map<String, String>> studentList;
	private String TeachingClassID;
	private ProgressDialog dialog;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_other_student_list);
		mActivity=this;
		
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		Intent intent =getIntent();
		currentclassname = intent.getStringExtra("TeachingClassName");
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		
		getViewObj();	
	}


	private void getViewObj() {
		StudentList_back = (Button)findViewById(R.id.home_other_studentlist_back);		
		className = (TextView)findViewById(R.id.home_other_studentlist_className);	
		classcount = (TextView)findViewById(R.id.home_other_studentlist_count);		
		studentListView = (ListView)findViewById(R.id.home_other_classlist_studentlist);
		progressBar = (ProgressBar) findViewById(R.id.home_other_studentlist_progressbar);
		noData = (TextView) findViewById(R.id.home_other_studentlist_no_data);		
		relativelayout = (RelativeLayout) findViewById(R.id.home_other_studentlist_relativelayout);
		
		
		className.setText(currentclassname);
		relativelayout.setVisibility(View.INVISIBLE);
		noData.setVisibility(View.INVISIBLE);
		
		Dialog();
		Listener();
		getStudentList();
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
	
	private void Listener() {
		StudentList_back.setOnClickListener(this);
		studentListView.setOnItemClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_other_studentlist_back:
			finish();
			break;
		}
	}
	
	
	private void getStudentList() {
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		String TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
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
									classcount.setText(jsonArray2.length()
											+ "");
									if(jsonArray2.length()==0){
										noData.setVisibility(View.VISIBLE);
									}else{
										for (int j = 0; j < jsonArray2.length(); j++){
											JSONObject json2 = jsonArray2
													.getJSONObject(j);
											Map<String, String> map = new HashMap<String, String>();											
											map.put("stuNum",
													json2.getString("StuNumber"));
											map.put("stuName",
													json2.getString("StuName"));
											map.put("ScoreGrade", "");
											studentList.add(map);							
										}
									}									
								}
							}							
							studentListView
									.setAdapter(new HomeModuleStudentListViewAdapter(mActivity, studentList));
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
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			Intent tointent = new Intent(mActivity, HomeOtherStudentScoreDetailActivity.class);
			tointent.putExtra("studentName",studentList.get(position).get("stuName"));
			tointent.putExtra("studentNumber",studentList.get(position).get("stuNum"));
			tointent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(tointent);
		//T.showShort(mActivity, "hkjkjk");
	}
}

