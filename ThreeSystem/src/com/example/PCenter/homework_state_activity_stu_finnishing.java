package com.example.PCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.exam.ThreeSystem.R;
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.adapter.ClassListViewAdapter_homework;
import com.example.PCenter.adapter.ClassListViewAdapter_homework_list;
import com.example.PCenter.adapter.ClassListViewAdapter_homework_list_finishing;
import com.example.PCenter.adapter.ClassListViewAdapter_homework_list_finishing2;
import com.example.PCenter.fragments.BaseFragment;
import com.example.PCenter.fragments.HomeWorkFragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
//点击未做列表之后的作业列表
public class homework_state_activity_stu_finnishing extends BaseActivity implements OnClickListener,
OnItemClickListener{
	private Button homework_inDate=null;
	private Button homework_homeworkList_back=null;
	private ListView termListView;
	private ListView termListView2;
	private TextView withoutdata_textview;
	private Button homeworkList_outOfDate=null;
	protected Activity mActivity;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	private String StuNumber="";
	private  static String TeachingClassID;
	private String action="";
	public static  String judge="inDate";
	public   static String WorkName;
//	public  static String WorkScore;
	public static String Memo;
	public static String WorkDesc;
	public  static String isTimeOut;
	public static String HomeworkID;
	public static String DeadTime;
	public static String WorkURL;
//	public static String judge;
	private List<Map<String, String>> classesList=null;
	private List<Map<String, String>> classesList2=null;
	private List<Map<String, String>> textList=null;
	public JSONArray jsonArray;
	private JSONObject json;
	private ProgressBar progressBar;
	private ProgressDialog dialog;
	//点击已完成后跳转到的学生homeworklist 页面

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_homeworklist);
		mActivity = this;
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
//		TeachingClassID=HomeWorkFragment.Stu_TeachingClassID;
		Dialog();
		dialog.show();
		init();
		
		
		
		
	}
	private void init() {
		getViewObj();
		setListener();
		gethomeworklist();
	}
	private void setListener() {
		homework_inDate.setOnClickListener(this);
		homeworkList_outOfDate.setOnClickListener(this);
		homework_homeworkList_back.setOnClickListener(this);
		termListView.setOnItemClickListener(this);
		termListView2.setOnItemClickListener(this);
		termListView2.setVisibility(View.INVISIBLE);
	}
	public void getViewObj()
	{
		termListView=(ListView) findViewById(R.id.homeworkList);
		termListView2=(ListView) findViewById(R.id.homeworkList2);
		homework_inDate=(Button)findViewById(R.id.homework_inDate);
		homeworkList_outOfDate=(Button) findViewById(R.id.homeworkList_outOfDate);
		withoutdata_textview=(TextView) findViewById(R.id.withoutdata_textview);
		homework_homeworkList_back=(Button) findViewById(R.id.homework_homeworkList_back);
		progressBar = (ProgressBar) findViewById(R.id.homework_list_progressbar);
	}
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.homework_inDate:
			{
				withoutdata_textview.setVisibility(View.INVISIBLE);
				if(classesList.toString().equals(textList.toString()))
				{
					
					withoutdata_textview.setVisibility(View.VISIBLE);
					
					termListView.setVisibility(View.GONE);
					termListView2.setVisibility(View.GONE);
				}
				else{
				judge="inDate";//未过期
				termListView.setVisibility(View.VISIBLE);
				termListView2.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.homeworkList_outOfDate:
			{
				withoutdata_textview.setVisibility(View.INVISIBLE);
				if(classesList2.toString().equals(textList.toString()))
				{
					
					withoutdata_textview.setVisibility(View.VISIBLE);
					termListView.setVisibility(View.GONE);
					termListView2.setVisibility(View.GONE);
				}
				else{
				judge="outOfDate";//已过期
				termListView.setVisibility(View.GONE);
				termListView2.setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.homework_homeworkList_back:
			{
				finish();
			}
			break;
		}
	}
	private void gethomeworklist()
	{
		
		withoutdata_textview.setVisibility(View.INVISIBLE);
		System.out.println(TeachingClassID);
		Map<String, String> params = new HashMap<String, String>();
		
		
		StuNumber= sp.getString(Constant.SPFIELD.NAME, "");
		
		L.d(TAG,TeachingClassID);
		System.out.println(StuNumber);
		params.put("TeachingClassID",TeachingClassID);
		params.put("StuNumber",StuNumber);
		action="StuGetHomeworkDetail";
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
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true"))
						{
							textList = new ArrayList<Map<String, String>>();
							classesList = new ArrayList<Map<String, String>>();
							classesList2 = new ArrayList<Map<String, String>>();
							 jsonArray = jsonObj.getJSONArray("DoingList");
							 if(jsonArray.length()==0)
							 {
//								 withoutdata_textview.setVisibility(View.VISIBLE);
							 }
							for (int i = 0; i < jsonArray.length(); i++)
							{
								 json = jsonArray.getJSONObject(i);	
								 Map<String, String> map = new HashMap<String, String>();
								 Map<String, String> map2 = new HashMap<String, String>();
								 if(json.getString("isTimeOut").equals("NO"))
								 {
									map.put("WorkName", json.getString("WorkName"));
//									map.put("WorkScore", json.getString("WorkScore"));
									map.put("DeadTime",json.getString("DeadTime"));
									map.put("isTimeOut",json.getString("isTimeOut"));
									map.put("WorkDesc",json.getString("WorkDesc"));
									map.put("TeachingClassID",json.getString("TeachingClassID"));
									map.put("Memo",json.getString("Memo"));
									map.put("HomeworkID",json.getString("HomeworkID"));
									map.put("WorkURL",json.getString("WorkURL"));
									classesList.add(map);
								 }
								 else if(json.getString("isTimeOut").equals("YES"))
								 {
									 map2.put("WorkName", json.getString("WorkName"));
//										map2.put("WorkScore", json.getString("WorkScore"));
										map2.put("DeadTime",json.getString("DeadTime"));
										map2.put("isTimeOut",json.getString("isTimeOut"));
										map2.put("WorkDesc",json.getString("WorkDesc"));
										map2.put("TeachingClassID",json.getString("TeachingClassID"));
										map2.put("Memo",json.getString("Memo"));
										map2.put("HomeworkID",json.getString("HomeworkID"));
										map2.put("WorkURL",json.getString("WorkURL"));
										classesList2.add(map2);
								 }
							}
							if(classesList.toString().equals(textList.toString()))
							{
								
								withoutdata_textview.setVisibility(View.VISIBLE);	
							}
//						 if(classesList2.toString().equals(textList.toString()))
//							{
//								
//								withoutdata_textview.setVisibility(View.VISIBLE);
//
//							}
							termListView.setAdapter(new  ClassListViewAdapter_homework_list_finishing(mActivity, classesList));
							termListView2.setAdapter(new  ClassListViewAdapter_homework_list_finishing2(mActivity, classesList2));
							progressBar.setVisibility(View.INVISIBLE);
							dialog.dismiss();
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
			}
		};
	}
	@Override
	public void onItemClick(AdapterView<?>parent, View view, int position,
			long id) {
		if(judge.equals("inDate"))
		{
		Map<String, String> map = classesList.get(position);
		
		WorkName=map.get("WorkName");
		isTimeOut=map.get("isTimeOut");
		DeadTime=map.get("DeadTime");
		WorkDesc=map.get("WorkDesc");
		Memo=map.get("Memo");
		HomeworkID=map.get("HomeworkID");
		WorkURL=map.get("WorkURL");
		System.out.println("这个未完成列表中的 ，，，，"+WorkName);
			Intent intent=new Intent(homework_state_activity_stu_finnishing.this, Homework_stu_detail_activity_finishing.class);
			intent.putExtra("TeachingClassID", TeachingClassID);
			intent.putExtra("HomeworkID", HomeworkID);
			startActivity(intent);
		}
		else if(judge.equals("outOfDate"))
		{
			Map<String, String> map2 = classesList2.get(position);
			WorkName=map2.get("WorkName");
			isTimeOut=map2.get("isTimeOut");
			WorkDesc=map2.get("WorkDesc");
			DeadTime=map2.get("DeadTime");
			Memo=map2.get("Memo");
			HomeworkID=map2.get("HomeworkID");
			WorkURL=map2.get("WorkURL");
			System.out.println("这个未完成列表中的 ，，，，"+WorkName);
				Intent intent=new Intent(homework_state_activity_stu_finnishing.this, Homework_stu_detail_activity_finishing.class);
				intent.putExtra("TeachingClassID", TeachingClassID);
				intent.putExtra("HomeworkID", HomeworkID);
				startActivity(intent);
		}
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
	public void onResume(){
		super.onResume();
		onStart();
	}
}

