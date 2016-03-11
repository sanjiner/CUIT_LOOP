package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MoreModuleAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	
	public MoreModuleAdapter(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.more_module_gridview_item, null);
			holder.moduleName = (TextView) convertView.findViewById(R.id.more_module_gridview_item);	
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.moduleName.setText(data.get(position).get("ModuleName"));		
		return convertView;
	}
	
	private class Holder {
		TextView moduleName;
	}

}
