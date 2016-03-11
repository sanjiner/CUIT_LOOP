package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Homework_everystu_Adapter extends BaseAdapter{
	private List<Map<String, String>> data;
	private Context context;
	public static String WorkScore;
	public static String WorkName;
	public static String WorkContent;
	public static String WorkMemo;
//	public static String StuAnswer;
	public static String uploadTime;
	public static String StuNumber;
	public static String StuName;
	public static String DeadTime;
	public Homework_everystu_Adapter(Context context, List<Map<String, String>> data) {
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder holder;
		if (convertView == null)
		{
			holder = new Holder();
			convertView = View.inflate(context, R.layout.activity_teahomeworklist, null);
			holder.StuName=(TextView) convertView.findViewById(R.id.tv_more_class_name);
			holder.StuNumber=(TextView) convertView.findViewById(R.id.tv_more_class_id);
			holder.DeadTime=(TextView) convertView.findViewById(R.id.tv_more_term1_deadtime);
			
			convertView.setTag(holder);
					
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		holder.StuName.setText("姓名:"+data.get(position).get("StuName"));
		holder.StuNumber.setText("学号:"+data.get(position).get("StuNumber"));
		holder.DeadTime.setText("截止日期:"+data.get(position).get("DeadTime"));
		getdata( position);
		return convertView;
	}

	public void getdata(int position)
	{
		WorkScore=data.get(position).get("WorkScore");
		WorkName=data.get(position).get("WorkName");
		WorkContent=data.get(position).get("WorkContent");
		WorkMemo=data.get(position).get("WorkMemo");
//		StuAnswer=data.get(position).get("StuAnswer");
		uploadTime=data.get(position).get("uploadTime");
		StuNumber=data.get(position).get("StuNumber");
		StuName=data.get(position).get("StuName");
		DeadTime=data.get(position).get("DeadTime");
		
	}
private class Holder {
		
		TextView StuNumber;
		TextView StuName;
		TextView DeadTime;

		
		
	}
}
