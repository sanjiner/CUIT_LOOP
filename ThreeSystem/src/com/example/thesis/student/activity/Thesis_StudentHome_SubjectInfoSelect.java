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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Thesis_StudentHome_SubjectInfoSelect extends BaseActivity implements OnClickListener {
	private TextView GuideTeacher, ViewTeacher, ReplyLeader, ReplySecretary, ReplyOtherTeacher, ReplyGroupStudent;
	private Button back;
	private TextView err;
	private LinearLayout layout;
	private SharedPreferences sp;
	private String FILE;
	private ProgressDialog progressdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_subjectinfoselect);
		initView();
	}

	private void initView() {
		GuideTeacher = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_GuideTeacher); 
		ViewTeacher = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ViewTeacher); 
		ReplyLeader = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ReplyLeader); 
		ReplySecretary = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ReplySecretary); 
		ReplyOtherTeacher = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ReplyOtherTeacher); 
		ReplyGroupStudent = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ReplyGroupStudent);
		err = (TextView) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_err);
//		ok =  (Button) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_ok);
//		ok.setVisibility(View.INVISIBLE);
		back =  (Button) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_back);
		layout = (LinearLayout) findViewById(R.id.thesis_StudentHome_SubjectInfoSelect_layout);
		layout.setVisibility(View.INVISIBLE);
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
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckTaskInfo", params);
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
							//data = new HashMap<String, String>();
							GuideTeacher.setText("指导老师:  " + jsonobject.getString("GuiTeacherName").toString());
							ViewTeacher.setText("评阅老师:  " + jsonobject.getString("RevTeacherName").toString());
							ReplyLeader.setText("答辩组长:  " + jsonobject.getString("LeaderTeacherName").toString());
							ReplySecretary.setText("答辩秘书:  " + jsonobject.getString("SecTeacherName").toString());
							ReplyOtherTeacher.setText("答辩其他老师:  " + jsonobject.getString("TeacherGroup").toString());
							ReplyGroupStudent.setText("答辩组学生:  " + jsonobject.getString("StudentGroup").toString());
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
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
