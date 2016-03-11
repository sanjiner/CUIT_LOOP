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

public class Thesis_StudentHome_MidleCheck_WriteActivity extends BaseActivity implements OnClickListener{
	private Button back, submit;
	private EditText summary;
	private RadioGroup result;
	private ProgressDialog progressdialog;
	private SharedPreferences sp;
	private String FILE;
	private Intent getI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		getI = getIntent();
		setContentView(R.layout.thesis_activity_studenthome_midlecheck_write);
		initView();
	}
	private void initView() {
		summary = (EditText) findViewById(R.id.thesis_StudentHome_MidleCheck_Write_summary);
		summary.setText(getI.getStringExtra("summary"));
		result = (RadioGroup) findViewById(R.id.thesis_StudentHome_MidleCheck_Write_rgroup);
		if(getI.getStringExtra("result").equals("指导教师坚持每周指导，认真负责，要求严格")){
			result.check(R.id.thesis_StudentHome_MidleCheck_Write_rb11);
		}else{
			result.check(R.id.thesis_StudentHome_MidleCheck_Write_rb22);
		}
		back = (Button) findViewById(R.id.thesis_StudentHome_MidleCheck_Write_back);
		submit = (Button) findViewById(R.id.thesis_StudentHome_MidleCheck_Write_submit);
		setEvent();
	}
	private void setEvent() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_MidleCheck_Write_back:
			finish();
			break;
		case R.id.thesis_StudentHome_MidleCheck_Write_submit:
			showDialog();
			submit();
//			finish();
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
		if((RadioButton)findViewById(result.getCheckedRadioButtonId()) != null){
			params.put("result", ((RadioButton)findViewById(result.getCheckedRadioButtonId())).getText().toString());
		}else{
			params.put("result", "");
		}
		params.put("summary", summary.getText().toString());
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "StuEditMidCheck", params);
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