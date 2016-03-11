package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassListViewAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	public ClassListViewAdapter(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.class_viewlist_item, null);
			holder.className = (TextView) convertView.findViewById(R.id.tv_more_class_listview_item);
			//holder.currentTerm = (TextView) convertView.findViewById(R.id.tv_more_term1_listview_item);			
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.className.setText(data.get(position).get("className"));
		//holder.currentTerm.setText(data.get(position).get("currentTerm"));		
		return convertView;
	}
	
	private class Holder {
		TextView className;
		//TextView currentTerm;
	}

}