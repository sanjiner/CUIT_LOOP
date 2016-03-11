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

public class Thesis_StudentMy_PersonInfoActivity extends BaseActivity implements OnClickListener {
	private SharedPreferences sp;
	private String FILE;
	private TextView name, ID, major, className, sex, age, phone, email;
	private Map<String, String> data;
	private Button back,ok;
	private ProgressDialog progressdialog;
	private LinearLayout xml;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thesis_activity_studentmy_personinfo);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		initView();
		
	}
	private void initView() {
		name = (TextView) findViewById(R.id.thesis_studentPersonInfo_name);
		ID = (TextView) findViewById(R.id.thesis_studentPersonInfo_ID);
		major = (TextView) findViewById(R.id.thesis_studentPersonInfo_major);
		className = (TextView) findViewById(R.id.thesis_studentPersonInfo_class);
		sex = (TextView) findViewById(R.id.thesis_studentPersonInfo_sex);
		age = (TextView) findViewById(R.id.thesis_studentPersonInfo_age);
		phone = (TextView) findViewById(R.id.thesis_studentPersonInfo_phone);
		email = (TextView) findViewById(R.id.thesis_studentPersonInfo_email);
		back = (Button)findViewById(R.id.thesis_StudentPersonInfo_back);
		ok = (Button)findViewById(R.id.thesis_studentPersonInfo_ok);
		ok.setVisibility(View.INVISIBLE);
		xml = (LinearLayout)findViewById(R.id.thises_studentPersonInfo_xml);
		showDialog();
		setOnclick();
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
	private void setOnclick() {
		ok.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	private void getData() {
		progressdialog.show();
		Map<String,String> params = new HashMap<String, String>();
		params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
		L.d(TAG, "currentID = " + sp.getString(Constant.SPFIELD.NAME, ""));
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "StuInfo", params);
		StringRequest StringRequest = new StringRequest(url, seccess(),  new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
								.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		} );
		executeRequest(StringRequest);
		
	}
	
	private Response.Listener<String> seccess() {
		return new Response.Listener<String>(){

			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!response.isEmpty()){
					try {
						JSONObject jsonobject = new JSONObject(response);
						if(jsonobject.getString("success").equals("true")){
							data = new HashMap<String, String>();
							L.d(TAG, jsonobject.getString("StudentName").toString());
							data.put("name", jsonobject.getString("StudentName").toString());
							data.put("ID",jsonobject.getString("StudentID").toString());
							data.put("major",jsonobject.getString("Major").toString());
							data.put("className",jsonobject.getString("Class").toString());
							data.put("sex",jsonobject.getString("Sex").toString());
							data.put("age",jsonobject.getString("Age").toString());
							data.put("phone",jsonobject.getString("Tel").toString());
							data.put("email",jsonobject.getString("Email").toString());
						}else{
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					setText();
				}
			}
			
		};
	}
	
	private void setText() {
		name.setText(data.get("name").toString());
		ID.setText(data.get("ID"));
		major.setText(data.get("major"));
		className.setText(data.get("className"));
		sex.setText(data.get("sex"));
		age.setText(data.get("age"));
		phone.setText(data.get("phone"));
		email.setText(data.get("email"));
		xml.setVisibility(View.VISIBLE);
		ok.setVisibility(View.VISIBLE);
		progressdialog.dismiss();
	}
	@Override
	public void onClick(View v) {
		finish();
	}
}

//name.setText(jsonobject.getString("StudentName").toString());
//ID.setText(jsonobject.getString("StudentID").toString());
//major.setText(jsonobject.getString("Major").toString());
//className.setText(jsonobject.getString("Class").toString());
//sex.setText(jsonobject.getString("Sex").toString());
//phone.setText(jsonobject.getString("Tel").toString());
//name.setText(jsonobject.getString("Email").toString());