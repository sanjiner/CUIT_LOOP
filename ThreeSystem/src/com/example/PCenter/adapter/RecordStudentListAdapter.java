package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;
import com.example.PCenter.Record.Record_studentList_Activity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordStudentListAdapter extends BaseAdapter{
	private List<Map<String, String>> data;
	private Record_studentList_Activity context;
	private Holder holder;
	
	public RecordStudentListAdapter(Activity mActivity,
			List<Map<String, String>> data) {
		this.context = (Record_studentList_Activity) mActivity;
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
			convertView = View.inflate(context, R.layout.record_student_list_item, null);
			holder.studentName = (TextView) convertView
					.findViewById(R.id.tv_record_studentname_listview_item);
			holder.studentId = (TextView) convertView
					.findViewById(R.id.tv_record_stunumber_listview_item);
			holder.studentScore = (TextView) convertView
					.findViewById(R.id.record_student_score);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.studentName.setText(data.get(position).get("StuName"));
		holder.studentId.setText(data.get(position).get("StuNumber"));
//		if(!data.get(position).get("AllScore").equals("0")){
			holder.studentScore.setText(data.get(position).get("AllScore") + "分");
//		}
		return convertView;
	}
	private class Holder {
		TextView studentName;
		TextView studentId;
		TextView studentScore;
	}
}
