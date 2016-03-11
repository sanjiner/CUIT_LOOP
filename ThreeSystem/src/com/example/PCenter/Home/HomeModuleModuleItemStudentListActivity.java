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
import com.common.tools.T;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.Constant.SPFIELD;
import com.example.PCenter.adapter.HomeModuleStudentListViewAdapter;

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

public class HomeModuleModuleItemStudentListActivity extends BaseActivity implements OnClickListener,OnItemClickListener  {
	private Button StudentList_back; 
	private TextView  noData;
	private ListView studentListView;
	
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	private HomeModuleStudentListViewAdapter adapter;
	private ProgressBar progressBar;
	private String  ModuleItemName, ModuleItemID, moduleID; //接受当前班级的模块下面的模块项名字
	private List<Map<String, String>> studentList;
	private String TeachingClassID;
	private ProgressDialog dialog;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_module_moduleitem_student_list);
		mActivity=this;
		
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		Intent intent =getIntent();
		ModuleItemName = intent.getStringExtra("ModuleItemName");
		ModuleItemID = intent.getStringExtra("ModuleItemID");
		moduleID = intent.getStringExtra("moduleID");
		
		getViewObj();	
	}


	private void getViewObj() {
		StudentList_back = (Button)findViewById(R.id.home_module_moduleitem_studentlist_back);		
		studentListView = (ListView)findViewById(R.id.home_module_moduleitem_studentlist_view);
		progressBar = (ProgressBar) findViewById(R.id.home_module_moduleitem_studentlist_progressbar);
		noData = (TextView) findViewById(R.id.home_module_moduleitem_studentlist_no_data);		
		
		noData.setVisibility(View.INVISIBLE);
		
		Dialog();
		Listener();
		//getStudentList();
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
		case R.id.home_module_moduleitem_studentlist_back:
			finish();
			break;
		}
	}
	
	
	private void getStudentList()
	{
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("ModuleID", moduleID);
		action = "New_GetAllModuleItemRecordByModuleID";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),new Response.ErrorListener() {
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
							studentList = new ArrayList<Map<String, String>>();						
							JSONArray jsonArray = jsonObj.getJSONArray("ModuleItemList");
							for (int i=0; i<jsonArray.length(); i++) {
								JSONObject json = jsonArray.getJSONObject(i);
								if(json.getString("ModuleItemID").equals(ModuleItemID)){
									JSONArray jsonArray2 = json.getJSONArray("ModuleScoreRecordList");
									if(jsonArray2.length()==0){
										progressBar.setVisibility(View.INVISIBLE);
										noData.setVisibility((View.VISIBLE));
									}else{
										for (int j=0; j<jsonArray2.length(); j++) {
											JSONObject json2 = jsonArray2.getJSONObject(j);
											//json2.opt(name)
											Map<String, String> map = new HashMap<String, String>();
											map.put("stuNum",json2.getString("StudentNum"));
											map.put("stuName",json2.getString("StuName"));
											//System.out.println(json2.getString("ScoreGrade"));
											if(json2.opt("ScoreGrade")!=null){
												map.put("ScoreGrade", json2.getString("ScoreGrade"));
												map.put("ModuleScoreRecordID", json2.getString("ModuleScoreRecordID"));
											}else{
												map.put("ScoreGrade", "");
											}
											studentList.add(map);											
										}
										adapter = new HomeModuleStudentListViewAdapter(mActivity, studentList);
										studentListView.setAdapter(adapter);
										progressBar.setVisibility(View.INVISIBLE);
										noData.setVisibility((View.INVISIBLE));
									}
								}
							}														
						} else {
							L.d(TAG, "Success is false");
						}
						dialog.dismiss();
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
		Intent tointent = new Intent(mActivity, HomeModuleModuleItemGiveGradeActivity.class);
		tointent.putExtra("ScoreGrade",studentList.get(position).get("ScoreGrade"));		
		tointent.putExtra("StuNumber",studentList.get(position).get("stuNum"));
		tointent.putExtra("StuName",studentList.get(position).get("stuName"));
		tointent.putExtra("ModuleItemID", ModuleItemID);			
		tointent.putExtra("ModuleScoreRecordID", studentList.get(position).get("ModuleScoreRecordID"));
		startActivity(tointent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		dialog.show();
		getStudentList();
	}
}
