package com.example.PCenter.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.AttendenceStudentListActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AttenStudentListViewAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private AttendenceStudentListActivity context;
	private String TeachingClassID;
	private String className;
	private String AddedAttendanceID;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;// 用于保存SharedPreferences的文件
	JSONObject json2;
	
	public AttenStudentListViewAdapter(Activity mActivity, List<Map<String, String>> data, 
			String TeachingClassID, String className) {
		this.context = (AttendenceStudentListActivity) mActivity;
		this.data = data;
		this.TeachingClassID = TeachingClassID;
		this.className = className;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			
			holder = new Holder();
			convertView = View.inflate(context, R.layout.more_attenstudent_gridview_item, null);
			holder.stuName = (Button) convertView.findViewById(R.id.tv_more_studentname_gridview_item);
			holder.stuNum = (TextView) convertView.findViewById(R.id.tv_more_stunumber_gridview_item);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.tv_more_studen_linearlayout);
			if (data.get(position).get("isAddedAttendance").equals("YES")) {
				holder.layout.setBackgroundResource(R.drawable.shape_attendence_color);
			}
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		//从SharedPreferences取出班级对应的分数
		sp = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		
		final LinearLayout layout = holder.layout;
		final int posit = position;
		
		final ProgressDialog dialog3 = new ProgressDialog(context);
		dialog3.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog3.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog3.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog3.setIcon(R.drawable.ic_launcher);//  
        dialog3.setMessage("正在操作，请等待。。。。");  
        dialog3.setTitle("提示");
		
		
//		final AlertDialog dialog = new AlertDialog.Builder(context)
//				.setTitle("正在点到，请等待。。。。")
//				.setCancelable(true)
//				.create();
//		final AlertDialog dialog2 = new AlertDialog.Builder(context)
//				.setTitle("正在取消，请等待。。。。")
//				.setCancelable(true)
//				.create();
		
		holder.stuName.setText(data.get(position).get("stuName"));
		holder.stuNum.setText(data.get(position).get("stuNum"));
		holder.stuName.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(final View v) {
				//取消点到
				if(data.get(posit).get("isAddedAttendance").equals("YES")){
					dialog3.show();
					v.setEnabled(false);
					Map<String, String> params = new HashMap<String, String>();
					String action = "";
					String Term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
					String TeacherName = sp.getString(Constant.SPFIELD.NAME, "");
					params.put("TeacherName", TeacherName);
					params.put("Term", Term);
					action = "New_GetAllStudentByTeacher";
					String url2 = URLTools
							.buildURL(Constant.INTERFACE_SITE + action, params);
					L.d("TTTTT", url2);
					StringRequest stringRequest2 = new StringRequest(url2, new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							if (response.length() != 0) {
								try {
									JSONObject jsonObj = new JSONObject(response);
									String success = jsonObj.getString("Success");
									if (success.equals("true")) {
										JSONArray jsonArray = jsonObj.getJSONArray("ClassList");
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject json = jsonArray.getJSONObject(i);
											if(json.getString("TeachingClassName").equals(className))
											{
												JSONArray jsonArray2 = json.getJSONArray("StudentList");
												json2 = jsonArray2.getJSONObject(posit);
												if(data.get(posit).get("isAddedAttendance").equals("YES"))
												{
													AddedAttendanceID = json2.getString("AddedAttendanceID");
	
													String url =Constant.INTERFACE_SITE
															+ "TeacherDeleteAttendance?AttendanceID="+AddedAttendanceID;
													L.d("TT", url);
													StringRequest stringRequest = new StringRequest(url,
															new Response.Listener<String>() {
																@Override
																public void onResponse(String response) {
																	try {
																		JSONObject json = new JSONObject(
																				response);
																		String success = json
																				.getString("Success");
																		if (success.equals("true")) {
																			Toast.makeText(context, "取消点到成功",
																				1000).show();																		
																			layout.setBackgroundResource(R.drawable.shape_attendence_no_color);
																			data.get(posit).put("isAddedAttendance","NO");	
																		} else {
																			Toast.makeText(context, "取消失败",
																					1000).show();
																		}
																		v.setEnabled(true);
																		dialog3.dismiss();
																	} catch (JSONException e) {
																		L.e("AttenStudentListViewAdapter",
																				e.toString());
																	}
																}

															}, new Response.ErrorListener() {
																@Override
																public void onErrorResponse(VolleyError error) {
																	Toast.makeText(
																			context,
																			VolleyErrorHelper
																					.getErrorType(error),
																			Toast.LENGTH_LONG).show();
																}
															});
													context.executeRequest(stringRequest);
												}
											}
										}
									}
									else {
										L.d("TTTTT", "Success is false");
									}
								} catch (JSONException e) {
									L.e("TTTTT", e.toString());
								}
							}
						}
					},
						new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(
									context,
									VolleyErrorHelper
											.getErrorType(error),
									Toast.LENGTH_LONG).show();
						}
					});
					context.executeRequest(stringRequest2);
					
					
				}else{
					dialog3.show();
					v.setEnabled(false);
					String stuNum = data.get(posit).get("stuNum");
					String url = Constant.INTERFACE_SITE+"New_AddAttendanceState?StuNumber="+stuNum
							+"&TeachingClassID="+TeachingClassID;
					L.d("TTTTT", url);
					StringRequest stringRequest = new StringRequest(url,
							new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									try {
										JSONObject json = new JSONObject(
												response);
										String success = json.getString("Success");
										if (success.equals("true")) {
											Toast.makeText(context, "点到成功", 1000)
													.show();
											layout.setBackgroundResource(R.drawable.shape_attendence_color);
											data.get(posit).put("isAddedAttendance","YES");	
										}else{
											Toast.makeText(context, "点到失败", 1000)
											.show();
										}
										v.setEnabled(true);
										dialog3.dismiss();
									}catch (JSONException e) {
										L.e("AttenStudentListViewAdapter",
												e.toString());
									}
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
									Toast.makeText(
											context,
											VolleyErrorHelper
													.getErrorType(error),
											Toast.LENGTH_LONG).show();
								}
							});
					context.executeRequest(stringRequest);
				}
			}
		});
		
		
		return convertView;
	}
	
	private class Holder {
		Button stuName;
		TextView stuNum;
		LinearLayout layout;
	}

}

