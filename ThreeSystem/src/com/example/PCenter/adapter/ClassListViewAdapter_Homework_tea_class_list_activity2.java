package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.example.PCenter.Homework_tea_class_list_activity;
import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_Homework_tea_class_list_activity2 extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	private  String isTimeOut;
	public  String WorkID;

	public ClassListViewAdapter_Homework_tea_class_list_activity2(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.acticity_homework_tea_class_list2, null);
			holder.isTimeOutornot=(TextView)convertView.findViewById(R.id.tv_more_class_listview_item_outtime2);
			holder.WorkName = (TextView) convertView
					.findViewById(R.id.tv_more_class_listview_item_homeworkname2);
			holder.IsStuSee = (TextView) convertView
					.findViewById(R.id.tv_more_term1_listview_item_homework_stusee2);
			holder.DeadTime = (TextView) convertView
					.findViewById(R.id.tv_more_term1_listview_item_homework_datetime2);
			
//			isTimeOut=data.get(position).get("isTimeOut");
//			System.out.println("判断作业过期否：正常显示YES"+isTimeOut);
			//测试数据
//			WorkID="123456789";
			if(data.get(position).get("isTimeOut").equals("YES"))
			{
				holder.isTimeOutornot.setText("作业已过期");
			}
//			else if(isTimeOut.equals("NO"))
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


