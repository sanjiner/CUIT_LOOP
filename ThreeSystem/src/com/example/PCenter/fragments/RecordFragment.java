package com.example.PCenter.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.Constant;
import com.example.PCenter.MainActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.Home.MyGridView;
import com.example.PCenter.Record.Record2_Detail_Activity;
import com.example.PCenter.Record.Record_studentList_Activity;
import com.example.PCenter.adapter.HomeModuleAdapter;

import android.app.ProgressDialog;
import android.content.Context;
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

public class RecordFragment extends BaseFragment implements OnItemClickListener{
	private String FILE = Constant.USERINFO_SP;
	private SharedPreferences sp;
	private String identity;
	private LinearLayout layout;
	public String[] groups = { "考勤", "作业" ,"提问" ,"其他"};
	private ProgressBar progressBar;
	private ProgressDialog dialog;
	private Button setTerm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_record, container, false);
		mActivity = (MainActivity) getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		identity = sp.getString(Constant.SPFIELD.USERIDENTITY,"");
		Dialog();
		//dialog.show();
		Init(view);	
//		if(identity.equalsIgnoreCase("老师")){
//			getClasses();
//		}else{
//			getClasses2();
//		}
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
	
	private void Init(View view){
		layout = (LinearLayout) view.findViewById(R.id.record_all_list_linear);
		progressBar = (ProgressBar) view.findViewById(R.id.recore_progressbar);
		setTerm = (Button) view.findViewById(R.id.record_button_set_term);
		setTerm.setVisibility(View.INVISIBLE);
	}
	
	//学生得到列表
	private void getClasses2() {
		String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		if(TextUtils.isEmpty(Term))
		{
			progressBar.setVisibility(View.INVISIBLE);
			setTerm.setVisibility(View.VISIBLE);
			dialog.dismiss();
			return;
		}
		String Id = sp.getString(Constant.SPFIELD.NAME, "");
		Map<String, String> params = new HashMap<String, String>();
		String action = "New_StudentGetAllClassModuleByTerm";
		params.put("StuNumber", Id);
		params.put("Term", Term);
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url,successListener2(),new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mActivity,
						VolleyErrorHelper.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(stringRequest);
	}
	private Listener<String> successListener2() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(response.length() != 0){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(success.equals("true")){
							
							JSONArray classlist = jsonObj.getJSONArray("ClassList");
							String classname, teachername, TeachingClassID;
							layout.removeAllViews();
							for(int i = 0; i < classlist.length(); i++){
								List<Map<String, String>> tpmoduleList =new ArrayList<Map<String,String>>();
								JSONObject each = classlist.getJSONObject(i);
								classname = each.getString("TeachingClassName");
								teachername = each.getString("TeacherName");
								TeachingClassID = each.getString("TeachingClassID");
								JSONArray mlist = each.getJSONArray("ModuleList");
								for(int j = 0; j < 4; j++){
									Map<String, String> map = new HashMap<String, String>();									
									map.put("ModuleName", groups[j]);
									map.put("TeachingClassName",teachername);
									map.put("TeachingClassID",TeachingClassID);
//									if(j==0){map.put("ModuleName","考勤");}
//									else{map.put("ModuleName","作业");}
									tpmoduleList.add(map);
								}
								for(int j = 0; j < mlist.length(); j++ ){
									JSONObject module = mlist.getJSONObject(j);
									Map<String, String> map = new HashMap<String, String>();
									map.put("ModuleID", module.getString("ModuleID"));
									map.put("ModuleName", module.getString("ModuleName"));
									map.put("TeachingClassID",TeachingClassID);
									tpmoduleList.add(map);
								}
								LayoutInflater	inflater2 = LayoutInflater.from(mActivity);
								View view2 = inflater2.inflate(R.layout.home_linearlayout, null);
								TextView ClassName = (TextView) view2.findViewById(R.id.home_linearlayout_textview);
								MyGridView ModuleList = (MyGridView) view2.findViewById(R.id.home_linearlayout_gridview);
								ClassName.setText(classname);
								ModuleList.setAdapter(new HomeModuleAdapter(mActivity, tpmoduleList));
								ModuleList.setOnItemClickListener(RecordFragment.this);
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
					
	//得到班级
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
		String TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "New_GetAllStudentByTeacher";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action,params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url,successListener(),new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mActivity,
						VolleyErrorHelper.getErrorType(error),
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
							JSONArray jsonArray = jsonObj.getJSONArray("ClassList");
							layout.removeAllViews();
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);
								String TeachingClassName = json.getString("TeachingClassName");
								String TeachingClassID = json.getString("TeachingClassID");
								List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>();								
								for(int k=0; k<4;++k){
									Map<String, String> map = new HashMap<String, String>();									
									map.put("ModuleName", groups[k]);
									map.put("TeachingClassName",TeachingClassName);
									map.put("TeachingClassID",TeachingClassID);
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
									map.put("TeachingClassID",TeachingClassID);
									map.put("ModuleID",json2.getString("ModuleID"));
									moduleList.add(map);
								}
								LayoutInflater	inflater2 = LayoutInflater.from(mActivity);
								View view2 = inflater2.inflate(R.layout.home_linearlayout, null);
								TextView ClassName = (TextView) view2.findViewById(R.id.home_linearlayout_textview);
								MyGridView ModuleList = (MyGridView) view2.findViewById(R.id.home_linearlayout_gridview);
								ClassName.setText(TeachingClassName);
								ModuleList.setAdapter(new HomeModuleAdapter(mActivity, moduleList));
								ModuleList.setOnItemClickListener(RecordFragment.this);
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
		Map<String, Object> map= (Map<String, Object>)parent.getAdapter().getItem(position);
		String TeachingClassID = (String) map.get("TeachingClassID");
		String TeachingClassName = (String) map.get("TeachingClassName");
		if(identity.equalsIgnoreCase("老师")){
			Intent intent;
			if(map.get("ModuleName").equals("考勤")){
				intent = new Intent(mActivity, Record_studentList_Activity.class);	
				intent.putExtra("number", "0");
			}else if(map.get("ModuleName").equals("作业")){
				intent = new Intent(mActivity, Record_studentList_Activity.class);	
				intent.putExtra("number", "2");
			}else if(map.get("ModuleName").equals("提问")){
				intent = new Intent(mActivity, Record_studentList_Activity.class);	
				intent.putExtra("number", "1");
			}else if(map.get("ModuleName").equals("其他")){
				intent = new Intent(mActivity, Record_studentList_Activity.class);	
				intent.putExtra("number", "3");
			}else{
				intent = new Intent(mActivity, Record_studentList_Activity.class);	
				intent.putExtra("number", (String)map.get("ModuleID"));
			}
			intent.putExtra("TeachingClassName", TeachingClassName);
			intent.putExtra("TeachingClassID", TeachingClassID);
			intent.putExtra("modulename", (String)map.get("ModuleName"));
			startActivity(intent);
		}else{
			Intent intent = null;
			if(map.get("ModuleName").equals("考勤")){
				 intent = new Intent(mActivity, Record2_Detail_Activity.class);	
//				attenintent.putExtra("TeachingClassName", TeachingClassName);
				intent.putExtra("number", "0");
			}else if(map.get("ModuleName").equals("作业")){
				intent = new Intent(mActivity, Record2_Detail_Activity.class);	
//				homeworkintent.putExtra("TeachingClassName", TeachingClassName);
				intent.putExtra("number", "2");
			}else if(map.get("ModuleName").equals("提问")){
				intent = new Intent(mActivity, Record2_Detail_Activity.class);
				intent.putExtra("number", "1");
			}else if(map.get("ModuleName").equals("其他")){
				intent = new Intent(mActivity, Record2_Detail_Activity.class);
				intent.putExtra("number", "3");
			}else{
				intent = new Intent(mActivity, Record2_Detail_Activity.class);	
//				otherintent.putExtra("TeachingClassName", TeachingClassName);
				intent.putExtra("number", (String)map.get("ModuleID"));
			}
			intent.putExtra("teachingclassid", TeachingClassID);
			intent.putExtra("modulename", (String)map.get("ModuleName"));
			startActivity(intent);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//T.showShort(mActivity, "REcordFragmentonResume");
		if(this.isAdded()){
			dialog.show();
			if(identity.equalsIgnoreCase("老师")){
				getClasses();
			}else{
				getClasses2();
			}
		}
	}
}
