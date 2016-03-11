package com.example.process;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProcessNewslistAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	public ProcessNewslistAdapter(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.process_news_viewlist, null);
			holder.className = (TextView) convertView.findViewById(R.id.process_news_title);
			holder.currentTerm = (TextView) convertView.findViewById(R.id.process_news_time);			
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.className.setText(data.get(position).get("newstitle"));
		holder.currentTerm.setText(data.get(position).get("newstime"));		
		return convertView;
	}
	
	private class Holder {
		TextView className;
		TextView currentTerm;
	}

}
