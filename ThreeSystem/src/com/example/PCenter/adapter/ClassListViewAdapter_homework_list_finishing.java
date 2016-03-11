package com.example.PCenter.adapter;


import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_homework_list_finishing extends BaseAdapter {
	private String pd;
	private List<Map<String, String>> data;
	private Context context;
	private Context context2;
	public  static String TeachingClassID;
	public  static String WorkName;
	public  static String WorkDesc;
	private  static String Memo;
	public ClassListViewAdapter_homework_list_finishing(Context context, List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
		pd="1";
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
			convertView = View.inflate(context, R.layout.viewlist_item_finishing, null);

			holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_finnishing_2);
//			holder.WorkScore= (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_finnishing_2);
			holder.DeadTime=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_finishing_DeadTime);
			holder.isTimeOut=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_finshing_isTimeOut);
			
			TeachingClassID=data.get(position).get("TeachingClassID");
			WorkName=data.get(position).get("WorkName");
			
			
			convertView.setTag(holder);
			}
		
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.WorkName.setText("题目:"+data.get(position).get("WorkName"));

		holder.DeadTime.setText("截止时间:"+data.get(position).get("DeadTime"));
		if(data.get(position).get("isTimeOut").equals("YES"))
		{
			holder.isTimeOut.setText("已过期");
		}
		else if(data.get(position).get("isTimeOut").equals("NO"))
		{
			holder.isTimeOut.setText("未过期");
		}
		
		return convertView;
	}
	
	private class Holder {
		TextView DeadTime;
		TextView isTimeOut;
		TextView className;
		TextView currentTerm;
		TextView TeacherName;
		TextView TeachingClassName;
		TextView WorkName;
//		TextView WorkScore;
//		String TeachingClassID;
	}

}


