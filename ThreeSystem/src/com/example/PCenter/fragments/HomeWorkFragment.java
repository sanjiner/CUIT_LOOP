package com.example.PCenter.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
//import com.example.PCenter.AttendenceStudentList;
import com.example.PCenter.Constant;
import com.example.PCenter.DownrefreshListView;
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.Homework_tea_class_list_activity;
import com.example.PCenter.MD5ENCODE;
import com.example.PCenter.MainActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.RadioCheckMoreInterface;
import com.example.PCenter.homework_state_activity;
import com.example.PCenter.homework_tea_addhomework;
import com.example.PCenter.adapter.ClassListViewAdapter_homework;
import com.example.PCenter.adapter.ClassListViewAdapter_homework_list;
import com.example.PCenter.adapter.LvAdapter;
import com.example.PCenter.checkbox_damobean;



public class HomeWorkFragment extends BaseFragment implements OnClickListener,
OnItemClickListener {
//	private ListView termListView;
	private SharedPreferences sp;
	public  static List<Map<String, String>> termsList;
	public static List<checkbox_damobean> demoDatas;
	private String StuNumber;
	private String TeacherName;
	private String currentTerm;
	private String TeachingClassID_1;
	private String TeachingClassID_2;
	private String stu_isNull="NO";
	private String tea_isNull="NO";
	public static String Tea_TeachingClassID;
	public static String Stu_TeachingClassID;
	public static String TeachingClassName;
	private String currentTeachingClassName;
	private String userIdentity="";
	private String TeacherPasswd="";
	private String Passwd="";
	private String FILE = Constant.USERINFO_SP;
	private Button homework_add=null;
	public JSONArray jsonArray;
	String action="";
	private JSONObject json;
	private List<Map<String, String>> classesList=null;
	private List<Map<String, String>> classesList2=null;
	private ProgressBar progressBar;//加载列表时转圈的那个
	private Button set_term;//没有相关权限的那个按钮
	private TextView withoutdata_textview_1;
	private TextView homework_attendance_show_term;
	private ProgressDialog dialog;
	private DownrefreshListView termListView;
	private LvAdapter adapter;  
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.activity_homework, container, false);
		mActivity = (MainActivity) getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		currentTerm=sp.getString(Constant.SPFIELD.CURRENTTERM,"");
		Dialog();
		dialog.show();
		getViewObj(view);
		setListener();
		getTerms();
		
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
        dialog.show();
	}
	

	private void getViewObj(View view)
	{
		termListView=(DownrefreshListView) view.findViewById(R.id.homework_ListView);
		homework_add=(Button) view.findViewById(R.id.homework_add);
		progressBar = (ProgressBar) view.findViewById(R.id.homework_progressbar);
		set_term = (Button) view.findViewById(R.id.homework_button_set_term);
		withoutdata_textview_1=(TextView) view.findViewById(R.id.withoutdata_textview_1);
		homework_attendance_show_term=(TextView) view.findViewById(R.id.homework_attendance_show_term);
//		lv = (DownrefreshListView) view.findViewById(R.id.homework_ListView);
		homework_attendance_show_term.setVisibility(View.INVISIBLE);
		set_term.setVisibility(View.INVISIBLE);
		
	}
	


	private void getTerms()
	{
		withoutdata_textview_1.setVisibility(View.INVISIBLE);
		currentTerm=sp.getString(Constant.SPFIELD.CURRENTTERM,"");
		if(currentTerm=="")
		{
			dialog.dismiss();
			set_term.setVisibility(View.VISIBLE);
			homework_attendance_show_term.setVisibility(View.INVISIBLE);
			
		}
		else
		{
			set_term.setVisibility(View.INVISIBLE);
			homework_attendance_show_term.setText(currentTerm);
			homework_attendance_show_term.setVisibility(View.VISIBLE);
			
		}
		Map<String, String> params = new HashMap<String, String>();
				
		userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		L.d(TAG,userIdentity);
		homework_add.setVisibility(View.INVISIBLE);
		if (userIdentity.equals(Constant.USERIDENTITY[0]))
		{			
			StuNumber= sp.getString(Constant.SPFIELD.NAME, "");
			params.put("StuNumber",StuNumber);
			params.put("Term",currentTerm);
			action="StudentGetAllClassByTerm";
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
					dialog.dismiss();
				}
			});
			executeRequest(stringRequest);
		}
		else if(userIdentity.equals(Constant.USERIDENTITY[1]))
		{
			if(currentTerm=="")
			{
				homework_add.setVisibility(View.INVISIBLE);
			}
			else{
			homework_add.setVisibility(View.VISIBLE);
			}
			TeacherName=sp.getString(Constant.SPFIELD.NAME, "");
			Passwd=sp.getString(Constant.SPFIELD.PASSWORD, "");
			TeacherPasswd=MD5ENCODE.MD5Encode(Passwd).toString();
			params.put("TeacherName", TeacherName);
			params.put("TeacherPasswd",TeacherPasswd);
			action="TeacherLogin";
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
					dialog.dismiss();
					
				}
			});
			executeRequest(stringRequest);
		}
		
	}
	
	private void setListener()
	{
		homework_add.setOnClickListener(this);
		termListView.setOnItemClickListener(this);
		
		termListView.setonRefreshListener(new OnRefreshListener() {  
			  
            @Override  
            public void onRefresh() {  
                new AsyncTask<Void, Void, Void>() {  
                    protected Void doInBackground(Void... params) {  
                        try {  
                            Thread.sleep(1000); 
                           
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }  
//                        list.add("刷新后添加的内容");
                        
                        return null;  
                    }  
  
                    @Override  
                    protected void onPostExecute(Void result) {  
//                        adapter.notifyDataSetChanged();  
//                    	Dialog();
//                		getViewObj(view);
//                		setListener();
                    	 getTerms();
                    	termListView.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        });  
		
//		lv.setOnItemClickListener(this);
	}

	private Response.Listener<String> successListener()
	{
		return new Response.Listener<String>()
				{
			public void onResponse(String response)
					{
						L.d(TAG, response);
						if (response.length() != 0)
						{
							try
							{
								if(userIdentity.equals(Constant.USERIDENTITY[0]))
							{
									JSONObject jsonObj = new JSONObject(response);
									String success = jsonObj.getString("Success");
									if (success.equals("true"))
									{
										classesList = new ArrayList<Map<String, String>>();
										 jsonArray = jsonObj.getJSONArray("ClassList");
										 if(jsonArray.length()==0)
										 {
											 stu_isNull="YES";
											 withoutdata_textview_1.setVisibility(View.VISIBLE);
										 }
										 else 
										 {
//											 
											for (int i = 0; i < jsonArray.length(); i++)
											{
												 json = jsonArray.getJSONObject(i);	
		
												 Map<String, String> map = new HashMap<String, String>();
													map.put("TeacherName", json.getString("TeacherName"));
													map.put("TeachingClassName", json.getString("TeachingClassName"));
													map.put("TeachingClassID",json.getString("TeachingClassID"));
													classesList.add(map);
											}
											
											
											termListView.setAdapter(new  ClassListViewAdapter_homework_list(mActivity, classesList));
										 }
										 
										 progressBar.setVisibility(View.INVISIBLE);
										 dialog.dismiss();
									}
									else 
									{
										L.d(TAG, "Success is false");
									} 
							}
								else if(userIdentity.equals(Constant.USERIDENTITY[1]))
								{
									JSONObject jsonObj = new JSONObject(response);
									String success = jsonObj.getString("Success");
									if (success.equals("true"))
								{
									classesList2 = new ArrayList<Map<String, String>>();
									termsList= new ArrayList<Map<String, String>>();
									 jsonArray = jsonObj.getJSONArray("ClassList");
								
									 if(jsonArray.length()==0)
									 {
										 tea_isNull="YES";
										 withoutdata_textview_1.setVisibility(View.VISIBLE);
									 }
									 else 
									 {
										 demoDatas = new ArrayList<checkbox_damobean>();
									 for (int i = 0; i < jsonArray.length(); i++)
										 {
											 json = jsonArray.getJSONObject(i);	
	
											 Map<String, String> map = new HashMap<String, String>();
											 Map<String, String> map2 = new HashMap<String, String>();
											 
											 
											 
											 
											if(json.getString("Term").equals(currentTerm))
											 {
											 	map.put("Term", json.getString("Term"));
											 	
												map.put("TeachingClassName", json.getString("TeachingClassName"));
												
												map.put("TeachingClassID", json.getString("TeachingClassID"));
												map2.put("TeachingClassID", json.getString("TeachingClassID"));
												map2.put("TeachingClassName",json.getString("TeachingClassName"));
												demoDatas.add(new checkbox_damobean(json.getString("TeachingClassID"), true));
												classesList2.add(map);
												termsList.add(map2);
											 }							
										}
									 
									 termListView.setAdapter(new  ClassListViewAdapter_homework(mActivity, classesList2));
									 }
									 progressBar.setVisibility(View.INVISIBLE);
									 dialog.dismiss();
									}
								}
							}
							catch(JSONException e)
							{
								
									L.e(TAG, e.toString());
								
							}
						}
					}
				};	
	}
	
	public void onItemClick(AdapterView<?>parent, View view, int position,
			long id) {
		if(userIdentity.equals(Constant.USERIDENTITY[0]))
		{
			if(stu_isNull.equals("YES"))
			{
				Toast.makeText(mActivity, "暂无作业数据", Toast.LENGTH_LONG).show();
				return;
			}
			else if(stu_isNull.equals("NO"))
			{
			Map<String, String> map = classesList.get(position-1);
			Stu_TeachingClassID=map.get("TeachingClassID");
			Intent intent = new Intent(getActivity(), homework_state_activity.class);
			intent.putExtra("TeachingClassID", Stu_TeachingClassID);
			startActivity(intent);
			}
		}
		else if(userIdentity.equals(Constant.USERIDENTITY[1]))
		{
			if(tea_isNull.equals("YES"))
			{
				Toast.makeText(mActivity, "暂无数据", Toast.LENGTH_LONG).show();
				return;
			}
			else if(tea_isNull.equals("NO"))
			{
			Map<String, String> map = classesList2.get(position-1);
			Tea_TeachingClassID= map.get("TeachingClassID");
			Intent intent =new Intent(getActivity(),Homework_tea_class_list_activity.class);
			intent.putExtra("TeachingClassID", Tea_TeachingClassID);
			startActivity(intent);
			}

		}
		
	}

	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.homework_add:
			{
				
//				if(termsList.toString().equals(""))
//				{
//					Toast.makeText(mActivity, "正在获取数据", 2000).show();
//					break;
//				}
//				else{
				Intent intent = new Intent(getActivity(),homework_tea_addhomework.class);
				startActivity(intent);
//				}
			}
			break;
		}
		
	}
	
	public void onResume(){
		super.onResume();

		if(this.isAdded()){

			getTerms();
		}
	}

	




}
