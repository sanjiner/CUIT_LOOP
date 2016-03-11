package com.example.thesis.teacher.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Thesis_Topic_Management_listAdapter extends BaseAdapter{
	Context context;
	public  List<Map<String, String>> data;
	
	public Thesis_Topic_Management_listAdapter(Context context, List<Map<String, String>> data) {
		// TODO 自动生成的构造函数存根
		this.context=context;
		this.data=data;
		
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		Holder holder;
		if(convertView==null){
			holder = new Holder();
			convertView = View.inflate(context, R.layout.listview_topicmanage_item, null);
			holder.tv_studentName=(TextView) convertView.findViewById
					(R.id.topicmanage_listview_item_studentname);
			convertView.setTag(holder);
			
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		holder.tv_studentName.setText(data.get(position).get("studentName"));
		return convertView;
	}
	public class Holder
	{
		TextView tv_studentName;
	}
}
