package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_homework_list_finished2 extends BaseAdapter{
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
	
	public ClassListViewAdapter_homework_list_finished2(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.activity_teahomeworklist2, null);
			holder.StuName=(TextView) convertView.findViewById(R.id.tv_more_class_name2);
			holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_id2);
			holder.DeadTime=(TextView) convertView.findViewById(R.id.tv_more_term1_deadtime2);
//			holder.StuName.setText("姓名:"+data.get(position).get("StuName"));
			holder.StuName.setVisibility(View.GONE);
			
			convertView.setTag(holder);
					
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		
		holder.DeadTime.setText("截止日期:"+data.get(position).get("DeadTime"));
		holder.WorkName.setText("题目:"+data.get(position).get("WorkName"));
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
		TextView WorkName;
		
		
	}
}

