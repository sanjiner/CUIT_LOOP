package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeModuleModuleItemListAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	
	public HomeModuleModuleItemListAdapter(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.home_module_moduleitem, null);
			holder.moduleItemName = (TextView) convertView.findViewById(R.id.home_module_add_moduleitem);	
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.moduleItemName.setText(data.get(position).get("ModuleItemName"));		
		return convertView;
	}
	
	private class Holder {
		TextView moduleItemName;
	}

}
