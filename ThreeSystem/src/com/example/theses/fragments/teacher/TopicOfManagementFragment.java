package com.example.theses.fragments.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.BaseAdapter;
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
import com.example.thesis.Topic_Management_Detail;
import com.example.thesis.teacher.adapter.Thesis_Topic_Management_listAdapter;

public class TopicOfManagementFragment extends BaseFragment implements OnItemClickListener{

	public DownrefreshListView layout_topicofmanagementfragment_ListView;
	public ProgressBar layout_topicofmanagementfragment_ProgressBar;
	public TextView layout_topicofmanagementfragment_TextView_NoData;
	public SharedPreferences spPreferences;
	protected Activity mActivity;
	private List<Map<String,String>> studentList = new ArrayList<Map<String, String>>();
	//选题管理
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		View view = inflater.inflate(R.layout.layout_topicofmanagementfragment, container, false);
		spPreferences=mActivity.getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		init(view);
		
		return view;
	}
	public void getViewObj(View view)
	{
		layout_topicofmanagementfragment_ListView=(DownrefreshListView) view.findViewById(R.id.layout_topicofmanagementfragment_ListView);
		layout_topicofmanagementfragment_ProgressBar=(ProgressBar) view.findViewById(R.id.layout_topicofmanagementfragment_ProgressBar);
		layout_topicofmanagementfragment_TextView_NoData=(TextView) view.findViewById(R.id.layout_topicofmanagementfragment_TextView_NoData);
		
	}
	public void setListener()
	{
		layout_topicofmanagementfragment_ListView.setOnItemClickListener(this);
		layout_topicofmanagementfragment_ListView.setonRefreshListener(new OnRefreshListener() {  
			  
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
                    	 layout_topicofmanagementfragment_ListView.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        });  
	}
	
	public void init(View view )
	{
		getViewObj(view);
		setListener();
		getStudentList();
	}
	
	/**
	 * onitemtclicklistener 的实现方法
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent chooseTaskIntent = new Intent(mActivity, Topic_Management_Detail.class);
		Map<String, String> map = studentList.get(position-1);
		chooseTaskIntent.putExtra("studentName", map.get("studentName"));
		chooseTaskIntent.putExtra("studentId", map.get("studentId"));
		startActivity(chooseTaskIntent);
	}
	
	/**
	 * 网络获取学生列表
	 */
	private void getStudentList(){
		String teacherID = spPreferences.getString(Constant.SPFIELD.TEACHERID, "");
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
				System.out.println(response);
				try {
					JSONObject json = new JSONObject(response);
					String success = json.getString("success");
					if(success.equals("true")){
						studentList = new ArrayList<Map<String, String>>();
						JSONArray jsonArray = json.getJSONArray("StudentInfo");
						for(int i = 0; i<jsonArray.length(); i++){
							JSONObject studentJson = jsonArray.getJSONObject(i);
							Map<String, String> stuMap = new HashMap<String, String>();
							stuMap.put("score", studentJson.getString("score"));
							stuMap.put("studentName", studentJson.getString("StudentName"));
							stuMap.put("studentId", studentJson.getString("StudentID"));
							studentList.add(stuMap);
							layout_topicofmanagementfragment_ProgressBar.setVisibility(View.GONE);
						}
						layout_topicofmanagementfragment_ListView.setAdapter(new Thesis_Topic_Management_listAdapter(mActivity, studentList));
					}else{
						T.showShort(mActivity, json.getString("ErrMsg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
}
