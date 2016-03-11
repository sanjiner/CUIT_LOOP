package com.example.thesis.teacher.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;
import com.example.thesis.teacher.adapter.Thesis_Project_Management_ListAdapter.Holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Project_GuideReport_detail_adapter extends BaseAdapter {
	
	Context context;
	private List<String> data = new ArrayList<String>();
	public Project_GuideReport_detail_adapter(Context context, List<String> data) {
		super();
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
		Holder	holder;
		if(convertView==null){
			holder=new Holder();
			convertView = View.inflate(context, R.layout.project_guidereport_detail_adapter_iterm, null);
			holder.report_listview_item_text=(TextView) convertView.findViewById(R.id.report_listview_item_text);
			convertView.setTag(holder);
			
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		holder.report_listview_item_text.setText(data.get(position));
		return convertView;
	}
	public class Holder
	{
		TextView report_listview_item_text;
	}

}
