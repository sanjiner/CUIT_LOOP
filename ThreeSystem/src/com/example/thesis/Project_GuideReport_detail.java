package com.example.thesis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.DownrefreshListView;
import com.example.PCenter.DownrefreshListView.OnRefreshListener;
import com.example.thesis.teacher.adapter.Project_GuideReport_detail_adapter;

/**
 * 指导记录
 * 综合系统
 *
 */
public class Project_GuideReport_detail extends BaseActivity implements OnClickListener{

	



	public Activity mActivity;
	public Button project_guidereport_detail_back;
	public ImageView project_guidereport_detail_edit_btn;
	public DownrefreshListView layout_project_guidereport_detail_ListView;
	public ProgressBar layout_project_guidereport_detail_ProgressBar;
	public TextView layout_project_guidereport_detail_TextView_NoData;
	private List<String> datas = new ArrayList<String>();
	private String studentId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_guidreport_detail);
		mActivity=this;
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		init();
	}
	public void getViewObj()
	{
		project_guidereport_detail_back=(Button) findViewById(R.id.project_guidereport_detail_back);
		project_guidereport_detail_edit_btn=(ImageView) findViewById(R.id.project_guidereport_detail_edit_btn);
		layout_project_guidereport_detail_ListView=(DownrefreshListView) findViewById(R.id.layout_project_guidereport_detail_ListView);
		layout_project_guidereport_detail_ProgressBar=(ProgressBar) findViewById(R.id.layout_project_guidereport_detail_ProgressBar);
		layout_project_guidereport_detail_TextView_NoData=(TextView) findViewById(R.id.layout_project_guidereport_detail_TextView_NoData);
		layout_project_guidereport_detail_TextView_NoData.setVisibility(View.GONE);
	}
	public void setlistener()
	{
		project_guidereport_detail_back.setOnClickListener(this);
		project_guidereport_detail_edit_btn.setOnClickListener(this);
		
		layout_project_guidereport_detail_ListView.setonRefreshListener(new OnRefreshListener() {  
			  
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
                    	getGradeInfo();
                    	layout_project_guidereport_detail_ListView.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        }); 
		
	}
	public void init()
	{
		getViewObj();
		setlistener();
		getGradeInfo();
	}
	
	/**
	 * 通过网络获取成绩信息
	 */
	private void getGradeInfo(){
		layout_project_guidereport_detail_TextView_NoData.setVisibility(View.GONE);
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckRecord", new String[]{"studentID"}, new String[]{studentId});
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
		executeRequest(stringRequest);
	}
	/**
	 * 网络相应成功是处理返回值
	 * @param v
	 */
	private Response.Listener<String> onResponse(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println(response);
				if(!TextUtils.isEmpty(response)){
					try {
						JSONObject json = new JSONObject(response);
						String isSuccess = json.getString("success");
						if(isSuccess.equals("true")){
							
							layout_project_guidereport_detail_TextView_NoData.setVisibility(View.GONE);
							layout_project_guidereport_detail_ProgressBar.setVisibility(View.GONE);
							datas=new ArrayList<String>();
							int i = 0;
							while(true){
								if(json.has("GuideContent"+i)){
									String content = json.optString("GuideContent"+i);
									i++;
									
									datas.add(content);
								}else{
									/*如果没有  跳出循环*/
									layout_project_guidereport_detail_ProgressBar.setVisibility(View.GONE);
									
									break;
								}
								
								
								
								
							}
							layout_project_guidereport_detail_ListView.setAdapter(new Project_GuideReport_detail_adapter(mActivity, datas));
							
						}else{
							layout_project_guidereport_detail_ProgressBar.setVisibility(View.GONE);
							T.showShort(mActivity, json.getString("ErrMsg"));
						}
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						L.e(TAG, e.toString());
						T.showShort(mActivity, e.toString());
					}
				}else{
					layout_project_guidereport_detail_ProgressBar.setVisibility(View.GONE);
					T.showShort(mActivity, "获取失败");
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
				layout_project_guidereport_detail_ProgressBar.setVisibility(View.GONE);
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.project_guidereport_detail_back:
			finish();
			break;
		case R.id.project_guidereport_detail_edit_btn:
			Intent intent =new Intent(mActivity,Project_GuideReport_detail_edit.class);
			intent.putExtra("studentId", studentId);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		getGradeInfo();
		super.onResume();
	}
}
