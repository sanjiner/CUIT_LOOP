package com.example.thesis.student.activity;

import java.util.HashMap;
import java.util.Map;

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
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Thesis_StudentHome_MidleCheckActivity extends BaseActivity implements OnClickListener{
	private Button back, write;
	private TextView StuSummarize, GuidanceResultS, EvaluationOfStuTask, GuidanceResultT;
	private TextView EvaluationOfStuStudy, EvaluationOfTeacher, CheckResultAndRequire;//以后可能用
	private LinearLayout layout;
	private ProgressDialog progressdialog;
	private TextView err;
	private SharedPreferences sp;
	private String FILE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_midlecheck);
		initView();
	}
	private void initView() {
		StuSummarize = (TextView) findViewById(R.id.thesis_StudentHome_MidleCheck_summary);
		GuidanceResultS= (TextView) findViewById(R.id.thesis_StudentHome_MidleCheck_Direct);
		EvaluationOfStuTask = (TextView) findViewById(R.id.thesis_StudentHome_MidleCheck_Finish);
		GuidanceResultT= (TextView) findViewById(R.id.thesis_StudentHome_MidleCheck_TeacherRedirect);
		err = (TextView) findViewById(R.id.thesis_StudentHome_MidleCheck_err);
		back = (Button) findViewById(R.id.thesis_StudentHome_MidleCheck_back);
		write = (Button) findViewById(R.id.thesis_StudentHome_MidleCheck_write);
		layout = (LinearLayout) findViewById(R.id.thesis_StudentHome_MidleCheck_layout);
		layout.setVisibility(View.GONE);
		showDialog();
		getData();
		setEvent();
	}
	private void showDialog() {
		progressdialog = new ProgressDialog(mActivity);
		progressdialog.setCanceledOnTouchOutside(false);
		progressdialog.setCancelable(true);
		progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressdialog.setTitle("提示");
		progressdialog.setIcon(R.drawable.ic_launcher);
		progressdialog.setMessage("正在加载。。。");
		L.d(TAG, "show dialog");
		progressdialog.show();
	}

	private void getData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckMidCheck", params);
		StringRequest request = new StringRequest(url, success(),
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
		executeRequest(request);
	}

	private Response.Listener<String> success() {
		return new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!response.isEmpty()){
					try {
						JSONObject jsonobject = new JSONObject(response);
						if(jsonobject.getString("success").equals("true")){
							StuSummarize.setText( jsonobject.getString("StuSummarize").toString());
							GuidanceResultS.setText( jsonobject.getString("GuidanceResultS").toString());
							EvaluationOfStuTask.setText( jsonobject.getString("EvaluationOfStuTask").toString());
							GuidanceResultT.setText( jsonobject.getString("GuidanceResultT").toString());
							layout.setVisibility(View.VISIBLE);
							progressdialog.dismiss();
						}else{
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
							err.setText(jsonobject.getString("ErrMsg"));
							err.setVisibility(View.VISIBLE);
							progressdialog.dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private void setEvent() {
		back.setOnClickListener(this);
		write.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_MidleCheck_back:
			finish();
			break;
		case R.id.thesis_StudentHome_MidleCheck_write:
			Intent i = new Intent(this,Thesis_StudentHome_MidleCheck_WriteActivity.class);
			i.putExtra("summary", StuSummarize.getText().toString());
			i.putExtra("result", GuidanceResultS.getText().toString());
			startActivity(i);
			break;

		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}

}
