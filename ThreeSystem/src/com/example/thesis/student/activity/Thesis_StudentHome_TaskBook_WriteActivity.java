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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Thesis_StudentHome_TaskBook_WriteActivity extends BaseActivity implements OnClickListener{
	private Intent getI;
	private EditText BackgroundAndGoal, TaskAndRequire;
	private RadioGroup taskSource, taskType;
	private Button back, submit;
	private SharedPreferences sp;
	private String FILE;
	private ProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_taskbook_write);
		getI = getIntent();
		initView();
	}
	private void initView() {
		BackgroundAndGoal = (EditText) findViewById(R.id.thesis_StudentHome_TaskBook_Write_BackgroundAndGoal);
		TaskAndRequire = (EditText) findViewById(R.id.thesis_StudentHome_TaskBook_Write_TaskAndRequire);
		BackgroundAndGoal.setText(getI.getStringExtra("BackgroundAndGoal"));
		TaskAndRequire.setText(getI.getStringExtra("TaskAndRequire"));
		taskSource = (RadioGroup) findViewById(R.id.thesis_StudentHome_Taskbook_Write_taskSource);
		taskSourceSetCheck();
		taskType = (RadioGroup) findViewById(R.id.thesis_StudentHome_Taskbook_Write_taskType);
		taskTypeSetCheck();
		back = (Button) findViewById(R.id.thesis_StudentHome_Taskbook_Write_back);
		submit = (Button) findViewById(R.id.thesis_StudentHome_TaskBook_Write_submit);
		setEvent();
	}
	private void taskSourceSetCheck() {
		if(getI.getStringExtra("taskSource").equals("科研项目")){
			taskSource.check(R.id.thesis_StudentHome_Taskbook_Write_rb1);
		}else if(getI.getStringExtra("taskSource").equals("生产实践")){
			taskSource.check(R.id.thesis_StudentHome_Taskbook_Write_rb2);
		}else if(getI.getStringExtra("taskSource").equals("自选题目")){
			taskSource.check(R.id.thesis_StudentHome_Taskbook_Write_rb3);
		}else if(getI.getStringExtra("taskSource").equals("其他")){
			taskSource.check(R.id.thesis_StudentHome_Taskbook_Write_rb4);
		}else{}
	}
	private void taskTypeSetCheck() {
		if(getI.getStringExtra("taskType").equals("理论研究")){
			taskType.check(R.id.thesis_StudentHome_Taskbook_Write_rb11);
		}else if(getI.getStringExtra("taskType").equals("科学实验")){
			taskType.check(R.id.thesis_StudentHome_Taskbook_Write_rb22);
		}else if(getI.getStringExtra("taskType").equals("设计开发")){
			taskType.check(R.id.thesis_StudentHome_Taskbook_Write_rb33);
		}else if(getI.getStringExtra("taskType").equals("其他")){
			taskType.check(R.id.thesis_StudentHome_Taskbook_Write_rb44);
		}else{}
	}
	private void setEvent() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_Taskbook_Write_back:
			finish();
			break;
		case R.id.thesis_StudentHome_TaskBook_Write_submit:
			showDialog();
			submit();
			break;
		default:
			break;
		}
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
	private void submit() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
		if((RadioButton)findViewById(taskSource.getCheckedRadioButtonId()) != null){
			params.put("taskSource", ((RadioButton)findViewById(taskSource.getCheckedRadioButtonId())).getText().toString());
			params.put("taskType", ((RadioButton)findViewById(taskType.getCheckedRadioButtonId())).getText().toString());
		}else{
			params.put("taskSource", "");
			params.put("taskType", "");
		}
		params.put("backGround", BackgroundAndGoal.getText().toString());
		params.put("require", TaskAndRequire.getText().toString());
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "EditRecord", params);
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
							progressdialog.dismiss();
							T.showShort(mActivity, "提交成功");
							finish();
						}else{
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
							progressdialog.dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

}
