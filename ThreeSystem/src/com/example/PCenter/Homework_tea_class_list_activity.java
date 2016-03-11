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
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.adapter.ClassListViewAdapter_Homework_tea_class_list_activity;
import com.example.PCenter.adapter.ClassListViewAdapter_Homework_tea_class_list_activity2;
import com.example.PCenter.adapter.ClassListViewAdapter_homework;
import com.example.PCenter.fragments.HomeWorkFragment;
import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
//教师点击班级后列出的作业列表  (未过期    已 过期  )
public class Homework_tea_class_list_activity extends BaseActivity implements OnClickListener
,OnItemClickListener, OnItemLongClickListener  {
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	private Button homework_inDate;
	private Button homeworkList_outOfDate;
	private Button homework_homeworkList_back;
	protected Activity mActivity;
	public String judge="inDate";
	private String isNULL="NO";
	public static  String TeachingClassID;
	public static String HomeworkID;
	public static String HomeworkID2;
	public static String WorkDesc;
	public static String WorkScore;
	public static String DeadTime;
	public static String Memo;
	public static String WorkName;
	public static String WorkURL;
	String action="";
	private List<Map<String, String>> classesList=null;
	private List<Map<String, String>> classesList2=null;
	private List<Map<String, String>> classesList3=null;
	public JSONArray jsonArray;
	private JSONObject json;
	private ListView termListView;
	private ListView termListView2;
	private ProgressBar progressBar;
	private TextView withoutdata_textview;
	private String isTimeOut="";
	private ProgressDialog dialog;
	//private String TeachingClassID;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_homeworklist);
		mActivity = this;
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		//TAG = mActivity.getClass().getSimpleName();
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		getViewObj();
		setListener();
		getList();
		Dialog();
	}


	public void setListener()
	{
		homework_inDate.setOnClickListener(this);
		homeworkList_outOfDate.setOnClickListener(this);
		homework_homeworkList_back.setOnClickListener(this);
		termListView.setOnItemClickListener(this);
		termListView2.setOnItemClickListener(this);
		termListView.setOnItemLongClickListener(this);
		
	}
	private void getViewObj()
	{
		homework_inDate=(Button) findViewById(R.id.homework_inDate);
		homeworkList_outOfDate=(Button) findViewById(R.id.homeworkList_outOfDate);
		homework_homeworkList_back=(Button) findViewById(R.id.homework_homeworkList_back);
		termListView=(ListView) findViewById(R.id.homeworkList);
		termListView2=(ListView) findViewById(R.id.homeworkList2);
		termListView2.setVisibility(View.INVISIBLE);
		progressBar = (ProgressBar)findViewById(R.id.homework_list_progressbar);
		withoutdata_textview=(TextView) findViewById(R.id.withoutdata_textview);
		
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
	
	
	private void getList()
	{
		withoutdata_textview.setVisibility(View.INVISIBLE);
		Map<String, String> params = new HashMap<String, String>();
       
//		TeachingClassID=HomeWorkFragment.Tea_TeachingClassID;
		L.d(TAG,TeachingClassID);
		params.put("TeachingClassID",TeachingClassID);
		action="TeacherGetAllWorkByTeachingClass";
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
	
	private Response.Listener<String> successListener()
	{
		return new Response.Listener<String>()
		{
			public void onResponse(String response)
			{
				L.d(TAG, response);
				try
				{
					JSONObject jsonObj = new JSONObject(response);
					String success = jsonObj.getString("Success");
					if (success.equals("true"))
					{
						classesList = new ArrayList<Map<String, String>>();
						classesList2=new ArrayList<Map<String, String>>();
						classesList3=new ArrayList<Map<String, String>>();
						 jsonArray = jsonObj.getJSONArray("WorkList");

						 if(jsonArray.length()==0)
						 {
							 withoutdata_textview.setVisibility(View.VISIBLE);
						 }
						 else if(jsonArray.length()!=0)
						 {
							 withoutdata_textview.setVisibility(View.INVISIBLE);
						 
							 for (int i = 0; i < jsonArray.length(); i++)
							 {
								 json = jsonArray.getJSONObject(i);	

								 Map<String, String> map = new HashMap<String, String>();
								 Map<String, String> map2 = new HashMap<String, String>();
									
									if(json.getString("isTimeOut").equals("NO"))
									{
										map.put("WorkName", json.getString("WorkName"));
										map.put("IsStuSee", json.getString("IsStuSee"));
										map.put("DeadTime",json.getString("DeadTime"));
										map.put("isTimeOut",json.getString("isTimeOut"));
										map.put("WorkID",json.getString("WorkID"));
										
										map.put("WorkDesc",json.getString("WorkDesc"));
										map.put("WorkScore",json.getString("WorkScore"));
//										map.put("DeadTime",json.getString("DeadTime"));
										map.put("Memo",json.getString("Memo"));
										map.put("WorkURL", json.getString("WorkURL"));
										classesList.add(map);
										

									}
									else if(json.getString("isTimeOut").equals("YES"))
									{
										map2.put("WorkName", json.getString("WorkName"));
										map2.put("IsStuSee", json.getString("IsStuSee"));
										map2.put("DeadTime",json.getString("DeadTime"));
										map2.put("isTimeOut",json.getString("isTimeOut"));
										map2.put("WorkID",json.getString("WorkID"));
										
										map2.put("WorkDesc",json.getString("WorkDesc"));
										map2.put("WorkScore",json.getString("WorkScore"));
//										map2.put("DeadTime",json.getString("DeadTime"));
										map2.put("Memo",json.getString("Memo"));
										map2.put("WorkURL", json.getString("WorkURL"));
										System.out.println("zhege kkk"+json.getString("isTimeOut"));
										System.out.println("zgege"+json.getString("IsStuSee"));

										classesList2.add(map2);
										
									}

									
									
							}
							 
								 termListView.setAdapter(new  ClassListViewAdapter_Homework_tea_class_list_activity(mActivity, classesList));
								 termListView2.setAdapter(new  ClassListViewAdapter_Homework_tea_class_list_activity2(mActivity, classesList2));
						 }
						 progressBar.setVisibility(View.INVISIBLE);
						 System.out.println("查看isNULL的值"+isNULL);
					}
					else 
					{
						L.d(TAG, "Success is false");
					}
				}
				catch(JSONException e)
				{
					L.e(TAG, e.toString());
				}
			}
		};
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.homework_homeworkList_back:
		{
			finish();
		}
		break;
		case R.id.homework_inDate:
		{
			termListView.setVisibility(View.VISIBLE);

			termListView2.setVisibility(View.GONE);
			System.out.println("点击了未过期按钮");
			judge="inDate";
		}
		break;
		case R.id.homeworkList_outOfDate:
		{
			termListView.setVisibility(View.GONE);
			termListView2.setVisibility(View.VISIBLE);
			judge="outOfDate";
			System.out.println("点击了已过期按钮");
		}
		break;
		
		}
		
		
	}


	@Override
	public void onItemClick(AdapterView<?>parent, View view, int position,
			long id) {

			System.out.println("在判断是NO后的onItemClick查看isNULL的值"+isNULL);
			if(judge.equals("inDate"))
			{
			Map<String, String> map = classesList.get(position);
			HomeworkID=map.get("WorkID");//未过期
			WorkName=map.get("WorkName");
			WorkDesc=map.get("WorkDesc");
			WorkScore=map.get("WorkScore");
			DeadTime=map.get("DeadTime");
			Memo=map.get("Memo");
			WorkURL=map.get("WorkURL");
			}
			else if(judge.equals("outOfDate"))
			{
			Map<String, String> map2 = classesList2.get(position);
			HomeworkID=map2.get("WorkID");//已过期
			WorkName=map2.get("WorkName");
			WorkDesc=map2.get("WorkDesc");
			WorkScore=map2.get("WorkScore");
			DeadTime=map2.get("DeadTime");
			Memo=map2.get("Memo");
			WorkURL=map2.get("WorkURL");
			}
			Intent intent = new Intent(Homework_tea_class_list_activity.this,homework_tea_class_activity.class);
			intent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(intent);
//		}
	}
	
	
	//删除作业

	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
			long id) {
//		Toast.makeText(mActivity, "长按点击了", Toast.LENGTH_SHORT).show();
		if(judge.equals("inDate"))
		{
			Map<String, String> map = classesList.get(position);
			HomeworkID= map.get("WorkID");
			String modulename = classesList.get(position).get("WorkName");
			final String moduleID = classesList.get(position).get("WorkID");
			final int post = position;
					
			LayoutInflater inflat = LayoutInflater.from(mActivity);
			View deletemoduleview = inflat.inflate(R.layout.homework_is_delete, null);
			TextView deletename = (TextView) deletemoduleview.findViewById(R.id.delete_homework);
			deletename.setText(modulename);
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setView(deletemoduleview);
			
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int which)
				{
					DeleteModule(moduleID, post);
				}
			});
			
			builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int which)
						{
					
						}
			} );
			AlertDialog dialog=builder.create();
			dialog.show();
			
		}
		else if(judge.equals("outOfDate"))
		{
			Map<String, String> map2 = classesList2.get(position);
			HomeworkID= map2.get("WorkID");
			String modulename = classesList2.get(position).get("WorkName");
			final String moduleID = classesList2.get(position).get("WorkID");
			final int post = position;
					
			LayoutInflater inflat = LayoutInflater.from(mActivity);
			View deletemoduleview = inflat.inflate(R.layout.more_module_is_delete, null);
			TextView deletename = (TextView) deletemoduleview.findViewById(R.id.delete_module);
			deletename.setText(modulename);
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setView(deletemoduleview);
			
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int which)
				{
					DeleteModule(moduleID, post);
				}
			});
			
			builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int which)
						{
					
						}
			} );
			AlertDialog dialog=builder.create();
			dialog.show();
			
		}
				
		return false;
	}
	
	//删除模块
	private void DeleteModule(String moduleID,int postion)
	{
		if(judge.equals("inDate"))
		{
			
			dialog.show();
			Map<String, String> params = new HashMap<String, String>();
			String action = "";
			params.put("HomeworkID", HomeworkID);
			action = "TeacherDeleteHomework";
			String url = URLTools
					.buildURL(Constant.INTERFACE_SITE + action, params);
			L.d(TAG, url);
			StringRequest stringRequest = new StringRequest(url, successListener01(postion),
					errorListener());
			executeRequest(stringRequest);
		}
		else if(judge.equals("outOfDate"))
		{
			dialog.show();
			Map<String, String> params = new HashMap<String, String>();
			String action = "";
			params.put("HomeworkID", HomeworkID);
			action = "TeacherDeleteHomework";
			String url = URLTools
					.buildURL(Constant.INTERFACE_SITE + action, params);
			L.d(TAG, url);
			StringRequest stringRequest = new StringRequest(url, successListener01(postion),
					errorListener());
			executeRequest(stringRequest);
		}
		
	}
	//删除模块监听器
	private Response.Listener<String> successListener01(final int postion) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(judge.equals("inDate"))
						{
							if (success.equals("true")) {							
								classesList.remove(postion);
								new  ClassListViewAdapter_Homework_tea_class_list_activity(mActivity, classesList).notifyDataSetChanged();
								getList();							
								Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
								L.d(TAG, "Success is false");
							}
							dialog.dismiss();
						}
						else if(judge.equals("outOfDate"))
						{
							if (success.equals("true")) {							
								classesList2.remove(postion);
								new  ClassListViewAdapter_Homework_tea_class_list_activity2(mActivity, classesList2).notifyDataSetChanged();
								getList();							
								Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
								L.d(TAG, "Success is false");
							}
							dialog.dismiss();
						}
						
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
	
}
