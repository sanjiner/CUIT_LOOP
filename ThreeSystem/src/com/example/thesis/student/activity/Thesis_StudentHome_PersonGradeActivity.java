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

public class Thesis_StudentHome_PersonGradeActivity extends BaseActivity implements OnClickListener {
	private Button back;// ok1;
	private TextView redirect, read, answer, init, pass, err;
	private SharedPreferences sp;
	private String FILE;
	private Map<String, String> data;
	private LinearLayout layout;
	private ProgressDialog progressdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_persongrade);
		initView();
	}
	private void initView() {
		redirect = (TextView)findViewById(R.id.thesis_stuHome_personGrade_redirect);
		read = (TextView)findViewById(R.id.thesis_stuHome_personGrade_read);
		answer = (TextView)findViewById(R.id.thesis_stuHome_personGrade_answer);
		init = (TextView)findViewById(R.id.thesis_stuHome_personGrade_intGrade);
		pass = (TextView)findViewById(R.id.thesis_stuHome_personGrade_pass);
		back = (Button)findViewById(R.id.thesis_stuHome_personGrade_back);
//		ok1 = (Button)findViewById(R.id.thesis_stuHome_personGrade_ok1);
//		ok1.setVisibility(View.INVISIBLE);
		err = (TextView)findViewById(R.id.thesis_stuHome_personGrade_err);
		layout = (LinearLayout)findViewById(R.id.thesis_stuhome_personGrade_layout);
		layout.setVisibility(View.GONE);
		showDialog();
		progressdialog.show();
		setEvent();
		getData();
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
	}
	private void getData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "StuCheckGrade", params);
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
							data = new HashMap<String, String>();
							data.put("GuidanceTeaMark", jsonobject.getString("GuiGrade").toString());
							data.put("ReviewTeaMark", jsonobject.getString("ReiGrade").toString());
							data.put("ReplyTeamMark", jsonobject.getString("TeamGrade").toString());
							data.put("UltimateMark", jsonobject.getString("UltimateGrade").toString());
							data.put("IsPass", jsonobject.getString("Pass").toString());
							setData();
						}else{
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
							err.setText(jsonobject.getString("ErrMsg"));
							err.setVisibility(View.VISIBLE);
//							ok1.setVisibility(View.VISIBLE);
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
//		ok1.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		finish();
	}
	private void setData() {
		redirect.setText("指导老师分数: " + data.get("GuidanceTeaMark"));
		read.setText("评阅老师分数: " + data.get("ReviewTeaMark"));
		answer.setText("答辩小组分数: " + data.get("ReplyTeamMark"));
		init.setText("初评总分: " + data.get("UltimateMark"));
		pass.setText("是否通过: " + data.get("IsPass"));
		layout.setVisibility(View.VISIBLE);
//		ok1.setVisibility(View.VISIBLE);
		progressdialog.dismiss();
	}
}
