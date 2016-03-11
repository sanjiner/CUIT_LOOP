package com.example.PCenter.Home;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.plus.URLTools;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.DateTimePickerDialog;
import com.common.tools.L;
import com.common.tools.DateTimePickerDialog.OnDateTimeSetListener;
import com.common.tools.T;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;

public class HomeModuleModuleItemAddActivity extends BaseActivity implements OnClickListener{
	
	private EditText editItemName;
	private Button backButton, submitButton, tiemButton;
	private String moduleID;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_module_add_moduleitem);
		Intent intent = getIntent();
		moduleID = intent.getStringExtra("moduleID");
		
		InitView();				
	}
	
	private void InitView(){
		editItemName = (EditText) findViewById(R.id.home_module_addmodule_set_name);
		backButton = (Button) findViewById(R.id.home_module_addmodule_back);
		tiemButton = (Button) findViewById(R.id.home_module_addmodule_set_time);
		submitButton = (Button) findViewById(R.id.home_module_addmodule_submit);
		Dialog();
		Listener();
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
	
	private void Listener(){
		backButton.setOnClickListener(this);
		submitButton.setOnClickListener(this);
		tiemButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_module_addmodule_back:
			finish();
			break;
		case R.id.home_module_addmodule_set_time:
			Set_Last_Date();
			break;
		case R.id.home_module_addmodule_submit:
			String ModuleItemName = editItemName.getText().toString();
			String DeadTime = tiemButton.getText().toString();
			if(TextUtils.isEmpty(ModuleItemName)||TextUtils.isEmpty(DeadTime)){
				T.showShort(mActivity, "请填写完整");
			}else{
				Add(ModuleItemName, DeadTime, v);
			}			
			break;
		}		
	}

	private void Set_Last_Date()
	{
		DateTimePickerDialog dialog  = new DateTimePickerDialog(this, System.currentTimeMillis());
		dialog.setOnDateTimeSetListener(new OnDateTimeSetListener()
	      {
			public void OnDateTimeSet(AlertDialog dialog, long date)
			{
				//Toast.makeText(mActivity, "您输入的日期是："+getStringDate(date), Toast.LENGTH_LONG).show();
				String[] s = getStringDate(date).split(":");
				tiemButton.setText(s[0]);
			}
		});
		dialog.show();
	}
	/**
	* 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	*
	*/
	public static String getStringDate(Long date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String dateString = formatter.format(date);	
		return dateString;
	}
	
	//添加模块子项
	private void Add(String ModuleItemName, String DeadTime, View v)
	{
		dialog.show();				
		v.setEnabled(false);
		Map<String, String> params = new HashMap<String, String>();
		String action = "";		
		params.put("ModuleID", moduleID);
		params.put("ModuleItemName", ModuleItemName);
		params.put("DeadTime", DeadTime);
		action = "New_AddModuleItem";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener02(v),
				errorListener());
		executeRequest(stringRequest);
	}
	//添加模块子项监听器
	private Response.Listener<String> successListener02(final View v) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							Toast.makeText(mActivity, "添加成功", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							finish();
						} else {
							Toast.makeText(mActivity, "添加失败", Toast.LENGTH_SHORT).show();
							L.d(TAG, "Success is false");
						}
						v.setEnabled(true);
						dialog.dismiss();
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
}
