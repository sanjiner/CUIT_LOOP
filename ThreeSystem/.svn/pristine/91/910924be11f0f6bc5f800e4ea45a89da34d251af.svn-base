package com.example.PCenter.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.RecordScoreDetail_Tea_Adapter;

public class Record_StudentDetail_Activity extends BaseActivity implements OnClickListener{
	private Button back, charline;
	private String number, TeachingClassID, StuNumber, className, moduleName;
	private List<Map<String, String>> info;
	private Record_StudentDetail_ListView Detail;
	private TextView tv_stuName, tv_stuId, tv_no, head, hint;
	private ProgressBar progressbar;
	private RelativeLayout relativelayout;
	private String [] score_t;
	private String [] date_t;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_students_score_tea_detail);
		Dialog();
		dialog.show();
		Intent intent = getIntent();
		number = intent.getStringExtra("number");
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		className = intent.getStringExtra("className");
		moduleName = intent.getStringExtra("moduleName");
		StuNumber = intent.getStringExtra("StuNumber");
		init();
		getDtail();
	}
	private void getDtail() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TeachingClassID", TeachingClassID);
		params.put("RecordType", number);
		String action = "New_GetStuScoreRecordByTeachingClass";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),
				new Response.ErrorListener(){
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
	private Response.Listener<String> successListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							JSONArray list = null ; 
							if(number.equals("0")||number.equals("1")||number.equals("2")||number.equals("3")){
								switch(Integer.parseInt(number)){
								case 0:list = jsonObj.getJSONArray("AttendanceRecordList");break;
								case 1:list = jsonObj.getJSONArray("QuestionRecordList");break;
								case 2:list = jsonObj.getJSONArray("StudentsWorkRecordList");break;
								case 3:list = jsonObj.getJSONArray("OtherRecordList");break;
//								case 4:list = jsonObj.getJSONArray("TotalScoreList");break;
								}
							}else{
								list = jsonObj.getJSONArray("ModuleItemRecordList");
							}
							for (int i = 0; i < list.length(); i++){
								JSONObject one = list.getJSONObject(i);
								if(one.getString("StuNumber").equals(StuNumber)){
									tv_stuName.setText(one.getString("StuName"));
									tv_stuId.setText(one.getString("StuNumber"));
									JSONArray jsonarray = null;
									if(!number.equals("4")) {
										jsonarray = one.getJSONArray("RecordDetailList");
										if(jsonarray.length() == 0){
											tv_no.setVisibility(View.VISIBLE);
										}else{
//											charline.setVisibility(View.VISIBLE);
											hint.setVisibility(View.VISIBLE);
										}
										for(int j = 0; j < jsonarray.length(); j++){
											JSONObject obj = jsonarray.getJSONObject(j);
											Map<String, String> map = new HashMap<String, String>();
											if(number.equals("0")||number.equals("1")||number.equals("2")||number.equals("3")){
												switch (Integer.parseInt(number)) {
												case 0:map.put("date", obj.getString("AttendanceDate"));
														map.put("scoreGrade", obj.getString("AttendanceState"));break;
												case 1:map.put("date", obj.getString("QuestionDate"));
														map.put("scoreGrade", obj.getString("ScoreGrade"));break;
												case 2:map.put("date", obj.getString("uploadTime"));
														map.put("scoreGrade", obj.getString("ScoreGrade"));break;
												case 3:map.put("date", obj.getString("ScoreDate"));
														map.put("scoreGrade", obj.getString("ScoreGrade"));break;
												}
											}else{
												map.put("date", obj.getString("ModuleItemName"));//date 和 name
												map.put("scoreGrade", obj.getString("ScoreGrade"));
											}
											map.put("score", obj.getString("Score"));
											map.put("ID", obj.getString("ID"));
											info.add(map);
										}
									}
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}finally{
						charline.setVisibility(View.INVISIBLE);//目前不用
						score_t = new String[info.size()];
						date_t = new String[info.size()];
						for(int i =0; i < info.size(); i++){
							score_t[i] = info.get(i).get("score");
						}
						for(int i =0; i < info.size(); i++){
							date_t[i] = info.get(i).get("date");
						}
						progressbar.setVisibility(View.INVISIBLE);
						relativelayout.setVisibility(View.VISIBLE);
						dialog.dismiss();
						String str = "1";
						final RecordScoreDetail_Tea_Adapter madapter = new RecordScoreDetail_Tea_Adapter(mActivity, info, str , number);
						madapter.setOnRightItemClickListener(new RecordScoreDetail_Tea_Adapter.onRightItemClickListener() {
							
				            @Override
				            public void onRightItemClick(View v, int position) {
				                del(position, madapter);
				            }
				        });
						Detail.setAdapter(madapter);
						if(number.equals("0")){
							charline.setVisibility(View.INVISIBLE);
						}
					}
				}
			}
		};
	}
	private void del(int position,RecordScoreDetail_Tea_Adapter madapter) {
		String action="";
		Map<String, String> params = new HashMap<String, String>();
		
		if(number.equals("0")||number.equals("2")||number.equals("1")||number.equals("3")){
			switch (Integer.parseInt(number)) {
			case 0:action = "TeacherDeleteAttendance";
					params.put("AttendanceID", info.get(position).get("ID"));break;
			case 1:action = "TeacherDeleteQuestionRecord";
					params.put("QuestionID", info.get(position).get("ID"));break;
			case 2:action = "TeacherDeleteHomework";
					params.put("HomeworkID", info.get(position).get("ID"));break;
			case 3:action="TeacherDeleteOtherScore";
					params.put("OtherScoreRecordID", info.get(position).get("ID"));break;
			}
		}else{
//			action="TeacherDeleteOtherScore";
//			params.put("OtherScoreRecordID", info.get(position).get("ID"));
		}
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener2(position , madapter),
				new Response.ErrorListener(){
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
	private Response.Listener<String> successListener2(final int position ,final RecordScoreDetail_Tea_Adapter madapter) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(success.equals("true")){
							Toast.makeText(mActivity, "删除成功",
			                        Toast.LENGTH_SHORT).show();
							info.remove(position);
							madapter.notifyDataSetChanged();
						}else{
							Toast.makeText(mActivity, "删除失败",
			                        Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	private void init() {
		getObjView();
		setOnclici();
		info = new ArrayList<Map<String, String>>();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		click();
		return false;
	}
	private void click() {
		Intent intent = new Intent(mActivity,Record_studentList_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
		intent.putExtra("TeachingClassID", TeachingClassID);
		intent.putExtra("number", number);
		intent.putExtra("TeachingClassName", className);
		intent.putExtra("modulename", moduleName);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.student_score_detail_back2:
			click();
			break;
		case R.id.student_score_detail_charLine2:
			Bundle b=new Bundle();
			Intent intent = new Intent(mActivity, Record_char_pesonActivity.class);
			b.putStringArray("score", score_t);
			b.putStringArray("date", date_t);
			b.putString("number", number);
			intent.putExtras(b);
			startActivity(intent);
		}
	}
	private void getObjView() {
		back = (Button)findViewById(R.id.student_score_detail_back2);
		charline = (Button)findViewById(R.id.student_score_detail_charLine2);
		Detail = (Record_StudentDetail_ListView)findViewById(R.id.mListView2);
		tv_stuName = (TextView)findViewById(R.id.studentName2);
		tv_stuId = (TextView)findViewById(R.id.studentID2);
		tv_no = (TextView)findViewById(R.id.record_no2);
		progressbar = (ProgressBar)findViewById(R.id.record_detail_progressbar2); 
		relativelayout = (RelativeLayout)findViewById(R.id.UserInfo2); 
		head = (TextView)findViewById(R.id.record_detail_head);
		head.setText(moduleName+"得分详情");
		hint = (TextView)findViewById(R.id.record_detail_moveHint);
	}
	private void setOnclici() {
		back.setOnClickListener(this);
		charline.setOnClickListener(this);
	}
	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在操作，请等待。。。。");  
        dialog.setTitle("提示");
	}
}
