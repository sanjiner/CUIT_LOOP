package com.example.PCenter.More;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.common.tools.T;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.MoreModuleAdapter;

public class MoreModule_SetModule extends BaseActivity implements OnClickListener,OnItemLongClickListener{
	
	private GridView moduleGridView;
	private Button module_back, addModle;
	private ProgressBar progressBar;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String userIdentity="";//用户身份
	private String TeachingClassID="";//获取当前班级ID
	private List<Map<String, String>> moduleList;
	private MoreModuleAdapter adapter;	
	private ProgressDialog dialog;
	private TextView noData, info;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_module_set_module);
		
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		
		InitView();
	}
	
	private void InitView()
	{
		module_back = (Button) findViewById(R.id.setModle_back);
		addModle = (Button) findViewById(R.id.setModle_addModle);		
		progressBar = (ProgressBar) findViewById(R.id.setModle_module_progressbar);
		moduleGridView = (GridView) findViewById(R.id.setModle_modle_gridview);
		info = (TextView) findViewById(R.id.setModle_info);
		noData = (TextView) findViewById(R.id.setModle_no_data);
		
		addModle.setVisibility((View.INVISIBLE));
		info.setVisibility((View.INVISIBLE));
		noData.setVisibility((View.INVISIBLE));
		
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
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
	}
	
	private void Listener()
	{
		module_back.setOnClickListener(this);
		addModle.setOnClickListener(this);
		moduleGridView.setOnItemLongClickListener(this);
		GetModuleList();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.setModle_back:
			finish();
			break;
		case R.id.setModle_addModle:	
			AddModule(v);
			break;
		}
	}
	
	private void AddModule(final View v) {
		LayoutInflater inflat = LayoutInflater.from(mActivity);
		View addmoduleview = inflat.inflate(R.layout.more_module_add, null);
		final EditText moduleName = (EditText) addmoduleview.findViewById(R.id.add_module_name);
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(addmoduleview);
		
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				String modulename = moduleName.getText().toString();
				if(!TextUtils.isEmpty(modulename)){					
					Add(modulename, TeachingClassID,v);
				}else{
					T.showShort(mActivity, "请填写模块名字");
				}
			}
		});
		
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
					{
				
					}
		} );		
		AlertDialog dialog=builder.create();
		dialog.show();	
	}

	private void GetModuleList()
	{
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("TeachingClassID", TeachingClassID);
		action = "New_GetModuleListByTeachingClassID";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
								.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(stringRequest);
	}

	private Response.Listener<String> successListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							moduleList = new ArrayList<Map<String, String>>();						
							JSONArray jsonArray = jsonObj.getJSONArray("ModuleList");
							if(jsonArray.length()==0){
								progressBar.setVisibility(View.INVISIBLE);
								addModle.setVisibility((View.VISIBLE));
								info.setVisibility((View.VISIBLE));
								noData.setVisibility((View.VISIBLE));
							}else{
								for (int i = 0; i < jsonArray.length(); i++) {						
									JSONObject json = jsonArray.getJSONObject(i);
									Map<String, String> map = new HashMap<String, String>();
									map.put("ModuleName",json.getString("ModuleName"));
									map.put("ModuleID",json.getString("ModuleID"));
									moduleList.add(map);
								}				
								adapter = new MoreModuleAdapter(mActivity, moduleList);
								moduleGridView.setAdapter(adapter);
								progressBar.setVisibility(View.INVISIBLE);
								addModle.setVisibility((View.VISIBLE));
								info.setVisibility((View.VISIBLE));
								noData.setVisibility((View.INVISIBLE));
							}
							dialog.dismiss();
						} else {
							L.d(TAG, "Success is false");
						}
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
			long id) {
		
		//Toast.makeText(mActivity, "长按点击了", Toast.LENGTH_SHORT).show();
		String modulename = moduleList.get(position).get("ModuleName");
		final String moduleID = moduleList.get(position).get("ModuleID");
		final int post = position;
				
		LayoutInflater inflat = LayoutInflater.from(mActivity);
		View deletemoduleview = inflat.inflate(R.layout.more_module_is_delete, null);
		TextView deletename = (TextView) deletemoduleview.findViewById(R.id.delete_module);
		deletename.setText(modulename);
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(deletemoduleview);
		
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				DeleteModule(moduleID, post);
			}
		});
		
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
					{
				
					}
		} );		
		AlertDialog dialog=builder.create();
		dialog.show();
		return false;
	}
	
	//删除模块
	private void DeleteModule(String moduleID,int postion)
	{
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("ModuleID", moduleID);
		action = "New_DeleteModule";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener01(postion),
				errorListener());
		executeRequest(stringRequest);
	}
	//删除模块监听器
	private Response.Listener<String> successListener01(final int postion) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {							
							moduleList.remove(postion);
							adapter.notifyDataSetChanged();
							GetModuleList();							
							Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
							L.d(TAG, "Success is false");
						}
						dialog.dismiss();
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
	
	//添加模块
	private void Add(String ModuleName,String TeachingClassID, View v)
	{
		dialog.show();
		v.setEnabled(false);
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("TeachingClassID", TeachingClassID);
		params.put("ModuleName", ModuleName);
		action = "New_AddModule";
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener02(v),
				errorListener());
		executeRequest(stringRequest);
	}
	//添加模块监听器
	private Response.Listener<String> successListener02(final View v) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							GetModuleList();
							//moduleList.add(ModuleName);
							//adapter.notifyDataSetChanged();
							Toast.makeText(mActivity, "添加成功", Toast.LENGTH_SHORT).show();
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
