package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.example.PCenter.Homework_tea_class_list_activity;
import com.exam.ThreeSystem.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassListViewAdapter_Homework_tea_class_list_activity extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	private String isTimeOut;
	public static String WorkID;
	private LinearLayout getdata_linearlayout;
	private FrameLayout nodata_framelayout;
	public ClassListViewAdapter_Homework_tea_class_list_activity(Context context, List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
		
		
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
			convertView = View.inflate(context, R.layout.activity_homework_tea_class_list, null);
//			if(data.size()==0)
//			{
//				
//			}
			holder.isTimeOutornot=(TextView)convertView.findViewById(R.id.tv_more_class_listview_item_outtime);
			holder.isTimeOutornot.setVisibility(View.GONE);
			holder.WorkName = (TextView) convertView
					.findViewById(R.id.tv_more_class_listview_item_homeworkname);
			holder.IsStuSee = (TextView) convertView
					.findViewById(R.id.tv_more_term1_listview_item_homework_stusee);
			holder.DeadTime = (TextView) convertView
					.findViewById(R.id.tv_more_term1_listview_item_homework_datetime);
			
//			isTimeOut=data.get(position).get("isTimeOut");
//			System.out.println("查看作业是否过期:正常显示NO"+isTimeOut);
			//测试数据
//			WorkID="123456789";
//			if(isTimeOut.equals("YES"))
//			{
//				holder.isTimeOutornot.setText("作业已过期");
//			}
//			if(isTimeOut.equals("NO"))
//			{
//				holder.isTimeOutornot.setText("作业未过期");
//			}
			
			convertView.setTag(holder);
			
		} 
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		if(data.get(position).get("IsStuSee").equals("yes"))
		{
			holder.IsStuSee.setText("学生可见");
		}
		else if(data.get(position).get("IsStuSee").equals("no"))
		{
			holder.IsStuSee.setText("学生不可见");
			
		}
		
		holder.WorkName.setText("作业题目："+data.get(position).get("WorkName"));
		//holder.IsStuSee.setText(data.get(position).get("IsStuSee"));
		holder.DeadTime.setText("截止时间:"+data.get(position).get("DeadTime"));
		
		return convertView;
	}
	
	private class Holder {
		
		TextView WorkName;
		TextView IsStuSee;
		TextView DeadTime;
		TextView isTimeOutornot;
		
		
	}

}

