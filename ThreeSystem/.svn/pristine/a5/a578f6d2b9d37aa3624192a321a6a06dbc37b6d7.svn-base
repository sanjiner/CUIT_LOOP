package com.example.PCenter.fragments;

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
import com.example.PCenter.AttendenceStudentListActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.Homework_tea_class_list_activity;
import com.example.PCenter.MainActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.RadioCheckMoreInterface;
import com.example.PCenter.homework_state_activity;
import com.example.PCenter.Home.HomeModuleModuleItemListActivity;
import com.example.PCenter.Home.HomeOtherStudentListActivity;
import com.example.PCenter.Home.MyGridView;
import com.example.PCenter.Home.HomeModuleModuleItemStudentListActivity;
import com.example.PCenter.Home.HomeQuestionStudentListActivity;
import com.example.PCenter.adapter.HomeModuleAdapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends BaseFragment implements OnItemClickListener{
	
	private String FILE = Constant.USERINFO_SP;
	private SharedPreferences sp;
	private String identity;
	private LinearLayout layout;
	private List<Map<String, String>> classesList;
	//private List<Map<String, String>> tmpList;
	private String class_and_count2 = ";";  //保存班级名字和对应的班级人数
	public String[] groups = { "考勤", "作业", "提问", "其他"};
	private ProgressBar progressBar;
	private ProgressDialog dialog;
	private Button setTerm;
     
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home, container, false);
		mActivity = (MainActivity) getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		identity = sp.getString(Constant.SPFIELD.USERIDENTITY,"");
		
		Dialog();
		
		Init(view);
		//dialog.show();
		//getClasses();	
		return view;
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
	
	private void Init(View view)
	{
		layout = (LinearLayout) view.findViewById(R.id.home_all_list_linear);
		progressBar = (ProgressBar) view.findViewById(R.id.home_progressbar);	
		setTerm = (Button) view.findViewById(R.id.home_button_set_term);
		setTerm.setVisibility(View.INVISIBLE);
	}				
	
	//老师或者学生得到班级和模块列表
	private void getClasses(){
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		if(TextUtils.isEmpty(Term))
		{
			progressBar.setVisibility(View.INVISIBLE);
			setTerm.setVisibility(View.VISIBLE);
			dialog.dismiss();
			return;
		}
		String Name = sp.getString(Constant.SPFIELD.NAME, "");
		if(identity.equals("老师")){		
			params.put("TeacherName", Name);
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
		}else{			
			params.put("StuNumber", Name);
			params.put("Term", Term);
			action = "New_StudentGetAllClassModuleByTerm";
			String url = URLTools.buildURL(Constant.INTERFACE_SITE + action,params);
			L.d(TAG, url);
			StringRequest stringRequest = new StringRequest(url,successListener2(),new Response.ErrorListener() {
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
		
	}
	//老师得到班级和模块列表成功后
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
							layout.removeAllViews();
							
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);
								//将班级与对应的人数拼接起来
								String classname = json.getString("TeachingClassName");
								JSONArray jsonArray2 = json.getJSONArray("StudentList");
								int count = jsonArray2.length();
								class_and_count2 = class_and_count2 + classname + "=" + count + ";";
								
								String TeachingClassName = json.getString("TeachingClassName");
								String TeachingClassID = json.getString("TeachingClassID");
								
								List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>();								
								for(int k=0; k<groups.length; ++k){
									Map<String, String> map = new HashMap<String, String>();									
									map.put("ModuleName", groups[k]);
									map.put("TeachingClassName",TeachingClassName);
									map.put("TeachingClassID", TeachingClassID);
									tmpList.add(map);
								}
								
								
								List<Map<String, String>> moduleList =new ArrayList<Map<String,String>>();
								moduleList.addAll(tmpList);
								JSONArray jsonArray3 = json.getJSONArray("ModuleList");
								for (int j = 0; j < jsonArray3.length(); j++) {					
									JSONObject json2 = jsonArray3.getJSONObject(j);
									Map<String, String> map = new HashMap<String, String>();
									map.put("ModuleName",json2.getString("ModuleName"));
									map.put("TeachingClassName",TeachingClassName);
									map.put("TeachingClassID", TeachingClassID);
									map.put("ModuleID",json2.getString("ModuleID"));
									moduleList.add(map);
								}
								
								
								LayoutInflater	inflater2 = LayoutInflater.from(mActivity);
								View view2 = inflater2.inflate(R.layout.home_linearlayout, null);
								TextView ClassName = (TextView) view2.findViewById(R.id.home_linearlayout_textview);
								MyGridView ModuleList = (MyGridView) view2.findViewById(R.id.home_linearlayout_gridview);
								ClassName.setText(TeachingClassName);
								ModuleList.setAdapter(new HomeModuleAdapter(mActivity, moduleList));
								ModuleList.setOnItemClickListener(HomeFragment.this);
								//ModuleList.setOnClickListener(this);
								layout.addView(view2);									
							}
							progressBar.setVisibility(View.GONE);
							setTerm.setVisibility(View.INVISIBLE);
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

	//学生得到班级和模块列表成功后
	private Response.Listener<String> successListener2(){
		return new Response.Listener<String>(){
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(response.length() != 0){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(success.equals("true")){
							JSONArray jsonArray = jsonObj.getJSONArray("ClassList");
							layout.removeAllViews();
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);			
								
								String TeachingClassName = json.getString("TeachingClassName");
								String TeachingClassID = json.getString("TeachingClassID");
								
								List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>();								
								Map<String, String> map = new HashMap<String, String>();									
								map.put("ModuleName", groups[1]);
								map.put("TeachingClassName",TeachingClassName);
								map.put("TeachingClassID", TeachingClassID);
								tmpList.add(map);
								
//								List<Map<String, String>> moduleList =new ArrayList<Map<String,String>>();
//								moduleList.addAll(tmpList);
//								JSONArray jsonArray3 = json.getJSONArray("ModuleList");
//								for (int j = 0; j < jsonArray3.length(); j++) {					
//									JSONObject json2 = jsonArray3.getJSONObject(j);
//									Map<String, String> map1 = new HashMap<String, String>();
//									map1.put("ModuleName",json2.getString("ModuleName"));
//									map1.put("TeachingClassName",TeachingClassName);
//									map1.put("TeachingClassID", TeachingClassID);
//									map1.put("ModuleID",json2.getString("ModuleID"));
//									moduleList.add(map1);
//								}
								
								LayoutInflater	inflater2 = LayoutInflater.from(mActivity);
								View view2 = inflater2.inflate(R.layout.home_linearlayout, null);
								TextView ClassName = (TextView) view2.findViewById(R.id.home_linearlayout_textview);
								MyGridView ModuleList = (MyGridView) view2.findViewById(R.id.home_linearlayout_gridview);
								ClassName.setText(TeachingClassName);
								ModuleList.setAdapter(new HomeModuleAdapter(mActivity, tmpList));
								ModuleList.setOnItemClickListener(HomeFragment.this);
								//ModuleList.setOnClickListener(this);
								layout.addView(view2);									
							}
							progressBar.setVisibility(View.GONE);
							setTerm.setVisibility(View.INVISIBLE);
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
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {		
		@SuppressWarnings("unchecked")
		Map<String, Object> map= (Map<String, Object>)parent.getAdapter().getItem(position);
		//T.showShort(mActivity, map.get("ModuleName")+"");
		String TeachingClassName = (String) map.get("TeachingClassName");
		String TeachingClassID = (String) map.get("TeachingClassID");
		
		if(map.get("ModuleName").equals("考勤")){
			Intent attenintent = new Intent(mActivity, AttendenceStudentListActivity.class);	
			attenintent.putExtra("currentclassname", TeachingClassName);
			attenintent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(attenintent);
		}else if(map.get("ModuleName").equals("作业")){
			if(identity.equals("学生")){
				Intent homeworkintent = new Intent(mActivity, homework_state_activity.class);	
				homeworkintent.putExtra("TeachingClassID", TeachingClassID);
				startActivity(homeworkintent);
			}else{
				Intent homeworkintent = new Intent(mActivity, Homework_tea_class_list_activity.class);	
				homeworkintent.putExtra("TeachingClassID", TeachingClassID);
				startActivity(homeworkintent);
			}
		}else if(map.get("ModuleName").equals("提问")){			
			Intent questintent = new Intent(mActivity, HomeQuestionStudentListActivity.class);	
			questintent.putExtra("TeachingClassName", TeachingClassName);
			questintent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(questintent);
		}else if(map.get("ModuleName").equals("其他")){
			Intent otherintent = new Intent(mActivity, HomeOtherStudentListActivity.class);
			otherintent.putExtra("TeachingClassName", TeachingClassName);
			otherintent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(otherintent);
		}	
		else{
			if(identity.equals("学生")){
				T.showShort(mActivity, map.get("ModuleName")+"");
			}else{
				Intent moduleintent = new Intent(mActivity, HomeModuleModuleItemListActivity.class);	
				moduleintent.putExtra("TeachingClassName", TeachingClassName);
				moduleintent.putExtra("modulename", (String)map.get("ModuleName"));
				moduleintent.putExtra("moduleID", (String)map.get("ModuleID"));
				startActivity(moduleintent);
			}
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//T.showShort(mActivity, "HomeFragmentonResume");
		if(this.isAdded()){
			dialog.show();
			getClasses();
		}
	}
}
