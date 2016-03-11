package com.example.PCenter.adapter;


import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_homework extends BaseAdapter {
	private String pd;
	private List<Map<String, String>> data;
	private Context context;
	private Context context2;
	public  static String TeachingClassID;
	private String WorkName;
	private String WorkDesc;
	private String Memo;
	public ClassListViewAdapter_homework(Context context, List<Map<String, String>> data) {
		this.context2 = context;
		this.data = data;
		
		pd="2";
	}

	public  ClassListViewAdapter_homework(Context context, List<Map<String, String>> data,String hhh ) {
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
//			convertView = View.inflate(context, R.layout.more_class_viewlist_item_homework, null);
//			holder.TeachingClassName = (TextView) convertView.findViewById(R.id.tv_more_class_listview_item_homework_1);
//			holder.Term = (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_homework_1);
//			holder.TeachingClassName.setText(data.get(position).get("TeachingClassName"));
//			holder.Term.setText(data.get(position).get("Term"));
			
			
//			convertView.setTag(holder);
			}
			else if(pd=="2")
			{
				
				convertView = View.inflate(context2, R.layout.tea_viewlist_item, null);
				
				holder.TeachingClassName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_4);
				
				holder.Term = (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_4);
//				holder.Term.setText(data.get(position).get("Term"));
				holder.Term.setVisibility(View.GONE);
//				holder.TeacherName=(TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_2);
//				holder.TeacherName.setText(data.get(position).get("TeacherName"));

		
//				holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_3);
//				holder.WorkScore= (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_3);
//				holder.WorkName.setText(data.get(position).get("WorkName"));
//				holder.WorkScore.setText(data.get(position).get("WorkScore"));
//				TeachingClassID=data.get(position).get("TeachingClassID");
				
				//测试数据
//				TeachingClassID="9b61baf866d044819887dd2c65f67334";
//				if(TeachingClassID==null)
//				{
//				TeachingClassID=(String)data.get(position).get("TeachingClassID");
//				System.out.println("教师的教学ID"+data.get(position).get("TeachingClassID"));
//				
//				}
				
				convertView.setTag(holder);
				
			}
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.TeachingClassName.setText(data.get(position).get("TeachingClassName"));
		
		return convertView;
	}
	
	private class Holder {
		TextView className;
		TextView currentTerm;
		TextView TeacherName;
		TextView TeachingClassName;
		TextView WorkName;
		TextView WorkScore;
		TextView Term;
		
	}

}

