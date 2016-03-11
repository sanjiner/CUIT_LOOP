package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;





import com.exam.ThreeSystem.R;
import com.example.PCenter.Home.HomeModuleModuleItemStudentListActivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeModuleStudentListViewAdapter extends BaseAdapter {
	
	private List<Map<String, String>> data;
	private Context context;
	Holder holder;
	
	public HomeModuleStudentListViewAdapter(Context context, List<Map<String, String>> data) {
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
		
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context, R.layout.home_module_studentlistview_item, null);			
			holder.studentId = (TextView) convertView
					.findViewById(R.id.home_module_listview_stunumber_item);
			holder.studentName = (TextView) convertView
					.findViewById(R.id.home_module_listview_stuname_item);
			holder.studentScore = (TextView) convertView.findViewById(R.id.home_module_listview_stuscore);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.studentId.setText(data.get(position).get("stuNum"));
		holder.studentName.setText(data.get(position).get("stuName"));	
		holder.studentScore.setText(data.get(position).get("ScoreGrade"));
		return convertView;
	}

	private class Holder {		
		TextView studentId;
		TextView studentName;
		TextView studentScore;
	}
}
