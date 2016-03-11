package com.example.PCenter.More;

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
import com.example.PCenter.adapter.ClassListViewAdapter;

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
import android.widget.TextView;
import android.widget.Toast;

public class MoreModuleClassListActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ListView classesListView;
	private Button no_power, module_back;
	private ProgressBar progressBar;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String userIdentity="";//用户身份
	private String Term="",TeacherName="";//获取学期与老师姓名 
	private String currentclassname,TeachingClassID;//获取当前班级名字和ID
	private List<Map<String, String>> classesList;
	private String class_and_count2 = ";";  //保存班级名字和对应的班级人数
	private String classCount;  //保存对应班级人数
	private TextView showTerm;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_module_select_class);
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		
		InitView();
	}
	
	private void InitView()
	{
		classesListView = (ListView) findViewById(R.id.more_module_class_listview);
		no_power = (Button) findViewById(R.id.more_module_nopower_button);
		progressBar = (ProgressBar) findViewById(R.id.more_module_selecte_class_progressbar);
		module_back = (Button) findViewById(R.id.more_module_select_class_back);
		showTerm = (TextView) findViewById(R.id.more_module_show_term);
		showTerm.setVisibility(View.INVISIBLE);
		
		Dialog();
		Listner();
		judgeUserType();
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
	
	private void Listner() {
		module_back.setOnClickListener(this);
		classesListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_module_select_class_back:
			finish();
			break;

		}		
	}
	
	private void judgeUserType()
	{
		userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		//身份为学生时
		if(userIdentity.equals(Constant.USERIDENTITY[0]))
		{
			classesListView.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.INVISIBLE);
		}
		//身份为老师时
		else if(userIdentity.equals(Constant.USERIDENTITY[1]))
		{
			no_power.setVisibility(View.INVISIBLE);
			getClassList();
		}
		else
		{
			L.d(TAG, "没有获取到用户身份");
			return ;
		}
	}

	private void getClassList() {
		dialog.show();
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		showTerm.setText(Term);
		TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "New_GetAllStudentByTeacher";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action,params);
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
							classesList = new ArrayList<Map<String, String>>();
							JSONArray jsonArray = jsonObj.getJSONArray("ClassList");
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);
								//将班级与对应的人数拼接起来
								String classname = json.getString("TeachingClassName");
								JSONArray jsonArray2 = json.getJSONArray("StudentList");
								int count = jsonArray2.length();
								class_and_count2 = class_and_count2 + classname + "=" + count + ";";
								
								Map<String, String> map = new HashMap<String, String>();
								map.put("currentTerm", json.getString("Term"));
								map.put("className", json.getString("TeachingClassName"));
								map.put("TeachingClassID", json.getString("TeachingClassID"));
								classesList.add(map);
							}
							classesListView.setAdapter(new ClassListViewAdapter(mActivity, classesList));
							progressBar.setVisibility(View.INVISIBLE);
							showTerm.setVisibility(View.VISIBLE);
							dialog.dismiss();
						}else{
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
		TeachingClassID = classesList.get(position).get("TeachingClassID");
		currentclassname = classesList.get(position).get("className");
		
		Intent intent = new Intent(mActivity, MoreModule_SetModule.class);	
		intent.putExtra("TeachingClassID", TeachingClassID);
		startActivity(intent);
	}
}
