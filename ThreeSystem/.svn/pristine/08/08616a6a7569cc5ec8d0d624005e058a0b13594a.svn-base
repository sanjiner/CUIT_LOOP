package com.example.thesis;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.PCenter.Login;
import com.example.PCenter.MD5ENCODE;

public class Modify_password_teacher extends BaseActivity implements OnClickListener{
	public Button modify_password_back;
	public EditText old_password_teacher;
	public EditText new_password_teacher;
	public EditText new_password_Confirm_teacher;
	public Button modify_password_teacher_Submit;
	public String id;
	private SharedPreferences sharedPref;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password_teacher);
		sharedPref = getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);		
		init();
	}
	public void getViewObj()
	{
		modify_password_back=(Button) findViewById(R.id.modify_password_back);
		old_password_teacher=(EditText) findViewById(R.id.old_password_teacher);
		new_password_teacher=(EditText) findViewById(R.id.new_password_teacher);
		new_password_Confirm_teacher=(EditText) findViewById(R.id.new_password_Confirm_teacher);
		modify_password_teacher_Submit=(Button) findViewById(R.id.modify_password_teacher_Submit);
		
		
	}
	public void setlistener()
	{
		modify_password_back.setOnClickListener(this);
		modify_password_teacher_Submit.setOnClickListener(this);
		
	}
	public void init()
	{
		getViewObj();
		setlistener();
	}
	
	/**
	 * 修改信息
	 */
	private void commitChange() {

		String oldPwd = old_password_teacher.getText().toString();
		String newPwd = new_password_teacher.getText().toString();
		String reNewPwd = new_password_Confirm_teacher.getText().toString();
		id=sharedPref.getString(Constant.SPFIELD.TEACHERID,"");
		if (TextUtils.isEmpty(oldPwd)) {
			T.showShort(mActivity, "旧不能为空");
		} else if (TextUtils.isEmpty(newPwd)) {
			T.showShort(mActivity, "新密码不能为空");
		}else if (TextUtils.isEmpty(reNewPwd)) {
			T.showShort(mActivity, "确认新密码不能为空");
		}else if (!newPwd.endsWith(reNewPwd)) {
			T.showShort(mActivity, "两次新密码不相同");
		}else {
			String oldPwdMd5 = MD5ENCODE.MD5Encode(oldPwd).toString();
			String newPwdMd5 = MD5ENCODE.MD5Encode(newPwd).toString();
			String url = URLTools.buildURL(Constant.THESIS_BASEURL + "ChangeTeaPassWord",
					new String[] { "ID", "oldPsWrd", "newPsWrd","againPsWrd" }, new String[] {
					id,oldPwdMd5, newPwdMd5,  newPwdMd5});
			L.d(TAG, url);
			StringRequest stringRequest = new StringRequest(url, onResponse(),
					errorListener());
			executeRequest(stringRequest);
		}
	}
	
	/**
	 * 网络相应成功是处理返回值
	 * 
	 * @param v
	 */
	private Response.Listener<String> onResponse() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if (!TextUtils.isEmpty(response)) {
					try {
						dialog.dismiss();
						JSONObject jsonObj = new JSONObject(response);
						String isSuccess = jsonObj.getString("success");
						
						if (isSuccess.equals("true")) {
							T.showShort(mActivity, "修改成功");
							Intent reLoginIntent = new Intent(mActivity, Login.class);
							startActivity(reLoginIntent);
						} else {
							T.showShort(mActivity, jsonObj.getString("ErrMsg"));
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						L.e(TAG, e.toString());
					}
				} else {
					T.showShort(mActivity, "修改失败");
				}

			}
		};

	}
	/**
	 * 网络请求失败
	 */
	@Override
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				L.d(TAG, error.getMessage());
				dialog.dismiss();
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	
	private void Dialog()
	{
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在提交，请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.modify_password_back:
			finish();
			break;
		case R.id.modify_password_teacher_Submit:
			Dialog();
			commitChange();
			break;
		default:
			break;
		}
	}
	 
}
