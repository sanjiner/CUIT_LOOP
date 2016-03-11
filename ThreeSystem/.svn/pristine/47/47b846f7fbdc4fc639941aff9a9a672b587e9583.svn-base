package com.example.PCenter.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.PCenter.adapter.RecordScoreDetail_Stu_Adapter;

public class Record2_Detail_Activity extends BaseActivity implements OnClickListener{
	private String number, teachingclassid;
	private TextView tv_StuId, tv_StuName, mulScore, noScore;
	private Button back, charLine;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	private List<Map<String, String>> detail; 
	private String time, score,scoreGrade;
	private ListView Lv_detail;
	private LinearLayout linearlayout;
	private RelativeLayout relativelayout;
	private ProgressBar progressbar;
	private String [] dateList;
	private String [] scoreList;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_students_score_stu_detail);
		Dialog();
		dialog.show();
		Intent intent = getIntent();
		number = intent.getStringExtra("number");
		teachingclassid = intent.getStringExtra("teachingclassid");
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		init();
		getDetail();
	}

	private void getDetail() {
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("TeachingClassID", teachingclassid );
		params.put("StuNumber", sp.getString(Constant.SPFIELD.NAME, ""));
		params.put("RecordType", number );
		action = "New_StuGetOwnScoreRecord";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action,params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url,successListener(),
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
	private Response.Listener<String> successListener(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(response.length() != 0){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if(success.equals("true")){
							charLine.setVisibility(View.VISIBLE);
							if(!number.equals("4")){
								JSONArray list = jsonObj.getJSONArray("RecordDetailList");
								if(list.length() == 0){
									noScore.setVisibility(View.VISIBLE);
									charLine.setVisibility(View.INVISIBLE);
								}
								if(number.equals("0")){
									charLine.setVisibility(View.INVISIBLE);
								}
								scoreList = new String[list.length()];
								dateList = new String[list.length()];
								for(int i = 0; i <list.length(); i++){
									Map<String, String> map = new HashMap<String, String>();
									JSONObject json = list.getJSONObject(i);
									if(number.equals("0")||number.equals("1")||number.equals("2")||number.equals("3")){
										switch(Integer.parseInt(number)){
										case 0:time = json.getString("AttendanceDate");
												scoreGrade = json.getString("AttendanceState");break;
										case 1:time = json.getString("QuestionDate");
												scoreGrade = json.getString("ScoreGrade");break;
										case 2:time = json.getString("uploadTime");
												scoreGrade = json.getString("ScoreGrade");break;
										case 3:time = json.getString("ScoreDate");
												scoreGrade = json.getString("ScoreGrade");break;
										}
									}else{
										time = json.getString("ModuleItemName");
										scoreGrade = json.getString("ScoreGrade");
									}
									score = json.getString("Score");
									scoreList[i] = score;
									dateList[i] = time;
									map.put("date", time);
									map.put("scoreGrade", scoreGrade);
									map.put("score", score);
									detail.add(map);
								}
							}else{
								linearlayout.setVisibility(View.VISIBLE);
								mulScore.setText(jsonObj.getString("TotalScore"));
							}
						}else{
							L.d(TAG, "Success is false");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}finally{
						charLine.setVisibility(View.GONE);
						relativelayout.setVisibility(View.VISIBLE);
						progressbar.setVisibility(View.INVISIBLE);
						tv_StuId.setText(sp.getString(Constant.SPFIELD.NAME, ""));
						tv_StuName.setText(sp.getString(Constant.SPFIELD.REALNAME, ""));
						String str = "0";
						Lv_detail.setAdapter(new RecordScoreDetail_Stu_Adapter(mActivity, detail, str, null));
						dialog.dismiss();
					}
				}
			}
		};
	}
	private void init() {
		getObjView();
		setOnclick();
	}

	private void setOnclick() {
		back.setOnClickListener(this);
		charLine.setOnClickListener(this);
	}
	private void getObjView() {
		back = (Button)findViewById(R.id.student_score_detail_back);
		charLine = (Button)findViewById(R.id.student_score_detail_charLine);
		
		tv_StuId = (TextView)findViewById(R.id.studentID);
		tv_StuName = (TextView)findViewById(R.id.studentName);
		detail = new ArrayList<Map<String, String>>();
		Lv_detail = (ListView)findViewById(R.id.Record_one_detail);
		linearlayout = (LinearLayout)findViewById(R.id.aa);
		relativelayout = (RelativeLayout)findViewById(R.id.UserInfo);
		mulScore = (TextView)findViewById(R.id.record_total);
		noScore = (TextView)findViewById(R.id.record_no);
		progressbar = (ProgressBar)findViewById(R.id.record_detail_progressbar);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.student_score_detail_back:
			finish();
			break;
//		case R.id.student_score_detail_charLine:
//			Bundle b=new Bundle();
//			Intent intent = new Intent(mActivity, Record2_char_Activity.class);
//			b.putStringArray("dateList", dateList);
//			b.putStringArray("scoreList", scoreList);
//			b.putString("number", number);
//			intent.putExtras(b);
//			startActivity(intent);
//			break;
		}
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
