package com.example.PCenter.adapter;


import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_homework_list extends BaseAdapter {
	private String pd;
	private List<Map<String, String>> data;
	private Context context;
	private Context context2;
	public static String TeachingClassID;
	private static String WorkName;
	private static String WorkDesc;
	private  static String Memo;
	public ClassListViewAdapter_homework_list(Context context, List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
		pd="1";
	}

	public  ClassListViewAdapter_homework_list(Context context, List<Map<String, String>> data,String TeachingClassID ) {
		this.context2 = context;
		this.data = data;
		
		
		pd="2";
	
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
			if(pd=="1"){
			convertView = View.inflate(context, R.layout.more_class_viewlist_item_homework, null);
//			holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_homework_1);
//			holder.WorkScore= (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_homework_1);
//			holder.WorkName.setText(data.get(position).get("WorkName"));
//			holder.WorkScore.setText(data.get(position).get("WorkScore"));
			holder.TeacherName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_homework_2);
			holder.TeachingClassName=(TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_homework_2);
			
			TeachingClassID=data.get(position).get("TeachingClassID");
			System.out.println("教师的教学ID是!!!!!"+data.get(position).get("TeachingClassID"));
			convertView.setTag(holder);
			}
			else if(pd=="2")
			{
				
				convertView = View.inflate(context2, R.layout.tea_viewlist_item, null);
				holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_4);
				holder.WorkScore= (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_4);
				
				convertView.setTag(holder);
			}
		} 
		else {
			holder = (Holder) convertView.getTag();
			}
		if(pd=="1")
		{
		holder.TeacherName.setText(data.get(position).get("TeacherName"));
		holder.TeachingClassName.setText(data.get(position).get("TeachingClassName"));
		}
		else if(pd=="2")
		{
		holder.WorkName.setText(data.get(position).get("WorkName"));
		holder.WorkScore.setText(data.get(position).get("WorkScore"));
		}
		return convertView;
	}
	
	private class Holder {
		TextView className;
		TextView currentTerm;
		TextView TeacherName;
		TextView TeachingClassName;
		TextView WorkName;
		TextView WorkScore;
//		String TeachingClassID;
	}

}

