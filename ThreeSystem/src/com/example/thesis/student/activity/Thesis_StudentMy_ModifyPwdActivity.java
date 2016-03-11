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
import com.example.PCenter.MD5ENCODE;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Thesis_StudentMy_ModifyPwdActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener{
	private EditText oldPwd, newPwd, againPwd;
	private Button back, submit;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thesis_activity_studentmy_modifypwd);
		
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		
		initView();
	}
	private void initView() {
		oldPwd = (EditText)findViewById(R.id.thesis_changPwd_oldPwd);
		newPwd = (EditText)findViewById(R.id.thesis_changPwd_newPwd);
		againPwd = (EditText)findViewById(R.id.thesis_changPwd_againPwd);
		
		back = (Button)findViewById(R.id.thesis_changPwd_back);
		submit = (Button)findViewById(R.id.thesis_changPwd_submit);
		setEvent();
	}
	private void setEvent() {
		oldPwd.setOnFocusChangeListener(this);
		newPwd.setOnFocusChangeListener(this);
		againPwd.setOnFocusChangeListener(this);

		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_changPwd_submit:
			changPwd();
			break;
		case R.id.thesis_changPwd_back:
			finish();
			break;
		default:
			break;
		}		
	}
	private void changPwd() {
		String oldPwdStr = oldPwd.getText().toString();
		String newPwdStr = newPwd.getText().toString();
		String againPwdStr = againPwd.getText().toString();
		
		oldPwd.clearFocus();
		newPwd.clearFocus();
		againPwd.clearFocus();
		
		if(oldPwdStr.equals("")){
			T.showShort(mActivity, "请输入原密码");
		}else if(newPwdStr.equals("")){
			T.showShort(mActivity, "请输入新密码");
		}else if(againPwdStr.equals("")){
			T.showShort(mActivity, "再次输入新密码");
		}else if(!newPwdStr.equals(againPwdStr)){
			T.showShort(mActivity, "两次密码不一样");
			newPwd.getText().clear();
			againPwd.getText().clear();
		}else{
			Map<String, String> params = new HashMap<String, String>();
			L.d(TAG, sp.getString(Constant.SPFIELD.NAME, ""));
			params.put("ID", sp.getString(Constant.SPFIELD.NAME, ""));
			params.put("oldPsWrd", MD5ENCODE.MD5Encode(oldPwdStr));
			params.put("newPsWrd", MD5ENCODE.MD5Encode(newPwdStr));
			params.put("againPsWrd", MD5ENCODE.MD5Encode(againPwdStr));
			String url = URLTools.buildURL(Constant.THESIS_BASEURL+"ChangeStuPassWord", params);
			L.d(TAG, "url= "+url);
			StringRequest StringRequest = new StringRequest(url, success(),
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
			executeRequest(StringRequest);
		}
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
							T.showShort(mActivity, "修改成功");
							Editor edit = sp.edit();
							edit.putString(Constant.SPFIELD.PASSWORD, newPwd.getText().toString());
							edit.putString(Constant.SPFIELD.ISMEMORY, "no");
							edit.commit();
							finish();
						}else{
							L.d(TAG, "Success : failse");
							T.showShort(mActivity, jsonobject.getString("ErrMsg"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					T.showShort(mActivity, "修改失败,请稍后再尝试。");
				}
			}
		};
	}
	//聚焦时消失hint
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText temp=(EditText)v;
		if (!hasFocus) {// 失去焦点
			temp.setHint(temp.getTag().toString());
		} else {
			String hint=temp.getHint().toString();
			temp.setTag(hint);
			temp.setHint("");
		}
	}
}
