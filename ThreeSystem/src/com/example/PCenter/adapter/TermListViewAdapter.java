package com.example.PCenter.adapter;

import java.util.List;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TermListViewAdapter extends BaseAdapter {
	private List<String> data;
	private Context context;
	
	public TermListViewAdapter(Context context, List<String> data) {
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
			convertView = View.inflate(context, R.layout.more_term_viewlist_item, null);
			holder.termName = (TextView) convertView
					.findViewById(R.id.tv_more_term_listview_item);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.termName.setText(data.get(position));
		return convertView;
	}
	
	private class Holder {
		TextView termName;
		
	}

}
