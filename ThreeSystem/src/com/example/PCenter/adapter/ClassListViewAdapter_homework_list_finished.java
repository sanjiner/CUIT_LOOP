package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter_homework_list_finished extends BaseAdapter{
	private List<Map<String, String>> data;
	private Context context;
	public  static String TeachingClassID;
	public ClassListViewAdapter_homework_list_finished(Context context, List<Map<String, String>> data) {
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

	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null)
		{
			holder = new Holder();
			convertView = View.inflate(context, R.layout.viewlist_item_finished, null);
			holder.WorkName=(TextView) convertView.findViewById(R.id.tv_more_class_listview_item_finnished_1);
			holder.DeadTime=(TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_finished_DeadTime);
			holder.isTimeOut=(TextView) convertView.findViewById(R.id.tv_more_term1_listview_item_finished_isTimeOut);
			TeachingClassID=data.get(position).get("TeachingClassID");
			
			
			convertView.setTag(holder);
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		if(data.get(position).get("isTimeOut").equals("YES"))
		{
			holder.isTimeOut.setText("已过期");
		}
		else if(data.get(position).get("isTimeOut").equals("NO"))
		{
			holder.isTimeOut.setText("未过期");
		}
		
		holder.WorkName.setText("题目:"+data.get(position).get("WorkName"));
		holder.DeadTime.setText("截止时间:"+data.get(position).get("DeadTime"));
		return convertView;
	}

private class Holder {
	TextView className;
	TextView currentTerm;
	TextView TeacherName;
	TextView TeachingClassName;
	TextView WorkName;
//	TextView WorkScore;
	TextView isTimeOut;
	TextView DeadTime;
//	String TeachingClassID;
}

}
