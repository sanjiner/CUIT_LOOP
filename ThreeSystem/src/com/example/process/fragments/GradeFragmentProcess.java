package com.example.process.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.exam.ThreeSystem.R;
import com.example.PCenter.Constant;
import com.example.PCenter.DownrefreshListView;
import com.example.PCenter.MD5ENCODE;
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.adapter.ClassListViewAdapter;
import com.example.PCenter.fragments.BaseFragment;
import com.example.process.DetailGradeActivity;

public class GradeFragmentProcess extends BaseFragment implements OnItemClickListener{

	private ProgressDialog dialog;
	private DownrefreshListView classesListView;
	private ProgressBar progressBar;	
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	
	private String userName,passWord;
	private List<Map<String, String>> classesList;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_process_grade, container, false);
		mActivity = getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		init(view);
		getClasses();
		
		return view;
	}
	
	private void getViewObj(View view)
	{
		classesListView = (DownrefreshListView) view.findViewById(R.id.process_class_ListView);
		progressBar = (ProgressBar) view.findViewById(R.id.process_class_progressbar);
	}
	
	private void setListener()
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

	private void init(View view)
	{
		getViewObj(view);
		setListener();
		Dialog();
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

	private void getClasses(){
		dialog.show();
		Map<String, String> params = new HashMap<String,String>();
		String action = "";
		userName = sp.getString(Constant.SPFIELD.NAME, "");
		passWord = sp.getString(Constant.SPFIELD.PASSWORD, "");
		params.put("StudentLogin", MD5ENCODE.MD5Encode(passWord).toString());
		params.put("StuNumber", userName);
		action = "StuLogin";
		String url = URLTools.buildURL(Constant.PROCESS_BASUURL + action,params);
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
							JSONArray jsonArray = jsonObj.getJSONArray("CourseList");
							for(int i = 0 ; i<jsonArray.length(); i++){
								JSONObject json = jsonArray.getJSONObject(i);						
								
								Map<String, String> map = new HashMap<String, String>();
								map.put("className", json.getString("CourseName"));
								map.put("CourseID",json.getString("CourseID"));
								classesList.add(map);
							}
							classesListView.setAdapter(new ClassListViewAdapter(mActivity, classesList));
							progressBar.setVisibility(View.INVISIBLE);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String CourseID = classesList.get(position-1).get("CourseID");
		Intent intent = new Intent(mActivity, DetailGradeActivity.class);
		intent.putExtra("CourseID", CourseID);
		startActivity(intent);
	}
	
	
}
