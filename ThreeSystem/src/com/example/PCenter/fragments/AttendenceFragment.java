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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.common.tools.T;
import com.example.PCenter.AttendenceStudentListActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.DownrefreshListView;
import com.example.PCenter.MainActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.RadioCheckMoreInterface;
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.adapter.ClassListViewAdapter;

public class AttendenceFragment extends BaseFragment implements OnItemClickListener{
	
	private DownrefreshListView classesListView;
	private Button no_power, set_term;
	private ProgressBar progressBar;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String userIdentity="";//用户身份
	private String Term="",TeacherName="";//获取学期与老师姓名 
	private String currentclassname="";//获取当前班级名字
	private List<Map<String, String>> classesList;
	private String class_and_count2 = ";";  //保存班级名字和对应的班级人数
	private String classCount;  //保存对应班级人数
	private ProgressDialog dialog;
	private TextView showTerm;
	private String TeachingClassID;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		mActivity = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.activity_attendance, container, false);
		Dialog();
		dialog.show();
		
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		
		Init(view);			
		
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
	
	private void Init(View view) {
		classesListView = (DownrefreshListView) view.findViewById(R.id.attendance_ListView);
		no_power = (Button) view.findViewById(R.id.attendance_button);
		set_term = (Button) view.findViewById(R.id.attendance_button_set_term);
		progressBar = (ProgressBar) view.findViewById(R.id.attendance_progressbar);
		showTerm = (TextView) view.findViewById(R.id.attendance_show_term);
		showTerm.setVisibility(View.INVISIBLE);
		judgeUserType();
		Listener();
	}
	
	private void Listener()
	{
		classesListView.setOnItemClickListener(this);	
		classesListView.setonRefreshListener(new OnRefreshListener() {  			  
            @Override  
            public void onRefresh() {  
                new AsyncTask<Void, Void, Void>() {  
                    protected Void doInBackground(Void... params) {  
                        try {  
                            Thread.sleep(1000); 
                           
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }                         
                        return null;  
                    }  
  
                    @Override  
                    protected void onPostExecute(Void result) {  
                    	getClasses();
                    	classesListView.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        });
	}
	
	private void judgeUserType()
	{
		userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		//身份为学生时
		if(userIdentity.equals(Constant.USERIDENTITY[0]))
		{
			classesListView.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.INVISIBLE);
			set_term.setVisibility(View.INVISIBLE);
			dialog.dismiss();
		}
		//身份为老师时
		else if(userIdentity.equals(Constant.USERIDENTITY[1])){
			set_term.setVisibility(View.INVISIBLE);
			no_power.setVisibility(View.INVISIBLE);
			getClasses();
		}else{
			L.d(TAG, "没有获取到用户身份");
			return ;
		}
	}	
	
	private void getClasses(){
		Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
		if(TextUtils.isEmpty(Term))
		{
			progressBar.setVisibility(View.INVISIBLE);
			set_term.setVisibility(View.VISIBLE);
			dialog.dismiss();
			return;
		}
		showTerm.setText(Term);
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
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
								map.put("TeachingClassID",json.getString("TeachingClassID"));
								classesList.add(map);
							}
							classesListView.setAdapter(new ClassListViewAdapter(mActivity, classesList));
							progressBar.setVisibility(View.INVISIBLE);
							set_term.setVisibility(View.INVISIBLE);
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
		currentclassname = classesList.get(position-1).get("className");
		TeachingClassID = classesList.get(position-1).get("TeachingClassID");
		
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
			Intent intent = new Intent(mActivity, AttendenceStudentListActivity.class);	
			intent.putExtra("currentclassname", currentclassname);
			intent.putExtra("TeachingClassID", TeachingClassID);
			startActivity(intent);
		}
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
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
					{
						progressBar.setVisibility(View.INVISIBLE);
						set_term.setVisibility(View.VISIBLE);
						dialog.dismiss();
					}
		} );
		AlertDialog dialog=builder.create();
		dialog.show();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//T.showShort(mActivity, "AttendenceFragmentonResume");
		if(this.isAdded()){	
			if(userIdentity.equals(Constant.USERIDENTITY[1])){
				//dialog.show();
				getClasses();
			}
		}
	}
	
}
