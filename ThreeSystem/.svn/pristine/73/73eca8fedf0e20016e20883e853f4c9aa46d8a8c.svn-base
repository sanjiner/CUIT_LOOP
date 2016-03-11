package com.example.PCenter.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.Inflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.PCenter.Constant;
import com.example.PCenter.Login;
import com.example.PCenter.MainActivity;
import com.example.PCenter.QuestionRandomStudentListActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.RadioCheckMoreInterface;
import com.example.PCenter.Home.HomeQuestionStudentListActivity;
import com.example.PCenter.More.MoreTermActivity;
import com.example.PCenter.adapter.ClassListViewAdapter;

public class QuestionFragment extends BaseFragment implements OnItemClickListener{
	
	private ListView questionListView;
	private TextView textView1, textView2;
	private Button no_power, set_term;
	private ProgressBar progressBar;
	private TextView classNumber, classNumberRandom;//教师指定提问时显示的班级人数,随机提问时显示的班级人数
	private EditText question_score; //教师指定提问时设置的分数的控件
	private EditText question_scoreRandom, randomCount;//随机提问时设置分数的控件，与随机选取的人数
	private String classCount;  //保存班级人数	
	private int question_Score;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String userIdentity="";//用户身份
	private String Term="",TeacherName="";//获取学期与老师姓名
	private String currentclassname="";//获取当前班级名字
	//private MainActivity Activity;
	private List<Map<String, String>> classesList;
	private String class_and_count2 = ";";  //保存班级名字和对应的班级人数
	private Button ramdomButton, teacherSetButton;//选择是随机抽取还是老师抽取
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_question, container, false);
		
		mActivity = (MainActivity) getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		
		getViewObj(view);
		
		questionListView.setOnItemClickListener(this);
		
		return view;
	}
	
	
	private void getViewObj(View view) {
		questionListView = (ListView) view.findViewById(R.id.question_ListView);
		no_power = (Button) view.findViewById(R.id.question_button);
		set_term = (Button) view.findViewById(R.id.question_button_set_term);
		progressBar = (ProgressBar) view.findViewById(R.id.question_progressbar);
		judgeUserType();
	}
	
	private void judgeUserType()
	{
		userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		if(userIdentity.equals(Constant.USERIDENTITY[0]))
		{
			questionListView.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.INVISIBLE);
			set_term.setVisibility(View.INVISIBLE);
		}
		else if(userIdentity.equals(Constant.USERIDENTITY[1]))
		{
			set_term.setVisibility(View.INVISIBLE);
			no_power.setVisibility(View.INVISIBLE);		
			getClasses();
		}
		else
		{
			L.d(TAG, "没有获取到用户身份");
			return ;
		}
	}
	
	private void getClasses(){
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		if(TextUtils.isEmpty(Term))
		{
			showdialog();
			return;
		}
		
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		TeacherName = sp.getString("name", "");
		params.put("TeacherName", TeacherName);
		params.put("Term", Term);
		action = "GetAllStudentByTeacher";
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
				//L.d(TAG, response);
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
								classesList.add(map);
							}
							questionListView.setAdapter(new ClassListViewAdapter(mActivity, classesList));
							progressBar.setVisibility(View.INVISIBLE);
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


	//当点击班级列表条目时
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
		currentclassname = classesList.get(position).get("className");
		String[] s = class_and_count2.split(";");
		for (int i = 0; i < s.length; i++) {
			if (s[i].contains(currentclassname)) {
				String score = s[i].substring(s[i].lastIndexOf('=')+1);
				classCount = score;
				break;
			}
		}		
		if(Integer.parseInt(classCount) == 0){
			Toast.makeText(mActivity,  "该班没有学生",
					Toast.LENGTH_SHORT).show();
			return;
		}else{
			LayoutInflater inflater = LayoutInflater.from(mActivity);
			View selectview = inflater.inflate(R.layout.activity_question_select_type, null);
			ramdomButton = (Button) selectview.findViewById(R.id.select_type_in_random);
			teacherSetButton = (Button) selectview.findViewById(R.id.select_type_in_teacher_set);		
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setView(selectview);
			final AlertDialog dialog=builder.create();
			dialog.show();
			
			teacherSetButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					AddQuestionTeacherSetScore();					
				}
			});	
			
			ramdomButton.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					AddQuestionRandomSetScore();
				}
			});
		}									
	}
	
		
	//老师指定抽取设置分数
	private void AddQuestionTeacherSetScore()
	{
		LayoutInflater	inflater = LayoutInflater.from(mActivity);
		View view = inflater.inflate(R.layout.question_teacher_set__score, null);	
		classNumber = (TextView) view.findViewById(R.id.question_the_class_number);
		question_score = (EditText) view.findViewById(R.id.question_teacher_set_score);
		classNumber.setText(classCount+"");
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(view);
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				String Score = question_score.getText().toString();
				if (TextUtils.isEmpty(Score)) {
					Toast.makeText(mActivity, "输入不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				question_Score = Integer.parseInt(Score);
				if(question_Score<=0)
				{
					Toast.makeText(mActivity,  "请输入大于0的整数",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if(question_Score>1000)
				{
					Toast.makeText(mActivity,  "不能超过1000，请重新输入",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(mActivity, HomeQuestionStudentListActivity.class);
				intent.putExtra("currentclassname", currentclassname);
				intent.putExtra("Score", Score);
				intent.putExtra("classCount", classCount);		
				startActivity(intent);
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
	
	//老师随机抽取设置分数
	private void AddQuestionRandomSetScore()
	{
		LayoutInflater inflat = LayoutInflater.from(mActivity);
		View view = inflat.inflate(R.layout.question_random_set_score, null);
		classNumberRandom = (TextView) view.findViewById(R.id.question_the_number);
		question_scoreRandom = (EditText) view.findViewById(R.id.question_random_setScore);
		randomCount = (EditText) view.findViewById(R.id.quetion_random_selectCount);
		classNumberRandom.setText(classCount+"");
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(view);
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String Score = question_scoreRandom.getText().toString();
				String Count = randomCount.getText().toString();
				if (TextUtils.isEmpty(Score) || TextUtils.isEmpty(Count)) {
					Toast.makeText(mActivity, "输入不能为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				question_Score = Integer.parseInt(Score);				
				if(question_Score<=0)
				{
					Toast.makeText(mActivity,  "请输入大于0的整数",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if(question_Score>1000)
				{
					Toast.makeText(mActivity,  "不能超过1000，请重新输入",
							Toast.LENGTH_SHORT).show();
					return;
				}
				int c1 = Integer.parseInt(Count);
				int c2 = Integer.parseInt(classCount);
				if(c1>c2)
				{
					Toast.makeText(mActivity,  "抽取学生数不能大于该班总数",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//生成不重复的随机数
				Random random = new Random();
				String s="";
				while((s.split(";")).length<c1)
				{
//					System.out.println("s.length():"+(s.split(";")).length);
					String r = random.nextInt(c2)+"";
					String[] m = s.split(";");
					for(int i=0; i<m.length;++i)
					{
//						System.out.println("m.length:"+m.length);
//						System.out.println("m[i]:"+m[i]);
						if(m[i].equals(r)){
							break;
						}
						if(i==(m.length-1)){
							s = s+r+";";
						}
					}					
				}
				
				System.out.println(s);
				Intent intent = new Intent(mActivity, QuestionRandomStudentListActivity.class);
				intent.putExtra("currentclassname", currentclassname);
				intent.putExtra("Score", Score);
				intent.putExtra("classCount", Count);
				intent.putExtra("randompick", s);
				startActivity(intent);
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
	
	
	//跳转到设置学期界面
	private void showdialog()
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
		builder.setTitle("提示");
		builder.setMessage("还没有设置学期，是否去设置学期");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
				{
					RadioCheckMoreInterface checkMore = (RadioCheckMoreInterface)getActivity();
					if (checkMore != null) {
						checkMore.checkMore();
					}
					progressBar.setVisibility(View.INVISIBLE);
				}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
					{
						progressBar.setVisibility(View.INVISIBLE);
						set_term.setVisibility(View.VISIBLE);
					}
		} );
		AlertDialog dialog=builder.create();
		dialog.show();
	}
}
