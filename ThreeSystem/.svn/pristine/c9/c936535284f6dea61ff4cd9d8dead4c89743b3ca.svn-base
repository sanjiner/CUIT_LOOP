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
import com.example.PCenter.adapter.Homework_everystu_Adapter;
import com.example.PCenter.adapter.Homework_everystu_Adapter2;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class homework_tea_class_activity extends BaseActivity implements OnClickListener,OnItemClickListener{
//点击作业列表显示的作业列表包括（显示学号  姓名  戒子日期）   头顶  （未批阅 ，已批阅）
//已评阅 详情  ，，，
	private String HomeworkID="";//等同于WorkID；
	String action="";
	protected Activity mActivity;
	private String FILE = Constant.USERINFO_SP;
	private SharedPreferences sp;
	public JSONArray jsonArray;
	public JSONArray jsonArray2;
	private JSONObject json;
	private ListView termListView;
	private ListView termListView2;
	private TextView withoutdata_textview_2;
	public static String WorkScore;
	private Button homework_finishList_back;
	private Button homework_finishList_work;
	private Button homework_finishList_Examines;
	private Button homework_finishList_UnExamines;
	private String isExamines="yes";
	public  static String WorkName;
	public static String WorkContent;
	public static String WorkMemo;
	public static String isTimeOut;
	public static String uploadTime;
	public static String TeacherMarkMemo="";
	public static String StuNumber;
	public static String StuName;
	public static String StuScore;
	public static String WorkDesc;
	public static String DeadTime;
	public static String StuGrade;
	public static String StuWorkID;
	public static String WorkURL;
	public static String AnswerURL;
	private String judge="UnExamines";
	private ProgressBar progressBar;
	private String judgedata="notnull";
	private String judgedata2="notnull";
	private String TeachingClassID;
	private List<Map<String, String>> classesList=null;
	private List<Map<String, String>> classesList2=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_finish_list);
		mActivity = this;
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		getViewObj();
		setListener();
		getList();
	
		
		
		
		
	}
	private void getViewObj()
	{
		termListView=(ListView) findViewById(R.id.homework_finishList_list);
		termListView2=(ListView) findViewById(R.id.homework_finishList_list2);
		homework_finishList_back=(Button) findViewById(R.id.homework_finishList_back);
		homework_finishList_work=(Button) findViewById(R.id.homework_finishList_work);
		homework_finishList_Examines=(Button) findViewById(R.id.homework_finishList_Examines);
		homework_finishList_UnExamines=(Button) findViewById(R.id.homework_finishList_UnExamines);
		withoutdata_textview_2=(TextView) findViewById(R.id.withoutdata_textview_2);
		progressBar = (ProgressBar)findViewById(R.id.homework_tea_class_activity_progressbar);
	}
	private void setListener()
	{
		homework_finishList_back.setOnClickListener(this);
		homework_finishList_work.setOnClickListener(this);
		homework_finishList_Examines.setOnClickListener(this);
		homework_finishList_UnExamines.setOnClickListener(this);
		termListView.setOnItemClickListener(this);
		termListView2.setOnItemClickListener(this);
		termListView.setVisibility(View.GONE);
		

		
	}
	private void getList()
	{
		withoutdata_textview_2.setVisibility(View.INVISIBLE);
		Map<String, String> params = new HashMap<String, String>();
		HomeworkID=Homework_tea_class_list_activity.HomeworkID;
		params.put("HomeworkID",HomeworkID);
		action="New_TeacherGetStuWork";
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
						 jsonArray = jsonObj.getJSONArray("MarkedList");
					if(jsonArray.length()==0)
					{
						judgedata="null";
						 withoutdata_textview_2.setVisibility(View.VISIBLE);
					}
					else if(jsonArray.length()!=0)
					{
						judgedata="notnull";
						withoutdata_textview_2.setVisibility(View.INVISIBLE);
					
					 for (int i = 0; i < jsonArray.length(); i++)
					{
								 json = jsonArray.getJSONObject(i);	
								 Map<String, String> map = new HashMap<String, String>();
									map.put("StuNumber", json.getString("StuNumber"));
									map.put("StuName", json.getString("StuName"));
									map.put("DeadTime",json.getString("DeadTime"));
//									map.put("StuScore",json.getString("StuScore"));
									map.put("WorkScore",json.getString("WorkScore"));
									map.put("WorkName",json.getString("WorkName"));
									map.put("WorkContent",json.getString("WorkContent"));
									map.put("WorkMemo",json.getString("WorkMemo"));
									map.put("isTimeOut",json.getString("isTimeOut"));
									map.put("uploadTime",json.getString("uploadTime"));
									map.put("TeacherMarkMemo",json.getString("TeacherMarkMemo"));
									map.put("WorkDesc",json.getString("WorkDesc"));
									map.put("DeadTime",json.getString("DeadTime"));
									map.put("StuGrade",json.getString("StuGrade"));
									map.put("StuWorkID",json.getString("StuWorkID"));
									map.put("WorkURL",json.getString("WorkURL"));
									map.put("AnswerURL",json.getString("AnswerURL"));
									classesList.add(map);
							}
						 termListView.setAdapter(new Homework_everystu_Adapter(mActivity,classesList));
					}
						 progressBar.setVisibility(View.INVISIBLE);
						 //get NoMarkList 
						 jsonArray2 = jsonObj.getJSONArray("NoMarkList");
						 if(jsonArray2.length()==0)
							{
								judgedata2="null";
								withoutdata_textview_2.setVisibility(View.VISIBLE);
							}
						 else if(jsonArray2.length()!=0)
						 {
							 judgedata2="notnull";
							 withoutdata_textview_2.setVisibility(View.INVISIBLE);
						 
						 for (int i = 0; i < jsonArray2.length(); i++)
						 {
							 json = jsonArray2.getJSONObject(i);	
							 Map<String, String> map2 = new HashMap<String, String>();
							 map2.put("StuNumber", json.getString("StuNumber"));
								map2.put("StuName", json.getString("StuName"));
								map2.put("DeadTime",json.getString("DeadTime"));
								map2.put("WorkScore",json.getString("WorkScore"));
								map2.put("WorkName",json.getString("WorkName"));
								map2.put("WorkContent",json.getString("WorkContent"));
								map2.put("WorkMemo",json.getString("WorkMemo"));
								map2.put("isTimeOut",json.getString("isTimeOut"));
								map2.put("uploadTime",json.getString("uploadTime"));
								map2.put("WorkDesc",json.getString("WorkDesc"));
								map2.put("DeadTime",json.getString("DeadTime"));
								map2.put("StuWorkID",json.getString("StuWorkID"));
								map2.put("WorkURL",json.getString("WorkURL"));
								map2.put("AnswerURL",json.getString("AnswerURL"));
								classesList2.add(map2);
						 }
						 termListView2.setAdapter(new Homework_everystu_Adapter2(mActivity,classesList2));
						 }
						 progressBar.setVisibility(View.INVISIBLE);
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
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.homework_finishList_back:
		{
			finish();
		}
		break;
		case R.id.homework_finishList_work:
		{
			Intent intent = new Intent(homework_tea_class_activity.this,Homework_finishList_work.class);
			startActivity(intent);
		}
		break;
		case R.id.homework_finishList_Examines:
		{
			judge="Examines";
			termListView2.setVisibility(View.GONE);//已评阅
			termListView.setVisibility(View.VISIBLE);
			
			if(judgedata=="null")
			{
				withoutdata_textview_2.setVisibility(View.VISIBLE);
			}
			else if(judgedata=="notnull")
			{
				withoutdata_textview_2.setVisibility(View.INVISIBLE);
			}
		}
		break;
		case R.id.homework_finishList_UnExamines:
		{
			termListView.setVisibility(View.GONE);//未评阅
			termListView2.setVisibility(View.VISIBLE);
			judge="UnExamines";
			if(judgedata2=="null")
			{
				withoutdata_textview_2.setVisibility(View.VISIBLE);
			}
			else if(judgedata2=="notnull")
			{
				withoutdata_textview_2.setVisibility(View.INVISIBLE);
			}
		}
		break;
		}
	}
	public void onItemClick(AdapterView<?>parent, View view, int position,
			long id)
	{
		System.out.println(judge);
		if(judge.equals("Examines"))
		{
		Map<String, String> map = classesList.get(position);
		WorkScore=map.get("WorkScore");
		WorkName=map.get("WorkName");
		WorkContent=map.get("WorkContent");
		System.out.println(judge+"该题学生答案是"+WorkContent);
		WorkMemo=map.get("WorkMemo");
		isTimeOut=map.get("isTimeOut");
		uploadTime=map.get("uploadTime");
		TeacherMarkMemo=map.get("TeacherMarkMemo");
		StuNumber=map.get("StuNumber");
		StuName=map.get("StuName");
		WorkDesc=map.get("WorkDesc");
		DeadTime=map.get("DeadTime");
		StuGrade=map.get("StuGrade");
		StuWorkID=map.get("StuWorkID");
		WorkURL=map.get("WorkURL");
		AnswerURL=map.get("AnswerURL");
		Intent intent = new Intent(homework_tea_class_activity.this,Homework_tea_class_activity_detail.class);
		intent.putExtra("TeachingClassID", TeachingClassID);
		startActivity(intent);
		}
		else if(judge.equals("UnExamines"))
		{
			Map<String, String> map2 = classesList2.get(position);
			WorkScore=map2.get("WorkScore");
			WorkName=map2.get("WorkName");
			WorkContent=map2.get("WorkContent");
			System.out.println(judge+"该题学生答案是"+WorkContent);
			WorkMemo=map2.get("WorkMemo");
			isTimeOut=map2.get("isTimeOut");
			uploadTime=map2.get("uploadTime");
			WorkDesc=map2.get("WorkDesc");
			StuNumber=map2.get("StuNumber");
			StuName=map2.get("StuName");
			DeadTime=map2.get("DeadTime");
			StuWorkID=map2.get("StuWorkID");
			WorkURL=map2.get("WorkURL");
			AnswerURL=map2.get("AnswerURL");
			Intent intent = new Intent(homework_tea_class_activity.this,Homework_tea_class_activity_detail_MarkedList.class);
			intent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(intent);
		}
	}
	
	public void onResume(){
		super.onResume();
		onStart();
		/*setContentView(R.layout.activity_homework_finish_list);
		getViewObj();
		setListener();
		getList();*/

		
	}
	
}
