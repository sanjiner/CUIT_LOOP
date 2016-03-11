package com.example.PCenter.Home;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.HomeModuleModuleItemListAdapter;

public class HomeModuleModuleItemListActivity extends BaseActivity implements OnClickListener,OnItemClickListener, OnItemLongClickListener{
	
	private ListView moduleItemListView;
	private Button moduleItem_back, addModleItem;
	private ProgressBar progressBar;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;
	private String userIdentity="";//用户身份
	private String TeachingClassID="";//获取当前班级ID
	private List<Map<String, String>> moduleList;
	private HomeModuleModuleItemListAdapter adapter;	
	private ProgressDialog dialog;
	private TextView noData;
	private String moduleID;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_module_moduleitem_list);
		
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
		moduleID = intent.getStringExtra("moduleID");
		
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在操作，请等待。。。。");
        dialog.setTitle("提示");
		
		//InitView();
	}
	
	private void InitView()
	{
		moduleItem_back = (Button) findViewById(R.id.moduleitem_back);
		addModleItem = (Button) findViewById(R.id.moduleitem_addModle);		
		progressBar = (ProgressBar) findViewById(R.id.moduleitem_progressbar);
		moduleItemListView = (ListView) findViewById(R.id.moduleitem_ListView);
		noData = (TextView) findViewById(R.id.moduleitem_no_data);
		
		addModleItem.setVisibility((View.INVISIBLE));
		noData.setVisibility((View.INVISIBLE));
		Listener();
	}
	
	private void Listener()
	{
		moduleItem_back.setOnClickListener(this);
		addModleItem.setOnClickListener(this);
		moduleItemListView.setOnItemClickListener(this);
		moduleItemListView.setOnItemLongClickListener(this);
		GetModuleList();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.moduleitem_back:
			finish();
			break;
		case R.id.moduleitem_addModle:
			Intent intent = new Intent(this, HomeModuleModuleItemAddActivity.class);
			intent.putExtra("moduleID", moduleID);
			startActivity(intent);
			break;
		}
	}

	private void GetModuleList()
	{
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("ModuleID", moduleID);
		action = "New_GetAllModuleItemRecordByModuleID";
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
							JSONArray jsonArray = jsonObj.getJSONArray("ModuleItemList");
							if(jsonArray.length()==0){
								progressBar.setVisibility(View.INVISIBLE);
								addModleItem.setVisibility((View.VISIBLE));
								noData.setVisibility((View.VISIBLE));
								dialog.dismiss();
							}else{
								for (int i = 0; i < jsonArray.length(); i++) {						
									JSONObject json = jsonArray.getJSONObject(i);
									Map<String, String> map = new HashMap<String, String>();
									map.put("ModuleItemName",json.getString("ModuleItemName"));
									map.put("ModuleItemID",json.getString("ModuleItemID"));
									moduleList.add(map);
								}				
								adapter = new HomeModuleModuleItemListAdapter(mActivity, moduleList);
								moduleItemListView.setAdapter(adapter);
								progressBar.setVisibility(View.INVISIBLE);
								addModleItem.setVisibility((View.VISIBLE));
								noData.setVisibility((View.INVISIBLE));
								dialog.dismiss();
							}														
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
		String ModuleItemName = moduleList.get(position).get("ModuleItemName");
		final String ModuleItemID = moduleList.get(position).get("ModuleItemID");
		final int post = position;
				
		LayoutInflater inflat = LayoutInflater.from(mActivity);
		View deletemoduleview = inflat.inflate(R.layout.more_module_is_delete, null);
		TextView deletename = (TextView) deletemoduleview.findViewById(R.id.delete_module);
		deletename.setText(ModuleItemName);
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(deletemoduleview);
		
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				DeleteModuleItem(ModuleItemID, post);
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
	private void DeleteModuleItem(String ModuleItemID,int postion)
	{
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		params.put("ModuleItemID", ModuleItemID);
		action = "New_DeleteModuleItem";
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
							//GetModuleList();							
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
	

	@Override
	protected void onResume() {
		super.onResume();
		dialog.show();
		InitView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String ModuleItemName = moduleList.get(position).get("ModuleItemName");
		String ModuleItemID = moduleList.get(position).get("ModuleItemID");
		Intent intent = new Intent(this, HomeModuleModuleItemStudentListActivity.class);
		intent.putExtra("ModuleItemName", ModuleItemName);
		intent.putExtra("ModuleItemID", ModuleItemID);
		intent.putExtra("moduleID", moduleID);
		startActivity(intent);
	}
}
