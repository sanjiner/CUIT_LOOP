package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeModuleAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	private int menu_toolbar_image_array[] = { R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question, R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question, R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question, R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question, R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question, R.drawable.attendance,
			R.drawable.homework, R.drawable.record,
			R.drawable.question };
	
	
	public HomeModuleAdapter(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.home_module_gridview_item, null);
			holder.moduleName = (TextView) convertView.findViewById(R.id.home_module_gridview_item);
			holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.home_module_relativeLayout_item);
			holder.relativeLayout.setBackgroundResource(menu_toolbar_image_array[position]);		
			convertView.setTag(holder);	
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.moduleName.setText(data.get(position).get("ModuleName"));
		return convertView;
	}
	
	private class Holder {
		TextView moduleName;
		RelativeLayout relativeLayout;
	}

}
