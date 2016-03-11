package com.example.theses.fragments.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.PCenter.fragments.BaseFragment;
import com.example.thesis.ProjectManagement_Thesis_Defense;
import com.example.thesis.Topic_Management_Detail;
import com.example.thesis.teacher.adapter.Thesis_Project_Management_ListAdapter;

public class ProjectManagementFragment extends BaseFragment implements OnItemClickListener{
	
	

	public DownrefreshListView thesis_projectmanagementfragment_Listview;
	public ProgressBar thesis_projectmanagementfragment_ProgressBar;
	public TextView thesis_projectmanagementfragment_TextView;
	public SharedPreferences shared_pref;
	public Activity mActivity;
	private List<Map<String,String>> studentList = new ArrayList<Map<String, String>>();
	
	//课题管理
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thesis_projectmanagementfragment, container, false);
		mActivity=getActivity();
		shared_pref=mActivity.getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		init(view);
		return view;
	}

	public void getViewObj(View view )
	{
		thesis_projectmanagementfragment_Listview=(DownrefreshListView) view.findViewById(R.id.thesis_projectmanagementfragment_Listview);
		thesis_projectmanagementfragment_ProgressBar=(ProgressBar) view.findViewById(R.id.thesis_projectmanagementfragment_ProgressBar);
		thesis_projectmanagementfragment_TextView=(TextView) view.findViewById(R.id.thesis_projectmanagementfragment_TextView);
		
	}
	public void setlistener()
	{
		thesis_projectmanagementfragment_Listview.setOnItemClickListener(this);
		
		thesis_projectmanagementfragment_Listview.setonRefreshListener(new OnRefreshListener() {  
			  
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
                    	
                    	getStudentList();
                    	thesis_projectmanagementfragment_Listview.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        }); 
	}
	
	public void init(View view )
	{
		getViewObj(view);
		setlistener();
		thesis_projectmanagementfragment_ProgressBar.setVisibility(View.VISIBLE);
		getStudentList();
	}
	
	/**
	 * 网络获取学生列表
	 */
	private void getStudentList(){
		String teacherID = shared_pref.getString(Constant.SPFIELD.TEACHERID, "");
		Map<String, String> params =new HashMap<String, String>();
		params.put("teacherID", teacherID);
		String url = URLTools.buildURL(Constant.THESIS_BASEURL+"GuiStudent", params);
		StringRequest stringRequest = new StringRequest(url,onResponse(),errorListener());
		executeRequest(stringRequest);
	}
	
	private Response.Listener<String> onResponse(){
		return new Response.Listener<String>(){
			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String success = json.getString("success");
					if(success.equals("true")){
						thesis_projectmanagementfragment_ProgressBar.setVisibility(View.GONE);
						studentList = new ArrayList<Map<String, String>>();
						JSONArray jsonArray = json.getJSONArray("StudentInfo");
						for(int i = 0; i<jsonArray.length(); i++){
							JSONObject studentJson = jsonArray.getJSONObject(i);
							Map<String, String> stuMap = new HashMap<String, String>();
							stuMap.put("score", studentJson.getString("score"));
							stuMap.put("studentName", studentJson.getString("StudentName"));
							stuMap.put("studentId", studentJson.getString("StudentID"));
							studentList.add(stuMap);
						}
						thesis_projectmanagementfragment_Listview.setAdapter(new Thesis_Project_Management_ListAdapter(mActivity, studentList));
					}else{
						thesis_projectmanagementfragment_ProgressBar.setVisibility(View.GONE);
						thesis_projectmanagementfragment_TextView.setVisibility(View.VISIBLE);
						T.showShort(mActivity, json.getString("ErrMsg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					thesis_projectmanagementfragment_ProgressBar.setVisibility(View.GONE);
					thesis_projectmanagementfragment_TextView.setVisibility(View.VISIBLE);
				}
			}
		};
	}
	

	/**
	 * 网络请求失败
	 */
	@Override
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				L.d(TAG, error.getMessage());
				thesis_projectmanagementfragment_ProgressBar.setVisibility(View.GONE);
				thesis_projectmanagementfragment_TextView.setVisibility(View.VISIBLE);
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		
		Intent chooseTaskIntent = new Intent(mActivity,ProjectManagement_Thesis_Defense.class);
		Map<String, String> map = studentList.get(position-1);
		chooseTaskIntent.putExtra("studentName", map.get("studentName"));
		chooseTaskIntent.putExtra("studentId", map.get("studentId"));
		startActivity(chooseTaskIntent);
		
	}
	
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
	}
	
}
