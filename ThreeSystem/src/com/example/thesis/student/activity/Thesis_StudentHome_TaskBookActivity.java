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

public class Thesis_StudentHome_TaskBookActivity extends BaseActivity implements OnClickListener {
	private TextView subjectName, subjectSource, subjectType, subjectBackgroundAndGoal, TaskAndRequire,DepartmentCheckIdea,DepartmentCheckTime,DepartmentCheckSig, err;
	private Button back,  write;
	private ProgressDialog progressdialog;
	private SharedPreferences sp;
	private String FILE;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_taskbook);
		initView();
	}
	private void initView() {
		subjectName = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_SubjectName);
		subjectSource = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_SubjectSource);
		subjectType = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_SubjectType);
		subjectBackgroundAndGoal = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_SubjectBackgroudAndGoal);
		TaskAndRequire = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_TaskAndRequire);
		DepartmentCheckIdea = (TextView)findViewById(R.id.thesis_StudentHome_TaskBook_DepartmentCheckIdea);
		DepartmentCheckTime = (TextView)findViewById(R.id.thesis_StudentHome_TaskBook_DepartmentCheckTime);
		DepartmentCheckSig = (TextView)findViewById(R.id.thesis_StudentHome_TaskBook_DepartmentCheckSig);
		err = (TextView) findViewById(R.id.thesis_StudentHome_TaskBook_err);
		back = (Button) findViewById(R.id.thesis_StudentHome_TaskBook_back);
//		ok = (Button) findViewById(R.id.thesis_StudentHome_TaskBook_ok);
//		ok.setVisibility(View.INVISIBLE);
		write = (Button) findViewById(R.id.thesis_StudentHome_TaskBook_write);
		layout = (LinearLayout) findViewById(R.id.thesis_StudentHome_TaskBook_layout);
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
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckTask", params);
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
							subjectName.setText("课题名称:  " + jsonobject.getString("TaskName").toString());
							subjectSource.setText( jsonobject.getString("TaskSource").toString());
							subjectType.setText( jsonobject.getString("TaskType").toString());
							subjectBackgroundAndGoal.setText(  jsonobject.getString("BackGroundAndAim").toString());
							TaskAndRequire.setText(  jsonobject.getString("TaskAndRequire").toString());
							DepartmentCheckIdea.setText(jsonobject.getString("DepartmentCheckIdea").toString());
							DepartmentCheckTime.setText(jsonobject.getString("DepartmentCheckTime").toString());
							DepartmentCheckSig.setText(jsonobject.getString("DepartmentCheckSign").toString());
							layout.setVisibility(View.VISIBLE);
//							ok.setVisibility(View.VISIBLE);
							progressdialog.dismiss();
						}else{
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
							err.setText(jsonobject.getString("ErrMsg"));
							err.setVisibility(View.VISIBLE);
//							ok.setVisibility(View.VISIBLE);
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
//		ok.setOnClickListener(this);
		back.setOnClickListener(this);
		write.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_TaskBook_back:
			finish();
			break;
//		case R.id.thesis_StudentHome_TaskBook_ok:
//			finish();
//			break;
		case R.id.thesis_StudentHome_TaskBook_write:
			Intent i = new Intent(mActivity, Thesis_StudentHome_TaskBook_WriteActivity.class);
			i.putExtra("taskSource", subjectSource.getText().toString());
			i.putExtra("taskType", subjectType.getText().toString());
			i.putExtra("BackgroundAndGoal", subjectBackgroundAndGoal.getText().toString());
			i.putExtra("TaskAndRequire", TaskAndRequire.getText().toString());
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
