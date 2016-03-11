package com.example.thesis;

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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.thesis.teacher.adapter.ThisesDefense_teacher_studentlist_adapter;

public class TeacherThesisDefenseRevscoreActivity_studentlist  extends BaseActivity implements OnClickListener,
OnItemClickListener{

	//评阅老师打分的学生列表
	public Button ThesisDefese_back;
	public ListView Frame_ThesisDefense_ListView;
	public ProgressBar Fram_ThesisDefense_ProgressBar;
	public TextView Fram_ThesisDefense_TextView_NoData;
	private List<Map<String, String>> studentList = new ArrayList<Map<String, String>>();
	protected SharedPreferences shared_Pref;
	protected Activity mActivity;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_teacher_rate);
		
		mActivity=this;
		init();
	}
	public void getViewObj()
	{
		ThesisDefese_back=(Button) findViewById(R.id.ThesisDefese_back);
		Frame_ThesisDefense_ListView=(ListView) findViewById(R.id.Frame_ThesisDefense_ListView);
		Fram_ThesisDefense_ProgressBar=(ProgressBar) findViewById(R.id.Fram_ThesisDefense_ProgressBar);
		Fram_ThesisDefense_TextView_NoData=(TextView) findViewById(R.id.Fram_ThesisDefense_TextView_NoData);
		Fram_ThesisDefense_TextView_NoData.setVisibility(View.GONE);
				
	}
	public void setlistener()
	{
		ThesisDefese_back.setOnClickListener(this);
		Frame_ThesisDefense_ListView.setOnItemClickListener(this);
	}
	public void init()
	{
		shared_Pref=getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		getViewObj();
		setlistener();
		getStudentList();
		Fram_ThesisDefense_ProgressBar.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 * 网络获取学生列表
	 */
	private void getStudentList(){
		String teacherID= shared_Pref.getString(Constant.SPFIELD.TEACHERID, "");
		Map<String, String> params =new HashMap<String, String>();
		params.put("teacherID", teacherID);
		String url = URLTools.buildURL(Constant.THESIS_BASEURL+"RevStudent", params);
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
						studentList = new ArrayList<Map<String, String>>();
						JSONArray jsonArray = json.getJSONArray("StudentInfo");
						for(int i = 0; i<jsonArray.length(); i++){
							JSONObject studentJson = jsonArray.getJSONObject(i);
							Map<String, String> stuMap = new HashMap<String, String>();
							stuMap.put("score", studentJson.getString("score"));
							stuMap.put("studentName", studentJson.getString("StudentName"));
							stuMap.put("studentID", studentJson.getString("StudentID"));
							studentList.add(stuMap);
							Fram_ThesisDefense_ProgressBar.setVisibility(View.GONE);
						}
						Fram_ThesisDefense_ProgressBar.setVisibility(View.GONE);
						Frame_ThesisDefense_ListView.setAdapter(new ThisesDefense_teacher_studentlist_adapter(mActivity, studentList));
					}else{
						T.showShort(mActivity, json.getString("ErrMsg"));
						Fram_ThesisDefense_ProgressBar.setVisibility(View.GONE);
						Fram_ThesisDefense_TextView_NoData.setVisibility(View.VISIBLE);
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
	
	
	@Override
	public void onItemClick(AdapterView<?>parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		Map<String, String> map = studentList.get(position);
		Intent intent = new Intent(mActivity, TeacherThesisDefenseRevscoreActivity_getscoreDetail.class);
		intent.putExtra("score", map.get("score"));
		intent.putExtra("studentName", map.get("studentName"));
		intent.putExtra("studentID", map.get("studentID"));
		startActivity(intent);
		
	}
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.ThesisDefese_back:
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		getStudentList();
	}
}
